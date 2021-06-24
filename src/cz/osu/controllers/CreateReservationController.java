package cz.osu.controllers;

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


    private final ObservableList<LocalTime> defaultListOfTimes = FXCollections.observableArrayList(LocalTime.of(7, 0,0), LocalTime.of(8, 0,0),
            LocalTime.of(9, 0,0), LocalTime.of(10, 0,0), LocalTime.of(11, 0,0), LocalTime.of(12, 0,0),
            LocalTime.of(13, 0,0), LocalTime.of(14, 0,0), LocalTime.of(15, 0,0), LocalTime.of(16, 0,0));

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //System.out.println("new inicized");

        ObservableList<LocalTime> alreadyListedTimes =  RequestUtils.getListOfReservedTimeForSelectedDay(MainWindowController.selectedDate);

        ObservableList<LocalTime> showList = defaultListOfTimes;
        showList.removeAll(alreadyListedTimes);
        comBoxReservedTime.setItems(showList);

        //to allow only to input numbers (but not + and space)
     /*   tfPhone.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                tfPhone.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });*/
    }


    public void requestCreateNewReservation(ActionEvent actionEvent) {
         if   (Validations.validateInputs(tfName,  tfSurname, tfPersonIdNumber,  tfPlateNumber,  tfPhone,  tfEmail,  comBoxReservedTime,  rdCz))
         {  RequestUtils.requestCreateNewReservation(tfName,  tfSurname, tfPersonIdNumber,  tfPlateNumber,  tfPhone,  tfEmail,  comBoxReservedTime,  rdCz, tfNote);


                Stage stage = (Stage) btnClose.getScene().getWindow();
                stage.close();}


    }



    public void reloadDate() {
        System.out.println("reload");

        ObservableList<LocalTime> todayListedTimes = RequestUtils.getListOfReservedTimeForSelectedDay(MainWindowController.selectedDate);
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


