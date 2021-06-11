package cz.osu.guiJavaFx;

import static org.junit.jupiter.api.Assertions.*;


class ValidationsTest {


    @org.junit.jupiter.api.Test
    void validateEmail1() {
        boolean ans = true;
        String num = "email@seznam.em";
        boolean value =  Validations.validateEmail(num);
        assertEquals(ans,value);
    }

    @org.junit.jupiter.api.Test
    void validateEmail2() {
        boolean ans = false;
        String num = "email";
        boolean value =  Validations.validateEmail(num);
        assertEquals(ans,value);
    }

    @org.junit.jupiter.api.Test
    void validateEmail3() {
        boolean ans = false;
        String num = "email@seznam";
        boolean value =  Validations.validateEmail(num);
        assertEquals(ans,value);
    }

    @org.junit.jupiter.api.Test
    void validateEmail4() {
        boolean ans = false;
        boolean val;
        String num = "@seznam.ca";
        boolean value =  Validations.validateEmail(num);
        assertEquals(ans,value);
    }

    @org.junit.jupiter.api.Test
    void validateEmail5() {
        boolean ans = false;
        String num = "email@seznam.c";
        boolean value =  Validations.validateEmail(num);
        assertEquals(ans,value);
    }

    @org.junit.jupiter.api.Test
    void validateEmail6() {
        boolean ans = false;
        String num = "email@@seznam.cz";
        boolean value =  Validations.validateEmail(num);
        assertEquals(ans,value);
    }



    @org.junit.jupiter.api.Test
    void validatePhone() {
        boolean ans = true;
        String num = "+420 777 112 233";
        boolean value =  Validations.validatePhone(num);
        assertEquals(ans,value);
    }

    @org.junit.jupiter.api.Test
    void validatePhone0() {
        boolean ans = true;
        String num = "+420 777 11 22 33";
        boolean value =  Validations.validatePhone(num);
        assertEquals(ans,value);
    }

    @org.junit.jupiter.api.Test
    void validatePhone1() {
        boolean ans = true;
        String num = "777 112 233, 123 456 789";
        boolean value =  Validations.validatePhone(num);
        assertEquals(ans,value);
    }

    @org.junit.jupiter.api.Test
    void validatePhone2() {
        boolean ans = true;
        String num = "+420 777112233";
        boolean value =  Validations.validatePhone(num);
        assertEquals(ans,value);
    }

    @org.junit.jupiter.api.Test
    void validatePhone3() {
        boolean ans = true;
        String num = "777 11 22 33";
        boolean value =  Validations.validatePhone(num);
        assertEquals(ans,value);
    }

    @org.junit.jupiter.api.Test
    void validatePhone4() {
        boolean ans = false;
        String num = "asd";
        boolean value =  Validations.validatePhone(num);
        assertEquals(ans,value);
    }

    @org.junit.jupiter.api.Test
    void validatePhone5() {
        boolean ans = false;
        String num = "123 456 1w8";
        boolean value =  Validations.validatePhone(num);
        assertEquals(ans,value);
    }

    @org.junit.jupiter.api.Test
    void validatePhone6() {
        boolean ans = true;
        String num = "00420 123 456 180";
        boolean value =  Validations.validatePhone(num);
        assertEquals(ans,value);
    }

    @org.junit.jupiter.api.Test
    void validatePhone7() {
        boolean ans = true;
        String num = "+420 777 112 233, +420 123 456 789";
        boolean value =  Validations.validatePhone(num);
        assertEquals(ans,value);
    }

    @org.junit.jupiter.api.Test
    void validatePhone8() {
        boolean ans = false;
        String num = "+420 777 112 233, +420 I23 456 789";
        boolean value =  Validations.validatePhone(num);
        assertEquals(ans,value);
    }

    @org.junit.jupiter.api.Test
    void validatePhone9() {
        boolean ans = true;
        String num = "+420 777 112 233, 00420 123 456 789";
        boolean value =  Validations.validatePhone(num);
        assertEquals(ans,value);
    }

    @org.junit.jupiter.api.Test
    void validatePhone10() {
        boolean ans = true;
        String num = "00420777112233";
        boolean value =  Validations.validatePhone(num);
        assertEquals(ans,value);
    }




    @org.junit.jupiter.api.Test
    void validateCZSyntaxPersonId() {
        boolean ans = true;
        String rc = "8501261153";
        boolean value =  Validations.validateCzechBirthNumberSyntax(rc);
        assertEquals(ans,value);
    }

    @org.junit.jupiter.api.Test
    void validateCZSyntaxPersonId2() {
        boolean ans = true;
        String rc = "850126/1153";
        boolean value =  Validations.validateCzechBirthNumberSyntax(rc);
        assertEquals(ans,value);
    }

