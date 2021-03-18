package cz.osu.guiJavaFx;

import cz.osu.database.DatabaseConnect;
import cz.osu.database.DatabaseData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.net.URL;
import java.sql.*;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class EditReservationController implements Initializable {
    @FXML
    private TextField tfName;
    @FXML
    private TextField tfSurname;
    @FXML
    private TextField tfPersonIdNumber;
    @FXML
    private TextField tfPhone;
    @FXML
    private TextField tfEmail;
    @FXML
    private TextField tfPlateNumber;
    @FXML
    private TextArea tfNote;
    @FXML
    private ComboBox<LocalTime> comBoxReservedTime;

    @FXML
    public Button btnClose;
    @FXML
    public Button btnSave;

    @FXML
    private RadioButton rdCz;
    @FXML
    private ToggleGroup tgNationality;
    @FXML
    private RadioButton rdOther;


    private final ObservableList<LocalTime> defaultListOfTimes = FXCollections.observableArrayList(LocalTime.of(7, 0), LocalTime.of(8, 0), LocalTime.of(9, 0), LocalTime.of(10, 0), LocalTime.of(11, 0), LocalTime.of(12, 0), LocalTime.of(13, 0), LocalTime.of(14, 0), LocalTime.of(15, 0), LocalTime.of(16, 0));
    private ObservableList<LocalTime> listedTimes;
    private ObservableList<LocalTime> showListOfAvailibeTimes;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<DatabaseData> reservationList = FXCollections.observableArrayList();
        Connection conn = DatabaseConnect.getDBConnection();
        String query = "SELECT reservation_time FROM reservation_table WHERE reservation_date = '" + DbController.selectedDate + "' AND id ="+DbController.selectedId +" ORDER BY reservation_time;";
       // System.out.println(query);
        Statement st;
        ResultSet rs;
        listedTimes = FXCollections.observableArrayList();
        
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);

            while (rs.next()) {
                listedTimes.add(rs.getTime("reservation_time").toLocalTime());
            }
            comBoxReservedTime.setValue(listedTimes.get(0));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("error when selecting times from db by date");
        }

        query = "SELECT reservation_time FROM reservation_table WHERE reservation_date = '" + DbController.selectedDate + "' ORDER BY reservation_time;";
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

        DatabaseData data = getDataList();
        tfName.setText(data.getName());
        tfSurname.setText(data.getSurname());
        tfPersonIdNumber.setText(data.getPersonIdNumber());
        tfPhone.setText(data.getPhone());
        tfEmail.setText(data.getEmail());
        tfPlateNumber.setText(data.getPlateNumber());
        tfNote.setText(data.getNote());
        if (data.getNationality().equals("cz"))
            rdCz.setSelected(true);
        else rdOther.setSelected(true);


        showListOfAvailibeTimes = defaultListOfTimes;
        showListOfAvailibeTimes.removeAll(listedTimes);
        comBoxReservedTime.setItems(showListOfAvailibeTimes);



    }

    public DatabaseData getDataList() {
        DatabaseData data ;
        Connection conn = DatabaseConnect.getDBConnection();
        String query = "SELECT * FROM reservation_table WHERE reservation_date = '" + DbController.selectedDate + "' AND id ="+DbController.selectedId +" ORDER BY reservation_time";
        System.out.println(query);
        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);

       //     DatabaseData data;

            while (rs.next()) {
                data = new DatabaseData(rs.getInt("id"), rs.getString("first_name"), rs.getString("last_name"), rs.getString("person_id_number"), rs.getString("phone"), rs.getString("email"), rs.getString("plate_number"), rs.getTimestamp("reservation_date"), rs.getTime("reservation_time"), rs.getTimestamp("created_at"), rs.getString("note"), rs.getString("nationality"));
                //reservationList.add(data);
                return data;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("error here");
        }
        return null;
    }

    public void editReservationToDb(ActionEvent actionEvent) throws Exception {
        String query = "UPDATE reservation_table Set first_name='"+ tfName.getText() + "', last_name='"+ tfSurname.getText() +"', person_id_number='"+ tfPersonIdNumber.getText() + "', phone='"+ tfPhone.getText() +"', email='" + tfEmail.getText() +"', plate_number='" + tfPlateNumber.getText() +"', note='" + tfNote.getText() + "', reservation_date='" + DbController.selectedDate + "', reservation_time='" + comBoxReservedTime.getValue() +"', nationality='" + (rdCz.isSelected()?"cz":"--") + "' WHERE id="+DbController.selectedId+" ;";

        Connection conn = DatabaseConnect.getDBConnection();
        Statement st;
        System.out.println(query);

        try {
            st = conn.createStatement();
            st.executeUpdate(query);
            Stage stage = (Stage) btnClose.getScene().getWindow();
            stage.close();

        } catch (SQLException e) {
            e.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Špatně zadaná hodnota !!!");
            alert.showAndWait();
        }
    }

    public void closeButtonAction(ActionEvent actionEvent) {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.setOnCloseRequest((WindowEvent event) -> {
            System.out.println("Close Requested");
        });
        stage.close();
    }


}
