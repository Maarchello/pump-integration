package io.onhigh.services.pump.integration.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

/**
 * @author Marat Kadzhaev
 */
@Data @EqualsAndHashCode(callSuper = true)
@Entity
public class PumpDictionaryValue extends AbstractEntity {

    private String code;
    private String name;
    private String versionId;
    private String pumpId;

    @ManyToOne(fetch = FetchType.LAZY)
    private PumpDictionary pumpDictionary;

}
