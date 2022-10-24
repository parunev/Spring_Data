package ORM_Fundamentals.orm;

import ORM_Fundamentals.annotations.Column;
import ORM_Fundamentals.annotations.Entity;
import ORM_Fundamentals.annotations.Id;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EntityManager<E> implements DbContext<E> {
    private Connection connection;

    public EntityManager(Connection connection){
        this.connection = connection;
    }

    private Field getId(Class<?> entity){
        return Arrays.stream(entity.getDeclaredFields()).filter(x -> x.isAnnotationPresent(Id.class))
                .findFirst()
                .orElseThrow(() -> new UnsupportedOperationException("Entity does not have primary key"));
    }

    @Override
    public boolean persist(E entity) throws IllegalAccessException, SQLException {
        Field column_id = getId(entity.getClass());
        column_id.setAccessible(true);
        Object value = column_id.get(entity);

        if (value == null || (long) value <= 0){
            return doInsert(entity);
        }

        return false;
    }

    // we insert into database
    private boolean doInsert(E entity) throws SQLException, IllegalAccessException {
        String tableName = getTableName(entity.getClass());
        String tableFields = getColumnsWithoutId(entity.getClass()); // username, age, registration
        String tableValues = getColumnsValuesWithoutId(entity);

        String insertQuery = String.format("INSERT INTO %s (%s) VALUES (%s)",
                tableName, tableFields, tableValues);

        return connection.prepareStatement(insertQuery).execute();
    }

    // we get the values without id from database
    private String getColumnsValuesWithoutId(E entity) throws IllegalAccessException {
        Class<?> aClass = entity.getClass();

        List<Field> fields = Arrays.stream(aClass.getDeclaredFields())
                .filter(f -> !f.isAnnotationPresent(Id.class))
                .filter(f -> f.isAnnotationPresent(Column.class)).toList();

        List<String> values = new ArrayList<>();

        for (Field field : fields) {
            field.setAccessible(true);
            Object o = field.get(entity);

            if (o instanceof String || o instanceof LocalDate) {
                values.add("'" + o + "'");
            } else {
                values.add(o.toString());
            }
        }

        return String.join(",", values);
    }

    // we get the columns without id from database
    private String getColumnsWithoutId(Class<?> aClass) throws IllegalArgumentException {
        return Arrays.stream(aClass.getDeclaredFields())
                .filter(f -> !f.isAnnotationPresent(Id.class))
                .filter(f -> f.isAnnotationPresent(Column.class))
                .map(f -> f.getAnnotationsByType(Column.class))
                .map(a -> a[0].name())
                .collect(Collectors.joining(","));

    }

    // we get the database table name
    private String getTableName(Class<?> aClass) {
        Entity[] annotationsByType = aClass.getAnnotationsByType(Entity.class);

        if (annotationsByType.length == 0) {
            throw new UnsupportedOperationException("Class must be Entity");
        }

        return annotationsByType[0].name();
    }


    @Override
    public Iterable<E> find(Class<E> table) {
        return null;
    }
    @Override
    public Iterable<E> find(Class<E> table, String where) {
        return null;
    }
    @Override
    public E findFirst(Class<E> table) {
        return null;
    }
    @Override
    public E findFirst(Class<E> table, String where) {
        return null;
    }
}
