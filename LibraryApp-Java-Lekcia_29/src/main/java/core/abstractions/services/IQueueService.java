package core.abstractions.services;


import core.entities.Member;
import core.entities.QueueItem;
import core.entities.Title;
import core.events.eventArgs.TitleReturnedEventArgs;

import java.util.ArrayList;

public interface IQueueService {
    void addToQueue(Title title, Member member);

    void markAsResolved(QueueItem item);

    ArrayList<QueueItem> getAllItems();

    void onTitleReturned(Object sender, TitleReturnedEventArgs args);

    ArrayList<QueueItem> getItems(int titleId, boolean onlyPendingItems);
}
