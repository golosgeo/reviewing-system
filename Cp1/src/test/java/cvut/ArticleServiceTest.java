package cvut;

import cvut.dao.ArticleDao;
import cvut.dao.AutorDao;
import cvut.model.Article;
import cvut.model.Autor;
import cvut.services.ArticleService;
import cvut.services.AutorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@Transactional
@AutoConfigureTestEntityManager
@TestPropertySource(locations = "classpath:application-test.properties")
@ActiveProfiles("test")
public class ArticleServiceTest {
    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private TestEntityManager em;

    @Test
    public void test_find_article_by_id() {
        // Arrange
        ArticleDao articleDao = mock(ArticleDao.class);
        AutorDao autorDao = mock(AutorDao.class);
        ArticleService articleService = new ArticleService(articleDao, autorDao);
        Integer id = 1;
        Article expectedArticle = new Article();
        when(articleDao.find(id)).thenReturn(expectedArticle);

        // Act
        Article actualArticle = articleService.findById(id);

        // Assert
        assertEquals(expectedArticle, actualArticle);
        verify(articleDao).find(id);
    }

    @Test
    public void test_find_all_articles() {
        // Arrange
        ArticleDao articleDao = mock(ArticleDao.class);
        AutorDao autorDao = mock(AutorDao.class);
        ArticleService articleService = new ArticleService(articleDao, autorDao);
        List<Article> expectedArticles = new ArrayList<>();
        expectedArticles.add(new Article());
        expectedArticles.add(new Article());
        when(articleDao.findAll()).thenReturn(expectedArticles);

        // Act
        List<Article> actualArticles = articleService.findAll();

        // Assert
        assertEquals(expectedArticles, actualArticles);
        verify(articleDao).findAll();
    }

    @Test
    public void test_persist_null_article() {
        // Arrange
        ArticleDao articleDao = mock(ArticleDao.class);
        AutorDao autorDao = mock(AutorDao.class);
        ArticleService articleService = new ArticleService(articleDao, autorDao);

        // Act and Assert
        assertThrows(NullPointerException.class, () -> articleService.persist(null));
    }
}
