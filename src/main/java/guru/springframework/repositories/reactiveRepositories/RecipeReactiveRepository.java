package guru.springframework.repositories.reactiveRepositories;

import guru.springframework.domain.Recipe;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * Created by Parisana on 15/12/17
 */
public interface RecipeReactiveRepository extends ReactiveMongoRepository<Recipe, String> {
}
