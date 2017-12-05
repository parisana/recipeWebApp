package guru.springframework.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Parisana on 3/12/17
 */
@Data
@EqualsAndHashCode(exclude = {"recipes"})
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    // mappedBy defines the field (in the Recipe) which owns the relationship
    @ManyToMany(mappedBy = "categories")
    private Set<Recipe> recipes;

}
