package lib.ui;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
abstract public class MyListPageObject extends MainPageObject {


    protected static String
            FOLDER_BY_NAME_TPL,
            ARTICLE_BY_TITLE_TPL,
            ALERT_SYNC_CLOSE_BUTTON;

    public MyListPageObject(AppiumDriver driver) {
        super(driver);
    }

    public void openFolderByName(String nameOfFolder) {
        waitForElementAndClick(
                getFolderXpathByName(nameOfFolder),
                "Cannot find folder by name " + nameOfFolder,
                5);
    }

    public void waitForArticleToAppearByTitle(String articleTitle) {
        waitForElementPresent(getSavedArticleXpathByTitle(articleTitle), "Cannot find saved article by title " + articleTitle, 15);
    }

    public void waitForArticleToDisappearByTitle(String articleTitle) {
        waitForElementNotPresent(getSavedArticleXpathByTitle(articleTitle), "Saved article still present with title" + articleTitle, 15);
    }

    public void closeSyncAlert() {
        waitForElementAndClick(ALERT_SYNC_CLOSE_BUTTON, "Can't close sync alert", 5);
        waitForElementAndClick(ALERT_SYNC_CLOSE_BUTTON, "Can't close sync alert", 5);
    }

    public void swipeByArticleToDelete(String articleTitle) {
        waitForArticleToAppearByTitle(articleTitle);
        swipeElementToLeft(
                getSavedArticleXpathByTitle(articleTitle),
                "Cannot find saved article");
        if (Platform.getInstance().isIOS()) {
            clickElementToTheRightUpperCorner(getSavedArticleXpathByTitle(articleTitle), "Error");
        }

        waitForArticleToDisappearByTitle(articleTitle);
    }

    private static String getFolderXpathByName(String nameOfFolder) {
        return FOLDER_BY_NAME_TPL.replace("{FOLDER_NAME}", nameOfFolder);
    }

    private static String getSavedArticleXpathByTitle(String articleTitle) {
        return ARTICLE_BY_TITLE_TPL.replace("{TITLE}", articleTitle);
    }



}
