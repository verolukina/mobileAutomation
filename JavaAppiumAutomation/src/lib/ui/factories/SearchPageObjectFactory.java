package lib.ui.factories;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
import lib.ui.Android.AndroidSearchPageObject;
import lib.ui.SearchPageObject;
import lib.ui.iOS.iOSSearchPageObject;

public class SearchPageObjectFactory {

    public static SearchPageObject get(AppiumDriver driver) {
        if (Platform.getInstance().isAndroid()) {
            return new AndroidSearchPageObject(driver);
        } else {
            return new iOSSearchPageObject(driver);
        }
    }

}
