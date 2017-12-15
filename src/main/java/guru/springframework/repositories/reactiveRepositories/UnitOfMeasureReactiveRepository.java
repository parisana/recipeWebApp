package guru.springframework.repositories.reactiveRepositories;

import guru.springframework.domain.UnitOfMeasure;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

/**
 * Created by Parisana on 15/12/17
 */
public interface UnitOfMeasureReactiveRepository extends ReactiveMongoRepository<UnitOfMeasure, String>{
    Mono<UnitOfMeasure> findByDescription(String description);
}
