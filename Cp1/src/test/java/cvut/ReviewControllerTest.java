package cvut;

import cvut.controllers.ReviewController;
import cvut.model.Review;
import cvut.services.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ReviewControllerTest {

    @InjectMocks
    private ReviewController reviewController;

    @Mock
    private ReviewService reviewService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetReviewById() {
        // Arrange
        String reviewId = "1";
        Review expectedReview = new Review();
        when(reviewService.findById(Integer.parseInt(reviewId))).thenReturn(expectedReview);

        // Act
        ResponseEntity<Review> responseEntity = reviewController.getReviewById(reviewId);

        // Assert
        assertEquals(expectedReview, responseEntity.getBody());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testGetReviewByIdNotFound() {
        // Arrange
        String reviewId = "999";
        when(reviewService.findById(Integer.parseInt(reviewId))).thenReturn(null);

        // Act
        ResponseEntity<Review> responseEntity = reviewController.getReviewById(reviewId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void testFetchAll() {
        // Arrange
        List<Review> expectedReviews = new ArrayList<>();
        expectedReviews.add(new Review());
        expectedReviews.add(new Review());
        when(reviewService.findAll()).thenReturn(expectedReviews);

        // Act
        List<Review> reviews = reviewController.fetchAll();

        // Assert
        assertEquals(expectedReviews, reviews);
    }
}

