package cvut.services;

import cvut.dao.ArticleDao;
import cvut.dao.AutorDao;
import cvut.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class ArticleService {
    private final ArticleDao dao;
    private final AutorDao autorDao;

    @Autowired
    public ArticleService(ArticleDao dao, AutorDao autorDao) {
        this.dao = dao;
        this.autorDao = autorDao;
    }

    @Transactional
    public Article findByTitle(String title) {
        Objects.requireNonNull(title, "Title cannot be null");
        return dao.findByTitle(title);
    }

    @Transactional
    public Article findById(Integer id) {
        Objects.requireNonNull(id, "Id cannot be null");
        return dao.find(id);
    }

    @Transactional
    public List<Article> findAll() {
        return dao.findAll();
    }

    @Transactional
    public void persist(Article article) {
        Objects.requireNonNull(article);
        dao.persist(article);
    }

    @Transactional
    public void update(Article article) {
        Objects.requireNonNull(article);
        dao.update(article);
    }

    @Transactional
    public void remove(Article article) {
        Objects.requireNonNull(article);
        autorDao.removeArticle(article.getAutor(), article);
        dao.remove(article);
    }

    @Transactional
    public List<Article> findAllByJournalId(Integer journalId) {
        Objects.requireNonNull(journalId, "Journal ID cannot be null");
        return dao.findByJournalId(journalId);
    }
}
