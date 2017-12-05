package guru.springframework.services;

import guru.springframework.commands.UnitofMeasureCommand;

import java.util.Set;

/**
 * Created by Parisana on 5/12/17
 */
public interface UnitOfMeasureService {
    Set<UnitofMeasureCommand> listAllUoms();
}
