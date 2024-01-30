package com.github.beichenlpl.nlutils.base.common;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

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

        if (obj instanceof Object[]) {
            return ((Object[]) obj).length == 0;
        }

        return isNull(obj);
    }

    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }

    public static void cpProps(Object src, Object dest, String... ignoreProps) {
        List<String> ignorePropList = isNotNull(ignoreProps) ? Arrays.stream(ignoreProps).collect(Collectors.toList()) : null;

        Field[] srcFields = src.getClass().getDeclaredFields();
        try {
            for (Field srcField : srcFields) {
                if (isNotNull(ignorePropList) && ignorePropList.contains(srcField.getName())) {
                    continue;
                }

                srcField.setAccessible(true);
                Field destField = dest.getClass().getDeclaredField(srcField.getName());
                destField.setAccessible(true);
                destField.set(dest, srcField.get(src));
            }
        } catch (Exception e) {
            throw new IllegalStateException("cpProps error", e);
        }
    }
}
