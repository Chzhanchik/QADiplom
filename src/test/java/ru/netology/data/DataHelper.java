package ru.netology.data;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.Year;
import java.util.Locale;

public class DataHelper {
    private static Faker faker = new Faker(new Locale("en"));

    private DataHelper() {
    }

    public static String getFirstCardNumber() {
        return "4444 4444 4444 4441";
    }

    public static String getFirstCardExpectedStatus() {
        return "APPROVED";
    }

    public static String getSecondCardNumber() {
        return "4444 4444 4444 4442";
    }

    public static String getSecondCardExpectedStatus() {
        return "DECLINED";
    }

    public static String getCardNumberIncomplete() {  return "4444"; }

    public static String getCardNumberZero() {
        return "0000 0000 0000 0000";
    }

    public static String getCardNumberRequestedData() {
        return "3333 3333 3333 3333";
    }

    public static String getCardNumberOverLimit() { return "4444 4444 4444 4441 1"; }

    public static String getValueText() { return "тест"; }

    public static String getValidMonth() {
        LocalDate localDate = LocalDate.now();
        return  String.format("%02d", localDate.getMonthValue());
    }

    public static String getInvalidMonth() {
        return  "14";
    }

    public static String getZeroValue() {
        return "00";
    }

    public static String getIncompleteMonth() { return "1";}

    public static String getMonthOverLimit() { return "022"; }

    public static String getValidYear() {
        LocalDate localDate = LocalDate.now();
        return String.format("%ty", localDate.plusYears(2));
    }

    public static String getInvalidYear() {
        LocalDate localDate = LocalDate.now();
        return String.format("%ty", localDate.minusYears(2));
    }

    public static String getYearOverLimit() {
        return "202";
    }

    public static String getIncompleteYear()  { return "2"; }

    public static String getValidOwner() {
        return faker.name().firstName() + " " + faker.name().lastName().replaceAll("[^A-Za-z]", "");
    }

    public static String getInvalidOwner() {
        return "Frio#*?%731";
    }

    public static String getDataWithoutSpaces() { return "IvanovPetr";}

    public static String getIncompleteOwner() {
        return "Ivan";
    }

    public static String getOwnerNumbers() {
        return " 12345";
    }

    public static String getValidCvс() {
        return faker.numerify("###");
    }

    public static String getInvalidCvс() {
        return faker.numerify("##");
    }

    public static String getEmptyValue() {return ""; }

    public static String getIncompleteCvс() {return "12"; }

    public static String getCvcOverLimit() {
        return "2777";
    }

    public static String getInvalidYearMoreThanFive() {
        LocalDate localDate = LocalDate.now();
        return String.format("%ty", localDate.plusYears(6));
    }

    public static String getInvalidOwnerRus() {
        Faker faker = new Faker(new Locale("ru"));
        return faker.name().fullName();
    }

    public static String getPastMonth() {
        LocalDate localDate = LocalDate.now().minusMonths(1);
        return  String.format("%02d", localDate.getMonthValue());
    }

    public static String getThisYear() {
        return String.format("%ty", Year.now());
    }
}
