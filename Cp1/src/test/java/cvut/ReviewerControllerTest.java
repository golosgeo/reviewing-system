package cvut;

import cvut.controllers.ReviewerController;
import cvut.model.Review;
import cvut.model.Reviewer;
import cvut.services.ReviewService;
import cvut.services.ReviewerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ReviewerControllerTest {

    @InjectMocks
    private ReviewerController reviewerController;

    @Mock
    private ReviewerService reviewerService;

    @Mock
    private ReviewService reviewService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetReviewerById() {
        // Arrange
        Integer reviewerId = 1;
        Reviewer expectedReviewer = new Reviewer();
        when(reviewerService.findById(reviewerId)).thenReturn(expectedReviewer);

        // Act
        ResponseEntity<Reviewer> responseEntity = reviewerController.getReviewerById(reviewerId);

        // Assert
        assertEquals(expectedReviewer, responseEntity.getBody());
        assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    void testUpdateReviewer() {
        // Arrange
        Integer reviewerId = 1;
        Reviewer updatedReviewer = new Reviewer();
        updatedReviewer.setUsername("newUsername");
        updatedReviewer.setEmail("newEmail");

        // Act
        ResponseEntity<String> responseEntity = reviewerController.updateReviewer(reviewerId, updatedReviewer);

        // Assert
        assertEquals("Reviewer successfully updated", responseEntity.getBody());
        assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    void testDeleteReviewer() {
        // Arrange
        Integer reviewerId = 1;

        // Act
        ResponseEntity<String> responseEntity = reviewerController.deleteReviewer(reviewerId);

        // Assert
        assertEquals("Reviewer successfully deleted", responseEntity.getBody());
        assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    void testCreateReview() {
        // Arrange
        Integer reviewerId = 1;
        Review review = new Review();

        // Act
        ResponseEntity<String> responseEntity = reviewerController.createReview(reviewerId, review);

        // Assert
        assertEquals("Review successfully created", responseEntity.getBody());
        assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    void testGetReviewById() {
        // Arrange
        Integer reviewId = 1;
        Review expectedReview = new Review();
        when(reviewService.findById(reviewId)).thenReturn(expectedReview);

        // Act
        ResponseEntity<Review> responseEntity = reviewerController.getReviewById(reviewId);

        // Assert
        assertEquals(expectedReview, responseEntity.getBody());
        assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    void testUpdateReview() {
        // Arrange
        Integer reviewId = 1;
        Review updatedReview = new Review();

        // Act
        ResponseEntity<String> responseEntity = reviewerController.updateReview(reviewId, updatedReview);

        // Assert
        assertEquals("Review successfully updated", responseEntity.getBody());
        assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    void testDeleteReview() {
        // Arrange
        Integer reviewId = 1;

        // Act
        ResponseEntity<String> responseEntity = reviewerController.deleteReview(reviewId);

        // Assert
        assertEquals("Review successfully deleted", responseEntity.getBody());
        assertEquals(200, responseEntity.getStatusCodeValue());
    }
}

