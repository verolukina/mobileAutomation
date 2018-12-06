package lib.ui;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
abstract public class MyListPageObject extends MainPageObject {


    protected static String
            FOLDER_BY_NAME_TPL,
            ARTICLE_BY_TITLE_TPL;

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
        waitForElementPresent(getFolderXpathByName(articleTitle), "Cannot find saved article by title " + articleTitle, 15);
    }

    public void waitForArticleToDisappearByTitle(String articleTitle) {
        waitForElementNotPresent(getFolderXpathByName(articleTitle), "Saved article still present with title" + articleTitle, 15);
    }

    public void swipeByArticleToDelete(String articleTitle) {
        waitForArticleToAppearByTitle(articleTitle);
        swipeElementToLeft(
                getFolderXpathByName(articleTitle),
                "Cannot find saved article");
        if (Platform.getInstance().isIOS()) {
            clickElementToTheRightUpperCorner(getFolderXpathByName(articleTitle), "Error");
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
