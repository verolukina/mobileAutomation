import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

import org.openqa.selenium.Dimension;
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

    @Test
    public void testSwipeArticle() {
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find search input",
                2);
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Appium",
                "Cannot find search input",
                2);
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='Appium']"),
                "Cannot find 'Appium' article title",
                2);
        waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                15);
        swipeUpToFindElement(
                By.xpath("//*[@text='View page in browser']"),
                "Cannot find the end of the article",
                20);
    }

    @Test
    public void saveFirstArticleToMyList() {
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
        waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                15);
        waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Cannot find button to open article options",
                15);
        waitForElementAndClick(
                By.xpath("//*[@text='Add to reading list']"),
                "Cannot find options to add article to reading list",
                15);
        waitForElementAndClick(
                By.id("org.wikipedia:id/onboarding_button"),
                "Cannot find 'Got it' tip overlay",
                5);
        waitForElementAndClear(
                By.id("org.wikipedia:id/text_input"),
                "Cannot find input to set name of articles folder",
                5);

        String nameOfFolder = "Learning programming";
        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                nameOfFolder,
                "Cannot put text into article folder input",
                5);
        waitForElementAndClick(
                By.xpath("//*[@text='OK']"),
                "Cannot press OK button",
                5);
        waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot close article, cannot find X link",
                5);
        waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "Cannot find navigation button to my list",
                5);
        //Кликает на рандомный элемент , если искать элемент по строке '1 article, 2.30 MiB' то находит нормально
        waitForElementAndClick(
                By.xpath("//*[@text='"+nameOfFolder+"']"),
                "Cannot find created folder",
                15);
        swipeElementToLeft(
                By.xpath("//*[@text='Java (programming language)']"),
                "Cannot find saved article");
        weaitForElementNotPresent(
                By.xpath("//*[@text='Java (programming language)']"),
                "Cannot delete saved article",
                5);

    }

    @Test
    public void testAmountOfNotEmptySearch() {
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find search input",
                2);
        String searchLine = "Linkin Park Diskography";
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                searchLine,
                "Cannot find search input",
                2);

        String searchResultLocator = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']";
        waitForElementsPresent(
                By.xpath(searchResultLocator),
                "Cannot find anything by the request" + searchLine,
                15);
        int amoutOfSearchResult = getAmountOfElements(
                By.xpath(searchResultLocator)
        );
        Assert.assertTrue("We found too few results!",
                amoutOfSearchResult > 0);
    }

    @Test
    public void testAmountOfEmptySearch() {
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find search input",
                2);
        String searchLine = "asdasdsfgdf";
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                searchLine,
                "Cannot find search input",
                2);
        String searchResultLocator = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']";
        String emptyResultLabel = "//*[@text='No results found']";

        waitForElementsPresent(
                By.xpath(emptyResultLabel),
                "Cannot find empty result label by the request " + searchLine,
                15);
        assertElementNotPresent(
                By.xpath(searchResultLocator),
                "We've found some results by request " + searchLine);
    }

    @Test
    public void testCheckSearchArticleInBackground() {
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
                2);
        driver.runAppInBackground(2);
        waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find article after returning from background",
                2);

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

       /* III. Сложные тесты
     Ex5: Тест: сохранение двух статей
     Написать тест, который:
     1. Сохраняет две статьи в одну папку
     2. Удаляет одну из статей
     3. Убеждается, что вторая осталась
     4. Переходит в неё и убеждается, что title совпадает
    */

    @Test
    public void testAddingTwoArticles() {
        String nameOfFolder = "Test Adding Two Articles";
        String survivingArticle = "Island of Indonesia";
        String deadArticle = "Object-oriented programming language";
        String checkTitle = "Java";

        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find search input",
                2);
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                checkTitle,
                "Cannot find search input",
                2);
        WebElement firstElement = waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='"+ deadArticle +"']"),
                "Cannot find '"+ deadArticle +"' topic by 'Java'",
                2);
        new TouchAction(driver).longPress(firstElement).release().perform();
        waitForElementAndClick(
                By.xpath("//*[@text='Add to reading list']"),
                "Cannot find 'Add to reading list' button",
                5);
        waitForElementAndClick(
                By.id("org.wikipedia:id/onboarding_button"),
                "Cannot find 'Got it' tip overlay",
                5);
        waitForElementAndClear(
                By.id("org.wikipedia:id/text_input"),
                "Cannot find input to set name of articles folder",
                5);
        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                nameOfFolder,
                "Cannot put text into article folder input",
                5);
        waitForElementAndClick(
                By.xpath("//*[@text='OK']"),
                "Cannot press OK button",
                5);
        WebElement secondElement = waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='"+ survivingArticle +"']"),
                "Cannot find '"+ survivingArticle +"' topic by 'Java'",
                2);
        new TouchAction(driver).longPress(secondElement).release().perform();
        waitForElementAndClick(
                By.xpath("//*[@text='Add to reading list']"),
                "Cannot find 'Add to reading list' button",
                5);
        waitForElementAndClick(
                By.xpath("//*[@text='"+ nameOfFolder +"']"),
                "Cannot find '"+ nameOfFolder +"' folder",
                5);
        waitForElementAndClick(
                By.xpath("//*[@class='android.widget.ImageButton']"),
                "",
                2);

        WebElement listElement =  waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "Cannot find navigation button to my list",
                10);
        waitForElementAndClick(
                By.id("org.wikipedia:id/item_image_container"),
                "Cannot find created folder",
                15);
        swipeElementToLeft(
                By.xpath("//*[@text='Java (programming language)']"),
                "Cannot find saved article");

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='"+ checkTitle +"']"),
                "Cannot find '"+ survivingArticle +"' topic by '"+ nameOfFolder +"'",
                2);
        String articleTitle = waitForElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Cannot find title of article",
                15);
        Assert.assertEquals(
                "article title not valid",
                articleTitle,
                checkTitle);

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

    protected void swipeUp(int timeOfSwipe) {
        TouchAction action = new TouchAction(driver);
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

    protected void swipeUpQuick() {
        swipeUp(200);
    }

    protected  void swipeUpToFindElement(By by, String error_message, int maxSwipes) {
        int alreadySwiped = 0;
        while (driver.findElements(by).size() == 0) {
            if (alreadySwiped > maxSwipes) {
                waitForElementsPresent(by, "Cannot find element by swiping up. \n" + error_message, 0);
                return;
            }
            swipeUpQuick();
            ++alreadySwiped;
        }
    }

    protected void swipeElementToLeft(By by, String error_message) {
        WebElement element = waitForElementPresent(
                by,
                error_message,
                10);
        int leftX = element.getLocation().x;
        int rightX = element.getSize().width;
        int upperY = element.getLocation().y;
        int lowerY = element.getSize().height + upperY;
        int middleY = (upperY + lowerY) / 2;

        TouchAction action = new TouchAction(driver);
        action.
                press(rightX, middleY)
                .waitAction(300)
                .moveTo(leftX, middleY)
                .release()
                .perform();
    }

    private int getAmountOfElements(By by) {
        List elements = driver.findElements(by);
        return elements.size();
    }

    private void assertElementNotPresent(By by, String errorMessage) {
        int amountOfElements = getAmountOfElements(by);
        if (amountOfElements > 0) {
            String defaultMessage = "An element '" + by.toString() + "' supposed to be not present";
            throw new AssertionError(defaultMessage + " " + errorMessage);
        }
    }

    private  String waitForElementAndGetAttribute(By by, String attribute, String errorMessage, long timeoutInSeconds) {
        return waitForElementPresent(by, errorMessage, timeoutInSeconds).getAttribute(attribute);
    }

    private void assertElementPresent(By by, String errorMessage) {
        WebElement element = driver.findElement(by);
        if (element == null) {
            throw new AssertionError(errorMessage);
        }
    }



}
