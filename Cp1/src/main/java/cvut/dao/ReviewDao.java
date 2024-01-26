package cvut.dao;

import cvut.model.Review;
import cvut.model.Reviewer;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public class ReviewDao extends BaseDao<Review>{
    public ReviewDao() {
        super(Review.class);
    }


    public void deleteById(Integer id){
        Review review = em.find(Review.class, id);
        if (review != null) {
            em.getTransaction().begin();
            em.remove(review);
            em.getTransaction().commit();
        }
    }

    public boolean equal(Integer id) {
        Objects.requireNonNull(id);
        return em.find(Review.class, id) != null;
    }

    public List<Review> findAllByRating(double rating) {
        return em.createQuery("SELECT r FROM Review r WHERE r.rating = :rating", Review.class)
                .setParameter("rating", rating)
                .getResultList();
    }

}
