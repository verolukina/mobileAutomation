package lib.ui.iOS;

import io.appium.java_client.AppiumDriver;
import lib.ui.MyListPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class iOSMyListPageObject extends MyListPageObject {

    static {
        ARTICLE_BY_TITLE_TPL = "xpath://XCUIElementTypeLink[contains(@name,'{TITLE}')]";
        ALERT_SYNC_CLOSE_BUTTON = "id:Close";
    }

    public iOSMyListPageObject(RemoteWebDriver driver) {
        super(driver);
    }
}
