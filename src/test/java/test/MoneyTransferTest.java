package test;

import com.codeborne.selenide.Configuration;
import data.DataHelper;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import page.DashboardPage;
import page.LoginPage;
import page.TransferPage;

import static com.codeborne.selenide.Selenide.open;
import static data.DataHelper.getFirstCardNumber;
import static data.DataHelper.getSecondCardNumber;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTransferTest {
    @Test
    @BeforeEach
    void shouldOpen() {
        open("http://localhost:9999");
        Configuration.holdBrowserOpen = true;
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = LoginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);

    }
    @Test
    public void shouldTransferMoneyFromFirstCardToSecondCard() {
        int amount = 3_000;
        val dashboardPage = new DashboardPage();
        val firstCardBalanceBeforeTransfer = dashboardPage.getFirstCardBalance();
        val secondCardBalanceBeforeTransfer = dashboardPage.getSecondCardBalance();
        dashboardPage.pushSecondCardButton();
        val transferPage= new TransferPage();
        transferPage.transferMoney(amount, getFirstCardNumber());
        val firstCardBalance = firstCardBalanceBeforeTransfer - amount;
        val secondCardBalance = secondCardBalanceBeforeTransfer + amount;

        assertEquals(firstCardBalance, dashboardPage.getFirstCardBalance());
        assertEquals(secondCardBalance, dashboardPage.getSecondCardBalance());

    }

    @Test
    public void shouldTransferMoneyFromSecondCardToFirstCard() {
        int amount = 6_000;
        val dashboardPage = new DashboardPage();
        val transferPage= new TransferPage();
        val firstCardBalanceBeforeTransfer = dashboardPage.getFirstCardBalance();
        val secondCardBalanceBeforeTransfer = dashboardPage.getSecondCardBalance();
        dashboardPage.pushFirstCardButton();
        transferPage.transferMoney(amount, getSecondCardNumber());
        val firstCardBalance = firstCardBalanceBeforeTransfer + amount;
        val secondCardBalance = secondCardBalanceBeforeTransfer - amount;

        assertEquals(firstCardBalance, dashboardPage.getFirstCardBalance());
        assertEquals(secondCardBalance, dashboardPage.getSecondCardBalance());
    }

   @Test
    public void shouldHaveNotGoodBalance() {
        int amount = 11_000;
        val dashboardPage = new DashboardPage();
        val transferPage= new TransferPage();
        val firstCardBalanceBeforeTransfer = dashboardPage.getFirstCardBalance();
        val secondCardBalanceBeforeTransfer = dashboardPage.getSecondCardBalance();
        dashboardPage.pushFirstCardButton();
        transferPage.transferMoney(amount, getSecondCardNumber());
        transferPage.getErrorLimit();
    }
}


