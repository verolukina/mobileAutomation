package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class WelcomePageObject extends MainPageObject {

    private static final String
        STEP_LEARN_MORE_LINK = "id:Learn more about Wikipedia",
        STEP_NEW_WAYS_TO_EXPLORE_TEXT = "id:New ways to explore",
        STEP_ADD_OR_EDIT_PREFERRED_LANG_LINK = "id:Add or edit preferred languages",
        STEP_LEARN_MORE_ABOUT_DATA_COLLECTED_LINK = "id:Learn more about data collected",
        NEXT_LINK = "id:Next",
        GET_STARTED_BUTTON = "id:Get started",
        SKIP_BUTTON = "id:Skip";
    ;

    public WelcomePageObject(AppiumDriver driver) {
        super(driver);
    }

    public void waitForLearnMoreLink() {
        waitForElementPresent(STEP_LEARN_MORE_LINK, "Cannot find 'Learn more about Wikipedia'", 5);
    }

    public void waitForNewWayToExploreText() {
        waitForElementPresent(STEP_NEW_WAYS_TO_EXPLORE_TEXT, "Cannot find 'New ways to explore'", 5);
    }

    public void waitForPreferredLangText() {
        waitForElementPresent(STEP_ADD_OR_EDIT_PREFERRED_LANG_LINK, "Cannot find 'Add or edit preferred languages'", 5);
    }

    public void waitForLearnMoreDataCollectedText() {
        waitForElementPresent(STEP_LEARN_MORE_ABOUT_DATA_COLLECTED_LINK, "Cannot find 'Learn more about data collected'", 5);
    }

    public void clickNextButton() {
        waitForElementAndClick(NEXT_LINK, "Cannot find next button", 5);
    }

    public void clickGetStartedButton() {
        waitForElementAndClick(GET_STARTED_BUTTON, "Cannot find and click Get started button", 5);
    }

    public void clickSkipButton() {
        waitForElementAndClick(SKIP_BUTTON, "Cannot find and click skip button", 5);
    }

}
