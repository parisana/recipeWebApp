package guru.springframework.repositories.reactiveRepositories;

import guru.springframework.domain.Category;
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
public class CategoryReactiveRepositoryTest {

    @Autowired
    CategoryReactiveRepository categoryReactiveRepository;

    @Before
    public void setUp() throws Exception {
        categoryReactiveRepository.deleteAll().block();
    }

    @Test
    public void saveTest(){
        Category category= new Category();
        category.setDescription("desc");

        categoryReactiveRepository.save(category).block();

        Long count = categoryReactiveRepository.count().block();

        assertEquals(Long.valueOf(1l), count);
    }

    @Test
    public void findByDescription() {
        Category category= new Category();
        category.setDescription("desc");

        categoryReactiveRepository.save(category).then().block();

        Category cat = categoryReactiveRepository.findByDescription("desc").block();

        assertEquals("desc", cat.getDescription());
    }
}