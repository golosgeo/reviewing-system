package cvut;

import cvut.dao.ArticleDao;
import cvut.dao.AutorDao;
import cvut.model.Article;
import cvut.model.Autor;
import cvut.services.AutorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@Transactional
@AutoConfigureTestEntityManager
@TestPropertySource(locations = "classpath:application-test.properties")
@ActiveProfiles("test")
public class AutorServiceTest {
    @Mock
    private AutorDao autorDao;

    @Mock
    private PasswordEncoder passwordEncoder;

    private ArticleDao articleDao;
    private AutorService autorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        articleDao = new ArticleDao();
        autorService = new AutorService(autorDao, articleDao, passwordEncoder);

    }

    @Test
    void testAutorExists() {
        String username = "testUser";
        when(autorDao.findByUsername(username)).thenReturn(new Autor());

        boolean result = autorService.exists(username);

        assertTrue(result);
        verify(autorDao).findByUsername(username);
    }

    @Test
    void testAutorDoesNotExist() {
        String username = "testUser";
        when(autorDao.findByUsername(username)).thenReturn(null);

        boolean result = autorService.exists(username);

        assertFalse(result);
        verify(autorDao).findByUsername(username);
    }

    @Test
    public void test_create_article_for_author() {
        AutorDao autorDao = mock(AutorDao.class);
        ArticleDao articleDao = mock(ArticleDao.class);
        PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
        AutorService autorService = new AutorService(autorDao, articleDao, passwordEncoder);

        Autor autor = new Autor("John", "Doe", "johndoe", "password", "johndoe@example.com");
        String title = "Test Article";
        String content = "This is a test article.";

        autorService.createArticle(autor, title, content);

        verify(articleDao, times(1)).persist(any(Article.class));
    }

    @Test
    public void test_update_author() {
        AutorDao autorDao = mock(AutorDao.class);
        ArticleDao articleDao = mock(ArticleDao.class);
        PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
        AutorService autorService = new AutorService(autorDao, articleDao, passwordEncoder);

        Autor autor = new Autor("John", "Doe", "johndoe", "password", "johndoe@example.com");

        autorService.update(autor);

        verify(autorDao, times(1)).update(any(Autor.class));
    }

    @Test
    public void test_persist_null_author() {
        AutorDao autorDao = mock(AutorDao.class);
        ArticleDao articleDao = mock(ArticleDao.class);
        PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
        AutorService autorService = new AutorService(autorDao, articleDao, passwordEncoder);

        assertThrows(NullPointerException.class, () -> autorService.persist(null));
    }
}
