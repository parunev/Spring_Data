package DB_Apps_Introduction.Lab;

import java.sql.*;
import java.util.Scanner;

public class Writing_Your_Own_Data_Retrieval_App {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/diablo", "root", "root");

        PreparedStatement stmt = connection.prepareStatement("SELECT user_name, first_name, last_name, COUNT(users_games.game_id) AS games_count " +
                "FROM users " +
                "JOIN users_games " +
                "ON users.id = users_games.user_id " +
                "WHERE users.user_name = ?" +
                "GROUP BY users.id");

        String username = scanner.nextLine();
        stmt.setString(1, username);

        ResultSet rs = stmt.executeQuery();

        if (rs.next()){
            int countGames = rs.getInt("games_count");
            String userName = rs.getString("user_name");
            String firstName = rs.getString("first_name");
            String lastName = rs.getString("last_name");
            System.out.printf("User: %s\n" +
                    "%s %s has played %d games",userName , firstName, lastName, countGames);
        }else{
            System.out.println("No such user exists");
        }
    }
}
