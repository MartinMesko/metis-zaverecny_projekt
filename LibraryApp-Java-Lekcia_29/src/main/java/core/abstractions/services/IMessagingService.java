package core.abstractions.services;

import core.entities.Message;

import java.util.ArrayList;

public interface IMessagingService {

    void sendMessage(int memberId, String subject, String message);

    ArrayList<Message> getMessagesForUser(int userId);

}
