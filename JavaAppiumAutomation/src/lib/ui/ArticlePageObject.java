package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import lib.Platform;

abstract public class ArticlePageObject extends MainPageObject {

        protected static String
        TITLE,
        FOOTER_ELEMENT,
        OPTION_BUTTON,
        OPTION_ADD_TO_MY_LIST_BUTTON,
        ADD_TO_MY_LIST_OVERLAY,
        MY_LIST_NAME_INPUT,
        MY_LIST_OK_BUTTON,
        CLOSE_ARTICLE_BUTTON,
        FOLDER_BY_NAME_TPL,
        SAVED_ARTICLE_BUTTON;


        public ArticlePageObject(AppiumDriver driver) {
                super(driver);
        }

        public WebElement waitForTitleElement() {
                return waitForElementPresent(TITLE, "Cannot find article title on page", 15);
        }

        public String getArticleTitle() {
                if (Platform.getInstance().isAndroid()) {
                        return waitForTitleElement().getAttribute("text");
                } else {
                        return waitForTitleElement().getAttribute("name");
                }
        }

        public void swipeToFooter() {
                if (Platform.getInstance().isAndroid()) {
                        swipeUpToFindElement(
                                FOOTER_ELEMENT,
                                "Cannot find the end of article",
                                20);
                } else {
                        swipeUpTillElementAppear(FOOTER_ELEMENT,
                                "Cannot find the end of article",
                                20);
                }
        }

        public void checkSavedButtonIsActive() {
                waitForElementPresent(SAVED_ARTICLE_BUTTON, "Article not saved", 5);
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
                waitForElementAndClick(
                        CLOSE_ARTICLE_BUTTON,
                        "Cannot close article, cannot find X link",
                        5);
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
                waitForElementAndClick(OPTION_ADD_TO_MY_LIST_BUTTON, "Can't find and click add to my reading list", 5);
        }
}
