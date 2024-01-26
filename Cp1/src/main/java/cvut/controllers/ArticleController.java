package cvut.controllers;

import cvut.model.Article;
import cvut.services.ArticleService;
import cvut.services.AutorService;
import cvut.utils.RestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/articles")
public class ArticleController {

    private static final Logger LOG = LoggerFactory.getLogger(AuthorController.class);
    private final ArticleService articleService;
    private final AutorService autorService;

    @Autowired
    public ArticleController(ArticleService articleService, AutorService autorService) {
        this.articleService = articleService;
        this.autorService = autorService;
    }

    @PreAuthorize("hasRole('ROLE_AUTHOR')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createArticle(@RequestBody Article article) {
        articleService.persist(article);
        autorService.addArticle(article.getAutor(), article);
        LOG.debug("createArticle: {}", article);
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/current");
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER', 'ROLE_GUEST')")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Article> getArticle(@PathVariable Integer id) {
        Article article = articleService.findById(id);
        LOG.debug("getArticle: {}", article);
        return new ResponseEntity<>(article, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER', 'ROLE_GUEST')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Article>> getArticles() {
        List<Article> articles = articleService.findAll();
        LOG.debug("getArticles: {}", articles);
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_AUTHOR', 'ROLE_ADMIN')")
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteArticle(@PathVariable Integer id) {
        Article article = articleService.findById(id);
        articleService.remove(article);
        LOG.debug("deleteArticle: {}", article);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
