package core.service;

import core.abstractions.services.IQueueService;
import core.entities.Member;
import core.entities.QueueItem;
import core.entities.Title;
import infrastructure.data.DatabaseContext;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class QueueService implements IQueueService {

    @Override
    public void addToQueue(Title title, Member member) {
        String insertStmt = "INSERT INTO \n" +
                "`librarydb`.`queue_items` (`MemberId`, `TimeAdded`, `TitleId`, `isResolved`) \n" +
                "VALUES " +
                "(" +
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
        return new ArrayList<>();
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
