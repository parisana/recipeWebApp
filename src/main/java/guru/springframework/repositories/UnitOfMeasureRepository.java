package guru.springframework.repositories;

import guru.springframework.domain.UnitOfMeasure;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Created by Parisana on 3/12/17
 */
public interface UnitOfMeasureRepository extends CrudRepository<UnitOfMeasure, String>{

    Optional<UnitOfMeasure> findByDescription(String description);
}
