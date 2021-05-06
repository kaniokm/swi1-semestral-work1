package cz.osu.guiJavaFx;

import cz.osu.ParameterStringBuilder;
import cz.osu.database.DatabaseConnect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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


    private final ObservableList<LocalTime> defaultListOfTimes = FXCollections.observableArrayList(LocalTime.of(7, 0,0), LocalTime.of(8, 0,0), LocalTime.of(9, 0,0), LocalTime.of(10, 0,0), LocalTime.of(11, 0,0), LocalTime.of(12, 0,0), LocalTime.of(13, 0,0), LocalTime.of(14, 0,0), LocalTime.of(15, 0,0), LocalTime.of(16, 0,0));

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //System.out.println("new inicized");

        ObservableList<LocalTime> alreadyListedTimes =  DatabaseConnect.getListOfReservedTimeForSelectedDay(DbController.selectedDate);

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

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    public static final Pattern VALID_PHONE_Number_REGEX =
            Pattern.compile("/^[^a-zA-Z]*$/", Pattern.CASE_INSENSITIVE);
    // Pattern.compile("^[+]?[()\0-9. -]{9,}$", Pattern.CASE_INSENSITIVE);


    public static boolean validate(String emailOrPhoneStr, Pattern regexPattern) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailOrPhoneStr);
        return matcher.find();
    }

    public void requestCreateNewReservation(ActionEvent actionEvent) {
        if (tfName.getText().equals("") || tfSurname.getText().equals("") || tfPersonIdNumber.getText().equals("") || tfPlateNumber.getText().equals("") || (tfPhone.getText().equals("") || tfEmail.getText().equals(""))||comBoxReservedTime.getValue()==null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Vyplňte prosím všechna pole !!!");
            alert.showAndWait();
        } else {
            URL url = null;
            try {
                //if (!tfPhone.getText().matches("/^[^a-zA-Z]*$/")) {
                /*if (!validate(tfPhone.getText(),VALID_PHONE_Number_REGEX)) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Neplatné telefoní číslo !!!");
                    alert.showAndWait();
                    return;
                }*/
                if (!validate(tfEmail.getText(),VALID_EMAIL_ADDRESS_REGEX)) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Neplatný email !!!");
                    alert.showAndWait();
                    return;
                }
                url = new URL("http://localhost:8080/reservations");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                String date =DbController.selectedDate.toString();

                String json = "{\n" +
                        "\"reservationDate\": \"" +DbController.selectedDate+"\",\n" +
                        "\"reservationTime\": \"" +comBoxReservedTime.getValue()+":00"+"\",\n" +
                        "\"firstName\": \"" +tfName.getText()+"\",\n" +
                        "\"lastName\": \"" +tfSurname.getText()+"\",\n" +
                        "\"plateNumber\": \"" +tfPlateNumber.getText()+"\",\n" +
                        "\"personIdNumber\": \"" +tfPersonIdNumber.getText()+"\",\n" +
                        "\"phone\": \"" +tfPhone.getText()+"\",\n" +
                        "\"email\": \"" +tfEmail.getText()+"\",\n" +
                        "\"note\": \"" +tfNote.getText().replaceAll("[\r\n]+", " ")+"\",\n" +
                        "\"nationality\": \"" +(rdCz.isSelected() ? "cz" : "--")+"\"\n" +
                        "}";
                System.out.println( json);





                con.setConnectTimeout(5000);
                con.setReadTimeout(5000);
                con.setDoOutput(true);
                con.setRequestProperty("Content-Type", "application/json");
                DataOutputStream out = new DataOutputStream(con.getOutputStream());
                //out.writeBytes(ParameterStringBuilder.getParamsString(parameters));
                out.write(json.getBytes());
                out.flush();
                out.close();

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



            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }




            Stage stage = (Stage) btnClose.getScene().getWindow();
            stage.close();
        }
    }

    public void reloadDate() {
        System.out.println("reload");

        ObservableList<LocalTime> todayListedTimes = DatabaseConnect.getListOfReservedTimeForSelectedDay(DbController.selectedDate);
        System.out.println(todayListedTimes);



        ObservableList<LocalTime> showListOfAvailableTimes = FXCollections.observableArrayList(LocalTime.of(7, 0), LocalTime.of(8, 0), LocalTime.of(9, 0), LocalTime.of(10, 0), LocalTime.of(11, 0), LocalTime.of(12, 0), LocalTime.of(13, 0), LocalTime.of(14, 0), LocalTime.of(15, 0), LocalTime.of(16, 0));;
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


