package core.service;

import core.abstractions.services.IQueueService;
import core.entities.*;
import core.enums.eTitleType;
import infrastructure.data.DatabaseContext;
import infrastructure.data.repositories.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class QueueService extends RowMapper implements IQueueService {

    @Override
    public void addToQueue(Title title, Member member) {
        String insertStmt = "INSERT INTO \n" +
                "`librarydb`.`queue_items` (`MemberId`, `TimeAdded`, `TitleId`, `isResolved`) \n" +
                "VALUES (" +
                member.getId() + ", " +
                "'" + LocalDate.now() + "', " +
                title.getId() + ", " +
                0 + ");";

        try {
            DatabaseContext.dbExecuteUpdate(insertStmt);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void markAsResolved(QueueItem item) {

    }

    @Override
    public ArrayList<QueueItem> getAllItems() {
        String selectStmt = "SELECT * FROM librarydb.queue_items\n" +
                "JOIN librarydb.members ON librarydb.queue_items.MemberId = members.Id\n" +
                "JOIN librarydb.title ON librarydb.queue_items.TitleId = title.Id;";

        try {
            ResultSet rsRentalEntries = DatabaseContext.dbExecuteQuery(selectStmt);
            return getAllQueueItems(rsRentalEntries);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    private ArrayList<QueueItem> getAllQueueItems(ResultSet rs) throws SQLException {
        ArrayList<QueueItem> queueItems = new ArrayList<>();

        QueueItem queueItem = null;
        while (rs.next()) {
            queueItem = new QueueItem();
            queueItem.setId(rs.getInt("Id"));
            queueItem.setMemberId(rs.getInt("MemberId"));

            eTitleType titleType = Objects.equals(rs.getString("Discriminator"), "book") ? eTitleType.book : eTitleType.dvd;
            queueItem.setDiscriminator(titleType);

            queueItem.setTitle(getTitleFromResultSet(rs, titleType));
            queueItem.setMember(getMemberFromResultSet(rs));
            queueItem.setResolved(rs.getBoolean("isResolved"));

            queueItems.add(queueItem);
        }

        return queueItems;
    }

//    @Override
//    public void onTitleReturned(Object sender, TitleReturnedEventArgs args) {
//
//    }

    @Override
    public ArrayList<QueueItem> getItems(int titleId, boolean onlyPendingItems) {
        String selectStmt;
        if (onlyPendingItems) {
            selectStmt =
                    "SELECT * FROM librarydb.queue_items WHERE TitleId = " + titleId + " AND isResolved = false;";
        } else {
            selectStmt = "SELECT * FROM librarydb.queue_items WHERE TitleId = " + titleId + ";";
        }

        try {
            ResultSet rs = DatabaseContext.dbExecuteQuery(selectStmt);
            return getItemsFromResultSet(rs);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    private ArrayList<QueueItem> getItemsFromResultSet(ResultSet rs) throws SQLException {
        ArrayList<QueueItem> queueItems = new ArrayList<>();
        while (rs.next()) {
            QueueItem queueItem = new QueueItem();
            queueItem.setId(rs.getInt("Id"));
            queueItem.setMemberId(rs.getInt("MemberId"));
            queueItem.setTimeAdded(rs.getString("TimeAdded"));
            queueItem.setTitleId(rs.getInt("TitleId"));
            queueItem.setResolved(rs.getBoolean("isResolved"));
            queueItems.add(queueItem);
        }
        return queueItems;
    }
}
