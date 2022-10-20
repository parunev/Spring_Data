package DB_Apps_Introduction.Exercise;

import java.sql.*;
import java.util.Scanner;

public class Remove_Villain {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/minions_db", "root", "root");

        int villain_id = Integer.parseInt(scanner.nextLine());

        PreparedStatement stmt = connection.prepareStatement("SELECT `name` FROM villains WHERE id = ? ");
        stmt.setInt(1, villain_id);
        ResultSet rs = stmt.executeQuery();

        if (!rs.next()){
            System.out.println("No such villain was found");
            connection.close();
        }

        connection.setAutoCommit(false);

        try{
            String v = rs.getString("name"); // villain, hate to type it

            //let minions free
            PreparedStatement free_stmt_m = connection.prepareStatement("DELETE FROM minions_villains WHERE villain_id = ? ");
            free_stmt_m.setInt(1, villain_id);
            int freeMinions = free_stmt_m.executeUpdate();

            //delete v from database
            PreparedStatement bye_stmt_v = connection.prepareStatement("DELETE FROM villains WHERE id = ? ");
            bye_stmt_v.setInt(1, villain_id);
            bye_stmt_v.executeUpdate();

            System.out.printf("%s was deleted%n", v);
            System.out.printf("%d minions released%n", freeMinions);

        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
        }

        connection.close();
    }
}
