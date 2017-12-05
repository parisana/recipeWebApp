package guru.springframework.repositories;

import guru.springframework.domain.Recipe;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Parisana on 3/12/17
 */
public interface RecipeRepository extends CrudRepository<Recipe, Long>{
}
