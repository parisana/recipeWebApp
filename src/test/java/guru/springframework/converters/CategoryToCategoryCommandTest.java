package guru.springframework.converters;

import guru.springframework.commands.CategoryCommand;
import guru.springframework.domain.Category;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Parisana on 4/12/17
 */
public class CategoryToCategoryCommandTest {

    CategoryToCategoryCommand categoryToCategoryCommand;

    @Before
    public void setUp() throws Exception {
        categoryToCategoryCommand = new CategoryToCategoryCommand();
    }

    @Test
    public void testNullObject() throws Exception{
        assertNull(categoryToCategoryCommand.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception{
        assertNotNull(categoryToCategoryCommand.convert(new Category()));
    }

    @Test
    public void convert() {

        Category category= new Category();
        category.setId(2l);
        category.setDescription("Test Desc");

        CategoryCommand categoryCommand = categoryToCategoryCommand.convert(category);

        assertEquals(category.getId(), categoryCommand.getId());
        assertEquals(category.getDescription(), categoryCommand.getDescription());
    }
}