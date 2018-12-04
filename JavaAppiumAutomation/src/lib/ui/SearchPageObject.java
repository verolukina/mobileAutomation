package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class SearchPageObject extends MainPageObject {

    private static final String
        SEARCH_INIT_ELEMENT = "xpath://*[contains(@text, 'Search Wikipedia')]",
        SEARCH_INPUT = "xpath://*[contains(@text, 'Search…')]",
        SEARCH_INPUT_ID = "id:org.wikipedia:id/search_src_text",
        SEARCH_CANCEL_BUTTON = "id:org.wikipedia:id/search_close_btn",
        SEARCH_RESULT_BY_SUBSTRING_TPL = "xpath://*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='{SUBSTRING}']",
        SEARCH_RESULT_ELEMENT = "xpath://*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']",
        SEARCH_EMPTY_RESULT_ELEMENT = "xpath://*[@text='No results found']",
        SEARCH_EMPTY_RESULT_ELEMENT_IMAGE = "id:org.wikipedia:id/search_empty_image";

    public  SearchPageObject(AppiumDriver driver) {
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
        new TouchAction(driver).longPress(waitForSearchResult(title)).release().perform();
    }

}
