package lib;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Platform {


    private static Platform instance;
    private static final String
            PLATFORM_IOS = "ios",
            PLATFORM_ANDROID = "android",
            PLATFORM_MOBILE_WEB = "mobile_web",
            APPIUM_URL = "http://127.0.0.1:4723/wd/hub";

    private Platform() {}

    public static Platform getInstance() {
        if (instance == null) {
            instance = new Platform();
        }
        return instance;
    }

    public RemoteWebDriver getDriver() throws Exception {
        URL url = new URL(APPIUM_URL);
        if (isAndroid()) {
            return new AndroidDriver(url, getAndroidDesiredCapabilities());
        } else if (isIOS()) {
            return new IOSDriver(url, getIOSDesiredCapabilities());
        } else if (isMW()) {
            return new ChromeDriver(getMWChromeOptions());
        } else {
            throw new Exception("Can't detect type of driver. Platform value:" + getPlatformVar());
        }
    }


    public boolean isAndroid() {
        return isPlatfrom(PLATFORM_ANDROID);
    }

    public boolean isIOS() {
        return isPlatfrom(PLATFORM_IOS);
    }

    public boolean isMW() { return  isPlatfrom(PLATFORM_MOBILE_WEB); }


    private  DesiredCapabilities getAndroidDesiredCapabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformName","Android");
        capabilities.setCapability("deviceName","AndroidTestDevice");
        capabilities.setCapability("platformName","8.0");
        capabilities.setCapability("automationName","Appium");
        capabilities.setCapability("appPackage","org.wikipedia");
        capabilities.setCapability("appActivity",".main.MainActivity");
        capabilities.setCapability("app","/Users/christian/Desktop/mobileAutomation/JavaAppiumAutomation/apks/org.wikipedia.apk");
        // без таймаута ошибка "org.openqa.selenium.WebDriverException: Returned value cannot be converted to WebElement: {element-6066-11e4-a52e-4f735466cecf=1}"
        capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, "60");
        return capabilities;
    }

    private  DesiredCapabilities getIOSDesiredCapabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformName","iOS");
        capabilities.setCapability("deviceName","iPhone X");
        capabilities.setCapability("platformVersion","11.4");
        capabilities.setCapability("app","/Users/christian/Desktop/mobileAutomation/JavaAppiumAutomation/apks//Wikipedia.app");
        // без таймаута ошибка "org.openqa.selenium.WebDriverException: Returned value cannot be converted to WebElement: {element-6066-11e4-a52e-4f735466cecf=1}"
        capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, "60");
        return capabilities;
    }

    private ChromeOptions getMWChromeOptions() {
        Map<String, Object> deviceMetrics = new HashMap<String, Object>();
        deviceMetrics.put("width", 360);
        deviceMetrics.put("height", 640);
        deviceMetrics.put("pixelRatio", 3.0);

        Map<String, Object> mobileEmulation = new HashMap<String, Object>();
        mobileEmulation.put("deviceMetrics", deviceMetrics);
        mobileEmulation.put("userAgent", "Mozilla/5.0 (Linux; Android 4.2.1; en-us; Nexus 5 Build/JOP40D) Chrome/18.0.1025.166 Mobile Safari/535.19");

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("window-size=340,640");

        return chromeOptions;
    }


    public String getPlatformVar() {
        return System.getenv("PLATFORM");
    }

    private boolean isPlatfrom(String myPlatform) {
        return myPlatform.equals(getPlatformVar());
    }
}
