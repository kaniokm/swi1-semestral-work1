package com.greglturnquist.payroll;



import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


@RestController
public class ReservationController {

    @Autowired

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
    @GetMapping("/reservations/localdate/timesjson")
    JSONArray allTimesByDateJSON(@RequestParam("localDate")
                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate localDate)
    {


        List<Reservation> data = repository.findAllByReservationDate(localDate);


        JSONArray reservedTimes = new JSONArray();
        JSONArray allTimes = new JSONArray();
        JSONArray availableTimes = new JSONArray();

        LocalTime time;
        time = LocalTime.of(7,0,0);


        for (int i = 0; i < 10; i++) {
            JSONObject allValuesObject = new JSONObject();
            allValuesObject.put("value",time);
            allValuesObject.put("label",time);
            allTimes.add(allValuesObject);
            time=time.plusHours(1);
        }




        for (Reservation r:data
        ) {
            JSONObject valuesObject = new JSONObject();
            valuesObject.put("value",r.getReservationTime());
            valuesObject.put("label",r.getReservationTime());

            reservedTimes.add(valuesObject);

        }


        availableTimes = allTimes;
        availableTimes.removeAll(reservedTimes);




        return  availableTimes;
    }


    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/reservations/{id}")
    Reservation get(@PathVariable Long id) {
        Reservation reservation = repository.findById(id).orElse(null);
        return  reservation;


    }


    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/reservations")
    Reservation newOrder(@Valid @RequestBody Reservation newReservation)
    {
        //System.out.println("validated");
        if (newReservation.getNationality().equals("cz") && !newReservation.getPersonIdNumber().contains("/")){
            newReservation.setPersonIdNumber(newReservation.getPersonIdNumber().substring(0,6)+"/"+newReservation.getPersonIdNumber().substring(6));
            log.info("added missing '/' to czech personIdNumber");
        }
        return repository.save(newReservation);
    }








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




    @DeleteMapping("/reservations/{id}")
    void deleteCustomerByID(@PathVariable Long id)
    {
        log.info("Delete /reservations");
        repository.deleteById(id);
    }


}
