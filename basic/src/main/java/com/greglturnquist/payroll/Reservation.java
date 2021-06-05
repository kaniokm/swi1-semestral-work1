/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.greglturnquist.payroll;

import com.greglturnquist.validation.ValidReservation;
//import com.greglturnquist.validation.ValidCzechPersonIdNumber;

import org.hibernate.annotations.Target;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.*;

/**
 * @author Greg Turnquist
 */
// tag::code[]
@Entity // <1>

@ValidReservation
public class Reservation {


	 @Id @GeneratedValue private long id;
	@NotBlank(message = "First name is mandatory")private String firstName;

	@NotBlank(message = "Last name is mandatory")private String lastName;

	@NotBlank(message = "Personal identification number is mandatory")
//	@Pattern(regexp="^[0-9]{6}\\/?[0-9]{4}$",message="unallowed symbols or lenght")
	//@ValidCzechPersonIdNumber(message = "czech id number is incorrect")
	private String personIdNumber;

	@NotBlank (message = "Phone is mandatory")
	@Pattern(regexp="^[+]?[()\0-9. -]{9,}$",message="uncorrect phone pattern")
	private String phone;

	@NotBlank(message = "Email is mandatory")
	@Email(message = "Email have to be valid")
	private String email;

	@NotBlank(message = "Registration plate is mandatory")private String plateNumber;

	@NotNull
	private LocalDate reservationDate;

	@NotNull
	private LocalTime reservationTime;

	private String note;

	@NotBlank(message = "Nationality is mandatory")private String nationality;

	public Reservation() {}

	public Reservation( String firstName, String lastName, String personIdNumber, String phone, String email, String plateNumber, LocalDate reservationDate, LocalTime reservationTime, String note, String nationality) {

		this.firstName = firstName;
		this.lastName = lastName;
		this.personIdNumber = personIdNumber;
		this.phone = phone;
		this.email = email;
		this.plateNumber = plateNumber;
		this.reservationDate = reservationDate;
		this.reservationTime = reservationTime;
		this.note = note;
		this.nationality = nationality;
	}

	/*@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Order order = (Order) o;
		return Objects.equals(id, order.id) &&
			Objects.equals(name, order.name) &&
			Objects.equals(surname, order.surname) &&
			Objects.equals(personIdNumber, order.personIdNumber)&&
			Objects.equals(phone, order.phone)&&
			Objects.equals(email, order.email)&&
			Objects.equals(plateNumber, order.plateNumber)&&
			Objects.equals(reservationDate, order.reservationDate)&&
			Objects.equals(reservationTime, order.reservationTime)&&
			Objects.equals(note, order.note)&&
			Objects.equals(nationality, order.nationality);
	}

	@Override
	public int hashCode() {

		return Objects.hash(id, name, surname, personIdNumber, phone, email, plateNumber, reservationDate, reservationTime, note, nationality);
	}
	*/


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String name) {
		this.firstName = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String surname) {
		this.lastName = surname;
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

	public void setReservationDate(LocalDate reservation_date) {
		this.reservationDate = reservation_date;
	}

	public LocalTime getReservationTime() {
		return reservationTime;
	}

	public void setReservationTime(LocalTime reservation_time) {
		this.reservationTime = reservation_time;
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

	@Override
	public String toString() {
		return "Order{" +

				", name='" + firstName + '\'' +
				", surname='" + lastName + '\'' +
				", personIdNumber='" + personIdNumber + '\'' +
				", phone='" + phone + '\'' +
				", email='" + email + '\'' +
				", plateNumber='" + plateNumber + '\'' +

				", note='" + note + '\'' +
				", nationality='" + nationality + '\'' +
				'}';
	}

}
// end::code[]
