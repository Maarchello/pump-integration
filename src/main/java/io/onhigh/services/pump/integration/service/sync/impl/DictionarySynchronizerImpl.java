package io.onhigh.services.pump.integration.service.sync.impl;

import io.onhigh.services.pump.integration.model.PumpDictionary;
import io.onhigh.services.pump.integration.model.SupportedDictionary;
import io.onhigh.services.pump.integration.repository.PumpDictionaryRepository;
import io.onhigh.services.pump.integration.repository.SupportedDictionaryRepository;
import io.onhigh.services.pump.integration.service.PumpClient;
import io.onhigh.services.pump.integration.service.sync.DictionarySynchronizer;
import io.onhigh.services.pump.integration.service.sync.DictionaryValueSynchronizer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;

/**
 * @author Marat Kadzhaev
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DictionarySynchronizerImpl implements DictionarySynchronizer {

    private final PumpDictionaryRepository dictionaryRepository;
    private final SupportedDictionaryRepository supportedDictionaryRepository;
    private final PumpClient pumpClient;
    private final DictionaryValueSynchronizer valueSynchronizer;

    @Override
    public void sync(PumpDictionary pumpDictionary) {
        // todo
    }

    @Override
    public void sync() {
        log.info("Sync started");

        List<String> supportedDictionaries = supportedDictionaryRepository.findAll()
                .stream()
                .map(SupportedDictionary::getDictionaryCode)
                .collect(toList());

        if (supportedDictionaries.isEmpty()) {
            log.info("No found supported dictionaries");
            return;
        }

        supportedDictionaries.forEach(this::syncByCode);

        log.info("Sync completed");
    }

    @Override
    public void sync(String version) {
        // todo
    }

    @Override
    public void sync(String code, String version) {
        PumpDictionary pumpDictionary = dictionaryRepository
                .findOneByCodeAndVersion(code, version)
                .orElseThrow(() -> new EntityNotFoundException("Pump dictionary with %s code and %s version not found in database"));

        sync(pumpDictionary);
    }

    @Override
    public void syncByCode(String code) {
        log.info("Sync by code {} started", code);

        List<String> savedVersions = findSavedVersions(code);

        List<PumpDictionary> unsavedDictionaryVersions = findUnsavedDictionaries(code, savedVersions);
        log.info("Found {} unsaved versions", unsavedDictionaryVersions.size());

        log.info("Start saving dictionaries");
        List<PumpDictionary> pumpDictionaries = dictionaryRepository.saveAll(unsavedDictionaryVersions);
        log.info("Saving dictionaries done");

        valueSynchronizer.syncValues(pumpDictionaries);
    }

    private List<String> findSavedVersions(String code) {
        return dictionaryRepository
                .findAllByCode(code)
                .stream()
                .map(PumpDictionary::getVersion)
                .collect(toList());
    }

    private List<PumpDictionary> findUnsavedDictionaries(String code, List<String> savedVersions) {
        return pumpClient.getDictionaryAllVer(code)
                .stream()
                .filter(notSaved(savedVersions))
                .collect(toList());
    }

    private Predicate<PumpDictionary> notSaved(List<String> savedVersions) {
        return dictionary -> !savedVersions.contains(dictionary.getVersion());
    }
}
