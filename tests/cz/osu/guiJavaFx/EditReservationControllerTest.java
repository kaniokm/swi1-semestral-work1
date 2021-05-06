package cz.osu.guiJavaFx;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;


class EditReservationControllerTest {


    @org.junit.jupiter.api.Test
    void validateEmail1() {
        boolean ans = true;
        String num = "email@seznam.em";
        boolean value =  EditReservationController.validateEmail(num);
        assertEquals(ans,value);
    }

    @org.junit.jupiter.api.Test
    void validateEmail2() {
        boolean ans = false;
        String num = "email";
        boolean value =  EditReservationController.validateEmail(num);
        assertEquals(ans,value);
    }

    @org.junit.jupiter.api.Test
    void validateEmail3() {
        boolean ans = false;
        String num = "email@seznam";
        boolean value =  EditReservationController.validateEmail(num);
        assertEquals(ans,value);
    }

    @org.junit.jupiter.api.Test
    void validateEmail4() {
        boolean ans = false;
        boolean val;
        String num = "@seznam.ca";
        boolean value =  EditReservationController.validateEmail(num);
        assertEquals(ans,value);
    }

    @org.junit.jupiter.api.Test
    void validateEmail5() {
        boolean ans = false;
        String num = "email@seznam.c";
        boolean value =  EditReservationController.validateEmail(num);
        assertEquals(ans,value);

    }


    @org.junit.jupiter.api.Test
    void validatephone() {
        boolean ans = true;
        String num = "+420 777 11 22 33";
        boolean value =  EditReservationController.validatePhone(num);
        assertEquals(ans,value);
    }

    @org.junit.jupiter.api.Test
    void validatephone1() {
        boolean ans = true;
        String num = "777 112 233, 123 456 789";
        boolean value =  EditReservationController.validatePhone(num);
        assertEquals(ans,value);
    }

    @org.junit.jupiter.api.Test
    void validatephone2() {
        boolean ans = true;
        String num = "+420 777112233";
        boolean value =  EditReservationController.validatePhone(num);
        assertEquals(ans,value);
    }

    @org.junit.jupiter.api.Test
    void validatephone3() {
        boolean ans = true;
        String num = "777 11 22 33";
        boolean value =  EditReservationController.validatePhone(num);
        assertEquals(ans,value);
    }

    @org.junit.jupiter.api.Test
    void validatephone4() {
        boolean ans = false;
        String num = "asd";
        boolean value =  EditReservationController.validatePhone(num);
        assertEquals(ans,value);
    }

    @org.junit.jupiter.api.Test
    void validatephon5() {
        boolean ans = false;
        String num = "123 456 1w8";
        boolean value =  EditReservationController.validatePhone(num);
        assertEquals(ans,value);
    }

    @org.junit.jupiter.api.Test
    void validatephon6() {
        boolean ans = true;
        String num = "00420 123 456 180";
        boolean value =  EditReservationController.validatePhone(num);
        assertEquals(ans,value);
    }


}