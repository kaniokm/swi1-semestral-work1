package com.company;

import java.sql.*;

public class Main {
    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    public static void main(String[] args) throws SQLException {

        Connection connection = Database.getDBConnection();

        String query = "SELECT first_name, last_name, created_at FROM customer WHERE first_name = ?";


        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1,"Radek");

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                String first_name = resultSet.getString(1);
                String last_name = resultSet.getString(2);
                Timestamp created_at = resultSet.getTimestamp(3);

                System.out.println(first_name);
                System.out.println(last_name);
                System.out.println(created_at);

            }

        }catch (SQLException throwables){
            throwables.printStackTrace();
        }

    }
}
