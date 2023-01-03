package core.abstractions.repositories;

import core.entities.Book;

public interface IBookRepository extends IRepository<Book> {

    boolean isBookAvailable(int id);

}
