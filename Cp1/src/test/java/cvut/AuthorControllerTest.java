package cvut;

import cvut.controllers.AuthorController;
import cvut.model.Article;
import cvut.model.Autor;
import cvut.services.AutorService;
import cvut.utils.RestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AuthorControllerTest {

    @InjectMocks
    private AuthorController authorController;

    @Mock
    private AutorService autorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAuthors() {
        // Arrange
        List<Autor> expectedAuthors = new ArrayList<>();
        expectedAuthors.add(new Autor());
        expectedAuthors.add(new Autor());
        when(autorService.findAll()).thenReturn(expectedAuthors);

        // Act
        ResponseEntity<List<Autor>> responseEntity = authorController.getAuthors();

        // Assert
        assertEquals(expectedAuthors, responseEntity.getBody());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testGetAuthor() {
        // Arrange
        String username = "testUser";
        Autor expectedAutor = new Autor();
        when(autorService.findByUsername(username)).thenReturn(expectedAutor);

        // Act
        ResponseEntity<Autor> responseEntity = authorController.getAuthor(username);

        // Assert
        assertEquals(expectedAutor, responseEntity.getBody());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testGetAuthorNotFound() {
        // Arrange
        String username = "nonExistentUser";
        when(autorService.findByUsername(username)).thenReturn(null);

        // Act
        ResponseEntity<Autor> responseEntity = authorController.getAuthor(username);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void testGetAuthorArticles() {
        // Arrange
        String username = "testUser";
        Autor expectedAutor = new Autor();
        expectedAutor.setArticles(new ArrayList<>());
        when(autorService.findByUsername(username)).thenReturn(expectedAutor);

        // Act
        ResponseEntity<List<Article>> responseEntity = authorController.getAuthorArticles(username);

        // Assert
        assertEquals(expectedAutor.getArticles(), responseEntity.getBody());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testGetAuthorArticlesNotFound() {
        // Arrange
        String username = "nonExistentUser";
        when(autorService.findByUsername(username)).thenReturn(null);

        // Act
        ResponseEntity<List<Article>> responseEntity = authorController.getAuthorArticles(username);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
}

