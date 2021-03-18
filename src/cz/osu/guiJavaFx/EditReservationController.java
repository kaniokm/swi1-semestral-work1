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
        listedTimes = FXCollections.observableArrayList();
        String query = "SELECT reservation_time FROM reservation_table WHERE reservation_date = '" + DbController.selectedDate + "' AND id =" + DbController.selectedId + " ORDER BY reservation_time;";
        ObservableList<LocalTime> currentlySetTime = DatabaseConnect.getListOfReservedTimeForSelectedDay(query);

        query = "SELECT reservation_time FROM reservation_table WHERE reservation_date = '" + DbController.selectedDate + "' ORDER BY reservation_time;";
        ObservableList<LocalTime> todaysListedTimes = DatabaseConnect.getListOfReservedTimeForSelectedDay(query);

        listedTimes.addAll(todaysListedTimes);
        listedTimes.removeAll(currentlySetTime);

        DatabaseData data = DatabaseConnect.getSelectedDataByDateAndId();

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
        comBoxReservedTime.setValue(currentlySetTime.get(0));
        comBoxReservedTime.setItems(showListOfAvailibeTimes);
    }


    public void editReservationToDb(ActionEvent actionEvent) {
        if (tfName.getText().equals("") || tfSurname.getText().equals("") || tfPersonIdNumber.getText().equals("") || tfPlateNumber.getText().equals("") || (tfPhone.getText().equals("") && tfEmail.getText().equals(""))) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Vyplňte prosím důležitá pole !!!");
            alert.showAndWait();
        } else {
            String query = "UPDATE reservation_table Set first_name='" + tfName.getText() + "', last_name='" + tfSurname.getText() + "', person_id_number='" + tfPersonIdNumber.getText() + "', phone='" + tfPhone.getText() + "', email='" + tfEmail.getText() + "', plate_number='" + tfPlateNumber.getText() + "', note='" + tfNote.getText() + "', reservation_date='" + DbController.selectedDate + "', reservation_time='" + comBoxReservedTime.getValue() + "', nationality='" + (rdCz.isSelected() ? "cz" : "--") + "' WHERE id=" + DbController.selectedId + " ;";
            DatabaseConnect.updateOrCreateRecordInDatabase(query);

            Stage stage = (Stage) btnClose.getScene().getWindow();
            stage.close();
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
