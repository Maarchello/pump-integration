package io.onhigh.services.pump.integration.service.sync;

import io.onhigh.services.pump.integration.model.PumpDictionary;
import io.onhigh.services.pump.integration.model.PumpDictionaryValue;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.ibs.pmp.ws.DictionaryDto171001;
import ru.ibs.pmp.ws.DictionaryDtoList171001;
import ru.ibs.pmp.ws.GetDictionaryReturnObject;
import ru.ibs.pmp.ws.GetNsiDictionariesReturnObject171001;
import ru.ibs.pmp.ws.NsiEntriesPage;
import ru.ibs.pmp.ws.NsiEntry;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Marat Kadzhaev
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PumpResponseConverter {

    private static final String VERSION_ID_KEY = "VERSION_ID";
    private static final String CODE_KEY = "CODE";
    private static final String NAME_KEY = "NAME";
    private static final String ID_KEY = "ID";

    public List<PumpDictionary> convert(GetNsiDictionariesReturnObject171001 getNsiDictionariesResponse) {
        log.info("Start convert response to list of {}", PumpDictionary.class.getName());

        DictionaryDtoList171001 nsiDictionaryList = getNsiDictionariesResponse.getNsiDictionaryList();
        List<DictionaryDto171001> nsiDictionaries = nsiDictionaryList.getList();
        return nsiDictionaries.stream()
                .map(dto -> {
                    PumpDictionary dictionary = new PumpDictionary();
                    dictionary.setCode(dto.getCode());
                    dictionary.setName(dto.getName());
                    dictionary.setScod(dto.getScod());
                    dictionary.setDateVersion(dto.getDateVersion().toGregorianCalendar().getTime());
                    dictionary.setVersion(dto.getVersion());

                    return dictionary;
                }).distinct().collect(Collectors.toList());
    }

    public List<PumpDictionaryValue> convert(GetDictionaryReturnObject getDictionaryResponse) {
        log.info("Start convert response to list of {}", PumpDictionaryValue.class.getName());

        NsiEntriesPage infoList = getDictionaryResponse.getNsiDictionaryEntryInfoList();
        List<NsiEntry> entries = infoList.getEntries();

        return entries.stream()
                .map(ent -> convert(ent.getFields().getEntry()))
                .collect(Collectors.toList());
    }

    private PumpDictionaryValue convert(List<NsiEntry.Fields.Entry> entries) {
        Map<String, String> entriesMap = toMap(entries);

        String code = entriesMap.get(CODE_KEY);
        String versionId = entriesMap.get(VERSION_ID_KEY);
        String name = entriesMap.get(NAME_KEY);
        String pumpId = entriesMap.get(ID_KEY);

        PumpDictionaryValue value = new PumpDictionaryValue();
        value.setVersionId(versionId);
        value.setCode(code);
        value.setName(name);
        value.setPumpId(pumpId);

        return value;
    }

    private Map<String, String> toMap(List<NsiEntry.Fields.Entry> entries) {
        return entries
                .stream()
                .collect(Collectors.toMap(NsiEntry.Fields.Entry::getKey, NsiEntry.Fields.Entry::getValue));
    }

}
