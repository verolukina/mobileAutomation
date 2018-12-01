package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ArticlePageObject extends MainPageObject {

        private static final String
        TITLE = "org.wikipedia:id/view_page_title_text",
        FOOTER_ELEMENT = "//*[@text='View page in browser']",
        OPTION_BUTTON = "//android.widget.ImageView[@content-desc='More options']",
        OPTION_ADD_TO_MY_LIST_BUTTON = "//*[@text='Add to reading list']",
        ADD_TO_MY_LIST_OVERLAY = "org.wikipedia:id/onboarding_button",
        MY_LIST_NAME_INPUT = "org.wikipedia:id/text_input",
        MY_LIST_OK_BUTTON = "//*[@text='OK']",
        CLOSE_ARTICLE_BUTTON = "//android.widget.ImageButton[@content-desc='Navigate up']";


        public ArticlePageObject(AppiumDriver driver) {
                super(driver);
        }

        public WebElement waitForTitleElement() {
                return waitForElementPresent(By.id(TITLE), "Cannot find article title on page", 15);
        }

        public String getArticleTitle() {
                return waitForTitleElement().getAttribute("text");
        }

        public void swipeToFooter() {
                swipeUpToFindElement(
                        By.xpath(FOOTER_ELEMENT),
                        "Cannot find the end of article",
                        20);
        }

        public void addArticleToMyList(String nameOfFolder) {
                waitForElementAndClick(
                        By.xpath(OPTION_BUTTON),
                        "Cannot find button to open article options",
                        15);
                waitForElementAndClick(
                        By.xpath(OPTION_ADD_TO_MY_LIST_BUTTON),
                        "Cannot find options to add article to reading list",
                        15);
                waitForElementAndClick(
                        By.id(ADD_TO_MY_LIST_OVERLAY),
                        "Cannot find 'Got it' tip overlay",
                        5);
                waitForElementAndClear(
                        By.id(MY_LIST_NAME_INPUT),
                        "Cannot find input to set name of articles folder",
                        5);

                waitForElementAndSendKeys(
                        By.id(MY_LIST_NAME_INPUT),
                        nameOfFolder,
                        "Cannot put text into article folder input",
                        5);
                waitForElementAndClick(
                        By.xpath(MY_LIST_OK_BUTTON),
                        "Cannot press OK button",
                        5);
        }

        public void closeArticle() {
                waitForElementAndClick(
                        By.xpath(CLOSE_ARTICLE_BUTTON),
                        "Cannot close article, cannot find X link",
                        5);
        }
}
