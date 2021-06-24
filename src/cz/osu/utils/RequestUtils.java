package cz.osu.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cz.osu.controllers.MainWindowController;
import cz.osu.model.Reservation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static cz.osu.controllers.MainWindowController.selectedId;

public class RequestUtils {


    public static void requestDeleteReservation( javafx.scene.control.TableView<Reservation> tableView){
        try {


        int id = tableView.getSelectionModel().getSelectedItem().getId();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Potvrzení smazání.");
        alert.setHeaderText(null);
        alert.setContentText("Opravdu chcete smazat vybrané pole?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            try {

                URL url = new URL("http://localhost:8080/reservations/"+id);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();

                con.setConnectTimeout(5000);
                con.setReadTimeout(5000);
                con.setRequestMethod("DELETE");


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


                //tableView.getItems().removeAll(tableView.getSelectionModel().getSelectedItems());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    } catch (Exception e) {
        System.out.println("nothing selected");
    }}



    public static void requestEditReservation(DatePicker datePicker, TextField tfName, TextField tfSurname, TextField tfPersonIdNumber, TextField tfPlateNumber, TextField tfPhone, TextField tfEmail, ComboBox<LocalTime> comBoxReservedTime, RadioButton rdCz, TextArea tfNote)
    {
        try {
            URL url = new URL("http://localhost:8080/reservations/" + selectedId);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("PUT");


            String json = "{\n" +
                    "\"reservationDate\": \"" + datePicker.getValue() + "\",\n" +
                    "\"reservationTime\": \"" + comBoxReservedTime.getValue() + ":00" + "\",\n" +
                    "\"firstName\": \"" + tfName.getText() + "\",\n" +
                    "\"lastName\": \"" + tfSurname.getText() + "\",\n" +
                    "\"plateNumber\": \"" + tfPlateNumber.getText() + "\",\n" +
                    "\"personIdNumber\": \"" + tfPersonIdNumber.getText() + "\",\n" +
                    "\"phone\": \"" + tfPhone.getText() + "\",\n" +
                    "\"email\": \"" + tfEmail.getText() + "\",\n" +
                    "\"note\": \"" + tfNote.getText().replaceAll("[\r\n]+", " ") + "\",\n" +
                    "\"nationality\": \"" + (rdCz.isSelected() ? "cz" : "--") + "\"\n" +
                    "}";
            System.out.println(json);


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



            con.disconnect();


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void requestCreateNewReservation(TextField tfName, TextField tfSurname, TextField tfPersonIdNumber, TextField tfPlateNumber, TextField tfPhone, TextField tfEmail, ComboBox<LocalTime> comBoxReservedTime, RadioButton rdCz, TextArea tfNote ){
        try {

            URL url = new URL("http://localhost:8080/reservations");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            String date = MainWindowController.selectedDate.toString();

            String json = "{\n" +
                    "\"reservationDate\": \"" + MainWindowController.selectedDate+"\",\n" +
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
    }

    public static String getHTML(String urlToRead) {
        StringBuilder result = new StringBuilder();
        URL url = null;
        try {
            url = new URL(urlToRead);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            try (var reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()))) {
                for (String line; (line = reader.readLine()) != null; ) {
                    result.append(line);
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return result.toString();}



    public static ObservableList<Reservation> getReservationListForSelectedDay(LocalDate localDate) {




        ObservableList<Reservation> reservationListTest = FXCollections.observableArrayList();
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.findAndRegisterModules();
            List<Reservation> data = mapper.readValue(getHTML("http://localhost:8080/reservations/localdate?localDate="+localDate), new TypeReference<List<Reservation>>(){});
            reservationListTest = FXCollections.observableArrayList(data);


        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return reservationListTest;
    }

    public static ObservableList<LocalTime> getListOfReservedTimeForSelectedDay(LocalDate localDate) {
        ObservableList<LocalTime> reservationListTest = FXCollections.observableArrayList();
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.findAndRegisterModules();
            List<LocalTime> data = mapper.readValue(getHTML("http://localhost:8080/reservations/localdate/times?localDate="+localDate), new TypeReference<List<LocalTime>>(){});
            reservationListTest = FXCollections.observableArrayList(data);


        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return reservationListTest;
    }



    public static Reservation getSelectedReservationById(int id) {


        Reservation data = new Reservation();
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.findAndRegisterModules();
            data = mapper.readValue(getHTML("http://localhost:8080/reservations/"+id), Reservation.class);



        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return data;












    }






}

