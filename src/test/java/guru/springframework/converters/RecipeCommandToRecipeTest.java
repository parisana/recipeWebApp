package guru.springframework.converters;

import guru.springframework.commands.CategoryCommand;
import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.NotesCommand;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Difficulty;
import guru.springframework.domain.Recipe;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Parisana on 4/12/17
 */
public class RecipeCommandToRecipeTest {

    public static Long ID=323l;
    public static String DESC="test desc";
    public static Integer PREP_TIME=438;
    public static Integer COOK_TIME= 3232;
    public static Integer SERVINGS= 2343;
    public static String SOURCE= "someSource";
    public static String URL="http://someurl.com";
    public static String DIRECTIONS= "Test Direction";
    public static Difficulty DIFFICULTY= Difficulty.HARD;
    public static Long CAT_ID1= 3l;
    public static Long CAT_ID2=4l;
    public static Long ING_ID1=3l;
    public static Long ING_ID2=4l;
    public static Long NOTE_ID= 3232l;

    RecipeCommandToRecipe recipeCommandToRecipe;

    @Before
    public void setUp() throws Exception {
        recipeCommandToRecipe= new RecipeCommandToRecipe(new CategoryCommandToCategory(), new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure()), new NotesCommandToNotes());
    }

    @Test
    public void convert() {

        //When
        RecipeCommand recipeCommand= new RecipeCommand();
        recipeCommand.setId(ID);
        recipeCommand.setDescription(DESC);
        recipeCommand.setPrepTime(PREP_TIME);
        recipeCommand.setCookTime(COOK_TIME);
        recipeCommand.setServings(SERVINGS);
        recipeCommand.setSource(SOURCE);
        recipeCommand.setUrl(URL);
        recipeCommand.setDirections(DIRECTIONS);
        recipeCommand.setDifficulty(DIFFICULTY);

        CategoryCommand categoryCommand1= new CategoryCommand();
        categoryCommand1.setId(CAT_ID1);

        CategoryCommand categoryCommand2= new CategoryCommand();
        categoryCommand2.setId(CAT_ID2);

        IngredientCommand ingredientCommand1= new IngredientCommand();
        ingredientCommand1.setId(ING_ID1);

        IngredientCommand ingredientCommand2= new IngredientCommand();
        ingredientCommand2.setId(ING_ID2);

        NotesCommand notesCommand= new NotesCommand();
        notesCommand.setId(NOTE_ID);

        recipeCommand.setNotes(notesCommand);
        recipeCommand.getIngredients().add(ingredientCommand1);
        recipeCommand.getIngredients().add(ingredientCommand2);
        recipeCommand.getCategories().add(categoryCommand1);
        recipeCommand.getCategories().add(categoryCommand2);

        //When
        Recipe recipe = recipeCommandToRecipe.convert(recipeCommand);

        //then
        assertEquals(ID, recipe.getId());
        assertEquals(COOK_TIME, recipe.getCookTime());
        assertEquals(PREP_TIME, recipe.getPrepTime());
        assertEquals(DESC, recipe.getDescription());
        assertEquals(DIRECTIONS, recipe.getDirections());
        assertEquals(DIFFICULTY, recipe.getDifficulty());
        assertEquals(SERVINGS, recipe.getServings());
        assertEquals(SOURCE, recipe.getSource());
        assertEquals(URL, recipe.getUrl());
        assertEquals(NOTE_ID, recipe.getNotes().getId());
        assertEquals(2, recipe.getCategories().size());
        assertEquals(2, recipe.getIngredients().size());
    }

    @Test
    public void testNullObject() throws Exception {
        assertNull(recipeCommandToRecipe.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(recipeCommandToRecipe.convert(new RecipeCommand()));
    }
}