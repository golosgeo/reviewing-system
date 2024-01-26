package cvut.controllers;

import cvut.model.AppUser;
import cvut.model.Article;
import cvut.model.Autor;
import cvut.security.model.UserDetails;
import cvut.services.AutorService;
import cvut.utils.RestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private static final Logger LOG = LoggerFactory.getLogger(AuthorController.class);
    private final AutorService autorService;

    @Autowired
    public AuthorController(AutorService autorService) {
        this.autorService = autorService;
    }

    @PreAuthorize("(!#user.isAdmin() && anonymous) || hasRole('ROLE_ADMIN')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> registerAuthor(@RequestBody Autor user) {
        autorService.persist(user);
        LOG.debug("registerAuthor: {}", user);
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/current");
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER', 'ROLE_GUEST')")
    @GetMapping(value = "/current", produces = MediaType.APPLICATION_JSON_VALUE)
    public AppUser getCurrentAuthor(Authentication authentication) {
        assert authentication.getPrincipal() instanceof UserDetails;
        return ((UserDetails) authentication.getPrincipal()).getUser();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER', 'ROLE_GUEST')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Autor>> getAuthors() {
        List<Autor> authors = autorService.findAll();
        LOG.debug("getAuthors: {}", authors);
        return new ResponseEntity<>(authors, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER', 'ROLE_GUEST')")
    @GetMapping(value = "/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Autor> getAuthor(@PathVariable String username) {
        Autor autor = autorService.findByUsername(username);
        LOG.debug("getAuthor: {}", autor);
        if (autor == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(autor, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER', 'ROLE_GUEST')")
    @GetMapping(value = "/{username}/articles", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Article>> getAuthorArticles(@PathVariable String username) {
        Autor autor = autorService.findByUsername(username);
        LOG.debug("getAuthorArticles: {}", autor);
        if (autor == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<Article> articles = new ArrayList<>(autor.getArticles());
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteAuthor(@PathVariable String username) {
        Autor autor = autorService.findByUsername(username);
        autorService.remove(autor);
        LOG.debug("deleteAuthor: {}", autor);
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/current");
        return new ResponseEntity<>(headers, HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PutMapping(value = "/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateAuthor(@PathVariable String username, @RequestBody Autor autor) {
        Autor autorToUpdate = autorService.findByUsername(username);
        autorToUpdate.setFirstname(autor.getFirstname());
        autorToUpdate.setLastname(autor.getLastname());
        autorToUpdate.setPassword(autor.getPassword());
        autorToUpdate.setEmail(autor.getEmail());
        // username should be updated only by admin
        autorService.update(autorToUpdate);
        LOG.debug("updateAuthor: {}", autorToUpdate);
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/current");
        return new ResponseEntity<>(headers, HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateAuthorUsername(@PathVariable Integer id, @RequestBody Autor autor) {
        if (autorService.exists(autor.getUsername())) {
            LOG.debug("ERROR: updateAuthorUsername: {}", autor);
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Autor autorToUpdate = autorService.findByID(id);
        autorToUpdate.setUsername(autor.getUsername());
        autorService.update(autorToUpdate);

        LOG.debug("updateAuthorUsername: {}", autorToUpdate);
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/current");
        return new ResponseEntity<>(headers, HttpStatus.ACCEPTED);
    }
}
