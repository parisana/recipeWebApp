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
public class RecipeToRecipeCommand implements Converter<Recipe, RecipeCommand>{
    private final NotesToNotesCommand notesToNotesCommand;
    private final CategoryToCategoryCommand categoryToCategoryCommand;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;

    public RecipeToRecipeCommand(NotesToNotesCommand notesToNotesCommand, CategoryToCategoryCommand categoryToCategoryCommand, IngredientToIngredientCommand ingredientToIngredientCommand) {
        this.notesToNotesCommand = notesToNotesCommand;
        this.categoryToCategoryCommand = categoryToCategoryCommand;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
    }

    @Nullable
    @Synchronized
    @Override
    public RecipeCommand convert(Recipe source) {
        if (source == null) return null;

        final RecipeCommand recipeCommand= new RecipeCommand();
        recipeCommand.setId(source.getId());
        recipeCommand.setCookTime(source.getCookTime());
        recipeCommand.setDescription(source.getDescription());
        recipeCommand.setDifficulty(source.getDifficulty());
        recipeCommand.setDirections(source.getDirections());
        recipeCommand.setPrepTime(source.getPrepTime());
        recipeCommand.setServings(source.getServings());
        recipeCommand.setSource(source.getSource());
        recipeCommand.setUrl(source.getUrl());
        recipeCommand.setImage(source.getImage());

        recipeCommand.setNotes(notesToNotesCommand.convert(source.getNotes()));

        if (source.getCategories()!=null && source.getCategories().size()>0){
            source.getCategories().forEach(category -> recipeCommand.getCategories().add(categoryToCategoryCommand.convert(category)));
        }

        if (source.getIngredients()!=null && source.getIngredients().size()>0){
            source.getIngredients().forEach(ingredient -> recipeCommand.getIngredients().add(ingredientToIngredientCommand.convert(ingredient)));
        }

        return recipeCommand;
    }
}
