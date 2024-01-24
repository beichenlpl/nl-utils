package com.github.beichenlpl.nlutils.base.common;

import java.util.Collection;
import java.util.Map;

/**
 * @author beichenlpl
 * @since 2023/08/30
 */
public class BaseUtils {

    public static boolean equals(Object o1, Object o2) {
        return o1 == o2 || isNotNull(o1) && o1.equals(o2);
    }

    public static boolean isNull(Object obj) {
        return obj == null;
    }

    public static boolean isNotNull(Object obj) {
        return !isNull(obj);
    }

    public static boolean isEmpty(Object obj) {

        if (obj instanceof Collection) {
            return ((Collection<?>) obj).isEmpty();
        }

        if (obj instanceof Map) {
            return ((Map<?, ?>) obj).isEmpty();
        }

        if (obj instanceof CharSequence) {
            return ((CharSequence) obj).length() == 0;
        }

        return isNull(obj);
    }
}
