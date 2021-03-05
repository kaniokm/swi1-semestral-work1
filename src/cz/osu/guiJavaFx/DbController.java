package cz.osu.guiJavaFx;

import cz.osu.Main;
import cz.osu.database.DatabaseConnect;
import cz.osu.database.DatabaseData;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class DbController implements Initializable {
    @FXML private javafx.scene.control.TableView<DatabaseData> tableView;
    @FXML private TableColumn<DatabaseData,Integer> colId;
    @FXML private TableColumn<DatabaseData,String> colName;
    @FXML private TableColumn<DatabaseData,String> colSurname;
    @FXML private TableColumn<DatabaseData,String> colPersonIdNumber;
    @FXML private TableColumn<DatabaseData,String> colPhone;
    @FXML private TableColumn<DatabaseData,String> colEmail;
    @FXML private TableColumn<DatabaseData,String> colPlateNumber;
    @FXML private TableColumn<DatabaseData, Timestamp> colReservationDate;
    @FXML private TableColumn<DatabaseData, Time> colReservationTime;
    @FXML private TableColumn<DatabaseData,String> colNote;

    @FXML private Button btnCreate;
    @FXML private Button btnEdit;
    @FXML private Button btnDelete;

    @FXML private DatePicker datePicker;

    private LocalDate selectedDate;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectedDate = LocalDate.now();
        ShowData();
    }

    public  ObservableList<DatabaseData> getDataList(){
        ObservableList<DatabaseData> reservationList = FXCollections.observableArrayList();
        Connection conn = DatabaseConnect.getDBConnection();
        String query = "SELECT * FROM reservation_table WHERE reservation_date = '"+selectedDate+"' ORDER BY reservation_time";
        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);

            DatabaseData data;

            while (rs.next()){
                 data = new DatabaseData(rs.getInt("id"),rs.getString("first_name"), rs.getString("last_name"),rs.getString("person_id_number"),rs.getString("phone"),rs.getString("email"),rs.getString("plate_number"),rs.getTimestamp("reservation_date"),rs.getTime("reservation_time"),rs.getTimestamp("created_at"),rs.getString("note"));
                 reservationList.add(data);
            }

        }catch (SQLException throwables){
            throwables.printStackTrace();
            System.out.println("error here");
        }
        return reservationList;
    }

    public  void ShowData(){
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

            /*stage.setOnCloseRequest((event) -> {
                System.out.println("Closing aaaaaaaaaaaaaaaaaa");
                ShowData();
               if (stage.isFocused())
                   System.out.println("F");
            });*/
            stage.setOnCloseRequest((e) -> {
                System.out.println("Close Requestedee"); //todo fix
                ShowData();
            });
            stage.show();

        }
        catch (IOException e) {
            e.printStackTrace();
            System.out.println("unable to open new window");
        }
    }

    public void editSelectedReservation(javafx.event.ActionEvent actionEvent) {
        //TODO
    }

    public void deleteSelectedReservation(javafx.event.ActionEvent actionEvent) {
        //TODO
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
}
