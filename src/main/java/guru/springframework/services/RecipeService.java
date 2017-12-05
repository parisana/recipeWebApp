package guru.springframework.services;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Recipe;

import java.util.Set;

/**
 * Created by Parisana on 3/12/17
 */
public interface RecipeService {
    Set<Recipe> getRecipes();

    Recipe findById(Long id);

    RecipeCommand saveRecipeCommand (RecipeCommand command);

    RecipeCommand findCommandById(Long id);

    void deleteById(Long id);
}
