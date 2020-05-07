package io.onhigh.services.pump.integration.service.sync;

import io.onhigh.services.pump.integration.model.PumpDictionary;

/**
 * @author Marat Kadzhaev
 */
public interface DictionarySynchronizer {

    void sync();

    void sync(String version);

    void sync(String code, String version);

    void syncByCode(String code);

    void sync(PumpDictionary pumpDictionary);


}
