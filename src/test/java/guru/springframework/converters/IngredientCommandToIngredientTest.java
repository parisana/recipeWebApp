package guru.springframework.converters;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.domain.Ingredient;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * Created by Parisana on 4/12/17
 */
public class IngredientCommandToIngredientTest {

    public static final BigDecimal AMOUNT = new BigDecimal("32.33");
    public static final String DESCRIPTION = "Test Desc";
    public static final Long ID_VALUE = new Long(132L);
    public static final Long UOM_ID = new Long(222L);

    IngredientCommandToIngredient ingredientCommandToIngredient;

    @Before
    public void setUp() throws Exception {
        ingredientCommandToIngredient= new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
    }

    @Test
    public void testNullObject() throws Exception {
        assertNull(ingredientCommandToIngredient.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(ingredientCommandToIngredient.convert(new IngredientCommand()));
    }

    @Test
    public void convert() {
        IngredientCommand ingredientCommand= new IngredientCommand();
        ingredientCommand.setId(ID_VALUE);
        ingredientCommand.setAmount(AMOUNT);
        ingredientCommand.setDescription(DESCRIPTION);

        UnitOfMeasureCommand unitofMeasure = new UnitOfMeasureCommand();
        unitofMeasure.setId(UOM_ID);

        ingredientCommand.setUom(unitofMeasure);

        Ingredient ingredient = ingredientCommandToIngredient.convert(ingredientCommand);

        assertNotNull(ingredient.getUom());
        assertNotNull(ingredient);

        assertEquals(ID_VALUE, ingredient.getId());
        assertEquals(DESCRIPTION, ingredient.getDescription());
        assertEquals(UOM_ID,ingredient.getUom().getId());
        assertEquals(AMOUNT, ingredient.getAmount());
    }
}