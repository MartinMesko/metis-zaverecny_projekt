package infrastructure.data.repositories;

import core.abstractions.repositories.IQueueItemRepository;
import core.entities.QueueItem;

import java.util.ArrayList;

public class QueueItemRepository implements IQueueItemRepository {

    public final QueueItem create(QueueItem entity) {
        return null;
    }

    public final QueueItem delete(int id) {
        return null;

    }

    public final ArrayList<QueueItem> getAll() {
        return null;
    }

    public final QueueItem getById(int id) {
        return null;
    }

    public final void update(int id, QueueItem entity) {

    }

//    public final java.lang.Iterable<QueueItem> find(Expression<tangible.Func1Param<QueueItem, Boolean>> expression) {
//        return this._context.QueueItems.Where(expression).Include(i -> i.Title).Include(i -> i.Member);
//    }

}

//Helper class added by C# to Java Converter:
//
//package tangible;
//
//@FunctionalInterface
//public interface Func1Param<T, TResult> {
//    TResult invoke(T t);
//}
