package core.abstractions.repositories;

import java.util.ArrayList;

public interface IRepository<T> {

    ArrayList<T> getAll();

}
