package io.onhigh.services.pump.integration.service.sync.impl;

import io.onhigh.services.pump.integration.model.PumpDictionary;
import io.onhigh.services.pump.integration.model.PumpDictionaryValue;
import io.onhigh.services.pump.integration.repository.PumpDictionaryRepository;
import io.onhigh.services.pump.integration.repository.PumpDictionaryValueRepository;
import io.onhigh.services.pump.integration.service.PumpClient;
import io.onhigh.services.pump.integration.service.sync.DictionaryValueSynchronizer;
import io.onhigh.services.pump.integration.util.DateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author Marat Kadzhaev
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DictionaryValueSynchronizerImpl implements DictionaryValueSynchronizer {

    private final PumpDictionaryRepository dictionaryRepository;
    private final PumpDictionaryValueRepository valueRepository;
    private final PumpClient pumpClient;

    @Override
    public void syncValues(PumpDictionary pumpDictionary) {
        List<PumpDictionaryValue> downloadedValues = downloadDictionaryValues(pumpDictionary);
        saveDictionaryValues(pumpDictionary, downloadedValues);
    }

    @Override
    public void syncValues(String dictionaryCode) {
        List<PumpDictionary> savedDictionaries = dictionaryRepository.findAllByCode(dictionaryCode);

        savedDictionaries.stream()
                .filter(withoutValues())
                .forEachOrdered(this::syncValues);
    }

    @Override
    public void syncValues(List<PumpDictionary> pumpDictionaries) {
        pumpDictionaries.forEach(this::syncValues);
    }


    private List<PumpDictionaryValue> downloadDictionaryValues(PumpDictionary pumpDictionary) {
        try {
            log.info("Start downloading values for ({}, {}) dictionary", pumpDictionary.getCode(), pumpDictionary.getVersion());
            List<PumpDictionaryValue> dictionaryValues = pumpClient.getDictionaryValues(pumpDictionary.getCode(), DateUtils.toXMLGregorianCalendar(pumpDictionary.getDateVersion()));
            dictionaryValues.forEach(value -> value.setPumpDictionary(pumpDictionary));
            log.info("Downloaded {} values for ({}, {}) dictionary", dictionaryValues.size(), pumpDictionary.getCode(), pumpDictionary.getVersion());
            return dictionaryValues;
        } catch (Exception ex) {
            log.info("Downloading ({}, {}) dictionary values failed", pumpDictionary.getCode(), pumpDictionary.getVersion(), ex);
            return new ArrayList<>();
        }
    }

    private void saveDictionaryValues(PumpDictionary pumpDictionary, List<PumpDictionaryValue> downloadedValues) {
        log.info("Start saving values for {} dictionary", pumpDictionary.getCode());
        valueRepository.saveAll(downloadedValues);
        log.info("Saving values for {} dictionary done", pumpDictionary.getCode());
    }

    private Predicate<PumpDictionary> withoutValues() {
        return pumpDictionary -> !valueRepository.existsByPumpDictionary(pumpDictionary);
    }
}
