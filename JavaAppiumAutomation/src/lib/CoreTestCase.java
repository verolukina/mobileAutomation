package lib;

import io.appium.java_client.AppiumDriver;
import junit.framework.TestCase;
import lib.ui.WelcomePageObject;
import org.openqa.selenium.ScreenOrientation;


public class CoreTestCase extends TestCase {



    protected AppiumDriver driver;

    @Override
    protected void setUp() throws  Exception {
        super.setUp();
        driver = Platform.getInstance().getDriver();
        rotateScreenPortrait();
        skipWelcomePageObjectForiOS();
    }

    @Override
    protected void tearDown() throws Exception {
        driver.quit();
        super.tearDown();
    }

    protected void rotateScreenPortrait() {
        driver.rotate(ScreenOrientation.PORTRAIT);
    }

    protected void rotateScreenLandscape() {
        driver.rotate(ScreenOrientation.LANDSCAPE);
    }

    protected void backgroundApp(int seconds) {
        driver.runAppInBackground(2);
    }

    private void skipWelcomePageObjectForiOS() {
        if (Platform.getInstance().isIOS()) {
            WelcomePageObject welcomePageObject = new WelcomePageObject(driver);
            welcomePageObject.clickSkipButton();
        }
    }


}
