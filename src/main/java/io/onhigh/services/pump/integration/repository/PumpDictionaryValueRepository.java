package io.onhigh.services.pump.integration.repository;

import io.onhigh.services.pump.integration.model.PumpDictionary;
import io.onhigh.services.pump.integration.model.PumpDictionaryValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Marat Kadzhaev
 */
@Repository
public interface PumpDictionaryValueRepository extends JpaRepository<PumpDictionaryValue, Long> {

    boolean existsByPumpDictionary(PumpDictionary pumpDictionary);

}
