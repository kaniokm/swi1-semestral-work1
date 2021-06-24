package cz.osu.controllers;

import cz.osu.model.Reservation;
import cz.osu.utils.RequestUtils;
import cz.osu.utils.Validations;
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

import static cz.osu.controllers.MainWindowController.selectedId;
import static cz.osu.controllers.MainWindowController.selectedTime;

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
    private DatePicker datePicker;



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


    public ObservableList<LocalTime> defaultListOfTimes = FXCollections.observableArrayList(LocalTime.of(7, 0), LocalTime.of(8, 0),
            LocalTime.of(9, 0), LocalTime.of(10, 0), LocalTime.of(11, 0), LocalTime.of(12, 0),
            LocalTime.of(13, 0), LocalTime.of(14, 0), LocalTime.of(15, 0), LocalTime.of(16, 0));
    private ObservableList<LocalTime> listedTimes;
    private ObservableList<LocalTime> showListOfAvailibeTimes;
    ObservableList<LocalTime> todaysListedTimes;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        datePicker.setValue(MainWindowController.selectedDate);
        listedTimes = FXCollections.observableArrayList();



        todaysListedTimes = RequestUtils.getListOfReservedTimeForSelectedDay(datePicker.getValue());

        //    System.out.println(todaysListedTimes);


        listedTimes.addAll(todaysListedTimes);
        listedTimes.remove(selectedTime);

        Reservation data = RequestUtils.getSelectedReservationById(selectedId);

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
        comBoxReservedTime.setValue(selectedTime);
        comBoxReservedTime.setItems(showListOfAvailibeTimes);

    }






    public void requestEditReservation(ActionEvent actionEvent) {
      if( Validations.validateInputs(tfName,  tfSurname, tfPersonIdNumber,  tfPlateNumber,  tfPhone,  tfEmail,  comBoxReservedTime,  rdCz))

      {  RequestUtils.requestEditReservation(datePicker, tfName,  tfSurname, tfPersonIdNumber,  tfPlateNumber,  tfPhone,  tfEmail,  comBoxReservedTime,  rdCz, tfNote);



            Stage stage = (Stage) btnClose.getScene().getWindow();
            stage.close();}

    }








    public void reloadDate() {
        System.out.println("reload");
        MainWindowController.selectedDate=datePicker.getValue();
        System.out.println(datePicker.getValue());
        ObservableList<LocalTime> todayListedTimes = RequestUtils.getListOfReservedTimeForSelectedDay(datePicker.getValue());
        System.out.println(todayListedTimes);

        ObservableList<LocalTime> showListOfAvailableTimes = FXCollections.observableArrayList(LocalTime.of(7, 0), LocalTime.of(8, 0),
                LocalTime.of(9, 0), LocalTime.of(10, 0), LocalTime.of(11, 0), LocalTime.of(12, 0),
                LocalTime.of(13, 0), LocalTime.of(14, 0), LocalTime.of(15, 0), LocalTime.of(16, 0));;
       // showListOfAvailableTimes.addAll(defaultListOfTimes) ;
        System.out.println(showListOfAvailableTimes);
        ObservableList<LocalTime> listedTimes = FXCollections.observableArrayList();
        listedTimes.addAll(showListOfAvailableTimes);
        System.out.println(listedTimes);
        listedTimes.removeAll(todayListedTimes);
        System.out.println(listedTimes);
        comBoxReservedTime.setItems(listedTimes);
    }


    public void closeButtonAction(ActionEvent actionEvent) {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.setOnCloseRequest((WindowEvent event) -> {
            System.out.println("Close Requested");
        });
        stage.close();
    }
}


