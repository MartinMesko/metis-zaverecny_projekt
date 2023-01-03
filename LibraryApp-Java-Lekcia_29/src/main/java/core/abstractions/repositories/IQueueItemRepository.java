package core.abstractions.repositories;

import core.entities.QueueItem;

import java.util.ArrayList;

public interface IQueueItemRepository extends IRepository<QueueItem> {

    ArrayList<QueueItem> getQueueItems(int titleId, boolean onlyPendingItems);

    QueueItem getQueueItemByMemberIdWhereIsResolved(int id, boolean isResolved);
}
