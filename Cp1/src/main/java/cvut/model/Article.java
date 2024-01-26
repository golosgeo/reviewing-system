package cvut.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "Article")
@NamedQueries({
        @NamedQuery(name = "article.findByTitle", query = "SELECT a FROM Article a WHERE a.title = :title")
})
@Getter
@Setter
@NoArgsConstructor
public class Article {
    @GeneratedValue(strategy = IDENTITY)
    @Id
    private Long id;

    @Enumerated(EnumType.STRING)
    private ArticleStatus articleStatus = ArticleStatus.IN_PROGRESS;

    @Column(name = "title", columnDefinition = "TEXT", nullable = false)
    private String title;

    @Column(name = "content", columnDefinition = "TEXT", nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "journal_id")
    private Journal journal;

    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Autor autor;

    @OneToMany(mappedBy = "article")
    @OrderBy("rating DESC")
    private Collection<Review> reviews;
}
