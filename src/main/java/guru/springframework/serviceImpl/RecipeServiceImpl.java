package guru.springframework.serviceImpl;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.converters.RecipeCommandToRecipe;
import guru.springframework.converters.RecipeToRecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Created by Parisana on 3/12/17
 */
@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService{

    private final RecipeRepository recipeRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeCommandToRecipe recipeCommandToRecipe, RecipeToRecipeCommand recipeToRecipeCommand) {
        this.recipeRepository = recipeRepository;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }

    @Override
    public Set<Recipe> getRecipes() {
        log.debug("***Inside RecipeServiceImpl***");

        Set<Recipe> recipeSet= new HashSet<>();
        recipeRepository.findAll().iterator().forEachRemaining(recipeSet::add);
        return recipeSet;
    }

    @Override
    public Recipe findById(Long id){
        log.debug("***Inside findById***");

        Optional<Recipe> recipeOptional= recipeRepository.findById(id);

        if (!recipeOptional.isPresent()){
            throw new RuntimeException("Recipe Not Found!");
        }

        return recipeOptional.get();

    }

    @Override
    @Transactional
    public RecipeCommand saveRecipeCommand(RecipeCommand command) {
        log.debug("***Inside saveRecipeCommand***");
        Recipe detatchedRecipe= recipeCommandToRecipe.convert(command);

        Recipe savedRecipe = recipeRepository.save(detatchedRecipe);
        log.debug("Saved RecipeId: "+ savedRecipe.getId());

        return recipeToRecipeCommand.convert(savedRecipe);
    }

    @Transactional
    @Override
    public RecipeCommand findCommandById(Long id) {
        log.debug("***Inside findCommandById***");
        return recipeToRecipeCommand.convert(findById(id));
    }

    @Override
    public void deleteById(Long id) {
        log.debug("***Inside delete by id***");
        recipeRepository.deleteById(id);
    }
}
