package cz.osu.guiJavaFx;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

import static cz.osu.guiJavaFx.EditReservationController.VALID_EMAIL_ADDRESS_REGEX;

class EditReservationControllerTest {


    @org.junit.jupiter.api.Test
    void validateEmail1() {
        boolean ans = true;
        String num = "email@seznam.em";
        boolean value =  EditReservationController.validate(num, VALID_EMAIL_ADDRESS_REGEX);
        assertEquals(ans,value);
    }

    @org.junit.jupiter.api.Test
    void validateEmail2() {
        boolean ans = false;
        String num = "email";
        boolean value =  EditReservationController.validate(num, VALID_EMAIL_ADDRESS_REGEX);
        assertEquals(ans,value);
    }

    @org.junit.jupiter.api.Test
    void validateEmail3() {
        boolean ans = false;
        String num = "email@seznam";
        boolean value =  EditReservationController.validate(num, VALID_EMAIL_ADDRESS_REGEX);
        assertEquals(ans,value);
    }

    @org.junit.jupiter.api.Test
    void validateEmail4() {
        boolean ans = false;
        boolean val;
        String num = "@seznam.ca";
        boolean value =  EditReservationController.validate(num, VALID_EMAIL_ADDRESS_REGEX);
        assertEquals(ans,value);
    }

    @org.junit.jupiter.api.Test
    void validateEmail5() {
        boolean ans = false;
        String num = "email@seznam.c";
        boolean value =  EditReservationController.validate(num, VALID_EMAIL_ADDRESS_REGEX);
        assertEquals(ans,value);
    }
}