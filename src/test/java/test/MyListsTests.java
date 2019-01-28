package test;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.*;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListsPageObjectFactory;
import lib.ui.factories.NavigationUIFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.WebElement;

import java.util.List;

public class MyListsTests extends CoreTestCase {

    public static String nameOfFolder = "Learning programming";
    private static final String login = "Verodartvaider";
    private static final String password = "qwertyzaq";

    @Test
    public void testSaveFirstArticleToMyList() {

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubstring("bject-oriented programming language");

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        articlePageObject.waitForTitleElement();
        String articleTitle = articlePageObject.getArticleTitle();

        if (Platform.getInstance().isAndroid()) {
            articlePageObject.addArticleToMyList(nameOfFolder);
        } else {
            articlePageObject.addArticleToMySaved();
        }

        if (Platform.getInstance().isMW()) {
            AutorizationPageObject Auth = new AutorizationPageObject(driver);
            Auth.clickAuthButton();
            Auth.enterLoginData(login, password);
            Auth.submitForm();

            articlePageObject.waitForTitleElement();

            Assert.assertEquals("We are not on the same page after login",
                    articleTitle,
                    articlePageObject.getArticleTitle());
            articlePageObject.addArticleToMySaved();
        }

        articlePageObject.closeArticle();

        if (Platform.getInstance().isIOS()) {
            searchPageObject.clickCancelSearch();
        }

        NavigationUI navigationUI = NavigationUIFactory.get(driver);
        navigationUI.openNavigation();
        navigationUI.clickMyLists();


        MyListPageObject myListPageObject = MyListsPageObjectFactory.get(driver);

        if (Platform.getInstance().isAndroid()) {
            myListPageObject.openFolderByName(nameOfFolder);
        } else if (Platform.getInstance().isIOS()) {
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

     Ex17: Рефакторинг
     Адаптировать под MW тест на удаление одной сохраненной статьи из двух.
     Вместо проверки title-элемента придумать другой способ верификации оставшейся статьи
     (т.е. способ убедиться, что осталась в сохраненных ожидаемая статья).
    */

    @Test
    public void testAddingTwoArticles() {
        String nameOfFolder = "Test Adding Two Articles";
        String survivingArticle = "sland of Indonesia";
        String deadArticle = "bject-oriented programming language";
        String checkTitle = "Java";
        String deadArticleTitle = "";

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(checkTitle);

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        if (Platform.getInstance().isAndroid()) {
            articlePageObject.addArticleToMyListThroughShortMenuFirstTime(nameOfFolder, survivingArticle, searchPageObject);
            articlePageObject.addArticleToMyExistingListThroughShortMenu(nameOfFolder, deadArticle, searchPageObject);
            searchPageObject.clickCancelSearch();
            searchPageObject.clickCancelSearch();
        } else  {
            searchPageObject.clickByArticleWithSubstring(survivingArticle);
            articlePageObject.addArticleToMySaved();
            if (Platform.getInstance().isIOS()) {
                articlePageObject.addArticleToMySaved();
                articlePageObject.closeArticle();
                searchPageObject.clickByArticleWithSubstring(deadArticle);
                articlePageObject.addArticleToMySaved();
                articlePageObject.closeArticle();
                searchPageObject.clickCancelSearch();
            } else {
                AutorizationPageObject Auth = new AutorizationPageObject(driver);
                Auth.clickAuthButton();
                Auth.enterLoginData(login, password);
                Auth.submitForm();

                articlePageObject.waitForTitleElement();

                Assert.assertEquals("We are not on the same page after login",
                        checkTitle,
                        articlePageObject.getArticleTitle());
                articlePageObject.addArticleToMySaved();

                searchPageObject.initSearchInput();
                searchPageObject.typeSearchLine(checkTitle);
                searchPageObject.clickByArticleWithSubstring(deadArticle);
                articlePageObject.addArticleToMySaved();
                deadArticleTitle = articlePageObject.getArticleTitle();
            }
        }


        NavigationUI navigationUI = NavigationUIFactory.get(driver);
        navigationUI.openNavigation();
        navigationUI.clickMyLists();

        MyListPageObject myListPageObject = MyListsPageObjectFactory.get(driver);
        if (Platform.getInstance().isAndroid()) {
            myListPageObject.openFolderByName(nameOfFolder);
            myListPageObject.swipeByArticleToDelete(Character.toLowerCase(deadArticle.charAt(0)) + deadArticle.substring(1));
            searchPageObject.clickByArticleWithSubstring(Character.toLowerCase(survivingArticle.charAt(0)) + survivingArticle.substring(1));
            assertEquals("Article title not valid",
                    articlePageObject.getArticleTitle(),
                    checkTitle);
        } else if (Platform.getInstance().isIOS()){
            myListPageObject.closeSyncAlert();
            myListPageObject.swipeByArticleToDelete(deadArticleTitle);
            searchPageObject.clickByArticleWithSubstring(survivingArticle);
            articlePageObject.checkSavedButtonIsActive();
        } else {
            myListPageObject.swipeByArticleToDelete(deadArticleTitle);
            myListPageObject.checkThatTitleAlive(checkTitle, "Article not found");
        }

    }

}
