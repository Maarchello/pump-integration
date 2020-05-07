package io.onhigh.services.pump.integration.controller.rest;

import io.onhigh.services.pump.integration.service.sync.SyncService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

/**
 * @author Marat Kadzhaev
 */
@RestController
@RequestMapping("/api/dictionaries/synchronizer")
@RequiredArgsConstructor
public class DictionarySynchronizerController {

    private final SyncService syncService;

    /**
     * Метод запускает синхронизацию справочников в отдельном потоке.
     * Если параметры не переданы, запускается синхронизация всех справочников.
     * Иначе запускается синхронизация соответствующих параметрам справочников.
     *
     * @param code - код справочника, который надо синхронизировать.
     * @param version - версия, до которой надо синхронизировать справочники.
     * @return возвращает сообщение о том, что синхронизация началась, для подробностей см. детали.
     */
    @ApiOperation("Запускает синхронизацию справочников")
    @PostMapping("/sync")
    public String start(@RequestParam(required = false) String code,
                        @RequestParam(required = false) String version) {

        CompletableFuture.runAsync(() -> syncService.sync(code, version));
        return "Synchronization started. See logs for details.";
    }

}
