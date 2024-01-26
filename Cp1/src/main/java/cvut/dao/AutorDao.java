package cvut.dao;

import cvut.model.AppUser;
import cvut.model.Article;
import cvut.model.Autor;
import jakarta.persistence.NoResultException;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository
public class AutorDao extends BaseDao<Autor>{

    public AutorDao() {
        super(Autor.class);
    }

    public AppUser findByUsername(String username) {
        try {
            return em.createNamedQuery("autor.findByUsername", AppUser.class).setParameter("username", username)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public void addArticle(Autor autor, Article article) {
        Objects.requireNonNull(autor);
        Objects.requireNonNull(article);
        autor.getArticles().add(article);
        article.setAutor(autor);
    }

    public void removeArticle(Autor autor, Article article) {
        Objects.requireNonNull(autor);
        Objects.requireNonNull(article);
        autor.getArticles().remove(article);
        article.setAutor(null);
    }

}
