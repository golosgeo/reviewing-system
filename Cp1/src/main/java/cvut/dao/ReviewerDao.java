package cvut.dao;

import cvut.model.AppUser;
import cvut.model.Reviewer;
import jakarta.persistence.NoResultException;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ReviewerDao extends BaseDao<Reviewer>{
    public ReviewerDao() {
        super(Reviewer.class);
    }

    public AppUser findByUsername(String username) {
        try {
            return em.createNamedQuery("reviewer.findByUsername", AppUser.class).setParameter("username", username)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    public AppUser findByEmail(String email) {
        try {
            return em.createNamedQuery("reviewer.findByEmail", AppUser.class).setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public Reviewer findById(int id) {
        return em.find(Reviewer.class, id);
    }

    public void deleteById(int criticId) {
        Reviewer reviewer = em.find(Reviewer.class, criticId);
        if (reviewer != null) {
            em.getTransaction().begin();
            em.remove(reviewer);
            em.getTransaction().commit();
        }
    }
}
