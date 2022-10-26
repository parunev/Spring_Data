package ORM_Fundamentals.orm;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

public interface DbContext<E> {
    boolean persist(E entity) throws IllegalAccessException, SQLException;
    boolean delete(E toDelete) throws IllegalAccessException, SQLException;

    Iterable<E> find(Class<E> table) throws SQLException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException;
    Iterable<E> find(Class<E> table, String where) throws SQLException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException;
    E findFirst(Class<E> table) throws SQLException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException;
    E findFirst(Class<E> table, String where) throws SQLException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException;
}
