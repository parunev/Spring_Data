package DB_Apps_Introduction.Exercise;

import java.sql.*;
import java.util.Scanner;

public class Add_Minion {
    public static void main(String[] args) throws SQLException { // Parunev
        Scanner scanner = new Scanner(System.in);
        String[] minion = scanner.nextLine().split("\\s+");

        String minion_name = minion[1];
        int minion_age = Integer.parseInt(minion[2]);
        String minion_town = minion[3];

        String villain = scanner.nextLine().split("\\s+")[1];

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/minions_db", "root", "root");

        int town_id = addTown(minion_town, connection);
        int villain_id = addVillain(connection, villain);
        int minion_id = addMinion(connection, minion_name, minion_age, town_id);

        addMinionToTheVillain(connection, villain_id, villain,  minion_id, minion_name);

        connection.close();
    }

    //method name says it all
    private static void addMinionToTheVillain(Connection connection, int villain_id, String villain, int minion_id, String minion_name) throws SQLException{
        PreparedStatement add_stmt = connection.prepareStatement("INSERT INTO minions_villains (minion_id, villain_id) VALUES (?, ?) ");

        add_stmt.setInt(1, minion_id);
        add_stmt.setInt(2, villain_id);
        add_stmt.executeUpdate();

        System.out.printf("Successfully added %s to be minion of %s%n", minion_name, villain);

    }

    //add minions
    private static int addMinion(Connection connection, String minion_name, int minion_age, int town_id) throws SQLException {
        PreparedStatement add_stmt = connection.prepareStatement(" INSERT INTO minions (`name`, age, town_id) VALUES (?, ?, ?) ", Statement.RETURN_GENERATED_KEYS);

        add_stmt.setString(1, minion_name);
        add_stmt.setInt(2, minion_age);
        add_stmt.setInt(3, town_id);
        add_stmt.executeUpdate();

        ResultSet rs = add_stmt.getGeneratedKeys();
        rs.next();

        return rs.getInt(1);
    }

    //first we check if the villain exists then add
    private static int addVillain(Connection connection, String villain) throws SQLException {
        PreparedStatement check_stmt = connection.prepareStatement("SELECT id FROM villains WHERE `name` = ? ");
        check_stmt.setString(1, villain);
        ResultSet rs = check_stmt.executeQuery();

        if (!rs.next()){
            PreparedStatement add_stmt = connection.prepareStatement("INSERT INTO villains(`name`, `evilness_factor`) VALUES (?, 'evil') ", Statement.RETURN_GENERATED_KEYS);

            add_stmt.setString(1, villain);
            add_stmt.executeUpdate();

            rs = add_stmt.getGeneratedKeys();
            rs.next();

            System.out.printf("Villain %s was added to the database.%n", villain);
        }
        return rs.getInt(1);
    }

    //first we check if the town exists then add
    private static int addTown(String minion_town, Connection connection) throws SQLException {
        PreparedStatement check_stmt = connection.prepareStatement("SELECT id FROM towns WHERE `name` = ? ");
        check_stmt.setString(1, minion_town);
        ResultSet rs = check_stmt.executeQuery();

        if (!rs.next()){
            PreparedStatement add_stmt = connection.prepareStatement("INSERT INTO towns(`name`) VALUES (?) ", Statement.RETURN_GENERATED_KEYS);

            add_stmt.setString(1, minion_town);
            add_stmt.executeUpdate();

            rs = add_stmt.getGeneratedKeys();
            rs.next();

            System.out.printf("Town %s was added to the database.%n", minion_town);
        }
        return rs.getInt(1);
    }
}
