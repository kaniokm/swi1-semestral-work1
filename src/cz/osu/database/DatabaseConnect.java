package cz.osu.database;

import cz.osu.guiJavaFx.DbController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.sql.*;
import java.time.LocalTime;

public class DatabaseConnect {

    public static Connection getDBConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/7swi1", "7swi1_user", "TajneHeslo123");
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return connection;
    }

    public static ObservableList<DatabaseData> getDatabaseDataListForSelectedDay(String query) {
        Connection conn = DatabaseConnect.getDBConnection();
        Statement st;
        ResultSet rs;

        ObservableList<DatabaseData> reservationList = FXCollections.observableArrayList();
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);

            DatabaseData data;

            while (rs.next()) {
                data = new DatabaseData(rs.getInt("id"), rs.getString("first_name"), rs.getString("last_name"), rs.getString("person_id_number"), rs.getString("phone"), rs.getString("email"), rs.getString("plate_number"), rs.getTimestamp("reservation_date"), rs.getTime("reservation_time"), rs.getTimestamp("created_at"), rs.getString("note"), rs.getString("nationality"));
                reservationList.add(data);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("error here");
        }
        return reservationList;
    }

    public static ObservableList<LocalTime> getListOfReservedTimeForSelectedDay(String query) {
        Statement st;
        ResultSet rs;
        Connection conn = DatabaseConnect.getDBConnection();
        ObservableList<LocalTime> listedTimes = FXCollections.observableArrayList();
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);

            while (rs.next()) {
                listedTimes.add(rs.getTime("reservation_time").toLocalTime());
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("error when selecting times from db by date");
        }
        return listedTimes;
    }

    public static DatabaseData getSelectedDataByDateAndId() {
        DatabaseData data;
        Connection conn = DatabaseConnect.getDBConnection();
        String query = "SELECT * FROM reservation_table WHERE reservation_date = '" + DbController.selectedDate + "' AND id =" + DbController.selectedId + " ORDER BY reservation_time";

        Statement st;
        ResultSet rs;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);

            while (rs.next()) {
                data = new DatabaseData(rs.getInt("id"), rs.getString("first_name"), rs.getString("last_name"), rs.getString("person_id_number"), rs.getString("phone"), rs.getString("email"), rs.getString("plate_number"), rs.getTimestamp("reservation_date"), rs.getTime("reservation_time"), rs.getTimestamp("created_at"), rs.getString("note"), rs.getString("nationality"));
                return data;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Nepodařilo se načíst data pro editaci");
            alert.showAndWait();
        }
        return null;
    }

    public static void updateOrCreateRecordInDatabase(String query) {
        Connection conn = DatabaseConnect.getDBConnection();
        Statement st;

        try {
            st = conn.createStatement();
            st.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Špatně zadaná hodnota !!!o");
            alert.showAndWait();
        }
    }

    public static void deleteRecordInDatabase(String query) {
        Connection conn = DatabaseConnect.getDBConnection();
        Statement st;
        try {
            st = conn.createStatement();
            st.executeUpdate(query);

        } catch (SQLException throwables) {
            System.out.println("unable to delete");
            throwables.printStackTrace();
        }
    }
}

