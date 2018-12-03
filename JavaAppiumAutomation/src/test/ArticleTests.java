package test;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import org.junit.Test;

public class ArticleTests extends CoreTestCase {

    @Test
    public  void testCompareArticleTitle() {
        SearchPageObject searchPageObject = new SearchPageObject(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject articlePageObject = new ArticlePageObject(driver);
        String articleTitle = articlePageObject.getArticleTitle();

        assertEquals(
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


        /*
    Ex6: Тест: assert title
    Написать тест, который открывает статью и убеждается, что у нее есть элемент title.
    Важно: тест не должен дожидаться появления title, проверка должна производиться сразу.
    Если title не найден - тест падает с ошибкой. Метод можно назвать assertElementPresent.
     */

    @Test
    public void testAssertTitle() {
        String searchTitle = "Java";
        String subtitleString = "Object-oriented programming language";

        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(searchTitle);
        searchPageObject.clickByArticleWithSubstring(subtitleString);

        ArticlePageObject articlePageObject = new ArticlePageObject(driver);
        articlePageObject.assertTitleElementPresent("Title not present");
    }



}
