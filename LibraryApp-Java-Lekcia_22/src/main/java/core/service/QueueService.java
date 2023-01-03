package core.service;

import core.abstractions.services.IQueueService;
import core.entities.Member;
import core.entities.QueueItem;
import core.entities.Title;
import infrastructure.data.DatabaseContext;

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
                null + ");";

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
        return null;
    }
}
