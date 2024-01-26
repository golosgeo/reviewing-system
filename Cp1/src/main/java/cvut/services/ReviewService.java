package cvut.services;

import cvut.dao.ReviewDao;
import cvut.exception.NotFoundException;
import cvut.exception.ValidationException;
import cvut.model.Review;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class ReviewService {

    private final ReviewDao reviewDao;


    public ReviewService(ReviewDao reviewDao) {
        this.reviewDao = reviewDao;
    }

    @Transactional(readOnly = true)
    public Review find(Integer id) {
        return reviewDao.find(id);
    }

    public List<Review> findAll() {
        List<Review> all = reviewDao.findAll();
        if (all.isEmpty()) {
            throw new ValidationException("There are no Review");
        }
        return all;
    }

    public void deleteById(@NonNull int Id) {
        if (reviewDao.find(Id) == null ) {
            throw new NotFoundException("Review with id " + Id + " does not exist");
        }
        reviewDao.deleteById(Id);
    }

    public Review findById(@NonNull int id) {
        Review review = reviewDao.find(id);
        if (review == null){
            throw new NotFoundException("Review with id " + id + " does not exist");
        }
        return review;
    }

    @Transactional
    public void update(@NonNull int reviewId, String title, double rating, String text) {
        Review review = findById(reviewId);

        if (title != null && !review.getTitle().equals(title)){
            review.setTitle(title);
        }

        if (review.getRating() != rating){
            review.setRating(rating);
        }

        if (!text.isEmpty()){
            review.setText(text);
        }
    }

    public List<Review> findAllByRating(@NonNull double rating) {
        return reviewDao.findAllByRating(rating);
    }

    public void updateReview(Integer reviewId, Review review) {
        Review review_old = findById(reviewId);
        review_old.setTitle(review.getTitle());
        review_old.setText(review.getText());
        review_old.setRating(review.getRating());
    }
}
