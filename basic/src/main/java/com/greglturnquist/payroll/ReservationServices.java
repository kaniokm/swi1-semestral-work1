package com.greglturnquist.payroll;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.greglturnquist.payroll.Reservation;
import com.greglturnquist.payroll.ReservationRepository;

@Service
public class ReservationServices {
    @Autowired
    ReservationRepository repository;

    public Reservation saveReservation(Reservation reservation) {
        return repository.save(reservation);
    }

    public List<Reservation> getReservationInfos(){
        return repository.findAll();
    }

    public Reservation getReservationById(long id) {
        return repository.findById(id).get();
    }

    public boolean checkExistedCustomer(long id) {
        if(repository.existsById((long) id)) {
            return true;
        }
        return false;
    }

    public Reservation updateCustomer(Reservation reservation) {
        return (Reservation) repository.save(reservation);
    }

    public void deleteCustomerById(long id) {
        repository.deleteById(id);
    }
}
