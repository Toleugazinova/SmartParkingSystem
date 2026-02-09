package DataAccessComponent.interfaces;

import java.util.List;

public interface IRepository<T> {
    List<T> getAll();
    T findById(int id);
}
