package cvut.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "journal")
@NamedQueries({
        @NamedQuery(name = "journal.findByName", query = "SELECT j FROM Journal j WHERE j.name = :name")
})
@Getter
@Setter
@NoArgsConstructor
public class Journal {
    @GeneratedValue(strategy = IDENTITY)
    @Id
    private Long id;

    @Column(name = "name", columnDefinition = "TEXT", nullable = false)
    private String name;

    @OneToMany(mappedBy = "journal")
    private Collection<Article> articles;
}
