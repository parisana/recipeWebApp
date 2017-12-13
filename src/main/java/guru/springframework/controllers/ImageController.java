package guru.springframework.controllers;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.services.ImageService;
import guru.springframework.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Parisana on 6/12/17
 */
@Slf4j
@Controller
public class ImageController {

    private final RecipeService recipeService;
    private final ImageService imageService;

    public ImageController(RecipeService recipeService, ImageService imageService) {
        this.recipeService = recipeService;
        this.imageService = imageService;
    }

    @GetMapping("/recipe/{recipeId}/uploadImage")
    public String showUploadForm(@PathVariable String recipeId, Model model){
        log.debug("***Showing upload Form for recipe***"+recipeId);
        model.addAttribute("recipe", recipeService.findCommandById(recipeId));

        return "recipe/imageUploadForm";
    }

    @PostMapping("recipe/{recipeId}/image")
    public String uploadImage(@PathVariable String recipeId, @RequestParam("imagefile")MultipartFile file){
        log.debug("***Uploading file: "+file+" recipeId: "+recipeId );
        imageService.saveImageFile(recipeId, file);

        return "redirect:/recipe/"+ recipeId +"/show";
    }

    @GetMapping("/recipe/{recipeId}/recipeImage")
    public void renderRecipeImage(@PathVariable String recipeId, HttpServletResponse response){
        RecipeCommand recipeCommand = recipeService.findCommandById(recipeId);
        response.setContentType("image/jpeg");
        if (recipeCommand.getImage()!=null){
            byte[] imageByte= new byte[recipeCommand.getImage().length];

            int i=0;
            for (Byte b: recipeCommand.getImage())
                imageByte[i++]= b;

            InputStream inputStream= new ByteArrayInputStream(imageByte);
            try {
                IOUtils.copy(inputStream, response.getOutputStream());
            } catch (IOException e) {
                log.debug("***Error handling response with image bytes****");
                e.printStackTrace();
            }
        }
    }
}
