package cvut.dao;

import cvut.model.Article;
import jakarta.persistence.NoResultException;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class ArticleDao extends BaseDao<Article>{
    public ArticleDao() {
        super(Article.class);
    }

    public Article findByTitle(String title) {
        try {
            return em.createNamedQuery("article.findByTitle", Article.class).setParameter("title", title)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Article> findByJournalId(Integer journalId) {
        try {
            return em.createQuery("SELECT a FROM Article a WHERE a.journal.id = :journalId", Article.class)
                    .setParameter("journalId", journalId)
                    .getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
}
