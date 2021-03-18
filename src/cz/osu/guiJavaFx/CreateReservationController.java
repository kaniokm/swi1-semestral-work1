package cz.osu.guiJavaFx;

import cz.osu.database.DatabaseConnect;
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

public class CreateReservationController implements Initializable {
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
    public javafx.scene.control.Button btnClose;
    @FXML
    public javafx.scene.control.Button btnSave;

    @FXML
    private RadioButton rdCz;
    @FXML
    private ToggleGroup tgNationality;
    @FXML
    private RadioButton rdOther;


    private final ObservableList<LocalTime> defaultListOfTimes = FXCollections.observableArrayList(LocalTime.of(7, 0), LocalTime.of(8, 0), LocalTime.of(9, 0), LocalTime.of(10, 0), LocalTime.of(11, 0), LocalTime.of(12, 0), LocalTime.of(13, 0), LocalTime.of(14, 0), LocalTime.of(15, 0), LocalTime.of(16, 0));

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //System.out.println("new inicized");
        String query = "SELECT reservation_time FROM reservation_table WHERE reservation_date = '" + DbController.selectedDate + "' ORDER BY reservation_time";
        ObservableList<LocalTime> alreadyListedTimes = DatabaseConnect.getListOfReservedTimeForSelectedDay(query);

        ObservableList<LocalTime> showList = defaultListOfTimes;
        showList.removeAll(alreadyListedTimes);
        comBoxReservedTime.setItems(showList);

        //to allow only to input numbers (but not + and space)
        tfPhone.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                tfPhone.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    public void createNewReservationToDb(ActionEvent actionEvent) {
        if (tfName.getText().equals("") || tfSurname.getText().equals("") || tfPersonIdNumber.getText().equals("") || tfPlateNumber.getText().equals("") || (tfPhone.getText().equals("") && tfEmail.getText().equals(""))||comBoxReservedTime.getValue()==null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Vyplňte prosím důležitá pole !!!");
            alert.showAndWait();
        } else {
            String query = "INSERT INTO reservation_table (first_name, last_name, person_id_number, phone, email, plate_number, note, reservation_date, reservation_time, nationality) VALUES ('" + tfName.getText() + "', '" + tfSurname.getText() + "', '" + tfPersonIdNumber.getText() + "', '" + tfPhone.getText() + "', '" + tfEmail.getText() + "', '" + tfPlateNumber.getText() + "', '" + tfNote.getText() + "', '" + DbController.selectedDate + "', '" + comBoxReservedTime.getValue() + "', '" + (rdCz.isSelected() ? "cz" : "--") + "');";
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


