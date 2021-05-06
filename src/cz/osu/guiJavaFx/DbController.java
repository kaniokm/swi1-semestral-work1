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


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
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
        createNewWindow(actionEvent,"CreateReservationWindow.fxml","Nová rezervace na den: " );
    }

    public void createWindowEditSelectedReservation(ActionEvent actionEvent) {
        createNewWindow(actionEvent,"EditReservationWindow.fxml","Edit Reservation");


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



        int id;
        URL url;
        try {
            id = tableView.getSelectionModel().getSelectedItem().getId();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Potvrzení smazání.");
            alert.setHeaderText(null);
            alert.setContentText("Opravdu chcete smazat vybrané pole?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                try {

                    url = new URL("http://localhost:8080/reservations/"+id);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();

                    con.setConnectTimeout(5000);
                    con.setReadTimeout(5000);
                    con.setRequestMethod("DELETE");


                    int status = con.getResponseCode();
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(con.getInputStream()));
                    String inputLine;
                    StringBuffer content = new StringBuffer();
                    while ((inputLine = in.readLine()) != null) {
                        content.append(inputLine);
                    }
                    in.close();

                    Reader streamReader = null;

                    if (status > 299) {
                        streamReader = new InputStreamReader(con.getErrorStream());
                    } else {
                        streamReader = new InputStreamReader(con.getInputStream());
                    }

                    con.disconnect();
                    requestRefresh();

                    //tableView.getItems().removeAll(tableView.getSelectionModel().getSelectedItems());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            System.out.println("nothing selected");
        }







    }



    public void requestRefresh() {





        ObservableList<DatabaseData> reservationList = DatabaseConnect.getDatabaseDataListForSelectedDay(datePicker.getValue());
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
