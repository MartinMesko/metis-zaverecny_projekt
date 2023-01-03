package infrastructure.data.repositories;

import core.abstractions.repositories.IQueueItemRepository;
import core.entities.QueueItem;
import core.enums.eTitleType;
import infrastructure.data.DatabaseContext;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

import static utils.date_utils.DateUtils.nowDate;

public class QueueItemRepository extends RowMapper implements IQueueItemRepository {

    @Override
    public ArrayList<QueueItem> getQueueItems(int titleId, boolean onlyPendingItems) {
        String selectStmt;
        if (onlyPendingItems) {
            selectStmt = "SELECT * FROM librarydb.queue_items WHERE TitleId = " + titleId + " AND isResolved = false;";
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

    @Override
    public ArrayList<QueueItem> getAll() {
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

        while (rs.next()) {
            QueueItem queueItem = getQueueItemAndMemberAndTitleFromResultSet(rs);
            queueItems.add(queueItem);
        }

        return queueItems;
    }

    @Override
    public QueueItem create(QueueItem entity) {
        String insertStmt = "INSERT INTO \n" + "`librarydb`.`queue_items` (`MemberId`, `TimeAdded`, `TitleId`, `isResolved`) \n" +
                "VALUES (" + entity.getMember().getId() + ", " + "'" + nowDate + "', " + entity.getTitle().getId() + ", " + 0 + ");";

        try {
            DatabaseContext.dbExecuteUpdate(insertStmt);
            return entity;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public QueueItem delete(int id) {
        String deleteStmt = "DELETE FROM librarydb.queue_items WHERE id = " + id;

        var entity = this.getById(id);
        if (entity == null) {
            return null;
        }

        try {
            DatabaseContext.dbExecuteUpdate(deleteStmt);
            return entity;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public QueueItem getById(int id) {
        String selectStmt = "SELECT * FROM librarydb.queue_items WHERE Id = " + id + ";";

        try {
            ResultSet resultSet = DatabaseContext.dbExecuteQuery(selectStmt);
            return getQueueItemFromResultSet(resultSet);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    private QueueItem getQueueItemFromResultSet(ResultSet rs) throws SQLException {
        QueueItem queueItem = null;
        if (rs.next()) {
            queueItem = new QueueItem();
            queueItem.setId(rs.getInt("Id"));
            queueItem.setMemberId(rs.getInt("MemberId"));
            queueItem.setTimeAdded(rs.getString("TimeAdded"));
            queueItem.setTitleId(rs.getInt("TitleId"));
            queueItem.setResolved(rs.getBoolean("isResolved"));
        }
        return queueItem;
    }

    @Override
    public void update(int id, QueueItem entity) {
        String updateStmt = "UPDATE librarydb.queue_items " +
                "SET isResolved = " + entity.isResolved() +
                " WHERE Id = " + id + ";";

        try {
            DatabaseContext.dbExecuteUpdate(updateStmt);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public QueueItem getQueueItemByMemberIdWhereIsResolved(int id, boolean isResolved) {

        int resolved = isResolved ? 1 : 0;

        String selectStmt = "SELECT * FROM librarydb.queue_items\n" +
                "JOIN librarydb.members ON librarydb.queue_items.MemberId = members.Id\n" +
                "JOIN librarydb.title ON librarydb.queue_items.TitleId = title.Id \n" +
                "WHERE isResolved = " + resolved + " AND TitleId = " + id + ";";

        try {
            ResultSet rs = DatabaseContext.dbExecuteQuery(selectStmt);
            if (rs.next()) {
                return getQueueItemAndMemberAndTitleFromResultSet(rs);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    private QueueItem getQueueItemAndMemberAndTitleFromResultSet(ResultSet rs) throws SQLException {
        QueueItem queueItem = null;
        queueItem = new QueueItem();
        queueItem.setId(rs.getInt("Id"));
        queueItem.setMemberId(rs.getInt("MemberId"));

        eTitleType titleType = Objects.equals(rs.getString("Discriminator"), "book") ? eTitleType.book : eTitleType.dvd;

        queueItem.setDiscriminator(titleType);
        queueItem.setTitle(getTitleFromResultSet(rs, titleType));
        queueItem.setMember(getMemberFromResultSet(rs));
        queueItem.setResolved(rs.getBoolean("isResolved"));
        return queueItem;
    }

}
