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

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.time.LocalTime;
import java.util.ResourceBundle;

import static cz.osu.guiJavaFx.DbController.selectedId;
import static cz.osu.guiJavaFx.DbController.selectedTime;

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







        ObservableList<LocalTime> todaysListedTimes = DatabaseConnect.getListOfReservedTimeForSelectedDay(DbController.selectedDate);

        System.out.println(todaysListedTimes);


        listedTimes.addAll(todaysListedTimes);
        listedTimes.remove(selectedTime);

        DatabaseData data = DatabaseConnect.getSelectedDataById(selectedId);

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


    public void editReservationToDb(ActionEvent actionEvent) {
        if (tfName.getText().equals("") || tfSurname.getText().equals("") || tfPersonIdNumber.getText().equals("") || tfPlateNumber.getText().equals("") || (tfPhone.getText().equals("") || tfEmail.getText().equals(""))) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Vyplňte prosím důležitá pole !!!");
            alert.showAndWait();
        } else {
            URL url = null;
            try {
                url = new URL("http://localhost:8080/reservations/"+ selectedId);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("PUT");


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

    public void closeButtonAction(ActionEvent actionEvent) {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.setOnCloseRequest((WindowEvent event) -> {
            System.out.println("Close Requested");
        });
        stage.close();
    }


}
