package cz.osu.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import java.time.LocalTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validations {


    public static boolean validateEmail(String emailOrPhoneStr) {
        Pattern pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(emailOrPhoneStr);
        return matcher.find();
    }

    public static boolean validatePhone(String number) {
        Pattern pattern = Pattern.compile("^[+]?[()\0-9. -]{9,}$");
        Matcher matcher = pattern.matcher(number);
        return matcher.find();
    }

    public static boolean validateCzechBirthNumberSyntax(String number) {
        Pattern pattern = Pattern.compile("^[0-9]{6}\\/?[0-9]{4}$");
        Matcher matcher = pattern.matcher(number);
        return matcher.find();
    }
    public static boolean validateCzechBirthNumberValue(String number) {
        if (number.contains("/"))
            number = number.replace("/","");
        if (number.length()!=10)
            return false;
        if( Integer.parseInt(number.substring(4, 6)) < 1 || Integer.parseInt(number.substring(4, 6)) > 31 ||
                Integer.parseInt(number.substring(2, 4)) < 1 || Integer.parseInt(number.substring(2, 4)) > 62 ||
                (Integer.parseInt(number.substring(2, 4)) > 12 && Integer.parseInt(number.substring(2, 4)) < 51) ||
                ((Integer.parseInt(number.substring(0, 9)) % 11) != Integer.parseInt(number.substring(9, 10))))
            return false;
        else
            return true;
    }


    public static boolean validateInputs(TextField tfName, TextField tfSurname, TextField tfPersonIdNumber, TextField tfPlateNumber, TextField tfPhone, TextField tfEmail, ComboBox<LocalTime> comBoxReservedTime, RadioButton rdCz ){
        if (tfName.getText().trim().isEmpty() || tfSurname.getText().trim().isEmpty()
                || tfPersonIdNumber.getText().trim().isEmpty() || tfPlateNumber.getText().trim().isEmpty()
                || (tfPhone.getText().trim().isEmpty() || tfEmail.getText().trim().isEmpty())
                || comBoxReservedTime.getValue() == null) {
            ShowErrorAlert("Error","Vyplňte prosím všechna důležitá pole !!!");
            return false;
        }
        if (!validatePhone(tfPhone.getText())) {
            ShowErrorAlert("Error","Neplatné telefoní číslo !!!");
            return false;
        }
        if (!validateEmail(tfEmail.getText())) {
            ShowErrorAlert("Error","Neplatný email !!!");
            return false;
        }
        if (rdCz.isSelected()){
            if (!validateCzechBirthNumberSyntax(tfPersonIdNumber.getText())) {
                ShowErrorAlert("Error","Neplatná syntaxe českého rodného čísla !!! \nPlatné je např.: 580123/1158, nebo 5801231158");
                return false;
            }else {
                if (!validateCzechBirthNumberValue(tfPersonIdNumber.getText())){
                    ShowErrorAlert("Error","Neplatné české rodné číslo !!! \nPlatné je např.: 580123/1158");
                    return false;
                }
                if (!tfPersonIdNumber.getText().contains("/")) {
                    tfPersonIdNumber.setText(tfPersonIdNumber.getText().substring(0, 6) + "/" + tfPersonIdNumber.getText().substring(6));
                }
                return true;
            }
        }

        else return true;
    }

    private static void ShowErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
