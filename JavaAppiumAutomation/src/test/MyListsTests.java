package test;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.MyListPageObject;
import lib.ui.NavigationUI;
import lib.ui.SearchPageObject;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

public class MyListsTests extends CoreTestCase {

    @Test
    public void testSaveFirstArticleToMyList() {

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

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

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(checkTitle);

        ArticlePageObject articlePageObject = new ArticlePageObject(driver);
        articlePageObject.addArticleToMyListThroughShortMenuFirstTime(nameOfFolder, survivingArticle, searchPageObject);
        articlePageObject.addArticleToMyExistingListThroughShortMenu(nameOfFolder, deadArticle, searchPageObject);

        searchPageObject.clickCancelSearch();
        searchPageObject.clickCancelSearch();

        NavigationUI navigationUI = new NavigationUI(driver);
        navigationUI.clickMyLists();

        MyListPageObject myListPageObject = new MyListPageObject(driver);
        // по названию папки попадает в нее через раз
        myListPageObject.openFolderByName("2 articles, 0.00 MiB");

        myListPageObject.swipeByArticleToDelete(Character.toLowerCase(deadArticle.charAt(0)) + deadArticle.substring(1));
        searchPageObject.clickByArticleWithSubstring(Character.toLowerCase(survivingArticle.charAt(0)) + survivingArticle.substring(1));

        assertEquals("Article title not valid",
                articlePageObject.getArticleTitle(),
                checkTitle);
    }

}
