package DB_Apps_Introduction.Exercise;

import java.sql.*;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Increase_Minions_Age {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/minions_db", "root", "root");

        List<String> minions_id = Arrays.stream(scanner.nextLine().split("\\s+")).collect(Collectors.toList());

        PreparedStatement update_stmt = connection.prepareStatement(String.format("UPDATE minions SET age = age + 1, `name` = lower(`name`) WHERE id IN (%s)",
                minions_id.stream()
                        .map(v -> "?")
                        .collect(Collectors.joining(", "))));

        for (int i = 1; i <= minions_id.size() ; i++) {
            update_stmt.setInt(i, Integer.parseInt(minions_id.get(i-1)));
        }

        update_stmt.executeUpdate();

        PreparedStatement select_stmt = connection.prepareStatement("SELECT `name`, age FROM minions ORDER BY id ");
        ResultSet select_rs = select_stmt.executeQuery();

        while (select_rs.next()){
            String minion_name = select_rs.getString("name");
            int minion_age = select_rs.getInt("age");

            System.out.printf("%s %d%n", minion_name, minion_age);
        }

        connection.close();
    }
}
