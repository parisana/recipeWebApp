package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;

/**
 * Created by Parisana on 5/12/17
 */
public interface IngredientService {
    IngredientCommand findByRecipeIdAndIngredientId(String recipeId, String ingredientId);

    IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand);

    IngredientCommand deleteIngredientById(String recipeId, String ingredientId);
}
