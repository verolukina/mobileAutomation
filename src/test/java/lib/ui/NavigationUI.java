package lib.ui;

import lib.Platform;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract  public class NavigationUI extends MainPageObject{

    protected static String
        MY_LISTS_LINK,
        OPEN_NAVIGATION;

    public NavigationUI(RemoteWebDriver driver) {
        super(driver);
    }


    public void openNavigation() {
        if (Platform.getInstance().isMW()) {
            waitForElementAndClick(OPEN_NAVIGATION,
                    "Cannot find and click open navigation",
                    5);
        }
    }

    public void clickMyLists() {

        if (Platform.getInstance().isMW()) {
            tryClickElementWithFewAttempts(MY_LISTS_LINK,
                    "Cannot find navigation button to my list",
                    5);
        } else {
            waitForElementAndClick(
                    MY_LISTS_LINK,
                    "Cannot find navigation button to my list",
                    5);
        }

    }
}
