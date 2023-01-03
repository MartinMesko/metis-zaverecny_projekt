package core.abstractions.repositories;


import java.util.ArrayList;

public interface IRepository<T> {

    ArrayList<T> getAll();

    T create(T entity);

    T delete(int id);

    T getById(int id);

    void update(int id, T entity);
}
