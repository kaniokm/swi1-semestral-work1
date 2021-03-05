package cz.osu.guiJavaFx;

import cz.osu.database.DatabaseConnect;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class CreateReservarionController  implements Initializable {
    @FXML private TextField tfName;
    @FXML private TextField tfSurname;
    @FXML private TextField tfPersonIdNumber;
    @FXML private TextField tfPhone;
    @FXML private TextField tfEmail;
    @FXML private TextField tfPlateNumber;
    @FXML private TextArea tfNote;

    @FXML public javafx.scene.control.Button btnClose;
    @FXML public javafx.scene.control.Button btnSave;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //TODO
    }

    public void createNewReservationToDb(ActionEvent actionEvent) throws Exception {
        String query = "INSERT INTO reservation_table (first_name, last_name, person_id_number, phone, email, plate_number, note, reservation_date, reservation_time) VALUES ('"+ tfName.getText() +"', '"+ tfSurname.getText() +"', '"+tfPersonIdNumber.getText() +"', '"+tfPhone.getText() +"', '"+tfEmail.getText() +"', '"+tfPlateNumber.getText() +"', '"+tfNote.getText() + "', '"+ LocalDate.now()+ "', '"+ LocalTime.now() +"');";
        //TODO load available time separated by 1/2 hour
        //TODO check inserted time
        //TODO make statement for czech - cizinec

        Connection conn = DatabaseConnect.getDBConnection();
        Statement st;
        System.out.println(query);

        try {
            st = conn.createStatement();
            st.executeUpdate(query);

            Stage stage = (Stage) btnClose.getScene().getWindow();
            //Platform.isImplicitExit();
            //TODO somehow call refresh
            stage.close();
            //Platform.exit();

        } catch (SQLException e) {
            e.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Špatně zadaná hodnota !!!");
            alert.showAndWait();

            //throw new Exception("unable to save to db");
        }

    }

    public void closeButtonAction(ActionEvent actionEvent) {

        //((Stage)(((Button)actionEvent.getSource()).getScene().getWindow())).close();

        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.setOnCloseRequest((WindowEvent event) -> {
            System.out.println("Close Requested");
            //TODO somehow call refresh

        });
        stage.close();
    }
}
