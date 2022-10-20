package DB_Apps_Introduction.Exercise;

import java.sql.*;

public class Get_Villains_Names {
    public static void main(String[] args) throws SQLException { // Parunev

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/minions_db", "root", "root");

        PreparedStatement stmt = connection.prepareStatement(
                "SELECT v.`name` AS name, count(distinct(mv.minion_id)) AS count_min " +
                    "FROM minions_villains AS mv JOIN villains AS v ON mv.villain_id = v.id " +
                    "GROUP BY mv.villain_id HAVING count_min > 15 ORDER BY count_min DESC");

        ResultSet rs = stmt.executeQuery();

        while (rs.next()){
            String villain_name = rs.getString("name");
            int minions_count = rs.getInt("count_min");

            System.out.printf("%s %d", villain_name, minions_count);
        }

        connection.close();
    }
}
