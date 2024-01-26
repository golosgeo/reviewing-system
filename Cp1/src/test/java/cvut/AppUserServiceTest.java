package cvut;

import cvut.dao.AppUserDao;
import cvut.model.AppUser;
import cvut.services.AppUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AppUserServiceTest {

    @Mock
    private AppUserDao appUserDao;

    @Mock
    private PasswordEncoder passwordEncoder;

    private AppUserService appUserService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        appUserService = new AppUserService(appUserDao, passwordEncoder);
    }

    @Test
    void testExists() {
        // Arrange
        String username = "testUser";
        when(appUserDao.findByUsername(username)).thenReturn(new AppUser());

        // Act
        boolean result = appUserService.exists(username);

        // Assert
        assertTrue(result);
        verify(appUserDao).findByUsername(username);
    }

    @Test
    void testDoesNotExist() {
        // Arrange
        String username = "nonExistentUser";
        when(appUserDao.findByUsername(username)).thenReturn(null);

        // Act
        boolean result = appUserService.exists(username);

        // Assert
        assertFalse(result);
        verify(appUserDao).findByUsername(username);
    }
}

