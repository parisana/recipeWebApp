package guru.springframework.repositories.reactiveRepositories;

import guru.springframework.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by Parisana on 15/12/17
 */
@RunWith(SpringRunner.class)
@DataMongoTest
public class UnitOfMeasureReactiveRepositoryTest {

    @Autowired
    UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;

    @Before
    public void setUp() throws Exception {
        unitOfMeasureReactiveRepository.deleteAll().block();
    }

    @Test
    public void findByDescription() {

        UnitOfMeasure unitOfMeasure= new UnitOfMeasure();
        unitOfMeasure.setDescription("desc");

        unitOfMeasureReactiveRepository.save(unitOfMeasure).then().block();

        UnitOfMeasure fetchedUOM= unitOfMeasureReactiveRepository.findByDescription("desc").block();

        assertEquals("desc", fetchedUOM.getDescription());

    }
}