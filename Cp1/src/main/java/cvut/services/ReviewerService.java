package cvut.services;

import cvut.dao.ReviewerDao;
import cvut.exception.NotFoundException;
import cvut.exception.ValidationException;
import cvut.model.AppUser;
import cvut.model.Review;
import cvut.model.ReviewState;
import cvut.model.Reviewer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewerService {

    private final ReviewerDao reviewerDao;
    private final PasswordEncoder passwordEncoder;
    final Reviewer currentUser = new Reviewer();
    @Autowired
    public ReviewerService(ReviewerDao reviewerDao, PasswordEncoder passwordEncoder) {
        this.reviewerDao = reviewerDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public boolean exists(String username) {
        return reviewerDao.findByUsername(username) != null;
    }

    @Transactional
    public Reviewer findById(@NonNull int id) {
        return reviewerDao.findById(id);
    }

    @Transactional
    public void createReview(Reviewer reviewer) {
        final Review review = new Review();
        review.setCritiqueState(ReviewState.ACCEPTED);
        reviewer.addReview(review);
    }

    @Transactional
    public void create(Reviewer reviewer) {
           if (exists(reviewer.getUsername())) {
                throw new ValidationException("Username " + reviewer.getUsername() + " has been taken");
            }
        reviewerDao.persist(reviewer);  // Assuming you have a save method in your ReviewerDao
    }

    public Review getCurrentReview() {
        if (currentUser.getReviewList().isEmpty()){
            return null;
        }
        return currentUser.getReviewList().get(currentUser.getReviewList().size()-1);
    }

    public List<Reviewer> findAllByLastnameAndFirstnameLike(@NonNull String lastname, @NonNull String firstname) {
        List<Reviewer> allByLastnameAndFirstnameLike = findAllByLastnameAndFirstnameLike(lastname, firstname);
        if (allByLastnameAndFirstnameLike.isEmpty()) {
            throw new NotFoundException("Review were not found");
        }
        return allByLastnameAndFirstnameLike;
    }

    @Transactional
    public void update(@NonNull int criticId, String username, String email) {
        Reviewer reviewer = findById(criticId);

        if (username != null && !username.isEmpty() && !reviewer.getUsername().equals(username)) {
            AppUser reviewerUsername = reviewerDao.findByUsername(username);
            if (reviewerUsername == null) {
                throw new ValidationException("Username " + username + " has been taken");
            }
            reviewer.setUsername(username);
        }
        if (email != null && !email.isEmpty() && !reviewer.getEmail().equals(email)) {
            AppUser appUserByEmail = reviewerDao
                    .findByEmail(email);
            if (appUserByEmail.getEmail().isEmpty()) {
                throw new ValidationException("Email " + email + " has been taken");
            }
            reviewer.setEmail(email);
        }
    }
    @Transactional
    public void deleteById(@NonNull int criticId) {
        boolean exists = reviewerDao.exists(criticId);
        if (!exists) {
            throw new NotFoundException("Can not delete critic with id " + criticId + ". Critic does not exist");
        }
        reviewerDao.deleteById(criticId);
    }

    public void createReview(Integer reviewerId, Review review) {

    }
}
