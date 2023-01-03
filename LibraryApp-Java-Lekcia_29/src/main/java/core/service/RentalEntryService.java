package core.service;

import UI.helpers.OutputHelper;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import core.abstractions.repositories.IBookRepository;
import core.abstractions.repositories.IDvdRepository;
import core.abstractions.repositories.IRentalEntryRepository;
import core.abstractions.services.IQueueService;
import core.abstractions.services.IRentalEntryService;
import core.entities.*;
import core.enums.eTitleCountUpdate;
import core.enums.eTitleType;
import utils.eventHandler.EventProducer;
import utils.eventHandler.IEvent;
import core.events.eventArgs.TitleReturnedEventArgs;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static core.enums.eTitleCountUpdate.remove;
import static utils.date_utils.DateUtils.*;

public class RentalEntryService implements IRentalEntryService {

    public static final int MAX_DAYS_TO_RENT_BOOK = 21;
    public static final int MAX_DAYS_TO_RENT_DVD = 7;
    public static final double DAILY_PENALTY_FEE_BOOK = 0.1;
    public static final double DAILY_PENALTY_FEE_DVD = 1.0;

    private HashMap<eTitleType, Integer> dayToRent;
    private HashMap<eTitleType, Double> dailyPenaltyFee;

    @Named("rentalRepo")
    @Inject
    private final IRentalEntryRepository _rentalEntryRepository;
    @Named("book")
    @Inject
    private final IBookRepository _bookRepository;
    @Named("dvd")
    @Inject
    private final IDvdRepository _dvdRepository;
    @Named("queue")
    @Inject
    private final IQueueService _queueService;

    @Inject
    public RentalEntryService(@Named("rentalRepo") IRentalEntryRepository rentalEntryRepository, @Named("book") IBookRepository bookRepository,
                              @Named("dvd") IDvdRepository dvdRepository, @Named("queue") IQueueService queueService) {
        this._rentalEntryRepository = rentalEntryRepository;
        this._bookRepository = bookRepository;
        this._dvdRepository = dvdRepository;
        this._queueService = queueService;

        initializeConstants();
    }

    private void initializeConstants() {
        this.dayToRent = new HashMap<eTitleType, Integer>(
                Map.ofEntries(Map.entry(eTitleType.book, MAX_DAYS_TO_RENT_BOOK), Map.entry(eTitleType.dvd, MAX_DAYS_TO_RENT_DVD))
        );

        this.dailyPenaltyFee = new HashMap<eTitleType, Double>(
                Map.ofEntries(Map.entry(eTitleType.book, DAILY_PENALTY_FEE_BOOK), Map.entry(eTitleType.dvd, DAILY_PENALTY_FEE_DVD))
        );
    }

    private void initializeEventSubscription(RentalEntry entry) {
        EventProducer eventProducer = new EventProducer();
        IEvent<TitleReturnedEventArgs> eventSink = _queueService::onTitleReturned;
        eventProducer.myEvent.subscribe(eventSink);
        eventProducer.onMyEvent(new TitleReturnedEventArgs(entry.getTitle()));
        eventProducer.myEvent.unSubscribe(eventSink);
        eventProducer.myEvent.close();
    }

    @Override
    public ArrayList<RentalEntry> getAllEntries() {
        return this._rentalEntryRepository.getAll();
    }

    @Override
    public RentalEntry rent(Title title, Member member) {
        updateAvailableTitleCopies(title, remove);

        var entry = new RentalEntry();
        entry.setTitleId(title.getId());
        entry.setMember(member);
        entry.setMemberId(member.getId());
        entry.setTitleType(title instanceof Book ? eTitleType.book : eTitleType.dvd);
        entry.setTitle(title);
        entry.setRentedDate(nowDate);
        entry.setTimesProlonged(0);

        return _rentalEntryRepository.create(entry);
    }

    private void updateAvailableTitleCopies(Title title, eTitleCountUpdate action) {
        var isBook = title instanceof Book;
        Title entity = isBook ? _bookRepository.getById(title.getId()) : _dvdRepository.getById(title.getId());

        if (action == eTitleCountUpdate.add) {
            entity.setAvailableCopies(entity.getAvailableCopies() + 1);
        } else {
            entity.setAvailableCopies(entity.getAvailableCopies() - 1);
        }

        if (isBook) {
            _bookRepository.update(title.getId(), (Book) entity);
        } else {
            _dvdRepository.update(title.getId(), (Dvd) entity);
        }
    }

    @Override
    public void returnEntry(RentalEntry entry) {
        updateAvailableTitleCopies(entry.getTitle(), eTitleCountUpdate.add);

        entry.setReturnDate(nowDate);
        this._rentalEntryRepository.update(entry.getId(), entry);

        initializeEventSubscription(entry);
    }

    @Override
    public ArrayList<RentalEntry> getByUnreturnedMember(int memberId) {
        return this._rentalEntryRepository.getUnreturnedTitleByMemberId(memberId);
    }

    @Override
    public double calculateReturnalFee(RentalEntry entry) {
        if (!isEntryPastDue(entry)) {
            return 0;
        }

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_FORMAT_DD_MM_YYYY);

        LocalDate d1 = LocalDate.parse(entry.getRentedDate(), dtf);
        LocalDate d2 = LocalDate.parse(nowDate, dtf);

        var dateDifference = d1.datesUntil(d2).count();

        return dateDifference * dailyPenaltyFee.get(entry.getTitleType());
    }

    @Override
    public ArrayList<RentalEntry> getRentalEntriesPastDue() {
        var notReturnedEntries = this._rentalEntryRepository.getRentalEntriesByReturnDate();

        var pastDueEntries = new ArrayList<RentalEntry>();

        for (var entry : notReturnedEntries) {
            if (isEntryPastDue(entry)) {
                pastDueEntries.add(entry);
            }
        }

        return pastDueEntries;
    }

    @Override
    public boolean isEntryPastDue(RentalEntry entry) {
        var daysToRent = dayToRent.get(entry.getTitleType());
        var daysAddedToRentedDate = daysToRent + (daysToRent + entry.getTimesProlonged());

        return parseStringDateIntoDate(addDaysToRentedDay(entry, daysAddedToRentedDate)).before(parseStringDateIntoDate(nowDate));
    }

    @Override
    public boolean canRent(Member member, Title title) {
        var rentedTitles = getByUnreturnedMember(member.getId());

        for (var rentedTitle : rentedTitles) {
            if (rentedTitle.getTitleId() == title.getId()) {
                OutputHelper.writeLine("Title " + title.getName() + " is already rented.");
                return false;
            }
        }

        if (rentedTitles.size() >= 2) {
            OutputHelper.writeLine("You've already rented 2 titles. You cannot rent anymore!");
            return false;
        }

        return true;
    }

    @Override
    public boolean canProlongRental(RentalEntry entry) {
        var queueItem = _queueService.getItems(entry.getTitleId(), true);

        if (queueItem.size() > 0) {
            OutputHelper.writeLine("Item cannot be prolongued - another member is waiting for it");
            return false;
        }

        if (entry.getTimesProlonged() >= 2) {
            OutputHelper.writeLine("Item already prolongued 2 times, which is maximum possible!");
            return false;
        }

        return true;
    }

    @Override
    public void prolongRental(RentalEntry entry) {
        entry.setTimesProlonged(entry.getTimesProlonged() + 1);
        this._rentalEntryRepository.update(entry.getId(), entry);
    }

}
