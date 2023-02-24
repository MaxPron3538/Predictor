package main.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationData {

    private static String regexEmail = "\\S[^|/?()@=.\\-,+\\\\*:%#{}\\[\\]]+\\@(gmail.com|ukr.net|yandex.ru|mail.ru|icloud.com|outlook.com|)";
    private static String regexPassword = "\\w[^()/\\\\{}\\[\\]*%,.:;]+";

    public static boolean validatePassword(String password){
        Pattern pattern = Pattern.compile(regexPassword);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public static boolean validateEmail(String email){
        Pattern pattern = Pattern.compile(regexEmail);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
