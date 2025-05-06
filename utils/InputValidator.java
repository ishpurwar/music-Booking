package utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class InputValidator {

    public static boolean isValidDate(String dateStr) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime.parse(dateStr, formatter);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isValidEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email.matches(regex);
    }
}
