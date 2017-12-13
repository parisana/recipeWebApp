package guru.springframework.serviceImpl;

import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Parisana on 6/12/17
 */
public class ImageServiceImplTest {

    ImageServiceImpl imageService;
    @Mock
    RecipeRepository recipeRepository;

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);

        imageService= new ImageServiceImpl(recipeRepository);
    }

    @Test
    public void saveImageFile() throws IOException {

        Recipe recipe= new Recipe();
        recipe.setId("1");

        MockMultipartFile multipartFile= new MockMultipartFile("imageFile", "testing.txt", "text/plain",
                "Spring FrameWork Guru".getBytes());

        Optional<Recipe> recipeOptional= Optional.of(recipe);

        when(recipeRepository.findById(anyString())).thenReturn(recipeOptional);

        ArgumentCaptor<Recipe> argumentCaptor= ArgumentCaptor.forClass(Recipe.class);

        //when
        imageService.saveImageFile("1", multipartFile);

        //then
        verify(recipeRepository, times(1)).findById(anyString());
        verify(recipeRepository, times(1)).save(argumentCaptor.capture());

        Recipe savedRecipe= argumentCaptor.getValue();
        assertEquals(multipartFile.getBytes().length, savedRecipe.getImage().length);
    }
}