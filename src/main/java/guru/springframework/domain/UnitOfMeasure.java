package guru.springframework.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by Parisana on 3/12/17
 */
@Getter
@Setter
@Document
public class UnitOfMeasure {

    @Id
    private String id;

    private String description;

}
