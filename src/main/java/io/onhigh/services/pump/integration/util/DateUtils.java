package io.onhigh.services.pump.integration.util;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author Marat Kadzhaev
 */
public class DateUtils {

    public static XMLGregorianCalendar toXMLGregorianCalendar(Date date) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);

        try {
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
        } catch (DatatypeConfigurationException e) {
            return null;
        }
    }

}
