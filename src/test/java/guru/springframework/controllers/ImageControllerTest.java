package guru.springframework.controllers;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.services.ImageService;
import guru.springframework.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by Parisana on 6/12/17
 */
public class ImageControllerTest {
    ImageController imageController;
    @Mock
    RecipeService recipeService;

    @Mock
    ImageService imageService;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);

        imageController= new ImageController(recipeService, imageService);
        mockMvc= MockMvcBuilders.standaloneSetup(imageController)
                .setControllerAdvice(new ExceptionHandlerController())
                .build();
    }

    @Test
    public void showUploadForm() throws Exception {

        when(recipeService.findCommandById(anyString())).thenReturn(new RecipeCommand());

        mockMvc.perform(get("/recipe/1/uploadImage"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("recipe"))
                .andExpect(view().name("recipe/imageUploadForm"));

        verify(recipeService, times(1)).findCommandById(anyString());

    }

    @Test
    public void uploadImage() throws Exception {

        MockMultipartFile multipartFile= new MockMultipartFile("imagefile",
                "testing.txt","text/plain", "Spring Framework Guru".getBytes());

        mockMvc.perform(multipart("/recipe/1/image").file(multipartFile))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/recipe/1/show"));

        verify(imageService, times(1)).saveImageFile(anyString(), any());

    }

    @Test
    public void renderRecipeImage() throws Exception {
        String txt= "this is the test image strring Use image if you can";
        Byte[] bytes= new Byte[txt.getBytes().length];
        int i=0;
        for(byte b: txt.getBytes())
            bytes[i++]= b;

        RecipeCommand recipeCommand= new RecipeCommand();
        recipeCommand.setId("1");
        recipeCommand.setImage(bytes);

        when(recipeService.findCommandById(anyString())).thenReturn(recipeCommand);

        MockHttpServletResponse response = mockMvc.perform(get("/recipe/1/recipeImage"))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        byte[] contentAsByteArray = response.getContentAsByteArray();

        assertEquals(bytes.length, contentAsByteArray.length);
    }

    @Test
    public void getImageNumberFormatException() throws Exception {
        mockMvc.perform(get("/recipe/asdg/recipeImage"))
                .andExpect(status().isBadRequest())
                .andExpect(view().name("error/400error"));
    }
}