package UI.pages.Rentals;


import UI.Application;
import UI.UIElements.Menu;
import UI.base.MenuPageBase;
import UI.helpers.InputHelper;
import UI.helpers.OutputHelper;
import core.abstractions.repositories.IBookRepository;
import core.abstractions.repositories.IDvdRepository;
import core.abstractions.repositories.IMemberRepository;
import core.abstractions.services.IQueueService;
import core.abstractions.services.IRentalEntryService;
import core.entities.Book;
import core.entities.Member;
import core.entities.Title;

import java.util.ArrayList;

public class RentATitlePage extends MenuPageBase {

    private static final String PAGE_HEADER = "Rent a title";

    private final IRentalEntryService _rentalEntryService;
    private final IQueueService _queueService;

    private final IMemberRepository _memberRepository;
    private final IDvdRepository _dvdRepository;
    private final IBookRepository _bookRepository;

    private final Menu _chooseTitleMenu = new Menu();
    private final Menu _chooseMemberMenu = new Menu();

    private Title _choosenTitle;
    private Member _choosenMember;

    public RentATitlePage(Application app) {
        super(PAGE_HEADER, app);

        this._rentalEntryService = app.getCoreServices().getIRentalEntryService();
        this._queueService = app.getCoreServices().getIQueueService();
        this._memberRepository = app.getInfraServices().getIMemberRepository();
        this._dvdRepository = app.getInfraServices().getIDvdRepository();
        this._bookRepository = app.getInfraServices().getIBookRepository();

        initializeMenu();
    }


    private void initializeMenu() {
        var members = getAllMembers();

        for (int index = 0; index < members.size(); index++) {
            var member = members.get(index);
            _chooseMemberMenu.add(index + 1, member.toString(), () -> this._choosenMember = member);
        }

        var titles = getAllTitles();

        for (int index = 0; index < titles.size(); index++) {
            var title = titles.get(index);
            _chooseTitleMenu.add(index + 1, title.toString(), () -> this._choosenTitle = title);
        }
    }

    @Override
    public void display() {
        super.display();

        OutputHelper.writeLine("Choose a member to rent to: ");
        this._chooseMemberMenu.display();

//        Console.Clear();

        OutputHelper.writeLine("Choose a title to rent to: ");
        this._chooseTitleMenu.display();

        this.rentATitle();

        InputHelper.readKey("Press any key to continue....");
        this.getApplication().navigateBack();
    }


    private ArrayList<Member> getAllMembers() {
        return _memberRepository.getAll();
    }

    private ArrayList<Title> getAllTitles() {
        var list = new ArrayList<Title>();

        list.addAll(this._dvdRepository.getAll());
        list.addAll(this._bookRepository.getAll());

        return list;
    }

    private void rentATitle() {
        var title = this._choosenTitle;
        var member = this._choosenMember;


        if (!_rentalEntryService.canRent(member, title)) {
            return;
        }

        //check title availibility
        if (!isTitleAvailable(title)) {
            showQueuePrompt(title, member);

            this.getApplication().navigateBack();
        }

        var result = _rentalEntryService.rent(title, member);

        if (result == null) {
            OutputHelper.writeLine("Title not rented ");
        } else {
            OutputHelper.writeLine("Title rented successfully. ");
        }
    }

    private boolean isTitleAvailable(Title title) {
        var isBook = title instanceof Book;
        return isBook ? this._bookRepository.isBookAvailable(title.getId()) : this._dvdRepository.isDvdAvailable(title.getId());
    }

    private void showQueuePrompt(Title title, Member member) {
        var menu = new Menu();

        menu.add(1, "Yes", () -> addToQueue(title, member));
        menu.add(2, "No", () -> {
            return;
        });

        OutputHelper.writeLine("Do you want to add your inquiry to the queue?");
        menu.display();
    }

    private void addToQueue(Title title, Member member) {
        this._queueService.addToQueue(title, member);
    }

}
