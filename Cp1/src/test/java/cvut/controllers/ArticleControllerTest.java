package cvut.controllers;

import cvut.model.Article;
import cvut.model.Autor;
import cvut.services.ArticleService;
import cvut.services.AutorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
@AutoConfigureTestEntityManager
@TestPropertySource(locations = "classpath:application-test.properties")
@ActiveProfiles("test")
public class ArticleControllerTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private AutorService autorService;

    @Autowired
    private ArticleService articleService;

    @Test
    public void test_getArticle_existingId() {
        // Arrange
        ArticleController articleController = new ArticleController(articleService, autorService);
        Article article = new Article();
        article.setTitle("Test Article");
        article.setContent("This is a test article");
        articleService.persist(article);

        // Act
        ResponseEntity<Article> response = articleController.getArticle(article.getId().intValue());

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(article.getId(), response.getBody().getId());
    }

    @Test
    public void test_getArticles() {
        // Arrange
        ArticleController articleController = new ArticleController(articleService, autorService);
        Article article1 = new Article();
        article1.setTitle("Test Article 1");
        article1.setContent("This is test article 1");
        articleService.persist(article1);
        Article article2 = new Article();
        article2.setTitle("Test Article 2");
        article2.setContent("This is test article 2");
        articleService.persist(article2);

        // Act
        ResponseEntity<List<Article>> response = articleController.getArticles();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
    }
}
