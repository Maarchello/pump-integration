package io.onhigh.services.pump.integration.service.sync;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @author Marat Kadzhaev
 */
@Service
@RequiredArgsConstructor
public class SyncService {

    private final DictionarySynchronizer dictionarySynchronizer;

    public void sync(String code, String version) {
        if (StringUtils.isNoneBlank(code, version)) {
            dictionarySynchronizer.sync(code, version);
            return;
        }

        if (StringUtils.isNotBlank(version)) {
            dictionarySynchronizer.sync(version);
            return;
        }

        if (StringUtils.isNotBlank(code)) {
            dictionarySynchronizer.syncByCode(code);
            return;
        }

        dictionarySynchronizer.sync();
    }

}
