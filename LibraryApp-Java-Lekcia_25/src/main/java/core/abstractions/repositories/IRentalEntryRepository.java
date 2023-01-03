package core.abstractions.repositories;

import core.entities.RentalEntry;

import java.util.ArrayList;

public interface IRentalEntryRepository extends IRepository<RentalEntry> {
    ArrayList<RentalEntry> getRentalEntriesByReturnDate();

    ArrayList<RentalEntry> getUnreturnedTitleByMemberId(int memberId);
}
