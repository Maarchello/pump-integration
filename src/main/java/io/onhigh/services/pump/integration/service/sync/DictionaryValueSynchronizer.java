package io.onhigh.services.pump.integration.service.sync;

import io.onhigh.services.pump.integration.model.PumpDictionary;

import java.util.List;

/**
 * @author Marat Kadzhaev
 */
public interface DictionaryValueSynchronizer {

    void syncValues(PumpDictionary pumpDictionary);

    void syncValues(String dictionaryCode);

    void syncValues(List<PumpDictionary> pumpDictionaries);

}
