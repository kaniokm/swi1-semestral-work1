package com.greglturnquist.payroll;



import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


@RestController
public class ReservationController {

    @Autowired
    ReservationServices reservationServices;
    private static final Logger log = LoggerFactory.getLogger(ReservationRepository.class);

    private final ReservationRepository repository;

    ReservationController(ReservationRepository repository) {this.repository = repository;}




    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/reservations")
    List<Reservation> all()
    {
        log.info("GetMapping /reservations");

        List<Reservation> ret = repository.findAll();

        log.info("Found "+ ret.size()+" reservations.");

        for(Reservation o : ret)
        {
            log.info("Order "+ o);
        }
        return ret;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/reservations/localdate")
    List<Reservation> allByDate(@RequestParam("localDate")
                                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate localDate)
    {


        List<Reservation> ret = repository.findAllByReservationDate(localDate);

        for(Reservation o : ret)
        {
            log.info("Order "+ o);
        }
        return ret;
    }


    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/reservations/localdate/times")
    List<LocalTime> allTimesByDate(@RequestParam("localDate")
                                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate localDate)
    {


        List<Reservation> data = repository.findAllByReservationDate(localDate);
        List<LocalTime> ret = new ArrayList<LocalTime>();
        
        for (Reservation r:data
             ) {
            ret.add(r.getReservationTime());
            
        }



        return  ret;
    }


    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/reservations/{id}")
    Reservation get(@PathVariable Long id) {
        Reservation reservation = repository.findById(id).orElse(null);
        return  reservation;


    }


    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/reservations")
    Reservation newOrder(@RequestBody Reservation newReservation)
    {

        return repository.save(newReservation);
    }

   /* @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("/reservations/{id}")
    Reservation updateOrder(@RequestBody Reservation newReservation, @PathVariable Long id) {
        return reservationServices.saveReservation(newReservation);
    }




   @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/reservations/{id}")
    Reservation getOrderById(@PathVariable Long id)
    {
        Reservation ret = repository.findById(id).orElseThrow(() -> new IllegalStateException("Order id: "+ id +" not found."));

        return ret;
    }
    */

    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("/reservations/{id}")
    Reservation replaceOrder(@RequestBody Reservation newReservation, @PathVariable(value="id") Long id)
    {

        Reservation ret =  repository.findById(id)
                .map(reservation -> {
                    reservation.setFirstName(newReservation.getFirstName());
                    reservation.setLastName(newReservation.getLastName());
                    reservation.setPersonIdNumber(newReservation.getPersonIdNumber());
                    reservation.setPhone(newReservation.getPhone());
                    reservation.setEmail(newReservation.getEmail());
                    reservation.setPlateNumber(newReservation.getPlateNumber());
                    reservation.setReservationDate(newReservation.getReservationDate());
                    reservation.setReservationTime(newReservation.getReservationTime());
                    reservation.setNote(newReservation.getNote());
                    reservation.setNationality(newReservation.getNationality());
                return repository.save(reservation);
                }).orElseGet(() -> {

                    newReservation.setId(newReservation.getId());
                    return repository.save(newReservation);
                });
        return ret;
    }

/*
    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("/reservations/{id}")
    Reservation replaceOrder(@RequestBody Reservation newReservation, @PathVariable Long id)
    {

        Reservation ret =  repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found on :: "+ id));;
        ret.setFirstName(newReservation.getFirstName());
        ret.setLastName(newReservation.getLastName());
        ret.setPersonIdNumber(newReservation.getPersonIdNumber());
        ret.setPhone(newReservation.getPhone());
        ret.setEmail(newReservation.getEmail());
        ret.setPlateNumber(newReservation.getPlateNumber());
        ret.setReservationDate(newReservation.getReservationDate());
        ret.setReservationTime(newReservation.getReservationTime());
        ret.setNote(newReservation.getNote());
        ret.setNationality(newReservation.getNationality());
        reservationServices.saveReservation(ret);

        return ret;
    }




*/


    @DeleteMapping("/reservations/{id}")
    void deleteCustomerByID(@PathVariable Long id)
    {
        log.info("Delete /reservations");
        repository.deleteById(id);
    }


}
