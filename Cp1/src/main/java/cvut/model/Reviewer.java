package cvut.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "reviewer")
@NamedQueries({
        @NamedQuery(name = "reviewer.findByUsername", query = "SELECT u FROM AppUser u WHERE u.username = :username"),
        @NamedQuery(name = "reviewer.findByEmail", query = "SELECT u FROM AppUser u WHERE u.email = :email")
})

@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class Reviewer extends AppUser{


    @Column(name = "review_rating", nullable = false)
    private double review_rating;

    public Reviewer(String firstname, String lastname, String username, String password, String email) {
        super(firstname, lastname, username, password, email);
    }
    @OneToMany(mappedBy = "reviewOwner", fetch = FetchType.EAGER)
    private List<Review> reviewList = new ArrayList<>();

    public void addReview(Review review){
        reviewList.add(review);
    }
}
