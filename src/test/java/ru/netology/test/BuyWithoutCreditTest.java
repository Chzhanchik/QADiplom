package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataBaseHelper;
import ru.netology.data.DataHelper;
import ru.netology.page.MainPage;
import ru.netology.page.PaymentPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BuyWithoutCreditTest {
    MainPage mainPage;
    PaymentPage paymentPage;

    @BeforeEach
    void shouldOpenWeb() {
        DataBaseHelper.cleanDataBase();
        mainPage = open("http://localhost:8080", MainPage.class);
        paymentPage = mainPage.buyWithoutCredit();
    }

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @Test
    void shouldApproveFirstCard() {  /* Покупка тура при вводе валидных данных карты*/
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getValidOwner();
        val cvс = DataHelper.getValidCvс();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvс);
        paymentPage.expectApprovalFromBank();
        val expected = DataHelper.getFirstCardExpectedStatus();
        val actual = DataBaseHelper.getStatusPaymentWithCredit();
        assertEquals(expected, actual);

    }

    @Test
    void shouldRejectSecondCard() { /*Покупка тура при вводе невалидных данных карты*/
        val cardNumber = DataHelper.getSecondCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getValidOwner();
        val cvс = DataHelper.getValidCvс();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvс);
        paymentPage.expectRejectionFromBank();
        val expected = DataHelper.getSecondCardExpectedStatus();
        val actual = DataBaseHelper.getStatusPaymentWithCredit();
        assertEquals(expected, actual);
    }

    @Test
    void сheckingIncompleteData() { /*Покупка тура при вводе неполных данных*/
        val cardNumber = DataHelper.getCardNumberIncomplete();
        val month = DataHelper.getIncompleteMonth();
        val year = DataHelper.getIncompleteYear();
        val owner = DataHelper.getIncompleteOwner();
        val cvс = DataHelper.getIncompleteCvс();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvс);
        paymentPage.waitNecessaryFillOutField();
    }

    @Test
    void сheckingСardNumberRequestedData() { /*Покупка тура при вводе одинаковых цифр номера карты*/
        val cardNumber = DataHelper.getCardNumberRequestedData();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getValidOwner();
        val cvс = DataHelper.getValidCvс();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvс);
        paymentPage.expectRejectionFromBank();
    }

    @Test
    void сheckingСardNumberZero() { /*Покупка тура при вводе нулей в поле номер карты */
        val cardNumber = DataHelper.getCardNumberZero();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getValidOwner();
        val cvс = DataHelper.getValidCvс();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvс);
        paymentPage.expectRejectionFromBank();
    }

    @Test
    void shouldRejectInvalidMonth() { /*Покупка тура при вводе невалидного месяца*/
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getInvalidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getValidOwner();
        val cvс = DataHelper.getValidCvс();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvс);
        paymentPage.waitInvalidDuration();
    }

    @Test
    void shouldRejectZeroMonth() { /*Покупка тура при вводе нулевого месяца*/
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getZeroValue();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getValidOwner();
        val cvс = DataHelper.getValidCvс();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvс);
        paymentPage.waitInvalidDuration();
    }


    @Test
    void shouldRejectPastMonth() {  /*Покупка тура по карте с истекшим сроком годности*/
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getPastMonth();
        val year = DataHelper.getThisYear();
        val owner = DataHelper.getValidOwner();
        val cvс = DataHelper.getValidCvс();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvс);
        paymentPage.waitInvalidDuration();
    }

    @Test
    void shouldRejectInvalidYear() { /*Покупка тура при вводе невалидного года*/
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getInvalidYear();
        val owner = DataHelper.getValidOwner();
        val cvс = DataHelper.getValidCvс();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvс);
        paymentPage.waitInvalidYear();
    }

    @Test
    void shouldRejectZeroYear() { /*Покупка тура при вводе нулевого года*/
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getZeroValue();
        val owner = DataHelper.getValidOwner();
        val cvс = DataHelper.getValidCvс();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvс);
        paymentPage.waitInvalidDuration();
    }

    @Test
    void shouldRejectInvalidOwner() { /*Покупка тура при вводе невалидного владельцы*/
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getInvalidOwner();
        val cvс = DataHelper.getValidCvс();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvс);
        paymentPage.waitInvalidFormat();
    }

    @Test
    void shouldRejectOwnerWithoutSpaces() { /*Покупка тура при вводе имени и фамилии владельца слитно*/
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getDataWithoutSpaces();
        val cvс = DataHelper.getValidCvс();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvс);
        paymentPage.waitInvalidFormat();
    }

    @Test
    void shouldRejectOwnerWithNumbers() { /*Покупка тура при вводе цифр в поле владелец*/
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getOwnerNumbers();
        val cvс = DataHelper.getValidCvс();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvс);
        paymentPage.waitInvalidFormat();
    }

    @Test
    void shouldRejectOwnerRus() { /*Покупка тура при вводе владельца на русском языке*/
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getInvalidOwnerRus();
        val cvс = DataHelper.getValidCvс();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvс);
        paymentPage.waitInvalidFormat();
    }

    @Test
    void shouldRejectInvalidCvс() { /*Покупка тура при вводе невалидного cvc*/
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getValidOwner();
        val cvс = DataHelper.getInvalidCvс();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvс);
        paymentPage.waitInvalidFormat();
    }

    @Test
    void shouldRejectZeroCvc() { /*Покупка тура при вводе нулевого cvc*/
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getValidOwner();
        val cvс = DataHelper.getCardNumberZero();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvс);
        paymentPage.waitInvalidDuration();
    }

    @Test
    void shouldRejectEmptyNumberCard() { /*Покупка тура при отсутствии ввода номера карты*/
        val cardNumber = DataHelper.getEmptyValue();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getValidOwner();
        val cvс = DataHelper.getValidCvс();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvс);
        paymentPage.waitNecessaryFillOutField();
    }

    @Test
    void shouldRejectEmptyMonth() { /*Покупка тура при отсутствии ввода месяца*/
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getEmptyValue();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getValidOwner();
        val cvс = DataHelper.getValidCvс();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvс);
        paymentPage.waitNecessaryFillOutField();
    }

    @Test
    void shouldRejectEmptyYear() { /*Покупка тура при отсутствии ввода года*/
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getEmptyValue();
        val owner = DataHelper.getValidOwner();
        val cvс = DataHelper.getValidCvс();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvс);
        paymentPage.waitNecessaryFillOutField();
    }

    @Test
    void shouldRejectEmptyCvс() { /*Покупка тура при отсутствии ввода cvc*/
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getValidOwner();
        val cvс = DataHelper.getEmptyValue();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvс);
        paymentPage.waitNecessaryFillOutField();
    }

    @Test
    void shouldRejectEmptyOwner() { /*Покупка тура при отсутствии ввода владельца*/
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getEmptyValue();
        val cvс = DataHelper.getValidCvс();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvс);
        paymentPage.waitNecessaryFillOutField();
    }

}