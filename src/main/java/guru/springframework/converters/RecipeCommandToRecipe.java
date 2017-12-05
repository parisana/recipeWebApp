package guru.springframework.converters;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * Created by Parisana on 4/12/17
 */
@Component
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe> {
    private final CategoryCommandToCategory categoryCommandToCategory;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final NotesCommandToNotes notesCommandToNotes;

    public RecipeCommandToRecipe(CategoryCommandToCategory categoryCommandToCategory, IngredientCommandToIngredient ingredientCommandToIngredient, NotesCommandToNotes notesCommandToNotes) {
        this.categoryCommandToCategory = categoryCommandToCategory;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.notesCommandToNotes = notesCommandToNotes;
    }

    @Nullable
    @Synchronized
    @Override
    public Recipe convert(RecipeCommand source) {
        if (source == null ) return null;

        final Recipe recipe= new Recipe();
        recipe.setId(source.getId());
        recipe.setSource(source.getSource());
        recipe.setServings(source.getServings());
        recipe.setUrl(source.getUrl());
        recipe.setDirections(source.getDirections());
        recipe.setDifficulty(source.getDifficulty());
        recipe.setPrepTime(source.getPrepTime());
        recipe.setCookTime(source.getCookTime());
        recipe.setDescription(source.getDescription());
        if (source.getNotes()!=null)
            recipe.setNotes(notesCommandToNotes.convert(source.getNotes()));
        if (source.getCategories()!=null && source.getCategories().size()>0){
            source.getCategories().forEach(categoryCommand -> recipe.getCategories().add(categoryCommandToCategory.convert(categoryCommand)));
        }

        if (source.getIngredients()!=null && source.getIngredients().size()>0){
            source.getIngredients().forEach(ingredientCommand -> recipe.getIngredients().add(ingredientCommandToIngredient.convert(ingredientCommand)));
        }

        return recipe;
    }
}
