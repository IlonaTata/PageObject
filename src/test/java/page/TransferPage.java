package page;

import com.codeborne.selenide.SelenideElement;
import data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static java.lang.String.valueOf;

public class TransferPage {
    private SelenideElement amountField = $("[data-test-id=amount] input"); //поле суммы
    private SelenideElement fromField = $("[data-test-id=from] input"); // с поля
    private SelenideElement transferButton = $("[data-test-id=action-transfer]");//кнопка передачи

    public void transferMoney(int amount, DataHelper.CardInfo from) {
        amountField.setValue(valueOf(amount));
        fromField.setValue(valueOf(from));
        transferButton.click();
    }

    public void getErrorLimit() {
        $(byText("Ошибка!")).shouldBe(visible);
    }
}

