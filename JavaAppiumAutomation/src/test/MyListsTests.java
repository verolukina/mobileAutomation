package test;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.ArticlePageObject;
import lib.ui.MyListPageObject;
import lib.ui.NavigationUI;
import lib.ui.SearchPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListsPageObjectFactory;
import lib.ui.factories.NavigationUIFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;
import org.openqa.selenium.WebElement;

import java.util.List;

public class MyListsTests extends CoreTestCase {

    public static String nameOfFolder = "Learning programming";

    @Test
    public void testSaveFirstArticleToMyList() {

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        articlePageObject.waitForTitleElement();
        String articleTitle = articlePageObject.getArticleTitle();

        if (Platform.getInstance().isAndroid()) {
            articlePageObject.addArticleToMyList(nameOfFolder);
        } else {
            articlePageObject.addArticleToMySaved();
        }

        articlePageObject.closeArticle();

        if (Platform.getInstance().isIOS()) {
            searchPageObject.clickCancelSearch();
        }

        NavigationUI navigationUI = NavigationUIFactory.get(driver);
        navigationUI.clickMyLists();


        MyListPageObject myListPageObject = MyListsPageObjectFactory.get(driver);

        if (Platform.getInstance().isAndroid()) {
            myListPageObject.openFolderByName(nameOfFolder);
        } else {
            myListPageObject.closeSyncAlert();
        }

        myListPageObject.swipeByArticleToDelete(articleTitle);

    }


       /* III. Сложные тесты
     Ex5: Тест: сохранение двух статей
     Написать тест, который:
     1. Сохраняет две статьи в одну папку
     2. Удаляет одну из статей
     3. Убеждается, что вторая осталась
     4. Переходит в неё и убеждается, что title совпадает
     
     Ex11: Рефакторинг тестов
     Адаптировать под iOS тест на удаление одной сохраненной статьи из двух.
     Вместо проверки title-элемента придумать другой способ верификации оставшейся статьи
     (т.е. способ убедиться, что осталась в сохраненных ожидаемая статья).
    */

    @Test
    public void testAddingTwoArticles() {
        String nameOfFolder = "Test Adding Two Articles";
        String survivingArticle = "Island of Indonesia";
        String deadArticle = "Object-oriented programming language";
        String checkTitle = "Java";

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(checkTitle);

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        if (Platform.getInstance().isAndroid()) {
            articlePageObject.addArticleToMyListThroughShortMenuFirstTime(nameOfFolder, survivingArticle, searchPageObject);
            articlePageObject.addArticleToMyExistingListThroughShortMenu(nameOfFolder, deadArticle, searchPageObject);
            searchPageObject.clickCancelSearch();
            searchPageObject.clickCancelSearch();
        } else {
            searchPageObject.clickByArticleWithSubstring(survivingArticle);
            // 2 тапа , потому что 1ый скрывает всплывающее сообщение
            articlePageObject.addArticleToMySaved();
            articlePageObject.addArticleToMySaved();
            articlePageObject.closeArticle();
            searchPageObject.clickByArticleWithSubstring(deadArticle);
            articlePageObject.addArticleToMySaved();
            articlePageObject.closeArticle();
            searchPageObject.clickCancelSearch();
        }


        NavigationUI navigationUI = NavigationUIFactory.get(driver);
        navigationUI.clickMyLists();

        MyListPageObject myListPageObject = MyListsPageObjectFactory.get(driver);
        // по названию папки попадает в нее через раз
        if (Platform.getInstance().isAndroid()) {
            myListPageObject.openFolderByName("2 articles, 0.00 MiB");
            myListPageObject.swipeByArticleToDelete(Character.toLowerCase(deadArticle.charAt(0)) + deadArticle.substring(1));
            searchPageObject.clickByArticleWithSubstring(Character.toLowerCase(survivingArticle.charAt(0)) + survivingArticle.substring(1));
            assertEquals("Article title not valid",
                    articlePageObject.getArticleTitle(),
                    checkTitle);
        } else {
            myListPageObject.closeSyncAlert();
            myListPageObject.swipeByArticleToDelete(deadArticle);
            searchPageObject.clickByArticleWithSubstring(survivingArticle);
            articlePageObject.checkSavedButtonIsActive();

        }


    }

}
