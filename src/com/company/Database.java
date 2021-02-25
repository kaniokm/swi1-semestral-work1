package com.company;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database{

    private  Database() {

    }

    public static Connection getDBConnection() {
        Connection connection = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e){
            e.printStackTrace();

        }

        try{
            connection = DriverManager.getConnection( "jdbc:mysql://localhost:3306/7swi1","7swi1_user","TajneHeslo123");
        }catch (SQLException throwable){
            throwable.printStackTrace();
        }
        return connection;
    }

}

