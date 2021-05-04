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


import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;


/**
 * @author Greg Turnquist
 */
// tag::code[]
@Component // <1>
public class ReservationDatabaseLoader implements CommandLineRunner { // <2>

	private static final Logger log = LoggerFactory.getLogger(ReservationRepository.class);

	private final ReservationRepository repository;

	LocalDateTime localDateTime = LocalDateTime.now();



	@Autowired // <3>
	public ReservationDatabaseLoader(ReservationRepository repository) {
		this.repository = repository;
	}

	@Override
	public void run(String... strings) throws Exception {
		log.info("Preloading data");// <4>

		LocalDate ts;
		ts = LocalDate.now();
		LocalTime dt;
		dt = LocalTime.of(12,0);

		this.repository.save(new Reservation("Tom","Novak","1212995647","777777777","email@email.cz","2T74545" ,ts,dt,"svetla","Ceska"));



		log.info("Number of loaded customers: " + this.repository.count());
	}
}
// end::code[]
