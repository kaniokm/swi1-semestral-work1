package cz.osu.database;

import java.sql.Time;
import java.sql.Timestamp;


public class DatabaseData {
    private int id;
    private String name;
    private String surname;
    private String personIdNumber;
    private String phone;
    private String email;
    private String plateNumber;
    private Timestamp reservationDate;
    private Time reservationTime;
    private Timestamp createdTime; //delete?
    private String note;
    //private boolean isNationalityCz;
    private String nationality;

    public DatabaseData(int id, String name, String surname, String personIdNumber, String phone, String email, String plateNumber, Timestamp reservationDate, Time reservationTime, Timestamp createdTime, String note, String nationality) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.personIdNumber = personIdNumber;
        this.phone = phone;
        this.email = email;
        this.plateNumber = plateNumber;
        this.reservationDate = reservationDate;
        this.reservationTime = reservationTime;
        //this.createdTime = reservationTime;
        this.note = note;
        //this.isNationalityCz = nationality;
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


    public Timestamp getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Timestamp reservationDate) {
        this.reservationDate = reservationDate;
    }

    public Time getReservationTime() {
        return reservationTime;
    }

    public void setReservationTime(Time reservationTime) {
        this.reservationTime = reservationTime;
    }

    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }
}

