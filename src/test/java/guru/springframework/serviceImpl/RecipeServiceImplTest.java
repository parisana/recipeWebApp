package guru.springframework.serviceImpl;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.converters.RecipeCommandToRecipe;
import guru.springframework.converters.RecipeToRecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.exceptions.NotFoundException;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

/**
 * Created by Parisana on 3/12/17
 */
public class RecipeServiceImplTest {

    private RecipeServiceImpl recipeService;

    @Mock
    private RecipeToRecipeCommand recipeToRecipeCommand;

    @Mock
    private RecipeCommandToRecipe recipeCommandToRecipe;

    @Mock
    private RecipeRepository recipeRepository;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);

        recipeService= new RecipeServiceImpl(recipeRepository, recipeCommandToRecipe, recipeToRecipeCommand);
    }

    @Test
    public void findById() throws Exception{
        Recipe recipe= new Recipe();
        recipe.setId("1");

        Optional<Recipe> recipeOptional= Optional.of(recipe);

        when(recipeRepository.findById(anyString())).thenReturn(recipeOptional);

        //Recipe recipeReturned= recipeService.findById(1l);
        assertNotNull("Null recipe returned", recipeService.findById("1"));

        verify(recipeRepository, times(1)).findById(anyString());
        verify(recipeRepository, never()).findAll();
    }

    @Test
    public void getRecipes() {

        Recipe recipe= new Recipe();
        HashSet recipesData = new HashSet();
        recipesData.add(recipe);

        when(recipeService.getRecipes()).thenReturn(recipesData);

        Set<Recipe> recipes = recipeService.getRecipes();

        assertEquals(recipes.size(), 1);
        //since recipeService calls recipeRepository.findAll(), we can verify recipeRepository.findAll() was called
        verify(recipeRepository, times(1)).findAll(); //verify that findAll() was called only once.
        verify(recipeRepository, never()).findById(anyString());

    }

//    @Transactional
    @Test
    public void getRecipeCommandByIdTest(){
        Recipe recipe= new Recipe();
        recipe.setId("12223");
        Optional<Recipe> optionalRecipe= Optional.of(recipe);

        RecipeCommand recipeCommand= new RecipeCommand();
        recipeCommand.setId("1223");

        when(recipeRepository.findById(anyString())).thenReturn(optionalRecipe);
        when(recipeService.findCommandById(anyString())).thenReturn(recipeCommand);

        assertNotNull("Null recipe returned", recipeService.findCommandById(anyString()));
        verify(recipeRepository, times(2)).findById(anyString());
        assertEquals(recipe.getId(), recipeService.findCommandById(anyString()).getId());

        verify(recipeRepository, times(3)).findById(anyString());
        verify(recipeRepository, never()).findAll();
    }

    @Test
    public void testDeleteById() throws Exception{
        //given
        String idToDelete= "32";

        //when
        recipeService.deleteById(idToDelete);

        //no when since method has void return type

        //then
        verify(recipeRepository, times(1)).deleteById(idToDelete);
    }

    @Test(expected = NotFoundException.class)
    public void findByIdNotFound(){

        Optional<Recipe> recipeOptional= Optional.empty();

        when(recipeRepository.findById(anyString())).thenReturn(recipeOptional);

        //then
        Recipe recipe= recipeService.findById(anyString());
    }
}