package cz.osu.guiJavaFx;

import cz.osu.database.DatabaseConnect;
import cz.osu.database.DatabaseData;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;


import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.stage.Modality;
import javafx.stage.Stage;

public class DbController implements Initializable {
    @FXML
    private javafx.scene.control.TableView<DatabaseData> tableView;
    @FXML
    private TableColumn<DatabaseData, Integer> colId;
    @FXML
    private TableColumn<DatabaseData, String> colName;
    @FXML
    private TableColumn<DatabaseData, String> colSurname;
    @FXML
    private TableColumn<DatabaseData, String> colPersonIdNumber;
    @FXML
    private TableColumn<DatabaseData, String> colPhone;
    @FXML
    private TableColumn<DatabaseData, String> colEmail;
    @FXML
    private TableColumn<DatabaseData, String> colPlateNumber;
    @FXML
    private TableColumn<DatabaseData, Timestamp> colReservationDate;
    @FXML
    private TableColumn<DatabaseData, Time> colReservationTime;
    @FXML
    private TableColumn<DatabaseData, String> colNote;
    @FXML
    private TableColumn<DatabaseData, String> colNationality;

    @FXML
    private Button btnCreate;
    @FXML
    private Button btnEdit;
    @FXML
    private Button btnDelete;

    @FXML
    private DatePicker datePicker;

    public static LocalDate selectedDate;
    public static int selectedId;

    public LocalDate getSelectedDate() {
        return selectedDate;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectedDate = LocalDate.now();
        ShowData();
    }

    public ObservableList<DatabaseData> getDataList() {
        ObservableList<DatabaseData> reservationList = FXCollections.observableArrayList();
        Connection conn = DatabaseConnect.getDBConnection();
        String query = "SELECT * FROM reservation_table WHERE reservation_date = '" + selectedDate + "' ORDER BY reservation_time";
        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);

            DatabaseData data;

            while (rs.next()) {
                data = new DatabaseData(rs.getInt("id"), rs.getString("first_name"), rs.getString("last_name"), rs.getString("person_id_number"), rs.getString("phone"), rs.getString("email"), rs.getString("plate_number"), rs.getTimestamp("reservation_date"), rs.getTime("reservation_time"), rs.getTimestamp("created_at"), rs.getString("note"), rs.getString("nationality"));
                reservationList.add(data);
                //System.out.println(rs.getString("reservation_time"));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("error here");
        }
        return reservationList;
    }

    public void ShowData() {
        ObservableList<DatabaseData> list = getDataList();
        colId.setCellValueFactory(new PropertyValueFactory<DatabaseData, Integer>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<DatabaseData, String>("name"));
        colSurname.setCellValueFactory(new PropertyValueFactory<DatabaseData, String>("surname"));
        colPersonIdNumber.setCellValueFactory(new PropertyValueFactory<DatabaseData, String>("personIdNumber"));
        colPhone.setCellValueFactory(new PropertyValueFactory<DatabaseData, String>("phone"));
        colEmail.setCellValueFactory(new PropertyValueFactory<DatabaseData, String>("email"));
        colPlateNumber.setCellValueFactory(new PropertyValueFactory<DatabaseData, String>("plateNumber"));
        colReservationTime.setCellValueFactory(new PropertyValueFactory<DatabaseData, Time>("reservationTime"));
        colNote.setCellValueFactory(new PropertyValueFactory<DatabaseData, String>("note"));
        colNationality.setCellValueFactory(new PropertyValueFactory<DatabaseData, String>("nationality"));

        datePicker.setValue(selectedDate);

        tableView.setItems(list);
    }

    public void createNewReservation(javafx.event.ActionEvent actionEvent) throws IOException {

        try {
            FXMLLoader fxmloader = new FXMLLoader(getClass().getResource("CreateReservationWindow.fxml"));
            Parent root = (Parent) fxmloader.load();
            Stage stage = new Stage();
            stage.setTitle("My New Stage Title");
            stage.setScene(new Scene(root, 600, 500));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("unable to open new window");
        }
    }

    public void editSelectedReservation(javafx.event.ActionEvent actionEvent) {

        try {
            selectedId = tableView.getSelectionModel().getSelectedItem().getId();

            FXMLLoader fxmloader = new FXMLLoader(getClass().getResource("EditReservationWindow.fxml"));
            Parent root = (Parent) fxmloader.load();
            Stage stage = new Stage();
            stage.setTitle("Edit Reservation");
            stage.setScene(new Scene(root, 600, 500));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());

            stage.showAndWait();
            refresh();

        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println("unable to open new window or edit");
        }

    }

    public void deleteSelectedReservation(javafx.event.ActionEvent actionEvent) throws Exception {
     try {
            int s = tableView.getSelectionModel().getSelectedItem().getId();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Potvrzení smazání.");
            alert.setHeaderText(null);
            alert.setContentText("Opravdu chcete smazat vybrané pole?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                tableView.getItems().removeAll(tableView.getSelectionModel().getSelectedItems());

                Connection conn = DatabaseConnect.getDBConnection();
                String query = "DELETE FROM reservation_table WHERE id = " + s;
                Statement st;
                try {
                    st = conn.createStatement();
                    st.executeUpdate(query);

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                    System.out.println("unable to delete");
                }
            }
        }catch (Exception e){
         System.out.println("nothing selected");
     }
    }


    public void refresh() {
        selectedDate = datePicker.getValue();
        getDataList();
        ShowData();
    }

    public void showPreviousDate(ActionEvent actionEvent) {
        selectedDate = selectedDate.minusDays(1);
        datePicker.setValue(selectedDate);
        refresh();
    }

    public void showNextDate(ActionEvent actionEvent) {
        selectedDate = selectedDate.plusDays(1);
        datePicker.setValue(selectedDate);
        refresh();
    }

    public void setToday(ActionEvent actionEvent) {
        datePicker.setValue(LocalDate.now());
        refresh();
    }
}
