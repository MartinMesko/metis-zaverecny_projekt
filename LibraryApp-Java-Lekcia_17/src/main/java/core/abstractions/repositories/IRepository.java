package core.abstractions.repositories;

import core.entities.Book;

import java.util.ArrayList;

public interface IRepository<T> {

    ArrayList<T> getAll();

    T create(T entity);

}
