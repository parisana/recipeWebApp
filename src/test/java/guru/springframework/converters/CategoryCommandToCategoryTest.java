package guru.springframework.converters;

import guru.springframework.commands.CategoryCommand;
import guru.springframework.domain.Category;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Parisana on 4/12/17
 */
public class CategoryCommandToCategoryTest {

    CategoryCommandToCategory categoryCommandToCategory;

    @Before
    public void setUp() throws Exception {
        categoryCommandToCategory= new CategoryCommandToCategory();
    }

    //Test if convert(null) returns null
    @Test
    public void testNullObject() throws Exception{
        assertNull(categoryCommandToCategory.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception{
        assertNotNull(categoryCommandToCategory.convert(new CategoryCommand()));
    }

    //Check if conversion is done properly
    @Test
    public void convert() {

        CategoryCommand categoryCommand = new CategoryCommand();
        categoryCommand.setId(1l);
        categoryCommand.setDescription("Test Description");

        Category category = categoryCommandToCategory.convert(categoryCommand);

        assertEquals(categoryCommand.getId(), category.getId());
        assertEquals(categoryCommand.getDescription(), category.getDescription());
    }
}