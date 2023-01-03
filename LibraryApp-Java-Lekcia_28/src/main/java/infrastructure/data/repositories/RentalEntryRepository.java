package infrastructure.data.repositories;

import core.abstractions.repositories.IRentalEntryRepository;
import core.entities.*;
import core.enums.eTitleType;
import infrastructure.data.DatabaseContext;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RentalEntryRepository extends RowMapper implements IRentalEntryRepository {

    @Override
    public ArrayList<RentalEntry> getAll() {
//        String selectStmt = "SELECT * FROM librarydb.rental_entries";
        String selectStmt = "SELECT * FROM librarydb.rental_entries\n" +
                "JOIN librarydb.members ON rental_entries.MemberId = members.Id\n" +
                "JOIN librarydb.title ON rental_entries.TitleId = title.Id " +
                "WHERE ReturnDate = 'null';";

        try {
            ResultSet rsRentalEntries = DatabaseContext.dbExecuteQuery(selectStmt);
            return getAllRentalEntries(rsRentalEntries);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    private ArrayList<RentalEntry> getAllRentalEntries(ResultSet rs) throws SQLException {
        ArrayList<RentalEntry> rentalEntries = new ArrayList<>();

        RentalEntry rentalEntry = null;
        while (rs.next()) {
            rentalEntry = new RentalEntry();
            rentalEntry.setId(rs.getInt("Id"));
            rentalEntry.setMemberId(rs.getInt("MemberId"));
            rentalEntry.setMember(getMemberFromResultSet(rs));
            rentalEntry.setRentedDate(rs.getString("RentedDate"));
            rentalEntry.setReturnDate(rs.getString("ReturnDate"));
            rentalEntry.setTitleId(rs.getInt("TitleId"));

            eTitleType titleType = rs.getInt("TitleType") == 1 ? eTitleType.book : eTitleType.dvd;
            rentalEntry.setTitleType(titleType);

            rentalEntry.setTitle(getTitleFromResultSet(rs, titleType));
            rentalEntry.setTimesProlonged(rs.getInt("TimesProlonged"));

            rentalEntries.add(rentalEntry);
        }
        return rentalEntries;
    }

    @Override
    public RentalEntry create(RentalEntry entity) {
        String insertStmt =
                "INSERT INTO `librarydb`.`rental_entries` \n" +
                        "(`MemberId`, `RentedDate`, `ReturnDate`, `TitleId`, `TimesProlonged`, `TitleType`) \n" +
                        "VALUES " +
                        "(" + entity.getMemberId() + ", " +
                        "'" + entity.getRentedDate() + "'" + ", " +
                        "'" + entity.getReturnDate() + "'" + ", " +
                        entity.getTitleId() + ", " +
                        entity.getTimesProlonged() + ", " +
                        entity.getTitleType().ordinal() + ");";

        try {
            DatabaseContext.dbExecuteUpdate(insertStmt);
            return entity;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public RentalEntry delete(int id) {
        return null;
    }

    @Override
    public RentalEntry getById(int id) {
        return null;
    }

    @Override
    public void update(int id, RentalEntry entity) {
        String updateStmt =
                "UPDATE librarydb.rental_entries " +
                        "SET ReturnDate = " + "'" + entity.getReturnDate() + "'" +
                        ", TimesProlonged = " + entity.getTimesProlonged() +
                        " WHERE Id = " + id + ";";

        try {
            DatabaseContext.dbExecuteUpdate(updateStmt);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<RentalEntry> getRentalEntriesByReturnDate() {
//        String selectStmt = "SELECT * FROM librarydb.rental_entries WHERE ReturnDate is null;";

        String selectStmt = "SELECT * FROM librarydb.rental_entries\n" +
                "JOIN librarydb.members ON rental_entries.MemberId = members.Id\n" +
                "JOIN librarydb.title ON rental_entries.TitleId = title.Id\n" +
                "WHERE ReturnDate = 'null';";

        try {
            ResultSet rsRentalEntries = DatabaseContext.dbExecuteQuery(selectStmt);
            return getAllRentalEntries(rsRentalEntries);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();

    }

    @Override
    public ArrayList<RentalEntry> getUnreturnedTitleByMemberId(int memberId) {
        String selectStmt = "SELECT * FROM librarydb.rental_entries\n" +
                "JOIN librarydb.members ON rental_entries.MemberId = members.Id\n" +
                "JOIN librarydb.title ON rental_entries.TitleId = title.Id\n" +
                "WHERE ReturnDate = 'null' AND MemberId = " + memberId + ";";

        try {
            ResultSet rsRentalEntries = DatabaseContext.dbExecuteQuery(selectStmt);
            return getAllRentalEntries(rsRentalEntries);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }
}
