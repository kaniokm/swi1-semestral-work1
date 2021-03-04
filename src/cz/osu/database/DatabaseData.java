package cz.osu.database;

import java.sql.Timestamp;


public class DatabaseData{
    private int id;
    private String name;
    private String surname;
    private String personIdNumber;
    private String phone;
    private String email;
    private String plateNumber;
    private Timestamp reservationTime;
    private String note;

    public DatabaseData(int id, String name, String surname, String personIdNumber, String phone, String email, String plateNumber, Timestamp reservationTime, String note) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.personIdNumber = personIdNumber;
        this.phone = phone;
        this.email = email;
        this.plateNumber = plateNumber;
        this.reservationTime = reservationTime;
        this.note = note;

    }

    public DatabaseData() {
    }

    public DatabaseData(String name, String surname, String personIdNumber, String phone, String email) {
        this.name = name;
        this.surname = surname;
        this.personIdNumber = personIdNumber;
        this.phone = phone;
        this.email = email;
    }

    public DatabaseData(String name, String surname) {
        this.name = name;
        this.surname = surname;
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

    public Timestamp getReservationTime() {
        return reservationTime;
    }

    public void setReservationTime(Timestamp reservationTime) {
        this.reservationTime = reservationTime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}

