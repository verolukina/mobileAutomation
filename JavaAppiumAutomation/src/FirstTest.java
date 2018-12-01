import io.appium.java_client.TouchAction;
import lib.CoreTestCase;
import lib.ui.*;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;


import java.util.List;


public class FirstTest extends CoreTestCase {

    private MainPageObject mainPageObject;
    protected  void setUp() throws Exception {
        super.setUp();
        mainPageObject = new MainPageObject(driver);
    }

    @Test
    public void testSearch() {
        SearchPageObject searchPageObject = new SearchPageObject(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.waitForSearchResult("Object-oriented programming language");
    }

    @Test
    public void testCancelSearch() {
        SearchPageObject searchPageObject = new SearchPageObject(driver);

        searchPageObject.initSearchInput();
        searchPageObject.waitForCancelButtonToAppear();
        searchPageObject.clickCancelSearch();
        searchPageObject.waitForCancelButtonToDisappear();
    }

    @Test
    public  void testCompareArticleTitle() {
        SearchPageObject searchPageObject = new SearchPageObject(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject articlePageObject = new ArticlePageObject(driver);
        String articleTitle = articlePageObject.getArticleTitle();

        Assert.assertEquals(
                "We see unexpected title!",
                "Java (programming language)",
                articleTitle);

    }

    @Test
    public void testSwipeArticle() {
        SearchPageObject searchPageObject = new SearchPageObject(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Appium");
        searchPageObject.clickByArticleWithSubstring("Appium");

        ArticlePageObject articlePageObject = new ArticlePageObject(driver);
        articlePageObject.waitForTitleElement();
        articlePageObject.swipeToFooter();
    }

    @Test
    public void testSaveFirstArticleToMyList() {

        SearchPageObject searchPageObject = new SearchPageObject(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject articlePageObject = new ArticlePageObject(driver);
        articlePageObject.waitForTitleElement();
        String articleTitle = articlePageObject.getArticleTitle();
        String nameOfFolder = "Learning programming";
        articlePageObject.addArticleToMyList(nameOfFolder);
        articlePageObject.closeArticle();

        NavigationUI navigationUI = new NavigationUI(driver);
        navigationUI.clickMyLists();

        //Кликает на рандомный элемент , если искать элемент по строке '1 article, 2.30 MiB' то находит нормально
        MyListPageObject myListPageObject = new MyListPageObject(driver);
        myListPageObject.openFolderByName(nameOfFolder);
        myListPageObject.swipeByArticleToDelete(articleTitle);

    }

    @Test
    public void testAmountOfNotEmptySearch() {
        mainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find search input",
                2);
        String searchLine = "Linkin Park Diskography";
        mainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                searchLine,
                "Cannot find search input",
                2);

        String searchResultLocator = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']";
        mainPageObject.waitForElementsPresent(
                By.xpath(searchResultLocator),
                "Cannot find anything by the request" + searchLine,
                15);
        int amoutOfSearchResult = mainPageObject.getAmountOfElements(
                By.xpath(searchResultLocator)
        );
        Assert.assertTrue("We found too few results!",
                amoutOfSearchResult > 0);
    }

    @Test
    public void testAmountOfEmptySearch() {
        mainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find search input",
                2);
        String searchLine = "asdasdsfgdf";
        mainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                searchLine,
                "Cannot find search input",
                2);
        String searchResultLocator = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']";
        String emptyResultLabel = "//*[@text='No results found']";

        mainPageObject.waitForElementsPresent(
                By.xpath(emptyResultLabel),
                "Cannot find empty result label by the request " + searchLine,
                15);
        mainPageObject.assertElementNotPresent(
                By.xpath(searchResultLocator),
                "We've found some results by request " + searchLine);
    }

