package guru.springframework.converters;

import guru.springframework.commands.UnitofMeasureCommand;
import guru.springframework.domain.UnitOfMeasure;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * Created by Parisana on 4/12/17
 */
@Component
public class UnitOfMeasureToUnitOfMeasureCommand implements Converter<UnitOfMeasure, UnitofMeasureCommand>{
    @Nullable
    @Synchronized
    @Override
    public UnitofMeasureCommand convert(UnitOfMeasure source) {
        if (source==null) return null;

        final UnitofMeasureCommand unitofMeasureCommand= new UnitofMeasureCommand();
        unitofMeasureCommand.setId(source.getId());
        unitofMeasureCommand.setDescription(source.getDescription());

        return unitofMeasureCommand;
    }
}