    @org.junit.jupiter.api.Test
    void validateCZSyntaxPersonId3() {
        boolean ans = false;
        String rc = "850126//1153";
        boolean value =  Validations.validateCzechBirthNumberSyntax(rc);
        assertEquals(ans,value);
    }

    @org.junit.jupiter.api.Test
    void validateCZSyntaxPersonId4() {
        boolean ans = false;
        String rc = "8501261/153";
        boolean value =  Validations.validateCzechBirthNumberSyntax(rc);
        assertEquals(ans,value);
    }

    @org.junit.jupiter.api.Test
    void validateCZSyntaxPersonId5() {
        boolean ans = false;
        String rc = "8501261 153";
        boolean value =  Validations.validateCzechBirthNumberSyntax(rc);
        assertEquals(ans,value);
    }

    @org.junit.jupiter.api.Test
    void validateCZSyntaxPersonId6() {
        boolean ans = false;
        String rc = "850I161153";
        boolean value =  Validations.validateCzechBirthNumberSyntax(rc);
        assertEquals(ans,value);
    }

    @org.junit.jupiter.api.Test
    void validateCZSyntaxPersonId7() {
        boolean ans = false;
        String rc = "85016115389";
        boolean value =  Validations.validateCzechBirthNumberSyntax(rc);
        assertEquals(ans,value);
    }

    @org.junit.jupiter.api.Test
    void validateCZSyntaxPersonId8() {
        boolean ans = false;
        String rc = "aaaaaaaaaa";
        boolean value =  Validations.validateCzechBirthNumberSyntax(rc);
        assertEquals(ans,value);
    }

    @org.junit.jupiter.api.Test
    void validateCZSyntaxPersonId9() {
        boolean ans = false;
        String rc = "8501%61153";
        boolean value =  Validations.validateCzechBirthNumberSyntax(rc);
        assertEquals(ans,value);
    }



    @org.junit.jupiter.api.Test
    void validateCZPersonId1() {
        boolean ans = true;
        String rc = "9851010015"; //dobre zena
        boolean value =  Validations.validateCzechBirthNumberValue(rc);
        assertEquals(ans,value);
    }

    @org.junit.jupiter.api.Test
    void validateCZPersonId1a() {
        boolean ans = true;
        String rc = "976231/0019"; //dobre zena2
        boolean value =  Validations.validateCzechBirthNumberValue(rc);
        assertEquals(ans,value);
    }

    @org.junit.jupiter.api.Test
    void validateCZPersonId1b() {
        boolean ans = true;
        String rc = "8501010012"; //dobre muz
        boolean value =  Validations.validateCzechBirthNumberValue(rc);
        assertEquals(ans,value);
    }

    @org.junit.jupiter.api.Test
    void validateCZPersonId1c() {
        boolean ans = true;
        String rc = "851231/0015"; //dobre muz2
        boolean value =  Validations.validateCzechBirthNumberValue(rc);
        assertEquals(ans,value);
    }

    @org.junit.jupiter.api.Test
    void validateCZPersonId2() {
        boolean ans = true;
        String rc = "850126/1153";
        boolean value =  Validations.validateCzechBirthNumberValue(rc);
        assertEquals(ans,value);
    }

    @org.junit.jupiter.api.Test
    void validateCZPersonId3() {
        boolean ans = false;
        String rc = "0000000000";
        boolean value =  Validations.validateCzechBirthNumberValue(rc);
        assertEquals(ans,value);
    }

    @org.junit.jupiter.api.Test
    void validateCZPersonId4() {
        boolean ans = false;
        String rc = "8513261153";//saptne den
        boolean value =  Validations.validateCzechBirthNumberValue(rc);
        assertEquals(ans,value);
    }

    @org.junit.jupiter.api.Test
    void validateCZPersonId5() {
        boolean ans = false;
        String rc = "8501321103";//spatne mesic
        boolean value =  Validations.validateCzechBirthNumberValue(rc);
        assertEquals(ans,value);
    }

    @org.junit.jupiter.api.Test
    void validateCZPersonId6() {
        boolean ans = false;
        String rc = "8501311103"; //spatne kontrolni modulo
        boolean value =  Validations.validateCzechBirthNumberValue(rc);
        assertEquals(ans,value);
    }


    @org.junit.jupiter.api.Test
    void validateCZPersonId7() {
        boolean ans = false;
        String rc = "850161106";//spatne mesic zena
        boolean value =  Validations.validateCzechBirthNumberValue(rc);
        assertEquals(ans,value);
    }

    @org.junit.jupiter.api.Test
    void validateCZPersonId8() {
        boolean ans = false;
        String rc = "8563300010";//spatne mesic zena2
        boolean value =  Validations.validateCzechBirthNumberValue(rc);
        assertEquals(ans,value);
    }
}