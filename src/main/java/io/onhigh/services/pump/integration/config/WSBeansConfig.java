package io.onhigh.services.pump.integration.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.ibs.pmp.ws.impl.PumpWsService;

/**
 * @author Marat Kadzhaev
 */
@Configuration
public class WSBeansConfig {

    @Bean
    public PumpWsService pumpWsService() {
        return new PumpWsService();
    }

}
