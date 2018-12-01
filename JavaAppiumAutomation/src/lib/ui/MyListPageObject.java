package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class MyListPageObject extends MainPageObject {


    public  static final String
            FOLDER_BY_NAME_TPL = "//*[@text='{FOLDER_NAME}']",
            ARTICLE_BY_TITLE_TPL = "//*[@text='{TITLE}']";

    public MyListPageObject(AppiumDriver driver) {
        super(driver);
    }

    public void openFolderByName(String nameOfFolder) {
        waitForElementAndClick(
                By.xpath(getFolderXpathByName(nameOfFolder)),
                "Cannot find folder by name " + nameOfFolder,
                5);
    }

    public void waitForArticleToAppearByTitle(String articleTitle) {
        waitForElementPresent(By.xpath(getFolderXpathByName(articleTitle)), "Cannot find saved article by title " + articleTitle, 15);
    }

    public void waitForArticleToDisappearByTitle(String articleTitle) {
        waitForElementNotPresent(By.xpath(getFolderXpathByName(articleTitle)), "Saved article still present with title" + articleTitle, 15);
    }

    public void swipeByArticleToDelete(String articleTitle) {
        waitForArticleToAppearByTitle(articleTitle);
        swipeElementToLeft(
                By.xpath(getFolderXpathByName(articleTitle)),
                "Cannot find saved article");
        waitForArticleToDisappearByTitle(articleTitle);
    }

    private static String getFolderXpathByName(String nameOfFolder) {
        return FOLDER_BY_NAME_TPL.replace("{FOLDER_NAME}", nameOfFolder);
    }

    private static String getSavedArticleXpathByTitle(String articleTitle) {
        return ARTICLE_BY_TITLE_TPL.replace("{TITLE}", articleTitle);
    }



}
