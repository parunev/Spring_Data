package DB_Apps_Introduction.Exercise;

import java.sql.*;
import java.util.Scanner;

public class Get_Minions_Names {
    public static void main(String[] args) throws SQLException { // Parunev
        Scanner scanner = new Scanner(System.in);

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/minions_db", "root", "root");

        int entry = Integer.parseInt(scanner.nextLine());

        PreparedStatement villainsStmt = connection.prepareStatement("SELECT `name` FROM villains WHERE id = ?");
        villainsStmt.setInt(1, entry);
        ResultSet villainRs = villainsStmt.executeQuery();

        if (villainRs.next()){
            String villain = villainRs.getString("name");
            System.out.printf("Villain: %s%n", villain );

            PreparedStatement minionsStmt = connection.prepareStatement("SELECT m.`name`, m.age " +
                    "FROM minions_villains AS mv JOIN minions AS m ON m.id = mv.minion_id " +
                    "WHERE mv.villain_id = ? GROUP BY mv.minion_id");

            minionsStmt.setInt(1, entry);
            ResultSet minionsRs = minionsStmt.executeQuery();
            for (int i = 1; minionsRs.next(); i++) {
                String minion_name = minionsRs.getString("name");
                int age = minionsRs.getInt("age");
                System.out.printf("%d. %s %d%n", i, minion_name, age);
            }
        }else{
            System.out.printf("No villain with ID %d exists in the database.", entry);
        }
    }
}
