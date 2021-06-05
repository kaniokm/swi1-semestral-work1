package com.greglturnquist.validation;

import com.greglturnquist.payroll.Reservation;
import com.greglturnquist.payroll.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReservationValidator implements ConstraintValidator<ValidReservation, Reservation> {
    @Override
    public void initialize(ValidReservation constraintAnnotation) {

    }

    @Autowired
    ReservationRepository repository;

    @Override
    public boolean isValid(Reservation reservation, ConstraintValidatorContext constraintValidatorContext) {
        //System.out.println("try to validate");
        if (!validateCorrectTimeDate(reservation))
            return false;

        if (!validateCzechPersonIdNumber(reservation))
            return false;


        return true;
    }

    private boolean validateCorrectTimeDate(Reservation reservation) {
        List<Reservation> data = repository.findAllByReservationDate(reservation.getReservationDate());

        ArrayList<LocalTime> unavailableTimes= new ArrayList<>();
        for (Reservation d : data) {
           unavailableTimes.add(d.getReservationTime());
        }

        ArrayList<LocalTime> allTimes = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            allTimes.add( LocalTime.of(7+i, 0));
        }

        allTimes.removeAll(unavailableTimes);

        for (LocalTime a : allTimes) {
            if (a.equals(reservation.getReservationTime())) {
                return true;
            }
        }
        return false;
    }

    private boolean validateCzechPersonIdNumber(Reservation reservation) {
        if (reservation.getNationality().equals("cz")) {
            String number = reservation.getPersonIdNumber();
            if(!validateCzechBirthNumberSyntax(number)){
                return false;
            }

            if (number.contains("/"))
                number = number.replace("/", "");
            if (number.length() != 10) {
                return false;
            }
            if (Integer.parseInt(number.substring(4, 6)) < 1 || Integer.parseInt(number.substring(4, 6)) > 31 ||
                    Integer.parseInt(number.substring(2, 4)) < 1 || Integer.parseInt(number.substring(2, 4)) > 62 ||
                    (Integer.parseInt(number.substring(2, 4)) > 12 && Integer.parseInt(number.substring(2, 4)) < 51) ||
                    ((Integer.parseInt(number.substring(0, 9)) % 11) != Integer.parseInt(number.substring(9, 10))))
                return false;
            else
            	return true;
        }
        return true;
    }

    public static boolean validateCzechBirthNumberSyntax(String number) {
        Pattern pattern = Pattern.compile("^[0-9]{6}\\/?[0-9]{4}$");
        Matcher matcher = pattern.matcher(number);
        return matcher.find();
    }

    public static boolean validatePhone(String number) {
        Pattern pattern = Pattern.compile("^[+]?[()\0-9. -]{9,}$");
        Matcher matcher = pattern.matcher(number);
        return matcher.find();
    }


}
