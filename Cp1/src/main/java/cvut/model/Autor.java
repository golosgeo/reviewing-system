package cvut.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

@Entity
@Table(name = "autor")
@NamedQueries({
        @NamedQuery(name = "autor.findByUsername", query = "SELECT u FROM AppUser u WHERE u.username = :username")
})
@Getter
@Setter
@NoArgsConstructor
public class Autor extends AppUser{
    public Autor(String firstname, String lastname, String username, String password, String email) {
        super(firstname, lastname, username, password, email);
    }

    @OneToMany(mappedBy = "autor")
    private Collection<Article> articles;
}
