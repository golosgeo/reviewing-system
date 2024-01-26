package cvut.controllers;

import cvut.model.Article;
import cvut.services.ArticleService;
import cvut.services.PdfService;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDDestination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/pdf")
public class PdfController {
    private static final Logger LOG = LoggerFactory.getLogger(AuthorController.class);
    private final ArticleService articleService;
    private final PdfService pdfService;

    @Autowired
    public PdfController(ArticleService articleService, PdfService pdfService) {
        this.articleService = articleService;
        this.pdfService = pdfService;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER', 'ROLE_GUEST')")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PDDocument> getArticle(@PathVariable Integer id) {
        Article article = articleService.findById(id);
        PDDocument document = pdfService.generatePdf(article.getContent(), article.getTitle());
        LOG.debug("getArticle: {}", article);
        return new ResponseEntity<>(document, HttpStatus.OK);
    }
}
