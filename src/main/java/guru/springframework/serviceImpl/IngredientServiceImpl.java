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
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

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

    @Transactional
    @Override
    public IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand) {
        log.debug("*** Saving/Updating ingredient ***");
        Optional<Recipe> recipeOptional = recipeRepository.findById(ingredientCommand.getRecipeId());

        if (!recipeOptional.isPresent()){
            //todo error handling
            //If recipe id is not found then return empty object, don't save
            log.debug("***Recipe not found! Id : "+ ingredientCommand.getRecipeId()+" ****");

            return new IngredientCommand();
        }
        else {
            //add or update? determine!
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
                .orElseThrow(() -> new RuntimeException("**UOM NOT FOUND**"))); //todo replace this with a more robust implementation
            }else {
                //add new ingredient
                recipe.addIngredient(ingredientCommandToIngredient.convert(ingredientCommand));
            }

            //now save the recipe
            Recipe savedRecipe= recipeRepository.save(recipe);

            //for update ingredientCommand will have an id but not for a new one so do a fail safe check for the case
            Optional<Ingredient> savedIngredientOptional= savedRecipe.getIngredients().stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
                    .findFirst();
            if (!savedIngredientOptional.isPresent()){
                //Fail safe: if not found then search by description and other properties
                savedIngredientOptional= savedRecipe.getIngredients().stream()
                        .filter(ingredient -> ingredient.getDescription().equals(ingredientCommand.getDescription()))
                        .filter(ingredient -> ingredient.getAmount().equals(ingredientCommand.getAmount()))
                        .filter(ingredient -> ingredient.getUom().getId().equals(ingredientCommand.getUom().getId()))
                        .findFirst();
            }
            if (savedIngredientOptional.isPresent())
                return ingredientToIngredientCommand.convert(savedIngredientOptional.get());

            return new IngredientCommand();

//            return savedRecipe.getIngredients().stream()
//                    .filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
//                    .map(ingredientToIngredientCommand::convert).findFirst().get();
        }
    }

    @Override
    public IngredientCommand deleteIngredientById(Long recipeId, Long ingredientId) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if (!recipeOptional.isPresent()){
            //todo handle exceptions
            log.debug("***Recipe not found for the deleting id: "+recipeId+" ***");

            return new IngredientCommand();
        }

        Recipe recipe= recipeOptional.get();

        Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .findFirst();

        if (!ingredientOptional.isPresent()){
            //todo handle exceptions
            log.debug("*** Ingredient might not be present ingredient id: "+ ingredientId);
            return new IngredientCommand();
        }
        Ingredient ingredientToDelete= ingredientOptional.get();
        ingredientToDelete.setRecipe(null);

        recipe.getIngredients().remove(ingredientToDelete);
        recipeRepository.save(recipe);

        return ingredientToIngredientCommand.convert(ingredientToDelete);

    }
}
