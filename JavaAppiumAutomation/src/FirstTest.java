import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.List;


public class FirstTest {

    private AppiumDriver driver;

    @Before
    public void setUp() throws  Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName","Android");
        capabilities.setCapability("deviceName","AndroidTestDevice");
        capabilities.setCapability("platformName","8.0");
        capabilities.setCapability("automationName","Appium");
        capabilities.setCapability("appPackage","org.wikipedia");
        capabilities.setCapability("appActivity",".main.MainActivity");
        capabilities.setCapability("app","/Users/vero/Desktop/JavaAppiumAutomation/apks/org.wikipedia.apk");
        // без таймаута ошибка "org.openqa.selenium.WebDriverException: Returned value cannot be converted to WebElement: {element-6066-11e4-a52e-4f735466cecf=1}"
        capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, "60");
        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void firstTest() {
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find search input",
                2);
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "JAVA",
                "Cannot find search input",
                2);
        waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' topic by 'Java'",
                15);
    }

    @Test
    public void testCancelSearch() {
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot fine 'Search Wikipedia' input",
                2);
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "JAVA",
                "Cannot find search input",
                2);
        waitForElementAndClear(
                By.id("org.wikipedia:id/search_src_text"),
                "Cannot find search field",
                2);
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Cannot find X to cancel search",
                2);
        weaitForElementNotPresent(
                By.id("org.wikipedia:id/search_close_btn"),
                "X is still present on the page",
                2);
    }

    @Test
    public  void testCompareArticleTitle() {
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find search input",
                2);
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "JAVA",
                "Cannot find search input",
                2);
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' topic by 'Java'",
                2);
        WebElement titleElement = waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                15);
        String articleTitle = titleElement.getAttribute("text");
        Assert.assertEquals(
                "We see unexpected title!",
                "Java (programming language)",
                articleTitle);

    }


    // II. Простые сценарии в Appium
    /*
     Ex2: Создание метода
     Написать функцию, которая проверяет наличие текста “Search…”
     в строке поиска перед вводом текста и помечает тест упавшим,
     если такого текста нет.
     */
    @Test
    public void testCheckSearchStringInSearchField() {
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find search input",
                2);
        WebElement searchField = waitForElementPresent(
                By.id("org.wikipedia:id/search_src_text"),
                "Cannot find search input",
                2);
        String placeholderText = searchField.getAttribute("text");
        Assert.assertEquals(
                "We see unexpected placeholder",
                "Search…",
                placeholderText);
    }

    /*
Ex3: Тест: отмена поиска
Написать тест, который:
1. Ищет какое-то слово
2. Убеждается, что найдено несколько статей
3. Отменяет поиск
4. Убеждается, что результат поиска пропал
 */
    @Test
    public void testSearchAndClearArticles() {
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find search input",
                2);
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "JAVA",
                "Cannot find search input",
                2);
        List<WebElement> pageListItems = waitForElementsPresent(
                By.id("org.wikipedia:id/page_list_item_container"),
                "Cannot find list item container",
                2);
        Assert.assertTrue(
                "Only one item or less",
                pageListItems.size() > 1);
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Cannot find X to cancel search",
                2);
        waitForElementsPresent(
                By.id("org.wikipedia:id/search_empty_image"),
                "Search result is not missing");
    }

    /*
    Ex4*: Тест: проверка слов в поиске
    Написать тест, который:
    1. Ищет какое-то слово
    2. Убеждается, что в каждом результате поиска есть это слово.
     */
    @Test
    public void testSearchContaintAndClearArticles() {
        String keyword = "JAVA";
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find search input",
                2);
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                keyword,
                "Cannot find search input",
                2);
        List<WebElement> pageListItems = waitForElementsPresent(
                By.id("org.wikipedia:id/page_list_item_title"),
                "Cannot find list item container",
                2);
        for (WebElement element: pageListItems) {
            String textString =  element.getAttribute("text");
            Assert.assertTrue("Text does not contain the word " + keyword, textString.toLowerCase().contains(keyword.toLowerCase()));
        }
    }



    // Private methods

    private WebElement waitForElementPresent(By by, String errorMessage, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    private WebElement waitForElementPresent(By by, String errorMessage) {
        return waitForElementPresent(by, errorMessage, 5);
    }

    private List<WebElement> waitForElementsPresent(By by, String errorMessage, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
    }

    private List<WebElement> waitForElementsPresent(By by, String errorMessage) {
        return waitForElementsPresent(by, errorMessage, 5);
    }

    private  WebElement waitForElementAndClick(By by, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.click();
        return element;
    }

    private  WebElement waitForElementAndSendKeys(By by, String value, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.sendKeys(value);
        return element;
    }

    private  boolean weaitForElementNotPresent(By by, String error_message, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message);
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    private  WebElement waitForElementAndClear(By by, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.clear();
        return element;
    }



}
