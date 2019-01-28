package lib.ui;

import org.openqa.selenium.remote.RemoteWebDriver;

public class AutorizationPageObject extends MainPageObject {

    private static final String
        LOGIN_BUTTON = "xpath://body/div/a[text()='Log in']",
        LOGIN_INPUT = "css:input[name='wpName']",
        PASSWORD_INPUT = "css:input[name='wpPassword']",
        SUBMIT_BUTTON = "css:button#wpLoginAttempt";

    public AutorizationPageObject(RemoteWebDriver driver) {
        super(driver);
    }

    public void clickAuthButton() {
        waitForElementPresent(LOGIN_BUTTON, "Cannot find auth button", 5);
        waitForElementAndClick(LOGIN_BUTTON, "Cannot find and click auth button", 5);
    }

    public void enterLoginData(String login, String password) {
        waitForElementAndSendKeys(LOGIN_INPUT, login, "Cannot find and put login to the login input", 5);
        waitForElementAndSendKeys(PASSWORD_INPUT, password, "Cannot find and put password to the password input", 5);
    }

    public  void submitForm() {
        waitForElementAndClick(SUBMIT_BUTTON, "Cannot find and click submit auth button",5);
    }

}
