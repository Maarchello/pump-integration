package io.onhigh.services.pump.integration.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Marat Kadzhaev
 */
public final class CollectionUtils {

    public static <T> List<T> listOf(T...values) {
        return new ArrayList<>(Arrays.asList(values));
    }

}
