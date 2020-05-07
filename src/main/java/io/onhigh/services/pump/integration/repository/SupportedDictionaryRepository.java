package io.onhigh.services.pump.integration.repository;

import io.onhigh.services.pump.integration.model.SupportedDictionary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Marat Kadzhaev
 */
@Repository
public interface SupportedDictionaryRepository extends JpaRepository<SupportedDictionary, Long> {
}
