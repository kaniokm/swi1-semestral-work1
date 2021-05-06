package cz.osu.database;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import cz.osu.guiJavaFx.DbController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.*;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class DatabaseConnect {




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



    public static ObservableList<DatabaseData> getDatabaseDataListForSelectedDay(LocalDate localDate) {




        ObservableList<DatabaseData> reservationListTest = FXCollections.observableArrayList();
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.findAndRegisterModules();
            List<DatabaseData> data = mapper.readValue(getHTML("http://localhost:8080/reservations/localdate?localDate="+localDate), new TypeReference<List<DatabaseData>>(){});
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



    public static DatabaseData getSelectedDatabaseDataById(int id) {


        DatabaseData data = new DatabaseData();
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.findAndRegisterModules();
            data = mapper.readValue(getHTML("http://localhost:8080/reservations/"+id), DatabaseData.class);



        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return data;












    }






}

