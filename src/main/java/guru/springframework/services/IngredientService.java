package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;

/**
 * Created by Parisana on 5/12/17
 */
public interface IngredientService {
    IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);

    IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand);
}
