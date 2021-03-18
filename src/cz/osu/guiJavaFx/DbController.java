package cz.osu.guiJavaFx;

import cz.osu.database.DatabaseConnect;
import cz.osu.database.DatabaseData;
import javafx.collections.ObservableList;
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
        datePicker.setValue(selectedDate);
        refresh();
    }

    public void createNewReservation(javafx.event.ActionEvent actionEvent) throws IOException {

        try {
            FXMLLoader fxmLoader = new FXMLLoader(getClass().getResource("CreateReservationWindow.fxml"));
            Parent root = (Parent) fxmLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Nová rezervace na den: " + selectedDate);
            stage.setScene(new Scene(root, 600, 500));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());

            stage.showAndWait();
            refresh();

        } catch (IOException e) {
            System.out.println("unable to open new window");
            e.printStackTrace();
        }
    }

    public void editSelectedReservation(javafx.event.ActionEvent actionEvent) {

        try {
            selectedId = tableView.getSelectionModel().getSelectedItem().getId();

            FXMLLoader fxmLoader = new FXMLLoader(getClass().getResource("EditReservationWindow.fxml"));
            Parent root = (Parent) fxmLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Edit Reservation");
            stage.setScene(new Scene(root, 600, 500));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());

            stage.showAndWait();
            refresh();

        } catch (Exception e) {
            System.out.println("unable to open new window or edit");
            e.printStackTrace();
        }
    }

    public void deleteSelectedReservation(javafx.event.ActionEvent actionEvent) throws Exception {
        int id;
        try {
            id = tableView.getSelectionModel().getSelectedItem().getId();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Potvrzení smazání.");
            alert.setHeaderText(null);
            alert.setContentText("Opravdu chcete smazat vybrané pole?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {

                String query = "DELETE FROM reservation_table WHERE id = " + id;
                DatabaseConnect.deleteRecordInDatabase(query);
                //tableView.getItems().removeAll(tableView.getSelectionModel().getSelectedItems());
                refresh();
            }
        } catch (Exception e) {
            System.out.println("nothing selected");
        }
    }


    public void refresh() {

        String query = "SELECT * FROM reservation_table WHERE reservation_date = '" + selectedDate + "' ORDER BY reservation_time";
        ObservableList<DatabaseData> reservationList = DatabaseConnect.getDatabaseDataListForSelectedDay(query);
        showData(reservationList);
    }

    public void showData(ObservableList<DatabaseData> reservationList) {

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

        tableView.setItems(reservationList);
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
        selectedDate = LocalDate.now();
        datePicker.setValue(selectedDate);
        refresh();
    }
}
