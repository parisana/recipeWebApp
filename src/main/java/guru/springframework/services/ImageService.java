package guru.springframework.services;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Parisana on 6/12/17
 */
public interface ImageService {
    void saveImageFile(Long recipeId, MultipartFile file);
}
