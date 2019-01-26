package test;

import lib.CoreTestCase;
import lib.ui.SearchPageObject;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

public class SearchTests extends CoreTestCase {

    @Test
    public void testSearch() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.waitForSearchResult("Object-oriented programming language");
    }

    @Test
    public void testCancelSearch() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.waitForCancelButtonToAppear();
        searchPageObject.clickCancelSearch();
        searchPageObject.waitForCancelButtonToDisappear();
    }

    @Test
    public void testAmountOfNotEmptySearch() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        String searchLine = "Linkin Park Discography";
        searchPageObject.typeSearchLine(searchLine);
        int amoutOfSearchResult = searchPageObject.getAmaountOfFoundArticles();
        assertTrue("We found too few results!",
                amoutOfSearchResult > 0);
    }

    @Test
    public void testAmountOfEmptySearch() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        String searchLine = "asdaszxxcz";
        searchPageObject.typeSearchLine(searchLine);
        searchPageObject.waitForEmptyResultsLabel();
        searchPageObject.assertThereIsNoResultOfSearch();
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
        String expectedString = "Search…";
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        String searchFieldContent = searchPageObject.getSearchFieldContent();
        assertEquals("Can't find expected string "+ expectedString +"", expectedString, searchFieldContent);
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
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        String searchLine = "java";
        searchPageObject.typeSearchLine(searchLine);

        int pagesCount = searchPageObject.getAmaountOfFoundArticles();
        assertTrue(
                "Only one item or less",
                pagesCount > 1);
        searchPageObject.clickCancelSearch();
        searchPageObject.waitForEmptySearch();
    }

    //
//    /*
//    Ex4*: Тест: проверка слов в поиске
//    Написать тест, который:
//    1. Ищет какое-то слово
//    2. Убеждается, что в каждом результате поиска есть это слово.
//     */
//    @Test
//    public void testSearchContaintAndClearArticles() {
//        String keyword = "JAVA";
//        mainPageObject.waitForElementAndClick(
//                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
//                "Cannot find search input",
//                2);
//        mainPageObject.waitForElementAndSendKeys(
//                By.xpath("//*[contains(@text, 'Search…')]"),
//                keyword,
//                "Cannot find search input",
//                2);
//        List<WebElement> pageListItems = mainPageObject.waitForElementsPresent(
//                By.id("org.wikipedia:id/page_list_item_title"),
//                "Cannot find list item container",
//                2);
//        for (WebElement element: pageListItems) {
//            String textString =  element.getAttribute("text");
//            assertTrue("Text does not contain the word " + keyword, textString.toLowerCase().contains(keyword.toLowerCase()));
//        }
//    }

}
