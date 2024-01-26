package cvut.controllers;


import cvut.model.Autor;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@Transactional
@AutoConfigureTestEntityManager
@TestPropertySource(locations = "classpath:application-test.properties")
@ActiveProfiles("test")
public class AuthorControllerTest {

    @Autowired
    private TestEntityManager em;

    @Test
    public void test_register_author_with_valid_data() {
        // Arrange
        AutorService autorService = mock(AutorService.class);
        AuthorController authorController = new AuthorController(autorService);
        Autor user = new Autor("John", "Doe", "johndoe", "password", "johndoe@example.com");

        // Act
        ResponseEntity<Void> response = authorController.registerAuthor(user);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void test_get_author_by_non_existing_username() {
        // Arrange
        AutorService autorService = mock(AutorService.class);
        AuthorController authorController = new AuthorController(autorService);
        String username = "nonexistinguser";
        when(autorService.findByUsername(username)).thenReturn(null);

        // Act
        ResponseEntity<Autor> response = authorController.getAuthor(username);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
