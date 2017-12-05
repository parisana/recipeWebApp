package guru.springframework.controllers;

import guru.springframework.domain.Recipe;
import guru.springframework.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * Created by Parisana on 3/12/17
 */
public class IndexControllerTest {

    IndexController indexController;

    @Captor
    ArgumentCaptor<Set<Recipe>> argumentCaptor;

    @Mock
    Model model;

    @Mock
    RecipeService recipeService;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this); //initialize the objects annotated with mocks in this test class.

        indexController= new IndexController(recipeService);
    }

    //spring mock mvc
    @Test
    public void testMockMVC(){
        MockMvc mockMvc= MockMvcBuilders.standaloneSetup(indexController).build();

        try {
            mockMvc.perform(get("/"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("index"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void getIndexPage() {

        //Given
        Set<Recipe> recipes= new HashSet<>();
        recipes.add(new Recipe());

        Recipe recipe= new Recipe();
        recipe.setId(1l);

        recipes.add(recipe);

        when(recipeService.getRecipes()).thenReturn(recipes);

        //ArgumentCaptor<Set<Recipe>> argumentCaptor= ArgumentCaptor.forClass(Set.class);//Using @Captor instead

        //When
        String view = indexController.getIndexPage(model);

        //then
        assertEquals("index", view);
        verify(recipeService, times(1)).getRecipes();
        verify(model, times(1)).addAttribute(eq("recipes"),argumentCaptor.capture());
        assertEquals(2, argumentCaptor.getValue().size());// need to use argument.capture() in verify() before getValue()...

    }
}