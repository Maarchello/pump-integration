package io.onhigh.services.pump.integration.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;

/**
 * @author Marat Kadzhaev
 */
@Data @EqualsAndHashCode(callSuper = true)
@Entity
public class SupportedDictionary extends AbstractEntity {

    private String dictionaryCode;

}
