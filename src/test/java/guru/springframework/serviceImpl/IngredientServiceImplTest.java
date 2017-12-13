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

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
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
//    @Transactional
    public void findByRecipeIdAndIngredientId() {

        Recipe recipe= new Recipe();
        recipe.setId("1");

        Ingredient ingredient1= new Ingredient();
        ingredient1.setId("1");

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId("2");

        recipe.addIngredient(ingredient1);
        recipe.addIngredient(ingredient2);

        Optional<Recipe> recipeOptional= Optional.of(recipe);

        when(recipeRepository.findById(anyString())).thenReturn(recipeOptional);

        IngredientCommand ingredientCommand= ingredientService.findByRecipeIdAndIngredientId("1", "2");

        //then
        assertEquals(Long.valueOf(1l), ingredientCommand.getRecipeId());
        assertEquals(Long.valueOf(2l), ingredientCommand.getId());

        verify(recipeRepository, times(1)).findById(any());
    }

    @Test
    public void saveIngredientCommand(){
        String ingredientId= "2";
        String recipeId="2";

        //given
        IngredientCommand ingredientCommand= new IngredientCommand();
        ingredientCommand.setId(ingredientId);
        ingredientCommand.setRecipeId(recipeId);

        Optional<Recipe> recipeOptional= Optional.of(new Recipe());

        Recipe recipe= new Recipe();
        recipe.setId(recipeId);
        recipe.addIngredient(new Ingredient());
        recipe.getIngredients().iterator().next().setId(ingredientId);

        when(recipeRepository.findById(anyString())).thenReturn(recipeOptional);
        when(recipeRepository.save(any())).thenReturn(recipe);

        //when
        IngredientCommand savedIngredientCommand = ingredientService.saveIngredientCommand(ingredientCommand);

        //then
        assertEquals(recipeId, savedIngredientCommand.getId());
        verify(recipeRepository, times(1)).findById(anyString());
        verify(recipeRepository, times(1)).save(any());
    }

    @Test
    public void deleteIngredientCommand() {

        String recipeId="1";
        String ingredientId1="1";
        String ingredientId2="1";

        Recipe recipe= new Recipe();
        recipe.setId(recipeId);

        Ingredient ingredient1= new Ingredient();
        ingredient1.setId(ingredientId1);
        Ingredient ingredient2= new Ingredient();
        ingredient2.setId(ingredientId2);

        recipe.addIngredient(ingredient1);
        recipe.addIngredient(ingredient2);

        Optional<Recipe> recipeOptional= Optional.of(recipe);

        when(recipeRepository.findById(anyString())).thenReturn(recipeOptional);

        //when
        ingredientService.deleteIngredientById(recipeId, ingredientId1);

        //then
        assertEquals(1, recipe.getIngredients().size());
        assert(recipe.getIngredients().contains(ingredient2));

        verify(recipeRepository, times(1)).findById(anyString());
        verify(recipeRepository, times(1)).save(any());

    }
}