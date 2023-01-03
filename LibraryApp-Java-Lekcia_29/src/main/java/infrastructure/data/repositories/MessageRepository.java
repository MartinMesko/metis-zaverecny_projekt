package infrastructure.data.repositories;

import core.abstractions.repositories.IMessageRepository;
import core.entities.Message;
import infrastructure.data.DatabaseContext;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MessageRepository extends RowMapper implements IMessageRepository {

    @Override
    public ArrayList<Message> getMembersById(int userid) {
        String selectStmt = "SELECT * FROM librarydb.message\n" +
                "JOIN librarydb.members ON librarydb.message.MemberId = members.Id \n" +
                "WHERE MemberId = " + userid + ";";

        try {
            ResultSet rs = DatabaseContext.dbExecuteQuery(selectStmt);
            return getAllMemberMessages(rs);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    private ArrayList<Message> getAllMemberMessages(ResultSet rs) throws SQLException {
        ArrayList<Message> messages = new ArrayList<>();

        Message message = null;
        while (rs.next()) {
            message = new Message();
            message.setId(rs.getInt("Id"));
            message.setMemberId(rs.getInt("MemberId"));
            message.setMember(getMemberFromResultSet(rs));
            message.setMessageContext(rs.getString("MessageContext"));
            message.setMessageSubject(rs.getString("MessageSubject"));
            message.setSendData(rs.getString("SendData"));
            messages.add(message);
        }

        return messages;
    }

    @Override
    public ArrayList<Message> getAll() {
        String selectStmt = "SELECT * FROM librarydb.message;";

        try {
            ResultSet rs = DatabaseContext.dbExecuteQuery(selectStmt);
            return getAllMessages(rs);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    private ArrayList<Message> getAllMessages(ResultSet rs) throws SQLException {
        ArrayList<Message> messages = new ArrayList<>();
        while (rs.next()) {
            messages.add(getMessageFromResultSet(rs));
        }
        return messages;
    }

    @Override
    public Message create(Message entity) {
        String insertStmt = "INSERT INTO \n" +
                "`librarydb`.`message` (`MemberId`, `MessageContext`, `MessageSubject`, `SendData`) \n" +
                "VALUES " +
                "(" +
                entity.getMemberId() + ", " +
                "'" + entity.getMessageContext() + "'" + ", " +
                "'" + entity.getMessageSubject() + "'" + ", " +
                "'" + entity.getSendData() + "'" +
                ");";

        try {
            DatabaseContext.dbExecuteUpdate(insertStmt);
            return entity;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Message delete(int id) {
        String deleteStmt = "DELETE FROM librarydb.message WHERE Id = " + id + ";";

        var message = this.getById(id);
        if (message == null) {
            return null;
        }

        try {
            DatabaseContext.dbExecuteUpdate(deleteStmt);
            return message;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Message getById(int id) {
        String selectStmt = "SELECT * FROM librarydb.message WHERE Id = " + id + ";";

        try {
            ResultSet rs = DatabaseContext.dbExecuteQuery(selectStmt);
            return getMessageFromResultSet(rs);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    private Message getMessageFromResultSet(ResultSet rs) throws SQLException {
        Message message = new Message();
        message.setId(rs.getInt("Id"));
        message.setMemberId(rs.getInt("MemberId"));
        message.setMessageContext(rs.getString("MessageContext"));
        message.setMessageSubject(rs.getString("MessageSubject"));
        message.setSendData(rs.getString("SendData"));
        return message;
    }

    @Override
    public void update(int id, Message entity) {

    }
}
