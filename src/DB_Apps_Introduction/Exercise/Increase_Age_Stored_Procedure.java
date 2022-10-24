package DB_Apps_Introduction.Exercise;

import java.sql.*;
import java.util.Scanner;

public class Increase_Age_Stored_Procedure {

    //CREATE PROCEDURE `usp_get_older`(`minion_id` INT)
    //BEGIN
    //UPDATE `minions`
    //SET `age` = `age` + 1
    //WHERE `id` = `minion_id`;
    //END

    public static void main(String[] args) throws SQLException { //Parunev
        Scanner scanner = new Scanner(System.in);
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/minions_db", "root", "root");

        int input = Integer.parseInt(scanner.nextLine()); //minion id

        ResultSet rs = minion_stmt(connection, input);

        // if there is no minion with equal to the input id
        if (!rs.next()) {
            System.out.printf("There is no minion with id %d. You can insert another id, maybe it will be correct!", input);
            return;
        }

        update_stmt(connection, input);
        ResultSet print = updated_rs(connection, input);
        output(print);

        connection.close();
    }

    // we print the result
    private static void output(ResultSet printResult) throws SQLException {
        while (printResult.next()) {
            String currentName = printResult.getString("name");
            int currentAge = printResult.getInt("age");

            System.out.println(currentName + " " + currentAge);
        }
    }

    // we take the updated information about the minion
    private static ResultSet updated_rs(Connection connection, int input) throws SQLException {
        PreparedStatement printStatement = connection.prepareStatement("SELECT `id`, `name`, `age` FROM `minions` WHERE `id` = ? ");
        printStatement.setInt(1, input);
        return printStatement.executeQuery();
    }

    // we increase the minion years by 1 ps. you can check the SQL query quoted in the beginning
    private static void update_stmt(Connection connection, int input) throws SQLException {
        PreparedStatement procedureStatement = connection.prepareStatement("CALL `usp_get_older`(?) ");
        procedureStatement.setInt(1, input);
        procedureStatement.executeUpdate();
    }

    // we take the minion by the given id
    private static ResultSet minion_stmt(Connection connection, int input) throws SQLException {
        PreparedStatement minionsStatement = connection.prepareStatement("SELECT `id`, `name`, `age` FROM `minions` WHERE `id` = ? ");
        minionsStatement.setInt(1, input);
        return minionsStatement.executeQuery();
    }
}