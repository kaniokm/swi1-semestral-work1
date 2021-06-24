package cz.osu.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;


public class Reservation {
     @JsonProperty("id")
     int id;
     @JsonProperty("firstName")
     String name;
     @JsonProperty("lastName")
     String surname;
     @JsonProperty("personIdNumber")
     String personIdNumber;
     @JsonProperty("phone")
     String phone;
     @JsonProperty("email")
     String email;
     @JsonProperty("plateNumber")
     String plateNumber;
     @JsonProperty("reservationDate")
     LocalDate reservationDate;
     @JsonProperty("reservationTime")
     LocalTime reservationTime;

    String note;
    //private boolean isNationalityCz;
     String nationality;


    public Reservation() {
    }

    public Reservation(int id, String name, String surname, String personIdNumber, String phone, String email, String plateNumber,
                       LocalDate reservationDate, LocalTime reservationTime, String note, String nationality) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.personIdNumber = personIdNumber;
        this.phone = phone;
        this.email = email;
        this.plateNumber = plateNumber;
        this.reservationDate = reservationDate;
        this.reservationTime = reservationTime;
        this.note = note;
        this.nationality = nationality;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPersonIdNumber() {
        return personIdNumber;
    }

    public void setPersonIdNumber(String personIdNumber) {
        this.personIdNumber = personIdNumber;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }


    public LocalDate getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(LocalDate reservationDate) {
        this.reservationDate = reservationDate;
    }

    public LocalTime getReservationTime() {
        return reservationTime;
    }

    public void setReservationTime(LocalTime reservationTime) {
        this.reservationTime = reservationTime;
    }



    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "DatabaseData{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", personIdNumber='" + personIdNumber + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", plateNumber='" + plateNumber + '\'' +
                ", reservationDate=" + reservationDate +
                ", reservationTime=" + reservationTime +
                ", note='" + note + '\'' +
                ", nationality='" + nationality + '\'' +
                '}';
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }
}

