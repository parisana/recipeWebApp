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
public class UnitOfMeasureCommandToUnitOfMeasure implements Converter<UnitofMeasureCommand, UnitOfMeasure>{
    @Override
    @Nullable
    @Synchronized
    public UnitOfMeasure convert(UnitofMeasureCommand source) {
        if (source==null) return null;

        final UnitOfMeasure unitOfMeasure= new UnitOfMeasure();
        unitOfMeasure.setId(source.getId());
        unitOfMeasure.setDescription(source.getDescription());

        return unitOfMeasure;
    }
}
