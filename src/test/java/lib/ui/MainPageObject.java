package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import lib.Platform;

import java.util.List;
import java.util.regex.Pattern;

public class MainPageObject {

    protected RemoteWebDriver driver;

    public MainPageObject(RemoteWebDriver driver) {
        this.driver = driver;
    }

    public WebElement waitForElementPresent(String locator, String errorMessage, long timeoutInSeconds) {
        By by = getLocatorByString(locator);
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n" + "by" + locator + "\n");
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public WebElement waitForElementPresent(String locator, String errorMessage) {
        return waitForElementPresent(locator, errorMessage, 5);
    }

    public List<WebElement> waitForElementsPresent(String locator, String errorMessage, long timeoutInSeconds) {
        By by = getLocatorByString(locator);
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
    }

    public List<WebElement> waitForElementsPresent(String locator, String errorMessage) {
        return waitForElementsPresent(locator, errorMessage, 5);
    }

    public  WebElement waitForElementAndClick(String locator, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(locator, error_message, timeoutInSeconds);
        element.click();
        return element;
    }

    public  WebElement waitForElementAndSendKeys(String locator, String value, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(locator, error_message, timeoutInSeconds);
        element.sendKeys(value);
        return element;
    }

    public  boolean waitForElementNotPresent(String locator, String error_message, long timeoutInSeconds) {
        By by = getLocatorByString(locator);
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message);
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    public  WebElement waitForElementAndClear(String locator, String error_message, long timeoutInSeconds) {
        By by = getLocatorByString(locator);
        WebElement element = waitForElementPresent(locator, error_message, timeoutInSeconds);
        element.clear();
        return element;
    }

    public void swipeUp(int timeOfSwipe) {
        if (driver instanceof AppiumDriver) {
            TouchAction action = new TouchAction((AppiumDriver)driver);
            Dimension size = driver.manage().window().getSize();
            int x = size.width / 2;
            int startY = (int) (size.height * 0.8);
            int endY = (int) (size.height * 0.2);
            action
                    .press(x, startY)
                    .waitAction(timeOfSwipe)
                    .moveTo(x, endY)
                    .release()
                    .perform();
        }
    }

    public void swipeUpQuick() {
        swipeUp(200);
    }

    public void scrollWebPageUp() {
        if (Platform.getInstance().isMW()) {
            JavascriptExecutor JSExecutor = (JavascriptExecutor) driver;
            JSExecutor.executeScript("window.scrollBy(0, 250)");
        }
    }

    public void scrollWebPageTillElementNotVisible(String locator, String errorMessage, int max_swipes) {
        int already_swiped = 0;

        WebElement element = waitForElementPresent(locator, errorMessage);

        while (!isElementLocatedOnTheScreen(locator)) {
            scrollWebPageUp();
            ++already_swiped;
            if (already_swiped > max_swipes) {
                Assert.assertTrue(errorMessage, element.isDisplayed());
            }
        }
    }

    public  void swipeUpToFindElement(String locator, String error_message, int maxSwipes) {
        By by = getLocatorByString(locator);

        int alreadySwiped = 0;
        while (driver.findElements(by).size() == 0) {
            if (alreadySwiped > maxSwipes) {
                waitForElementsPresent(locator, "Cannot find element by swiping up. \n" + error_message, 0);
                return;
            }
            swipeUpQuick();
            ++alreadySwiped;
        }
    }

    public void swipeUpTillElementAppear(String locator, String errorMessage, int maxSwipes) {
        int alreadySwiped = 0;

        while (!isElementLocatedOnTheScreen(locator)) {
            if (alreadySwiped > maxSwipes) {
                Assert.assertTrue(errorMessage, isElementLocatedOnTheScreen(locator));
            }
            swipeUpQuick();
            ++alreadySwiped;
        }
    }

    public boolean isElementLocatedOnTheScreen(String locator) {
        int elementLocationByY = waitForElementPresent(locator, "", 5).getLocation().getY();
        if (Platform.getInstance().isMW()) {
            JavascriptExecutor JSExecutor = (JavascriptExecutor) driver;
            Object js_result = JSExecutor.executeScript("return window.pageYOffset");
            elementLocationByY -= Integer.parseInt(js_result.toString());
        }
        int screenSizeByY = driver.manage().window().getSize().getHeight();
        return elementLocationByY < screenSizeByY;
    }

    public void clickElementToTheRightUpperCorner(String locator, String error) {
        if (driver instanceof AppiumDriver) {
            WebElement element = waitForElementPresent(locator + "/..", error);

            int rightX = element.getLocation().getX();
            int upperY = element.getLocation().getY();
            int lowerY = upperY + element.getSize().getHeight();
            int middleY = (upperY + lowerY) / 2;
            int width = element.getSize().getWidth();

            int pointToClickX = (rightX + width) - 3;
            int pointToClickY = middleY;

            TouchAction action = new TouchAction((AppiumDriver)driver);
            action.tap(pointToClickX, pointToClickY).perform();
        }

    }

    public void swipeElementToLeft(String locator, String error_message) {
        if (driver instanceof AppiumDriver) {
            WebElement element = waitForElementPresent(
                    locator,
                    error_message,
                    10);
            int leftX = element.getLocation().x;
            int rightX = element.getSize().width;
            int upperY = element.getLocation().y;
            int lowerY = element.getSize().height + upperY;
            int middleY = (upperY + lowerY) / 2;

            TouchAction action = new TouchAction((AppiumDriver)driver);
            action.press(rightX, middleY);
            action.waitAction(300);
            if (Platform.getInstance().isAndroid()) {
                action.moveTo(leftX, middleY);
            } else {
                int offsetX = (-1 * element.getSize().getWidth());
                action.moveTo(offsetX, 0);
            }
            action.release();
            action.perform();
        }
    }

    public int getAmountOfElements(String locator) {
        By by = getLocatorByString(locator);

        List elements = driver.findElements(by);
        return elements.size();
    }

    public boolean isElementPresent(String locator) {
        return getAmountOfElements(locator) > 0;
    }

    public void tryClickElementWithFewAttempts(String locator, String errorMessage, int amountOfAttempts) {
        int current_attempts = 0;
        boolean needMoreAttempts = true;

        while (needMoreAttempts) {
            try {
                waitForElementAndClick(locator, errorMessage, 1);
                needMoreAttempts = false;
            } catch (Exception e) {
                if (current_attempts > amountOfAttempts) {
                    waitForElementAndClick(locator, errorMessage, 1);
                }
            }
            ++current_attempts;
        }
    }

    public void assertElementNotPresent(String locator, String errorMessage) {
        int amountOfElements = getAmountOfElements(locator);
        if (amountOfElements > 0) {
            String defaultMessage = "An element '" + locator + "' supposed to be not present";
            throw new AssertionError(defaultMessage + " " + errorMessage);
        }
    }

    public  String waitForElementAndGetAttribute(String locator, String attribute, String errorMessage, long timeoutInSeconds) {
        return waitForElementPresent(locator, errorMessage, timeoutInSeconds).getAttribute(attribute);
    }

    private By getLocatorByString(String locatorWithType) {
        String[] explodedLocator = locatorWithType.split(Pattern.quote(":"), 2);
        String byType = explodedLocator[0];
        String locator = explodedLocator[1];

        if(byType.equals("xpath")) {
            return By.xpath(locator);
        } else if (byType.equals("id")) {
            return By.id(locator);
        } else if (byType.equals("css")) {
            return By.cssSelector(locator);
        } else {
            throw new IllegalArgumentException("Can't get type by locator. Locator:" + locatorWithType);
        }
    }






}
