package guru.springframework.domain;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Parisana on 3/12/17
 */
public class CategoryTest {

    Category category;

    @Before
    public void setUp(){
        category= new Category();
    }

    @Test
    public void getId() {
        Long id= 4l;

        category.setId(id);

        assertEquals(id, category.getId());
    }

    @Test
    public void getDescription() {
    }

    @Test
    public void getRecipes() {
    }
}