package guru.springframework.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Parisana on 4/12/17
 */
@Getter
@Setter
@NoArgsConstructor
public class NotesCommand {
    private String id;
    private String recipeNotes;
}
