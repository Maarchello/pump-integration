package io.onhigh.services.pump.integration.repository;

import io.onhigh.services.pump.integration.model.PumpDictionary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Marat Kadzhaev
 */
@Repository
public interface PumpDictionaryRepository extends JpaRepository<PumpDictionary, Long> {

    List<PumpDictionary> findAllByCode(String code);

    Optional<PumpDictionary> findOneByCodeAndVersion(String code, String version);

}
