package guru.springframework.serviceImpl;

import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.services.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

/**
 * Created by Parisana on 6/12/17
 */
@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    private final RecipeRepository recipeRepository;

    public ImageServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    @Transactional
    public void saveImageFile(Long recipeId, MultipartFile file) {

        log.debug("***Saving Image file***");

        Recipe recipe = recipeRepository.findById(recipeId).get();

        Byte[] fileBytes;
        try {
            fileBytes= new Byte[file.getBytes().length];
            int i=0;
            for (byte b: file.getBytes())
                fileBytes[i++]= b;

            recipe.setImage(fileBytes);

            recipeRepository.save(recipe);
        } catch (IOException e) {
            //todo proper handling of exceptions
            log.debug("***Conversion of file to byte[] failed. Message: "+ e.getMessage());
            e.printStackTrace();
        }
    }
}
