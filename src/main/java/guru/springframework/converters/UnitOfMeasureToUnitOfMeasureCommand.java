package guru.springframework.converters;

import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.domain.UnitOfMeasure;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * Created by Parisana on 4/12/17
 */
@Component
public class UnitOfMeasureToUnitOfMeasureCommand implements Converter<UnitOfMeasure, UnitOfMeasureCommand>{
    @Nullable
    @Synchronized
    @Override
    public UnitOfMeasureCommand convert(UnitOfMeasure source) {
        if (source==null) return null;

        final UnitOfMeasureCommand unitofMeasure = new UnitOfMeasureCommand();
        unitofMeasure.setId(source.getId());
        unitofMeasure.setDescription(source.getDescription());

        return unitofMeasure;
    }
}
