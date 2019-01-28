package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import lib.Platform;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class ArticlePageObject extends MainPageObject {

        protected static String
        TITLE,
        FOOTER_ELEMENT,
        OPTION_BUTTON,
        OPTION_ADD_TO_MY_LIST_BUTTON,
        OPTIONS_REMOVE_FROM_MY_LIST_BUTTON,
        ADD_TO_MY_LIST_OVERLAY,
        MY_LIST_NAME_INPUT,
        MY_LIST_OK_BUTTON,
        CLOSE_ARTICLE_BUTTON,
        FOLDER_BY_NAME_TPL,
        SAVED_ARTICLE_BUTTON;


        public ArticlePageObject(RemoteWebDriver driver) {
                super(driver);
        }

        public WebElement waitForTitleElement() {
                return waitForElementPresent(TITLE, "Cannot find article title on page", 15);
        }

        public String getArticleTitle() {
                if (Platform.getInstance().isAndroid()) {
                        return waitForTitleElement().getAttribute("text");
                } else if (Platform.getInstance().isIOS()){
                        return waitForTitleElement().getAttribute("name");
                } else {
                        return waitForTitleElement().getText();
                }
        }

        public void swipeToFooter() {
                if (Platform.getInstance().isAndroid()) {
                        swipeUpToFindElement(
                                FOOTER_ELEMENT,
                                "Cannot find the end of article",
                                40);
                } else if (Platform.getInstance().isIOS()){
                        swipeUpTillElementAppear(FOOTER_ELEMENT,
                                "Cannot find the end of article",
                                40);
                } else {
                        scrollWebPageTillElementNotVisible(FOOTER_ELEMENT, "Cannot find the end of article", 40);
                }
        }

        public void checkSavedButtonIsActive() {
                if (Platform.getInstance().isIOS()) {
                        waitForElementPresent(SAVED_ARTICLE_BUTTON, "Article not saved", 5);
                } else if (Platform.getInstance().isMW()) {
                        waitForElementPresent(OPTIONS_REMOVE_FROM_MY_LIST_BUTTON, "Article not saved", 5);
                }
        }

        public void addArticleToMyList(String nameOfFolder) {
                waitForElementAndClick(
                        OPTION_BUTTON,
                        "Cannot find button to open article options",
                        15);
                addArticleToListFirstTime(nameOfFolder);
        }

        public void addArticleToMyListThroughShortMenuFirstTime(String nameOfFolder, String nameOfArticle, SearchPageObject searchPageObject) {
                searchPageObject.longPressForSearchResultWithTitle(nameOfArticle);
                addArticleToListFirstTime(nameOfFolder);
        }

        public void addArticleToMyExistingListThroughShortMenu(String nameOfFolder, String nameOfArticle, SearchPageObject searchPageObject) {
                searchPageObject.longPressForSearchResultWithTitle(nameOfArticle);
                addArticleToExistingList(nameOfFolder);
        }

        private void addArticleToListFirstTime(String nameOfFolder) {
                waitForElementAndClick(
                        OPTION_ADD_TO_MY_LIST_BUTTON,
                        "Cannot find 'Add to reading list' button",
                        5);
                waitForElementAndClick(
                        ADD_TO_MY_LIST_OVERLAY,
                        "Cannot find 'Got it' tip overlay",
                        5);
                waitForElementAndClear(
                        MY_LIST_NAME_INPUT,
                        "Cannot find input to set name of articles folder",
                        5);
                waitForElementAndSendKeys(
                        MY_LIST_NAME_INPUT,
                        nameOfFolder,
                        "Cannot put text into article folder input",
                        5);
                waitForElementAndClick(
                        MY_LIST_OK_BUTTON,
                        "Cannot press OK button",
                        5);
        }

        private void addArticleToExistingList(String nameOfFolder) {
                waitForElementAndClick(
                    OPTION_ADD_TO_MY_LIST_BUTTON,
                   "Cannot find 'Add to reading list' button",
                        5);
                waitForElementAndClick(
                     getFolderXpathByName(nameOfFolder),
                     "Cannot find '"+ nameOfFolder +"' folder",
                    5);
        }

        public void closeArticle() {
                if (!Platform.getInstance().isMW()) {
                        waitForElementAndClick(
                                CLOSE_ARTICLE_BUTTON,
                                "Cannot close article, cannot find X link",
                                5);
                }
        }

        public void removeArticleFromSavedIfItAdded () {
                if (isElementPresent(OPTIONS_REMOVE_FROM_MY_LIST_BUTTON)) {
                        waitForElementAndClick(OPTIONS_REMOVE_FROM_MY_LIST_BUTTON,
                                "Cannot click button to remove an article form saved",
                                1);
                        waitForElementPresent(OPTION_ADD_TO_MY_LIST_BUTTON,
                                "Cannot find button to add an article to saved list after removing it from this list before",
                                5);
                }
        }

        private static String getFolderXpathByName(String nameOfFolder) {
                return FOLDER_BY_NAME_TPL.replace("{FOLDER_NAME}", nameOfFolder);
        }

        public void assertTitleElementPresent(String errorMessage) {
                if (!driver.findElement(By.id(TITLE)).isDisplayed()) {
                        throw new AssertionError(errorMessage);
                }
        }

        public void addArticleToMySaved() {
                if (Platform.getInstance().isMW()) {
                        removeArticleFromSavedIfItAdded();
                }
                waitForElementAndClick(OPTION_ADD_TO_MY_LIST_BUTTON, "Can't find and click add to my reading list", 5);
        }
}
