package ORM_Fundamentals.orm;

import ORM_Fundamentals.annotations.Column;
import ORM_Fundamentals.annotations.Entity;
import ORM_Fundamentals.annotations.Id;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
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

    //TABLE CREATION

    //we create table
    public void doCreate(Class<E> entityClass) throws SQLException {
        String tableName = getTableName(entityClass);
        String query = String.format("CREATE TABLE %s (`id` INT PRIMARY KEY AUTO_INCREMENT, %s); ",
                tableName, getAlLFieldsAndDataTypes(entityClass));

        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.execute();
    }

    // we get the database table name
    private String getTableName(Class<?> aClass) {
        Entity[] annotationsByType = aClass.getAnnotationsByType(Entity.class);

        if (annotationsByType.length == 0) {
            throw new UnsupportedOperationException("Class must be Entity");
        }

        return annotationsByType[0].name();
    }

    //we get from "Address" class all the fields and data types we want to insert
    private String getAlLFieldsAndDataTypes(Class<E> entityClass) {
        return getEntityColumnFieldsWithoutId(entityClass)
                .stream()
                .map(field -> {
                    String fieldName = getSQLColumnName(field);
                    String sqlType = getSQLType(field.getType());

                    return fieldName + " " + sqlType;
                })
                .collect(Collectors.joining(","));
    }

    //we find column fields without id
    private List<Field> getEntityColumnFieldsWithoutId(Class<E> entityClass) {
        return Arrays.stream(entityClass.getDeclaredFields())
                .filter(f -> !f.isAnnotationPresent(Id.class))
                .filter(f -> f.isAnnotationPresent(Column.class))
                .collect(Collectors.toList());
    }

    // we get the wanted column name
    private String getSQLColumnName(Field field) {
        return field.getAnnotationsByType(Column.class)[0].name();
    }

    // we scan the model declared fields and map them to annotated names and MYSQL data types
    private String getSQLType(Class<?> type) {
        String sqlType = "";
        if (type == Integer.class || type == int.class) {
            sqlType = "INT";
        } else if (type == String.class) {
            sqlType = "VARCHAR(200)";
        } else if (type == LocalDate.class) {
            sqlType = "DATE";
        }

        return sqlType;
    }


    //QUERYING

    //alter - adding new model fields
    public void doAlter(Class<E> entity) throws SQLException{
        String tableName = getTableName(entity);
        String field_stmts = getAddColumnStatementsForNewFields(entity);

        String query = String.format("ALTER TABLE %s %s;",
                tableName, field_stmts);

        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.execute();
    }

    //we get
    private String getAddColumnStatementsForNewFields(Class<E> entity) throws SQLException {
        Set<String> sqlColumns = getSQLColumnNames(entity);
        List<Field> fields = getEntityColumnFieldsWithoutId(entity);

        List<String> allAddStatements = new ArrayList<>();
        for (Field field : fields) {
            String fieldName = getSQLColumnName(field);

            if (!sqlColumns.contains(fieldName)) {
                continue;
            }

            String sqlType = getSQLType(field.getType());

            String addStatement = String.format("ADD COLUMN %s %s", fieldName, sqlType);
            allAddStatements.add(addStatement);
        }

        return String.join(",", allAddStatements);
    }

    // we get all column names from sql database
    private Set<String> getSQLColumnNames(Class<E> entity) throws SQLException {
        String tableName = getTableName(entity);
        Field idColumn = getIdColumn(entity);
        String idColumnName = getSQLColumnName(idColumn);

        String schemaQuery = "SELECT COLUMN_NAME FROM information_schema.`COLUMNS` c" +
                " WHERE c.TABLE_SCHEMA = 'custom-orm'" +
                " AND COLUMN_NAME != '%s'" +
                " AND TABLE_NAME = '%s';";

        PreparedStatement statement = connection.prepareStatement(String.format(schemaQuery, tableName, idColumnName));

        ResultSet resultSet = statement.executeQuery();

        Set<String> result = new HashSet<>();
        while(resultSet.next()) {
            String columnName = resultSet.getString("COLUMN_NAME");

            result.add(columnName);
        }

        return result;
    }

    // we get column id
    private Field getIdColumn(Class<?> entity) {
        return Arrays.stream(entity.getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(Id.class))
                .findFirst()
                .orElseThrow(() ->
                        new UnsupportedOperationException("Entity missing an Id column"));
    }

    // we insert into database
    public boolean doInsert(E entity) throws SQLException, IllegalAccessException {
        String insertQuery = String.format("INSERT INTO %s (%s) VALUES (%s)",
                getTableName(entity.getClass()), getColumnsWithoutId(entity.getClass()), getColumnsValuesWithoutId(entity));

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

    // deleting records
    @Override
    public boolean delete(E toDelete) throws IllegalAccessException, SQLException {
        String tableName = getTableName(toDelete.getClass());

        Field idColumn = getIdColumn(toDelete.getClass());
        String idColumnName = getSQLColumnName(idColumn);
        Object idColumnValue = getFieldValue(toDelete, idColumn);

        String query = String.format("DELETE FROM %s WHERE %s = %s",
                tableName, idColumnName, idColumnValue);

        PreparedStatement statement = connection.prepareStatement(query);

        return statement.execute();
    }

    private Object getFieldValue(E entity, Field id) throws  IllegalAccessException{
        id.setAccessible(true);

        return id.get(entity);
    }

    private List<E> baseFind(Class<E> table, String where, String limit) throws SQLException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        String tableName = getTableName(table);

        String selectQuery = String.format("SELECT * FROM %s %s %s",
                tableName,
                where != null ? "WHERE " + where : "",
                limit != null ? limit : "");

        PreparedStatement statement = connection.prepareStatement(selectQuery);
        ResultSet resultSet = statement.executeQuery();

        List<E> result = new ArrayList<>();
        while (resultSet.next()) {
            E entity = table.getDeclaredConstructor().newInstance();
            fillEntity(table, resultSet, entity);

            result.add(entity);
        }

        return result;
    }

    private void fillEntity(Class<E> table, ResultSet resultSet, E entity) throws SQLException, IllegalAccessException {
        Field[] declaredFields = table.getDeclaredFields();

        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);
            fillFiled(declaredField, resultSet, entity);
        }
    }

    private void fillFiled(Field declaredField, ResultSet resultSet, E entity) throws SQLException, IllegalAccessException {
        Class<?> fieldType = declaredField.getType();
        String fieldName = getSQLColumnName(declaredField);

        if (fieldType == int.class || fieldType == Integer.class) {
            int value = resultSet.getInt(fieldName);

            declaredField.set(entity, value);
        } else if (fieldType == long.class || fieldType == Long.class) {
            long value = resultSet.getLong(fieldName);

            declaredField.set(entity, value);
        } else if (fieldType == LocalDate.class) {
            LocalDate value = LocalDate.parse(resultSet.getString(fieldName));

            declaredField.set(entity, value);
        } else {
            String value = resultSet.getString(fieldName);

            declaredField.set(entity, value);
        }
    }


    @Override
    public Iterable<E> find(Class<E> table) throws SQLException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        return find(table, null);
    }
    @Override
    public Iterable<E> find(Class<E> table, String where) throws SQLException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        return baseFind(table, where, null);
    }
    @Override
    public E findFirst(Class<E> table) throws SQLException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        return findFirst(table, null);
    }
    @Override
    public E findFirst(Class<E> table, String where) throws SQLException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        List<E> res = baseFind(table, where, "LIMIT 1");

        return res.get(0);
    }
}
