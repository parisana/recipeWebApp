package guru.springframework.converters;

import guru.springframework.commands.UnitofMeasureCommand;
import guru.springframework.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UnitOfMeasureCommandToUnitOfMeasureTest {

    public static final String DESCRIPTION = "description";
    public static final Long LONG_VALUE = new Long(1L);

    UnitOfMeasureCommandToUnitOfMeasure unitOfMeasureCommandToUnitOfMeasure;

    @Before
    public void setUp() throws Exception {
        unitOfMeasureCommandToUnitOfMeasure = new UnitOfMeasureCommandToUnitOfMeasure();

    }

    @Test
    public void testNullParamter() throws Exception {
        assertNull(unitOfMeasureCommandToUnitOfMeasure.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(unitOfMeasureCommandToUnitOfMeasure.convert(new UnitofMeasureCommand()));
    }

    @Test
    public void convert() throws Exception {
        //given
        UnitofMeasureCommand command = new UnitofMeasureCommand();
        command.setId(LONG_VALUE);
        command.setDescription(DESCRIPTION);

        //when
        UnitOfMeasure uom = unitOfMeasureCommandToUnitOfMeasure.convert(command);

        //then
        assertNotNull(uom);
        assertEquals(LONG_VALUE, uom.getId());
        assertEquals(DESCRIPTION, uom.getDescription());

    }

}