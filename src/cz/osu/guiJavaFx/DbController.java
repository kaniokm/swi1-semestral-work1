package cz.osu.guiJavaFx;

import cz.osu.database.DatabaseConnect;
import cz.osu.database.DatabaseData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;


import java.awt.event.ActionEvent;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class DbController implements Initializable {
    @FXML private javafx.scene.control.TableView<DatabaseData> tableView;
    @FXML private TableColumn<DatabaseData,String> colName;
    @FXML private TableColumn<DatabaseData,String> colSurname;
    @FXML private TableColumn<DatabaseData,String> colPersonIdNumber;
    @FXML private TableColumn<DatabaseData,String> colPhone;
    @FXML private TableColumn<DatabaseData,String> colEmail;
    @FXML private TableColumn<DatabaseData,String> colElateNumber;
    @FXML private TableColumn<DatabaseData, Timestamp> colReservationTime;
    @FXML private TableColumn<DatabaseData,String> colNote;

    @FXML private Button btnCreate;
    @FXML private Button btnEdit;
    @FXML private Button btnDelete;


    @FXML private void handleButtonAction(ActionEvent event){
        System.out.println("xd");
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ShowData();
    }

    public ObservableList<DatabaseData> getDataList(){
        ObservableList<DatabaseData> reservationList = FXCollections.observableArrayList();
        Connection conn = DatabaseConnect.getDBConnection();
        String query = "SELECT * FROM customer";
        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);

            DatabaseData data;

            while (rs.next()){
                String first_name = rs.getString(2);
                String last_name = rs.getString(3);

                System.out.println(first_name);
                System.out.println(last_name);
               // data = new DatabaseData(first_name, last_name);
                data = new DatabaseData(rs.getString("first_name"), rs.getString("last_name"),rs.getString("person_id_number"),rs.getString("phone"),rs.getString("email"));
                reservationList.add(data);
            }

        }catch (SQLException throwables){
            throwables.printStackTrace();
            System.out.println("error here");
        }
        return reservationList;
    }
    public void ShowData(){
        ObservableList<DatabaseData> list = getDataList();
        colName.setCellValueFactory(new PropertyValueFactory<DatabaseData, String>("name"));
        colSurname.setCellValueFactory(new PropertyValueFactory<DatabaseData, String>("surname"));
        colPersonIdNumber.setCellValueFactory(new PropertyValueFactory<DatabaseData, String>("personIdNumber"));
        colPhone.setCellValueFactory(new PropertyValueFactory<DatabaseData, String>("phone"));
        colEmail.setCellValueFactory(new PropertyValueFactory<DatabaseData, String>("email"));
        tableView.setItems(list);
    }
}
