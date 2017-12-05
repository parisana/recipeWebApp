package guru.springframework.serviceImpl;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.converters.IngredientCommandToIngredient;
import guru.springframework.converters.IngredientToIngredientCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import guru.springframework.services.IngredientService;
import guru.springframework.services.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by Parisana on 5/12/17
 */
@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService{

    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final RecipeRepository recipeRepository;

    public IngredientServiceImpl(IngredientToIngredientCommand ingredientToIngredientCommand, IngredientCommandToIngredient ingredientCommandToIngredient, UnitOfMeasureRepository unitOfMeasureRepository, RecipeRepository recipeRepository) {
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.recipeRepository = recipeRepository;
    }

    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if (!recipeOptional.isPresent()){
            log.debug("***Recipe not found for Id: "+ recipeId);
            //todo impl error handling
        }

        Recipe recipe = recipeOptional.get();

        Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .map(ingredientToIngredientCommand::convert)
                .findFirst();

        if (!ingredientCommandOptional.isPresent()){
            log.debug("***Ingredient not found with Id: "+ ingredientId);
            //todo impl error handling
        }

        return ingredientCommandOptional.get();
    }

    @Override
    public IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(ingredientCommand.getRecipeId());

        if (recipeOptional.isPresent()){
            //If recipe id is not found then return empty object, don't save
            log.debug("***Recipe not found! Id : "+ ingredientCommand.getRecipeId()+" ****");

            return new IngredientCommand();
        }
        else {
            //add or update
            Recipe recipe = recipeOptional.get();
            Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
                    .findFirst();

            if (ingredientOptional.isPresent()){
                //update the existing
                Ingredient ingredientFound = ingredientOptional.get();

                ingredientFound.setAmount(ingredientCommand.getAmount());
                ingredientFound.setDescription(ingredientCommand.getDescription());
                ingredientFound.setUom(unitOfMeasureRepository.findById(ingredientCommand.getUom().getId())
                .orElseThrow(() -> new RuntimeException("**UOM NOT FOUND**"))); //todo address this
            }else {
                //add new ingredient
                recipe.addIngredient(ingredientCommandToIngredient.convert(ingredientCommand));
            }

            //now save the recipe
            Recipe savedRecipe= recipeRepository.save(recipe);
            //todo check for fail

            return savedRecipe.getIngredients().stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
                    .map(ingredient -> ingredientToIngredientCommand.convert(ingredient)).findFirst().get();
        }
    }
}
