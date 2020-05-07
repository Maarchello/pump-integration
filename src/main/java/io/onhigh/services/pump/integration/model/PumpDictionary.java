package io.onhigh.services.pump.integration.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * @author Marat Kadzhaev
 */
@Data @EqualsAndHashCode(callSuper = true)
@Entity
public class PumpDictionary extends AbstractEntity {

    private String code;
    private String name;
    private String version;
    private String scod;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateVersion;

}
