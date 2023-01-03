package core.abstractions.services;

import core.entities.Member;
import core.entities.RentalEntry;
import core.entities.Title;

import java.math.BigDecimal;
import java.util.ArrayList;

public interface IRentalEntryService {
    ArrayList<RentalEntry> getAllEntries();

    RentalEntry rent(Title title, Member member);

    RentalEntry returnEntry(RentalEntry entry);

    ArrayList<RentalEntry> getByUnreturnedMember(int memberId);

    double calculateReturnalFee(RentalEntry entry);

    ArrayList<RentalEntry> getRentalEntriesPastDue();

    boolean isEntryPastDue(RentalEntry entry);

    boolean canRent(Member member, Title title);

    boolean canProlongRental(RentalEntry entry);

    boolean prolongRental(RentalEntry entry);

}
