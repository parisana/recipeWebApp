package guru.springframework.controllers;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.repositories.UnitOfMeasureRepository;
import guru.springframework.serviceImpl.UnitOfMeasureServiceImpl;
import guru.springframework.services.IngredientService;
import guru.springframework.services.RecipeService;
import guru.springframework.services.UnitOfMeasureService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * Created by Parisana on 5/12/17
 */
public class IngredientControllerTest {

    IngredientController ingredientController;

    UnitOfMeasureService unitOfMeasureService;
    UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand= new UnitOfMeasureToUnitOfMeasureCommand();

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    private MockMvc mockMvc;

    @Mock
    private RecipeService recipeService;

    @Mock
    private IngredientService ingredientService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        unitOfMeasureService= new UnitOfMeasureServiceImpl(unitOfMeasureRepository, unitOfMeasureToUnitOfMeasureCommand);
        ingredientController= new IngredientController(unitOfMeasureService, recipeService, ingredientService);

        mockMvc= MockMvcBuilders.standaloneSetup(ingredientController).build();
    }

    @Test
    public void showIngredientListTest() throws Exception{
        //given
        RecipeCommand recipeCommand= new RecipeCommand();
        when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);

        //when
        mockMvc.perform(get("/recipe/1/ingredients"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/list"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    public void showIngredientTest() throws Exception{
        //given
        Long recipeId= 21l;
        Long ingredientId= 32l;

        IngredientCommand ingredientCommand= new IngredientCommand();

        when(ingredientService.findByRecipeIdAndIngredientId(anyLong(), anyLong())).thenReturn(ingredientCommand);

        //then
        mockMvc.perform(get("/recipe/"+recipeId+"/ingredient/"+ingredientId+"/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/show"))
                .andExpect(model().attributeExists("ingredient"));
    }

    @Test
    public void updateRecipeIngredientTest() throws Exception {
        IngredientCommand ingredientCommand= new IngredientCommand();

        when(ingredientService.findByRecipeIdAndIngredientId(anyLong(), anyLong())).thenReturn(ingredientCommand);
        when(unitOfMeasureService.listAllUoms()).thenReturn(new HashSet<>());

        mockMvc.perform(get("/recipe/1/ingredient/1/update"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("uomList"))
                .andExpect(view().name("recipe/ingredient/ingredient-form"));
    }

    @Test
    public void saveOrUpdateTest() throws Exception {
        IngredientCommand ingredientCommand= new IngredientCommand();
        ingredientCommand.setId(2l);
        ingredientCommand.setRecipeId(3l);

        when(ingredientService.saveIngredientCommand(any())).thenReturn(ingredientCommand);
        mockMvc.perform(post("/recipe/1/ingredient")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("someId", "")
                        .param("description", "test desc")
                        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/3/ingredient/2/show"));

    }

    @Test
    public void newRecipeIngredient() throws Exception {

        when(ingredientService.findByRecipeIdAndIngredientId(anyLong(), anyLong())).thenReturn(new IngredientCommand());
        when(unitOfMeasureService.listAllUoms()).thenReturn(new HashSet<>());
        mockMvc.perform(get("/recipe/1/ingredient/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("ingredient", "uomList"))
                .andExpect(view().name("recipe/ingredient/ingredient-form"));
    }
}