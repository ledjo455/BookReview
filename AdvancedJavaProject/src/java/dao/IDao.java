
package dao;

import java.util.List;

public interface IDao<T> {
    public List<T> findAll();
    public T find(Object id);
    public void create(T entity);
    public void update(T entity);
    public void delete(T entity);
}
