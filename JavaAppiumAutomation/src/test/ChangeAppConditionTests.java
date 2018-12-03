package test;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import org.junit.Test;

public class ChangeAppConditionTests extends CoreTestCase {

    @Test
    public void testCheckSearchArticleInBackground() {
        SearchPageObject searchPageObject = new SearchPageObject(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.waitForSearchResult("Object-oriented programming language");
        backgroundApp(2);
        searchPageObject.waitForSearchResult("Object-oriented programming language");
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
            SearchPageObject searchPageObject = new SearchPageObject(driver);

            searchPageObject.initSearchInput();
            searchPageObject.typeSearchLine("Java");
            searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

            ArticlePageObject pageObject = new ArticlePageObject(driver);
            String titleBeforeRotation = pageObject.getArticleTitle();

            rotateScreenLandscape();
            String titleAfterRotation = pageObject.getArticleTitle();
            assertEquals(
                    "article title have been changed after screen rotation",
                    titleBeforeRotation,
                    titleAfterRotation);
            rotateScreenPortrait();
            String titleAfterSecondRotation = pageObject.getArticleTitle();
            assertEquals(
                    "article title have been changed after screen rotation",
                    titleBeforeRotation,
                    titleAfterSecondRotation);
        } catch (Exception error) {
            rotateScreenPortrait();
            throw error;
        }
    }

}
