package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class SearchPageObject extends MainPageObject {

     protected static String
        SEARCH_INIT_ELEMENT,
        SEARCH_INPUT,
        SEARCH_INPUT_ID,
        SEARCH_CANCEL_BUTTON,
        SEARCH_RESULT_BY_SUBSTRING_TPL,
        SEARCH_RESULT_ELEMENT,
        SEARCH_EMPTY_RESULT_ELEMENT,
        SEARCH_EMPTY_RESULT_ELEMENT_IMAGE;

    public  SearchPageObject(RemoteWebDriver driver) {
        super(driver);
    }

    public void initSearchInput() {
        waitForElementAndClick(SEARCH_INIT_ELEMENT, "Cannot find and click search init element", 5);
        waitForElementPresent(SEARCH_INIT_ELEMENT, "Cannot find search input after clicking search init element");
    }

    public void typeSearchLine(String searchLine) {
        waitForElementAndSendKeys(SEARCH_INPUT, searchLine, "Cannot find and type into search input", 5);
    }

    public WebElement waitForSearchResult(String substring) {
        return waitForElementPresent(getResultSearchElement(substring), "Cannot find search result with substring "+ substring +"");
    }

    public void clickByArticleWithSubstring(String substring) {
        waitForElementAndClick(getResultSearchElement(substring), "Cannot find search and click result with substring "+ substring +"", 10);
    }

    public void waitForCancelButtonToAppear() {
        waitForElementPresent(SEARCH_CANCEL_BUTTON, "Cannot find cancel button", 5);
    }

    public void waitForCancelButtonToDisappear() {
        waitForElementNotPresent(SEARCH_CANCEL_BUTTON, "Search cancel button is still present", 5);
    }

    public void clickCancelSearch() {
        waitForElementAndClick(SEARCH_CANCEL_BUTTON, "Cannot find and click cancel button", 5);
    }

    public int getAmaountOfFoundArticles() {
        waitForElementsPresent(
                SEARCH_RESULT_ELEMENT,
                "Cannot find anything by the request",
                15);
        return getAmountOfElements(SEARCH_RESULT_ELEMENT);
    }

    public void waitForEmptyResultsLabel() {
        waitForElementPresent(SEARCH_EMPTY_RESULT_ELEMENT, "Cannot find empty result element", 15);
    }

    public void waitForEmptySearch() {
        waitForElementPresent(SEARCH_EMPTY_RESULT_ELEMENT_IMAGE, "Cannot find empty result element", 15);
    }

    public void assertThereIsNoResultOfSearch() {
        assertElementNotPresent(SEARCH_RESULT_ELEMENT, "We supposed not to find any results");
    }

    public String getSearchFieldContent() {
        return waitForElementPresent(SEARCH_INPUT_ID, "Cannot find search input", 2).getAttribute("text");
    }

    private static String getResultSearchElement(String substring) {
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
    }

    public void longPressForSearchResultWithTitle(String title) {
        if (driver instanceof AppiumDriver) {
            new TouchAction((AppiumDriver)driver).longPress(waitForSearchResult(title)).release().perform();
        }
    }

}
