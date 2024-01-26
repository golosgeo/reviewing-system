package cvut.services;

import cvut.dao.AppUserDao;
import cvut.dao.ArticleDao;
import cvut.dao.JournalDao;
import cvut.model.Journal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class JournalService {
    private final AppUserDao appUserDao;
    private final ArticleDao articleDao;
    private final JournalDao dao;

    @Autowired
    public JournalService(AppUserDao appUserDao, ArticleDao articleDao, JournalDao dao) {
        this.appUserDao = appUserDao;
        this.articleDao = articleDao;
        this.dao = dao;
    }

    @Transactional
    public Journal findByName(String name) {
        Objects.requireNonNull(name, "Name cannot be null");
        return dao.findByName(name);
    }

    @Transactional
    public List<Journal> findAll() {
        return dao.findAll();
    }

    @Transactional
    public void persist(Journal journal) {
        Objects.requireNonNull(journal);
        dao.persist(journal);
    }

    @Transactional
    public void update(Journal journal) {
        Objects.requireNonNull(journal);
        dao.update(journal);
    }

    @Transactional
    public void remove(Journal journal) {
        Objects.requireNonNull(journal);
        dao.remove(journal);
    }

    public Journal findById(Integer journalId) {
        return dao.find(journalId);
    }
}
