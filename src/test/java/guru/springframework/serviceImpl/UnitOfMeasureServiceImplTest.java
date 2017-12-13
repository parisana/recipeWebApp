package guru.springframework.serviceImpl;

import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.repositories.UnitOfMeasureRepository;
import guru.springframework.services.UnitOfMeasureService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Created by Parisana on 5/12/17
 */
public class UnitOfMeasureServiceImplTest {

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    UnitOfMeasureService unitOfMeasureService;
    UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        unitOfMeasureToUnitOfMeasureCommand= new UnitOfMeasureToUnitOfMeasureCommand();
        unitOfMeasureService= new UnitOfMeasureServiceImpl(unitOfMeasureRepository, unitOfMeasureToUnitOfMeasureCommand);
    }

    @Test
    public void listAllUoms() {
        String id1= "1";
        String id2= "2";

        UnitOfMeasure unitofMeasure = new UnitOfMeasure();
        unitofMeasure.setId(id1);

        UnitOfMeasure unitofMeasure1 = new UnitOfMeasure();
        unitofMeasure.setId(id2);

        Set<UnitOfMeasure> unitofMeasureSet = new HashSet<>();
        unitofMeasureSet.add(unitofMeasure);
        unitofMeasureSet.add(unitofMeasure1);

        when(unitOfMeasureRepository.findAll()).thenReturn(unitofMeasureSet);

        //when
        Set<UnitOfMeasureCommand> unitofMeasures = unitOfMeasureService.listAllUoms();

        //then
        assertEquals(2, unitofMeasures.size());
    }
}