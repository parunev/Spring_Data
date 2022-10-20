package DB_Apps_Introduction.Exercise;

import java.sql.*;
import java.util.ArrayDeque;

public class Print_All_Minion_Names {
    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/minions_db", "root", "root");
        PreparedStatement minions = connection.prepareStatement("SELECT `name` FROM minions ORDER BY id ");
        ResultSet rs = minions.executeQuery();

        ArrayDeque<String> names = new ArrayDeque<>();

        while (rs.next()) {names.offer(rs.getString("name"));}

        while (names.size() > 2){
            System.out.println(names.pollFirst());
            System.out.println(names.pollLast());
        }

        while(!names.isEmpty()) {System.out.println(names.poll());}

        connection.close();
    }
}

