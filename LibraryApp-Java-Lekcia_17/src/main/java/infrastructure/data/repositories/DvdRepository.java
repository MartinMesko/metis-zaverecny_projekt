package infrastructure.data.repositories;

import core.abstractions.repositories.IDvdRepository;
import core.entities.Dvd;
import infrastructure.data.DatabaseContext;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DvdRepository implements IDvdRepository {

    @Override
    public ArrayList<Dvd> getAll() {
        String selectStmt = "SELECT * FROM librarydb.dvd";

        try {
            ResultSet rsDvds = DatabaseContext.dbExecuteQuery(selectStmt);
            return getAllDvd(rsDvds);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    private ArrayList<Dvd> getAllDvd(ResultSet rs) throws SQLException {
        ArrayList<Dvd> dvds = new ArrayList<>();

        while(rs.next()) {
            Dvd dvd = new Dvd();
            dvd.setAuthor(rs.getString("Author"));
            dvd.setName(rs.getString("Name"));
            dvd.setAvailableCopies(rs.getInt("AvailableCopies"));
            dvd.setNumberOfChapters(rs.getInt("NumberOfChapters"));
            dvd.setNumberOfMinutes(rs.getInt("NumberOfMinutes"));
            dvds.add(dvd);
        }

        return dvds;
    }

    @Override
    public Dvd create(Dvd entity) {
        String insertDvdStmt = "" +
                "INSERT INTO librarydb.dvd " +
                "(Author, Name, AvailableCopies, NumberOfChapters, NumberOfMinutes) " +
                "VALUES (" + "'" + entity.getAuthor() + "'" + ", " + "'" + entity.getName() + "'" + ", " + entity.getAvailableCopies() + ", " + entity.getNumberOfChapters() + ", " +  entity.getNumberOfMinutes() + ")";
        try {
            DatabaseContext.dbExecuteUpdate(insertDvdStmt);
            return entity;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

}
