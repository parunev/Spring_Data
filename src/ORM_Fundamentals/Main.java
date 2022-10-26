package ORM_Fundamentals;

import ORM_Fundamentals.entities.User;
import ORM_Fundamentals.orm.EntityManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

import static ORM_Fundamentals.orm.MyConnector.createConnection;
import static ORM_Fundamentals.orm.MyConnector.getConnection;

public class Main {
    public static void main(String[] args) throws SQLException, IllegalAccessException {
        createConnection();
        Connection connection = getConnection();
        EntityManager<User> userEntityManager = new EntityManager<>(connection);

        Scanner scanner = new Scanner(System.in);

        String username = scanner.nextLine();
        int age = Integer.parseInt(scanner.nextLine());

        User user = new User(username, age, LocalDate.now());

        userEntityManager.persist(user);
    }
}
