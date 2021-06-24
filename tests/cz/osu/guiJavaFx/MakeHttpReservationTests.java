package cz.osu.guiJavaFx;

import cz.osu.utils.RequestUtils;
import cz.osu.model.Reservation;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Assertions;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;

//import org.json.JSONException;
//import org.json.JSONObject;

public class MakeHttpReservationTests {

    private static LocalDate date2 = LocalDate.of(1801, 1, 2);
    private static LocalTime lt2 = LocalTime.of(9, 0);

    @org.junit.jupiter.api.Test
    void PostTwiceAndDelete() throws IOException {
        LocalDate date = LocalDate.of(1801, 1, 1);
        //      LocalDate date = LocalDate.of(2021, 6, 11);
        LocalTime lt = LocalTime.of(12, 0);

       if (!post(date,lt)) {
           System.out.println("first post failed (dunno, maby time date already used)");
           Assertions.fail();Assertions.fail();
       }
        if (post(date,lt)) {
            System.out.println("second post failed (the same post was posted, but shoould be able to, please delete it manually from db) ");
            Assertions.fail();
        }
        if (!delete(date,lt)) {
            System.out.println("Deletion failed (please delete manually)");
            Assertions.fail();
        }
    }


    private boolean post(LocalDate date, LocalTime lt) {
        URL url = null;
        try {
            url = new URL("http://localhost:8080/reservations");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");

            String json = "{\n" +
                    "\"reservationDate\": \"" +date+"\",\n" +
                    "\"reservationTime\": \"" +lt+"\",\n" +
                    "\"firstName\": \"" +"unitposttest"+"\",\n" +
                    "\"lastName\": \"" +"unitposttest"+"\",\n" +
                    "\"plateNumber\": \"" +"unitposttest"+"\",\n" +
                    "\"personIdNumber\": \"" +"011012/5961"+"\",\n" +
                    "\"phone\": \"" +"123456789"+"\",\n" +
                    "\"email\": \"" +"email@mail.mm"+"\",\n" +
                    "\"note\": \"" +"unitnotee"+"\",\n" +
                    "\"nationality\": \"" +"cz"+"\"\n" +
                    "}";
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);
            con.setDoOutput(true);
            con.setRequestProperty("Content-Type", "application/json");
            DataOutputStream out = new DataOutputStream(con.getOutputStream());

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
            System.out.println(content);
            in.close();

            Reader streamReader = null;
            if (status > 299) {
                streamReader = new InputStreamReader(con.getErrorStream());
                con.disconnect();
                return false;
            } else {
                streamReader = new InputStreamReader(con.getInputStream());
                con.disconnect();
                return true;
            }

        } catch (IOException e) {
           // e.printStackTrace();
        }
        return false;
    }

    @org.junit.jupiter.api.Test
    void unallowedPost0() {
        if (post(date2,LocalTime.of(12, 5))) {
            System.out.println("bad time minutes ");
            Assertions.fail();
        }
    }

    @org.junit.jupiter.api.Test
    void unallowedPost00() {
        if (post(date2,LocalTime.of(17, 0))) {
            System.out.println("bad time hours ");
            Assertions.fail();
        }
    }

    @org.junit.jupiter.api.Test
    void unallowedPost1() {
        String json = "{\n" +
                "\"reservationDate\": \"" +date2+"\",\n" +
                "\"reservationTime\": \"" +lt2.plusHours(1)+"\",\n" +
                "\"plateNumber\": \"" +"unitposttest"+"\",\n" +
                "\"personIdNumber\": \"" +"011012/5961"+"\",\n" +
                "\"phone\": \"" +"123456789"+"\",\n" +
                "\"email\": \"" +"email@mail.mm"+"\",\n" +
                "\"note\": \"" +"unitnotee"+"\",\n" +
                "\"nationality\": \"" +"cz"+"\"\n" +
                "}";
        if (post2Json(json)) {
            System.out.println("bad json, missing values, shouldnt went");
            Assertions.fail();
        }
    }

    @org.junit.jupiter.api.Test
    void unallowedPost2() {
        String json = "{\n" +
                "\"reservationDate\": \"" +date2+"\",\n" +
                "\"reservationTime\": \"" +lt2.plusHours(2)+"\",\n" +
                "\"firstName\": \"" +""+"\",\n" +
                "\"lastName\": \"" +""+"\",\n" +
                "\"plateNumber\": \"" +""+"\",\n" +
                "\"personIdNumber\": \"" +"011012/5961"+"\",\n" +
                "\"phone\": \"" +"123456789"+"\",\n" +
                "\"email\": \"" +"email@mail.mm"+"\",\n" +
                "\"note\": \"" +""+"\",\n" +
                "\"nationality\": \"" +"cz"+"\"\n" +
                "}";
        if (post2Json(json)) {
            System.out.println("bad json2 missing values");
            Assertions.fail();
        }
    }

    @org.junit.jupiter.api.Test
    void unallowedPost3() {
        String json = "{\n" +
                "\"reservationDate\": \"" +date2+"\",\n" +
                "\"reservationTime\": \"" +lt2.plusHours(3)+"\",\n" +
                "\"firstName\": \"" +"unitposttest"+"\",\n" +
                "\"lastName\": \"" +"unitposttest"+"\",\n" +
                "\"plateNumber\": \"" +"unitposttest"+"\",\n" +
                "\"personIdNumber\": \"" +"011012/5961"+"\",\n" +
                "\"phone\": \"" +"123456789"+"\",\n" +
                "\"email\": \"" +"email@mail.mm"+"\",\n" +
                "\"note\": \"" +"unitnotee"+"\",\n" +
                "\"nationality\": \"" +"cze"+"\"\n" +
                "}";
        if (post2Json(json)) {
            System.out.println("bad json3 bad nationality");
            Assertions.fail();
        }
    }

    @org.junit.jupiter.api.Test
    void unallowedPost4() {
        String json = "{\n" +
                "\"reservationDate\": \"" +"xd"+"\",\n" +
                "\"reservationTime\": \"" +lt2.plusHours(4)+"\",\n" +
                "\"firstName\": \"" +"unitposttest"+"\",\n" +
                "\"lastName\": \"" +"unitposttest"+"\",\n" +
                "\"plateNumber\": \"" +"unitposttest"+"\",\n" +
                "\"personIdNumber\": \"" +"011012/5961"+"\",\n" +
                "\"phone\": \"" +"123456789"+"\",\n" +
                "\"email\": \"" +"email@mail.mm"+"\",\n" +
                "\"note\": \"" +"unitnotee"+"\",\n" +
                "\"nationality\": \"" +"cz"+"\"\n" +
                "}";
        if (post2Json(json)) {
            System.out.println("bad json4 bad date");
            Assertions.fail();
        }

    }

    @org.junit.jupiter.api.Test
    void unallowedPost5() {
        String json = "{\n" +
                "\"reservationDate\": \"" +date2+"\",\n" +
                "\"reservationTime\": \"" +"xd"+"\",\n" +
                "\"firstName\": \"" +"unitposttest"+"\",\n" +
                "\"lastName\": \"" +"unitposttest"+"\",\n" +
                "\"plateNumber\": \"" +"unitposttest"+"\",\n" +
                "\"personIdNumber\": \"" +"011012/5961"+"\",\n" +
                "\"phone\": \"" +"123456789"+"\",\n" +
                "\"email\": \"" +"email@mail.mm"+"\",\n" +
                "\"note\": \"" +"unitnotee"+"\",\n" +
                "\"nationality\": \"" +"cz"+"\"\n" +
                "}";
        if (post2Json(json)) {
            System.out.println("bad json5 bad time2");
            Assertions.fail();
        }
    }

    @org.junit.jupiter.api.Test
    void unallowedPost6() {
        String json = "{\n" +
                "\"reservationDate\": \"" +date2+"\",\n" +
                "\"reservationTime\": \"" +lt2.plusHours(5)+"\",\n" +
                "\"firstName\": \"" +"unitposttest"+"\",\n" +
                "\"lastName\": \"" +"unitposttest"+"\",\n" +
                "\"plateNumber\": \"" +"unitposttest"+"\",\n" +
                "\"personIdNumber\": \"" +"011012/5961"+"\",\n" +
                "\"phone\": \"" +"asd"+"\",\n" +
                "\"email\": \"" +"email@mail.mm"+"\",\n" +
                "\"note\": \"" +"unitnotee"+"\",\n" +
                "\"nationality\": \"" +"cz"+"\"\n" +
                "}";
        if (post2Json(json)) {
            System.out.println("bad json6 bad phone");
            Assertions.fail();
        }
    }

    @org.junit.jupiter.api.Test
    void unallowedPost7() {
        String json = "";
        if (post2Json(json)) {
            System.out.println("bad json7 empty string");
            Assertions.fail();
        }
    }


    private boolean post2Json(String json) {
        URL url = null;
        try {
            url = new URL("http://localhost:8080/reservations");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");

            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);
            con.setDoOutput(true);
            con.setRequestProperty("Content-Type", "application/json");
            DataOutputStream out = new DataOutputStream(con.getOutputStream());

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
            System.out.println(content);
            in.close();

            Reader streamReader = null;

            if (status > 299) {
                streamReader = new InputStreamReader(con.getErrorStream());
                con.disconnect();
                return false;
            } else {
                streamReader = new InputStreamReader(con.getInputStream());
                con.disconnect();
                return true;
            }

        } catch (IOException e) {
           // e.printStackTrace();
        }
        return false;
    }

    private boolean delete(LocalDate date, LocalTime lt) throws IOException {
        Reservation dd = null;
        ObservableList<Reservation> reservationList = RequestUtils.getReservationListForSelectedDay(date);
        for (Reservation reservation : reservationList) {
            System.out.println(reservation);
            if (reservation.getReservationTime()==lt){
                dd = reservation;
                break;
            }
        }

       if (dd==null) {
          // fail();
           return false;
       }

       /////
        try {
            URL url;
            url = new URL("http://localhost:8080/reservations/"+dd.getId());
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
        }catch (Exception e){
            return false;
        }
            return true;
    }

}