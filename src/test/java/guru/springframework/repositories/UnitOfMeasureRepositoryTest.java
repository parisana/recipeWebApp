package guru.springframework.repositories;

import guru.springframework.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;

/**
 * Created by Parisana on 3/12/17
 */
//spring Integration test
@RunWith(SpringRunner.class)
@DataJpaTest
public class UnitOfMeasureRepositoryTest {

    @Autowired
    UnitOfMeasureRepository unitOfMeasureRepository;

    @Before
    public void setUp() throws Exception{

    }

    @Test
    public void findByDescription() {

        Optional<UnitOfMeasure> unitOfMeasureOptional= unitOfMeasureRepository.findByDescription("Teaspoon");

        assertEquals("Teaspoon", unitOfMeasureOptional.get().getDescription());
    }

    @Test
    public void findByDescriptionTablespoon() {

        Optional<UnitOfMeasure> unitOfMeasureOptional= unitOfMeasureRepository.findByDescription("Tablespoon");

        assertEquals("Tablespoon", unitOfMeasureOptional.get().getDescription());
    }
}