    @Test
    public void testCheckSearchArticleInBackground() {
        mainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find search input",
                2);
        mainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "JAVA",
                "Cannot find search input",
                2);
        mainPageObject.waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' topic by 'Java'",
                2);
        driver.runAppInBackground(2);
        mainPageObject.waitForElementPresent(
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
        mainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find search input",
                2);
        WebElement searchField = mainPageObject.waitForElementPresent(
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
        mainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find search input",
                2);
        mainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "JAVA",
                "Cannot find search input",
                2);
        List<WebElement> pageListItems = mainPageObject.waitForElementsPresent(
                By.id("org.wikipedia:id/page_list_item_container"),
                "Cannot find list item container",
                2);
        Assert.assertTrue(
                "Only one item or less",
                pageListItems.size() > 1);
        mainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Cannot find X to cancel search",
                2);
        mainPageObject.waitForElementsPresent(
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
        mainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find search input",
                2);
        mainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                keyword,
                "Cannot find search input",
                2);
        List<WebElement> pageListItems = mainPageObject.waitForElementsPresent(
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

        mainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find search input",
                2);
        mainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                checkTitle,
                "Cannot find search input",
                2);
        WebElement firstElement = mainPageObject.waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='"+ deadArticle +"']"),
                "Cannot find '"+ deadArticle +"' topic by 'Java'",
                2);
        new TouchAction(driver).longPress(firstElement).release().perform();
        mainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='Add to reading list']"),
                "Cannot find 'Add to reading list' button",
                5);
        mainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/onboarding_button"),
                "Cannot find 'Got it' tip overlay",
                5);
        mainPageObject.waitForElementAndClear(
                By.id("org.wikipedia:id/text_input"),
                "Cannot find input to set name of articles folder",
                5);
        mainPageObject.waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                nameOfFolder,
                "Cannot put text into article folder input",
                5);
        mainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='OK']"),
                "Cannot press OK button",
                5);
        WebElement secondElement = mainPageObject.waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='"+ survivingArticle +"']"),
                "Cannot find '"+ survivingArticle +"' topic by 'Java'",
                2);
        new TouchAction(driver).longPress(secondElement).release().perform();
        mainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='Add to reading list']"),
                "Cannot find 'Add to reading list' button",
                5);
        mainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='"+ nameOfFolder +"']"),
                "Cannot find '"+ nameOfFolder +"' folder",
                5);
        mainPageObject.waitForElementAndClick(
                By.xpath("//*[@class='android.widget.ImageButton']"),
                "",
                2);

        WebElement listElement =  mainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "Cannot find navigation button to my list",
                10);
        mainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/item_image_container"),
                "Cannot find created folder",
                15);
        mainPageObject.swipeElementToLeft(
                By.xpath("//*[@text='Java (programming language)']"),
                "Cannot find saved article");

        mainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='"+ checkTitle +"']"),
                "Cannot find '"+ survivingArticle +"' topic by '"+ nameOfFolder +"'",
                2);
        String articleTitle = mainPageObject.waitForElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Cannot find title of article",
                15);
        Assert.assertEquals(
                "article title not valid",
                articleTitle,
                checkTitle);

    }

        /*
    Ex6: Тест: assert title
    Написать тест, который открывает статью и убеждается, что у нее есть элемент title.
    Важно: тест не должен дожидаться появления title, проверка должна производиться сразу.
    Если title не найден - тест падает с ошибкой. Метод можно назвать assertElementPresent.
     */

    @Test
    public void testAssertTitle() {
        String searchTitle = "Java";

        mainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find search input",
                2);
        mainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                searchTitle,
                "Cannot find search input",
                2);
        mainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' topic by 'Java'",
                2);
        mainPageObject.assertElementPresent(
                By.id("org.wikipedia:id/view_page_title_textssss"),
                "Title not present"
        );
    }

        /*
      Ex7*: Поворот экрана
      Appium устроен так, что может сохранить у себя в памяти поворот экрана, который использовался в предыдущем тесте,
      и начать новый тест с тем же поворотом. Мы написали тест на поворот экрана, и он может сломаться до того,
      как положение экрана восстановится. Следовательно, если мы запустим несколько тестов одновременно,
      последующие тесты будут выполняться в неправильном положении экрана, что может привести к
      незапланированным проблемам.
      Как нам сделать так, чтобы после теста на поворот экрана сам экран всегда оказывался в правильном положении,
      даже если тест упал в тот момент, когда экран был наклонен?
     */

    @Test
    public void testChangeScreenOrientationOnSearchResults() {
        try {
            mainPageObject.waitForElementAndClick(
                    By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                    "Cannot find search input",
                    2);
            String searchLine = "Java";
            mainPageObject.waitForElementAndSendKeys(
                    By.xpath("//*[contains(@text, 'Search…')]"),
                    searchLine,
                    "Cannot find search input",
                    2);
            mainPageObject.waitForElementAndClick(
                    By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                    "Cannot find 'Object-oriented programming language' topic by " + searchLine,
                    2);
            String titleBeforeRotation = mainPageObject.waitForElementAndGetAttribute(
                    By.id("org.wikipedia:id/view_page_title_text"),
                    "text",
                    "Cannot find title of article",
                    15);
            driver.rotate(ScreenOrientation.LANDSCAPE);
            String titleAfterRotation = mainPageObject.waitForElementAndGetAttribute(
                    By.id("org.wikipedia:id/view_page_title_text"),
                    "text",
                    "Cannot find title of article",
                    15);
            Assert.assertEquals(
                    "article title have been changed after screen rotation",
                    titleBeforeRotation,
                    titleAfterRotation);
            driver.rotate(ScreenOrientation.PORTRAIT);
            String titleAfterSecondRotation = mainPageObject.waitForElementAndGetAttribute(
                    By.id("org.wikipedia:id/view_page_title_text"),
                    "text",
                    "Cannot find title of article",
                    15);
            Assert.assertEquals(
                    "article title have been changed after screen rotation",
                    titleBeforeRotation,
                    titleAfterSecondRotation);
        } catch (Exception error) {
            driver.rotate(ScreenOrientation.PORTRAIT);
            throw error;
        }
    }

}
