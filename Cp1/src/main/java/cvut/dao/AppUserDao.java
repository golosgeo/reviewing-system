package cvut.dao;

import cvut.model.AppUser;
import jakarta.persistence.NoResultException;
import org.springframework.stereotype.Repository;

@Repository
public class AppUserDao extends BaseDao<AppUser>{
    public AppUserDao() {
        super(AppUser.class);
    }

    public AppUserDao(Class<AppUser> type) {
        super(type);
    }

    public AppUser findByUsername(String username) {
        try {
            return em.createNamedQuery("app_user.findByUsername", AppUser.class).setParameter("username", username)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
