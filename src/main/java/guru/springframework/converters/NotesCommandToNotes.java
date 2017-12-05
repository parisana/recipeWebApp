package guru.springframework.converters;

import guru.springframework.commands.NotesCommand;
import guru.springframework.domain.Notes;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * Created by Parisana on 4/12/17
 */
@Component
public class NotesCommandToNotes implements Converter<NotesCommand, Notes>{
    @Nullable
    @Synchronized
    @Override
    public Notes convert(NotesCommand source) {
        if (source==null) return null;

        final Notes notes= new Notes();
        notes.setId(source.getId());
        notes.setRecipeNotes(source.getRecipeNotes());

        return notes;
    }
}
