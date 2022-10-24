package ORM_Fundamentals.orm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnector {
    private static Connection connection;

    public static void createConnection() throws SQLException{
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/custom-orm", "root", "root");
    }

    public static Connection getConnection(){
        return connection;
    }
}
