package core.service;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import core.abstractions.repositories.IMessageRepository;
import core.abstractions.services.IMessagingService;
import core.entities.Message;

import java.util.ArrayList;

import static utils.date_utils.DateUtils.nowDate;

public class MessagingService implements IMessagingService {
    @Named("msgRepo")
    @Inject
    private final IMessageRepository _messageRepository;

    @Inject
    public MessagingService(@Named("msgRepo") IMessageRepository repo) {
        _messageRepository = repo;
    }

    public final ArrayList<Message> getMessagesForUser(int userid) {
        return this._messageRepository.getMembersById(userid);
    }

    public final void sendMessage(int memberId, String subject, String message) {
        var msg = new Message();
        msg.setMemberId(memberId);
        msg.setMessageSubject(subject);
        msg.setMessageContext(message);
        msg.setSendData(nowDate);

        this._messageRepository.create(msg);
    }
}
