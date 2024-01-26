package cvut.services;

import cvut.dao.ArticleDao;
import cvut.dao.AutorDao;
import cvut.model.Article;
import cvut.model.Autor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class AutorService {
    private final AutorDao dao;
    private final ArticleDao articleDao;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AutorService(AutorDao autorDao, ArticleDao articleDao, PasswordEncoder passwordEncoder) {
        this.dao = autorDao;
        this.articleDao = articleDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public boolean exists(String username) {
        return dao.findByUsername(username) != null;
    }

    @Transactional
    public Autor findByUsername(String username) {
        Objects.requireNonNull(username, "Username cannot be null");
        return (Autor) dao.findByUsername(username);
    }

    @Transactional
    public Autor findByID(Integer id) {
        Objects.requireNonNull(id, "ID cannot be null");
        return (Autor) dao.find(id);
    }

    @Transactional
    public List<Autor> findAll() {
        return dao.findAll();
    }

    @Transactional
    public void createArticle(Autor autor, String title, String content) {
        Objects.requireNonNull(autor);
        Objects.requireNonNull(title);
        Objects.requireNonNull(content);
        final Article article = new Article();
        article.setTitle(title);
        article.setContent(content);
        article.setAutor(autor);
        articleDao.persist(article);
    }

    @Transactional
    public void persist(Autor autor) {
        Objects.requireNonNull(autor);
        dao.persist(autor);
    }

    @Transactional
    public void update(Autor autor) {
        Objects.requireNonNull(autor);
        dao.update(autor);
    }

    @Transactional
    public void remove(Autor autor) {
        Objects.requireNonNull(autor);
        dao.remove(autor);
    }

    @Transactional
    public void addArticle(Autor autor, Article article) {
        Objects.requireNonNull(autor);
        Objects.requireNonNull(article);
        dao.addArticle(autor, article);
    }
}
