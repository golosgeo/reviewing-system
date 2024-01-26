package cvut;

import cvut.dao.ReviewDao;
import cvut.exception.NotFoundException;
import cvut.exception.ValidationException;
import cvut.model.Review;
import cvut.services.ReviewService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.lang.NonNull;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class ReviewServiceTest {

    @InjectMocks
    private ReviewService reviewService;

    @Mock
    private ReviewDao reviewDao;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void deleteById_ExistingReview_DeletesReview() {
        // Arrange
        int reviewId = 1;
        when(reviewDao.find(reviewId)).thenReturn(new Review());

        // Act
        reviewService.deleteById(reviewId);

        // Assert
        verify(reviewDao, times(1)).deleteById(eq(reviewId));
        verify(reviewDao, times(1)).find(eq(reviewId));
    }

    @Test(expected = NotFoundException.class)
    public void deleteById_NonExistingReview_ThrowsNotFoundException() {
        // Arrange
        int nonExistingReviewId = 999;
        when(reviewDao.find(eq(nonExistingReviewId))).thenReturn(null);

        // Act
        reviewService.deleteById(nonExistingReviewId);
    }

    @Test
    public void update_ValidReviewId_UpdatesReview() {
        // Arrange
        int reviewId = 1;
        Review existingReview = new Review();
        existingReview.setTitle("Old Title");
        existingReview.setRating(3.5);
        existingReview.setText("Old Text");

        when(reviewDao.find(eq(reviewId))).thenReturn(existingReview);

        String newTitle = "New Title";
        double newRating = 4.0;
        String newText = "New Text";

        // Act
        reviewService.update(reviewId, newTitle, newRating, newText);

        // Assert
        assertEquals(newTitle, existingReview.getTitle());
        assertEquals(newRating, existingReview.getRating(), 0.001);
        assertEquals(newText, existingReview.getText());
    }

    @Test(expected = NotFoundException.class)
    public void update_NonExistingReviewId_ThrowsNotFoundException() {
        // Arrange
        int nonExistingReviewId = 999;
        when(reviewDao.find(eq(nonExistingReviewId))).thenReturn(null);

        // Act
        reviewService.update(nonExistingReviewId, "New Title", 4.0, "New Text");
    }

    @Test
    public void findAll_ReviewsExist_ReturnsList() {
        // Arrange
        List<Review> mockReviews = Collections.singletonList(new Review());
        when(reviewDao.findAll()).thenReturn(mockReviews);

        // Act
        List<Review> result = reviewService.findAll();

        // Assert
        assertEquals(mockReviews, result);
    }
}

