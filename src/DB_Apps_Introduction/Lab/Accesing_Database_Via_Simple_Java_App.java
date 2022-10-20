package DB_Apps_Introduction.Lab;

import java.sql.*;
import java.util.Scanner;

public class Accesing_Database_Via_Simple_Java_App {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/soft_uni", "root", "root");

        PreparedStatement stmt = connection.prepareStatement("SELECT first_name, last_name FROM employees WHERE salary > ?");
        String salary = scanner.nextLine();
        stmt.setDouble(1, Double.parseDouble(salary));

        ResultSet rs =stmt.executeQuery();

        while (rs.next()){
            System.out.println(rs.getString("first_name") + " " + rs.getString("last_name"));
        }
    }
}
