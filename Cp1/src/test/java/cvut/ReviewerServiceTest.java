package cvut;

import cvut.dao.ReviewerDao;
import cvut.model.Review;
import cvut.model.ReviewState;
import cvut.model.Reviewer;
import cvut.services.ReviewerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReviewerServiceTest {

    @Mock
    private ReviewerDao reviewerDao;

    @Mock
    private PasswordEncoder passwordEncoder;

    private ReviewerService reviewerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        reviewerService = new ReviewerService(reviewerDao, passwordEncoder);
    }

    @Test
    void testExists() {
        // Arrange
        String username = "testUser";
        when(reviewerDao.findByUsername(username)).thenReturn(new Reviewer());

        // Act
        boolean result = reviewerService.exists(username);

        // Assert
        assertTrue(result);
        verify(reviewerDao).findByUsername(username);
    }

    @Test
    void testDoesNotExist() {
        // Arrange
        String username = "nonExistentUser";
        when(reviewerDao.findByUsername(username)).thenReturn(null);

        // Act
        boolean result = reviewerService.exists(username);

        // Assert
        assertFalse(result);
        verify(reviewerDao).findByUsername(username);
    }

    @Test
    void testCreateReview() {
        // Arrange
        Reviewer reviewer = new Reviewer();

        // Act
        reviewerService.createReview(reviewer);

        // Assert
        assertFalse(reviewer.getReviewList().isEmpty());
        Review review = reviewer.getReviewList().get(0);
        assertEquals(ReviewState.ACCEPTED, review.getCritiqueState());
    }

    @Test
    void testGetCurrentReviewNoReview() {
        // Arrange

        // Act
        Review currentReview = reviewerService.getCurrentReview();

        // Assert
        assertNull(currentReview);
    }
}
