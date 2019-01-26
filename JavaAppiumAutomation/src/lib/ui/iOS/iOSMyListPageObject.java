package lib.ui.iOS;

import io.appium.java_client.AppiumDriver;
import lib.ui.MyListPageObject;

public class iOSMyListPageObject extends MyListPageObject {

    static {
        ARTICLE_BY_TITLE_TPL = "xpath://XCUIElementTypeLink[contains(@name,'{TITLE}')]";
        ALERT_SYNC_CLOSE_BUTTON = "id:Close";
    }

    public iOSMyListPageObject(AppiumDriver driver) {
        super(driver);
    }
}
