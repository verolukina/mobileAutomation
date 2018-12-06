package lib.ui.iOS;

import io.appium.java_client.AppiumDriver;
import lib.ui.ArticlePageObject;

public class iOSArticlePageObject extends ArticlePageObject {

    static {
        TITLE = "id:Java (programming language)";
        FOOTER_ELEMENT = "id:View article in browser";
        OPTION_ADD_TO_MY_LIST_BUTTON = "xpath://XCUIElementTypeButton[@name='Save for later']";
        CLOSE_ARTICLE_BUTTON = "id:Search";
        FOLDER_BY_NAME_TPL = "xpath://*[@text='{FOLDER_NAME}']";
    }

    public iOSArticlePageObject(AppiumDriver driver) {
        super(driver);
    }
}
