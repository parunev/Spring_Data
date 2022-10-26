package ORM_Fundamentals;

import ORM_Fundamentals.entities.Address;
import ORM_Fundamentals.entities.User;
import ORM_Fundamentals.orm.EntityManager;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

import static ORM_Fundamentals.orm.MyConnector.createConnection;
import static ORM_Fundamentals.orm.MyConnector.getConnection;

public class Main {
    public static void main(String[] args) throws SQLException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
        createConnection();
        Connection connection = getConnection();

        EntityManager<User> userEntityManager = new EntityManager<>(connection);
        EntityManager<Address> addressEntityManager = new EntityManager<>(connection);

        User user = new User("Boriana", 32, LocalDate.now());
        new Scanner(System.in);

    }
}
