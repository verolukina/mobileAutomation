package lib.ui.iOS;

import io.appium.java_client.AppiumDriver;
import lib.ui.ArticlePageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class iOSArticlePageObject extends ArticlePageObject {

    static {
        TITLE = "id:Java (programming language)";
        FOOTER_ELEMENT = "id:View article in browser";
        OPTION_ADD_TO_MY_LIST_BUTTON = "id:Save for later";
        CLOSE_ARTICLE_BUTTON = "id:Search";
        FOLDER_BY_NAME_TPL = "xpath://*[@text='{FOLDER_NAME}']";
        SAVED_ARTICLE_BUTTON = "xpath://XCUIElementTypeButton[@name='Saved. Activate to unsave.']";
    }

    public iOSArticlePageObject(RemoteWebDriver driver) {
        super(driver);
    }
}
