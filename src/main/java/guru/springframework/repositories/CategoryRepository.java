package guru.springframework.repositories;

import guru.springframework.domain.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Created by Parisana on 3/12/17
 */
public interface CategoryRepository extends CrudRepository<Category, Long>{

    Optional<Category> findByDescription(String description);
}
