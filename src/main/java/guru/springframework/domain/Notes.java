package guru.springframework.domain;

import lombok.*;
import org.springframework.data.annotation.Id;

/**
 * Created by Parisana on 3/12/17
 */
@Getter
@Setter
public class Notes {

    @Id
    private String id;

    private Recipe recipe;

    String recipeNotes;

}
