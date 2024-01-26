package cvut.controllers;

import cvut.model.Review;
import cvut.model.Reviewer;
import cvut.services.ReviewService;
import cvut.services.ReviewerService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/reviewers")
@Validated
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ROLE_CRITIC', 'ROLE_ADMIN', 'ROLE_GUEST')")
public class ReviewerController {

    private final ReviewerService reviewerService;
    private final ReviewService reviewService;

    @GetMapping(value = "/{reviewerId}")
    public ResponseEntity<Reviewer> getReviewerById(@PathVariable @NonNull Integer reviewerId) {
        Reviewer reviewer = reviewerService.findById(reviewerId);
        return ResponseEntity.ok().body(reviewer);
    }

    @PutMapping(value = "/{reviewerId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateReviewer(@PathVariable @NonNull Integer reviewerId,
                                                 @RequestBody Reviewer reviewer) {
        reviewerService.update(reviewerId, reviewer.getUsername(), reviewer.getEmail());
        return ResponseEntity.ok("Reviewer successfully updated");
    }

    @DeleteMapping(value = "/{reviewerId}")
    public ResponseEntity<String> deleteReviewer(@PathVariable @NonNull Integer reviewerId) {
        reviewerService.deleteById(reviewerId);
        return ResponseEntity.ok("Reviewer successfully deleted");
    }

    @PostMapping(value = "/{reviewerId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createReviewer(@RequestBody Reviewer reviewer) {
        reviewerService.create(reviewer);
        return ResponseEntity.ok("Reviewer successfully created");
    }


    @PostMapping("/{reviewerId}/reviews")
    public ResponseEntity<String> createReview(@PathVariable @NonNull Integer reviewerId,
                                               @RequestBody Review review) {
        reviewerService.createReview(reviewerService.findById(reviewerId));
        return ResponseEntity.ok("Review successfully created");
    }

    @GetMapping("/reviews/{reviewId}")
    public ResponseEntity<Review> getReviewById(@PathVariable @NonNull Integer reviewId) {
        Review review = reviewService.findById(reviewId);
        return ResponseEntity.ok(review);
    }

    @PutMapping("/reviews/{reviewId}")
    public ResponseEntity<String> updateReview(@PathVariable @NonNull Integer reviewId,
                                               @RequestBody Review review) {
        reviewService.updateReview(reviewId, review);
        return ResponseEntity.ok("Review successfully updated");
    }

    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable @NonNull Integer reviewId) {
        reviewService.deleteById(reviewId);
        return ResponseEntity.ok("Review successfully deleted");
    }


}