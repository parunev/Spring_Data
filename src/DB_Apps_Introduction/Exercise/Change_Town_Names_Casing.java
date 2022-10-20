package DB_Apps_Introduction.Exercise;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Change_Town_Names_Casing {
    public static void main(String[] args) throws SQLException { // Parunev
        Scanner scanner = new Scanner(System.in);
        List<String> town_names = new ArrayList<>();

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/minions_db", "root", "root");

        String country = scanner.nextLine();
        int updates = updateNames(connection, country);

        if (updates == 0){
            System.out.println("No town names were affected.");
        }else{
            System.out.printf("%d town names were affected.%n", updates);

            PreparedStatement town_stmt = connection.prepareStatement("SELECT `name` FROM towns WHERE country = ? ");
            town_stmt.setString(1, country);
            ResultSet town_rs = town_stmt.executeQuery();

            while (town_rs.next()){
                town_names.add(town_rs.getString("name"));
            }
            System.out.println(town_names);
        }

        connection.close();
    }


    // we set country names to be upper case
    private static int updateNames(Connection connection, String country) throws SQLException {
        PreparedStatement upd_stmt = connection.prepareStatement("UPDATE towns SET `name` = upper(`name`) WHERE country = ? ORDER BY id");

        upd_stmt.setString(1, country);

        return upd_stmt.executeUpdate();
    }
}
