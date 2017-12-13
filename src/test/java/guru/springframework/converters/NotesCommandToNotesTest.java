package guru.springframework.converters;

import guru.springframework.commands.NotesCommand;
import guru.springframework.domain.Notes;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class NotesCommandToNotesTest {

    public static final String ID_VALUE = "1";
    public static final String RECIPE_NOTES = "Notes";
    NotesCommandToNotes notesCommandToNotes;

    @Before
    public void setUp() throws Exception {
        notesCommandToNotes = new NotesCommandToNotes();

    }

    @Test
    public void testNullParameter() throws Exception {
        assertNull(notesCommandToNotes.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(notesCommandToNotes.convert(new NotesCommand()));
    }

    @Test
    public void convert() throws Exception {
        //given
        NotesCommand notesCommand = new NotesCommand();
        notesCommand.setId(ID_VALUE);
        notesCommand.setRecipeNotes(RECIPE_NOTES);

        //when
        Notes notes = notesCommandToNotes.convert(notesCommand);

        //then
        assertNotNull(notes);
        assertEquals(ID_VALUE, notes.getId());
        assertEquals(RECIPE_NOTES, notes.getRecipeNotes());
    }

}