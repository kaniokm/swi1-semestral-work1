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
    @FXML private TableColumn<DatabaseData,Integer> colId;
    @FXML private TableColumn<DatabaseData,String> colName;
    @FXML private TableColumn<DatabaseData,String> colSurname;
    @FXML private TableColumn<DatabaseData,String> colPersonIdNumber;
    @FXML private TableColumn<DatabaseData,String> colPhone;
    @FXML private TableColumn<DatabaseData,String> colEmail;
    @FXML private TableColumn<DatabaseData,String> colPlateNumber;
    @FXML private TableColumn<DatabaseData, Timestamp> colReservationDateTime;
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
                //                                                 (int id, String name, String surname, String personIdNumber, String phone,                                                                                                                   String email, String plateNumber, Timestamp reservationTime, Timestamp createdTime, String note)
                data = new DatabaseData(rs.getInt("id"),rs.getString("first_name"), rs.getString("last_name"),rs.getString("person_id_number"),rs.getString("phone"),rs.getString("email"),rs.getString("plate_number"),rs.getTimestamp("reservated_time"),rs.getTimestamp("created_at"),rs.getString("note"));
              //  data = new DatabaseData(rs.getInt("id"),rs.getString("first_name"), rs.getString("last_name"),rs.getString("person_id_number"),rs.getString("phone"),rs.getString("email"),rs.getString("plate_number"),rs.getString("note"));
                //data = new DatabaseData(rs.getInt("id"),rs.getString("first_name"), rs.getString("last_name"));
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
        colId.setCellValueFactory(new PropertyValueFactory<DatabaseData, Integer>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<DatabaseData, String>("name"));
        colSurname.setCellValueFactory(new PropertyValueFactory<DatabaseData, String>("surname"));
        colPersonIdNumber.setCellValueFactory(new PropertyValueFactory<DatabaseData, String>("personIdNumber"));
        colPhone.setCellValueFactory(new PropertyValueFactory<DatabaseData, String>("phone"));
        colEmail.setCellValueFactory(new PropertyValueFactory<DatabaseData, String>("email"));
        colPlateNumber.setCellValueFactory(new PropertyValueFactory<DatabaseData, String>("plateNumber"));
        colReservationDateTime.setCellValueFactory(new PropertyValueFactory<DatabaseData, Timestamp>("reservationTime"));
        colNote.setCellValueFactory(new PropertyValueFactory<DatabaseData, String>("note"));

        tableView.setItems(list);
    }
}
