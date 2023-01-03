package core.service;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import core.abstractions.repositories.IQueueItemRepository;
import core.abstractions.services.IMessagingService;
import core.abstractions.services.IQueueService;
import core.entities.*;
import core.events.eventArgs.TitleReturnedEventArgs;

import java.util.ArrayList;

public class QueueService implements IQueueService {
    @Named("queueItem")
    @Inject
    private final IQueueItemRepository _queueItemRepository;
    @Named("msgService")
    @Inject
    private final IMessagingService _messagingService;

    @Inject
    public QueueService(@Named("queueItem") IQueueItemRepository _repository, @Named("msgService") IMessagingService _messagingService) {
        this._queueItemRepository = _repository;
        this._messagingService = _messagingService;
    }

    @Override
    public void addToQueue(Title title, Member member) {
        var queueItem = new QueueItem();
        queueItem.setTitle(title);
        queueItem.setMember(member);
        this._queueItemRepository.create(queueItem);
    }

    @Override
    public void markAsResolved(QueueItem item) {
        item.setResolved(true);
        this._queueItemRepository.update(item.getId(), item);
    }

    @Override
    public ArrayList<QueueItem> getAllItems() {
        return this._queueItemRepository.getAll();
    }

    @Override
    public void onTitleReturned(Object source, TitleReturnedEventArgs eventArgs) {
        var queueItem =
                this._queueItemRepository.getQueueItemByMemberIdWhereIsResolved(eventArgs.getTitle().getId(), false);

        if (queueItem != null ) {
            notifyMember(queueItem);
            markAsResolved(queueItem);
        }
    }

    private void notifyMember(QueueItem item) {
        var subject = "Title " + item.getTitle().getName() + " is available now!";
        var message = "Dear Mr./Ms. " + item.getMember().getLastName() + "\n the title " +
                item.getTitle().getName() + " is available for rent!\n" +
                " Best regards, The Library Team <3";

        this._messagingService.sendMessage(item.getMemberId(), subject, message);
    }

    @Override
    public ArrayList<QueueItem> getItems(int titleId, boolean onlyPendingItems) {
        return this._queueItemRepository.getQueueItems(titleId, onlyPendingItems);
    }

}
