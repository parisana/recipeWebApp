package guru.springframework.serviceImpl;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.converters.IngredientCommandToIngredient;
import guru.springframework.converters.IngredientToIngredientCommand;
import guru.springframework.converters.UnitOfMeasureCommandToUnitOfMeasure;
import guru.springframework.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Parisana on 5/12/17
 */
public class IngredientServiceImplTest {

    IngredientServiceImpl ingredientService;

    IngredientToIngredientCommand ingredientToIngredientCommand;
    IngredientCommandToIngredient ingredientCommandToIngredient;

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    @Mock
    RecipeRepository recipeRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        ingredientToIngredientCommand= new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
        ingredientCommandToIngredient = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());

        ingredientService= new IngredientServiceImpl(ingredientToIngredientCommand, ingredientCommandToIngredient, unitOfMeasureRepository, recipeRepository);
    }

    @Test
    @Transactional
    public void findByRecipeIdAndIngredientId() {

        Recipe recipe= new Recipe();
        recipe.setId(1l);

        Ingredient ingredient1= new Ingredient();
        ingredient1.setId(1l);

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(2l);

        recipe.addIngredient(ingredient1);
        recipe.addIngredient(ingredient2);

        Optional<Recipe> recipeOptional= Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        IngredientCommand ingredientCommand= ingredientService.findByRecipeIdAndIngredientId(1l, 2l);

        //then
        assertEquals(Long.valueOf(1l), ingredientCommand.getRecipeId());
        assertEquals(Long.valueOf(2l), ingredientCommand.getId());

        verify(recipeRepository, times(1)).findById(any());
    }
}