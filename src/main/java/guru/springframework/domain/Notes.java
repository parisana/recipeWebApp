package guru.springframework.domain;

import lombok.*;

import javax.persistence.*;

/**
 * Created by Parisana on 3/12/17
 */
@Data
@EqualsAndHashCode(exclude = {"recipe"})
@Entity
public class Notes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Recipe recipe;

    @Lob
    String recipeNotes;

}
