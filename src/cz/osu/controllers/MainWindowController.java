package cz.osu.controllers;

import cz.osu.model.Reservation;
import cz.osu.utils.RequestUtils;
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


import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainWindowController implements Initializable {
    @FXML
    private javafx.scene.control.TableView<Reservation> tableView;
    @FXML
    private TableColumn<Reservation, Integer> colId;
    @FXML
    private TableColumn<Reservation, String> colName;
    @FXML
    private TableColumn<Reservation, String> colSurname;
    @FXML
    private TableColumn<Reservation, String> colPersonIdNumber;
    @FXML
    private TableColumn<Reservation, String> colPhone;
    @FXML
    private TableColumn<Reservation, String> colEmail;
    @FXML
    private TableColumn<Reservation, String> colPlateNumber;
    @FXML
    private TableColumn<Reservation, Timestamp> colReservationDate;
    @FXML
    private TableColumn<Reservation, Time> colReservationTime;
    @FXML
    private TableColumn<Reservation, String> colNote;
    @FXML
    private TableColumn<Reservation, String> colNationality;

    @FXML
    private Button btnCreate;
    @FXML
    private Button btnEdit;
    @FXML
    private Button btnDelete;

    @FXML
    private DatePicker datePicker;

    public static LocalDate selectedDate;
    public static LocalTime selectedTime;
    public static int selectedId;

    public LocalDate getSelectedDate() {
        return selectedDate;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectedDate = LocalDate.now();
        datePicker.setValue(selectedDate);
        requestRefresh();
    }

    public void createWindowCreateNewReservation(ActionEvent actionEvent) {
        createNewWindow(actionEvent,"CreateReservationWindow.fxml","Nová rezervace:" );
    }

    public void createWindowEditSelectedReservation(ActionEvent actionEvent) {
        createNewWindow(actionEvent,"EditReservationWindow.fxml","Úprava rezervace:");
        requestRefresh();
    }

    private void createNewWindow(javafx.event.ActionEvent actionEvent, String window,String title){
        try {
            //selectedTime = tableView.getSelectionModel().getSelectedItem().getReservationTime();
            selectedId = tableView.getSelectionModel().getSelectedItem().getId();
            selectedTime = tableView.getSelectionModel().getSelectedItem().getReservationTime();
        }
        catch (Exception ignored){}

        try {

            FXMLLoader fxmLoader = new FXMLLoader(getClass().getResource(window));
            Parent root = (Parent) fxmLoader.load();
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root, 600, 500));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());

            stage.showAndWait();
            requestRefresh();

        } catch (Exception e) {
            System.out.println("unable to open new window or edit");
            e.printStackTrace();
        }
    }

    public void deleteSelectedReservation(javafx.event.ActionEvent actionEvent) {


                RequestUtils.requestDeleteReservation(tableView);


        requestRefresh();

    }



    public void requestRefresh() {

        ObservableList<Reservation> reservationList = RequestUtils.getReservationListForSelectedDay(datePicker.getValue());
        showData(reservationList);

    }




    public void showData(ObservableList<Reservation> reservationList) {

        colId.setCellValueFactory(new PropertyValueFactory<Reservation, Integer>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<Reservation, String>("name"));
        colSurname.setCellValueFactory(new PropertyValueFactory<Reservation, String>("surname"));
        colPersonIdNumber.setCellValueFactory(new PropertyValueFactory<Reservation, String>("personIdNumber"));
        colPhone.setCellValueFactory(new PropertyValueFactory<Reservation, String>("phone"));
        colEmail.setCellValueFactory(new PropertyValueFactory<Reservation, String>("email"));
        colPlateNumber.setCellValueFactory(new PropertyValueFactory<Reservation, String>("plateNumber"));
        colReservationTime.setCellValueFactory(new PropertyValueFactory<Reservation, Time>("reservationTime"));
        colNote.setCellValueFactory(new PropertyValueFactory<Reservation, String>("note"));
        colNationality.setCellValueFactory(new PropertyValueFactory<Reservation, String>("nationality"));

        datePicker.setValue(selectedDate);




        tableView.setItems(reservationList);
    }

    public void showPreviousDate(ActionEvent actionEvent) {
        selectedDate = selectedDate.minusDays(1);
        datePicker.setValue(selectedDate);
        requestRefresh();
    }

    public void showNextDate(ActionEvent actionEvent) {
        selectedDate = selectedDate.plusDays(1);
        datePicker.setValue(selectedDate);
        requestRefresh();
    }

    public void setToday(ActionEvent actionEvent) {
        selectedDate = LocalDate.now();
        datePicker.setValue(selectedDate);
        requestRefresh();
    }
}
