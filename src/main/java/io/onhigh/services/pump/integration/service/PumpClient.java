package io.onhigh.services.pump.integration.service;

import io.onhigh.services.pump.integration.model.PumpDictionary;
import io.onhigh.services.pump.integration.model.PumpDictionaryValue;
import io.onhigh.services.pump.integration.service.sync.PumpResponseConverter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.ibs.pmp.ws.Filter;
import ru.ibs.pmp.ws.GetDictionary171001;
import ru.ibs.pmp.ws.GetDictionaryReturnObject;
import ru.ibs.pmp.ws.GetNsiDictionariesReturnObject171001;
import ru.ibs.pmp.ws.PmpGatewayResponseBean171001;
import ru.ibs.pmp.ws.WsAuthInfo;
import ru.ibs.pmp.ws.impl.PumpWsService;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.List;

/**
 * @author Marat Kadzhaev
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PumpClient {

    @Getter
    @Value("${settings.credentials.system}")
    private String system;

    @Getter
    @Value("${settings.credentials.username}")
    private String username;

    @Getter
    @Value("${settings.credentials.password}")
    private String password;

    @Getter
    @Value("#{T(java.lang.Integer).parseInt('${settings.credentials.orgId}')}")
    private int orgId;

    private final PumpWsService wsService;
    private final PumpResponseConverter pumpResponseConverter;

    public List<PumpDictionary> getDictionaryAllVer(String dictionaryName) {
        PmpGatewayResponseBean171001 responseBean = wsService.getClient().getDictionaryAllVer(createAuthInfo(), dictionaryName, null, null, 0, 0);

        return pumpResponseConverter.convert((GetNsiDictionariesReturnObject171001) responseBean.getResponse());
    }

    public List<PumpDictionaryValue> getDictionaryValues(String dictionaryCode, XMLGregorianCalendar dateVersion) {
        Filter filter = new Filter();
        filter.setDictionaryName(dictionaryCode);
        filter.setVersionDate(dateVersion);

        GetDictionary171001 parameters = new GetDictionary171001();
        parameters.setAuthInfo(createAuthInfo());
        parameters.setFilter(filter);

        log.info("Send SOAP request [getDictionary171001]");
        PmpGatewayResponseBean171001 responseBean = wsService.getClient().getDictionary171001(parameters).getReturn();
        log.info("Receive SOAP response [getDictionary171001]");
        return pumpResponseConverter.convert((GetDictionaryReturnObject) responseBean.getResponse());
    }

    private WsAuthInfo createAuthInfo() {
        WsAuthInfo authInfo = new WsAuthInfo();
        authInfo.setOrgId(orgId);
        authInfo.setSystem(system);
        authInfo.setUser(username);
        authInfo.setPassword(password);

        return authInfo;
    }

}
