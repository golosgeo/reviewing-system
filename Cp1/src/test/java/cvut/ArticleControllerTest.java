package cvut;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import cvut.controllers.ArticleController;
import cvut.model.Article;
import cvut.services.ArticleService;
import cvut.services.AutorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class ArticleControllerTest {

    @Mock
    private ArticleService articleService;
    @Mock
    private AutorService autorService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        ArticleController articleController = new ArticleController(articleService, autorService);
        mockMvc = standaloneSetup(articleController).build();
    }

    @Test
    public void testCreateArticle() throws Exception {
        Article article = new Article();
        article.setId(1L);
        article.setTitle("Test Article");

        mockMvc.perform(post("/articles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(article)))
                .andExpect(status().isCreated());

        verify(articleService).persist(any(Article.class));
        verify(autorService).addArticle(any(), any());
    }

    @Test
    public void testGetArticleExisting() throws Exception {
        int articleId = 1;
        Article article = new Article();
        article.setId((long) articleId);
        article.setTitle("Existing Article");

        given(articleService.findById(articleId)).willReturn(article);

        mockMvc.perform(get("/articles/" + articleId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Existing Article"));
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
