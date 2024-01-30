package com.github.beichenlpl.nlutils.base.common;

/**
 * @author beichenlpl
 * @since 2023/08/31
 */
public class ComparableUtils {

    /**
     * return max value.
     * T extends Comparable<T>
     */
    @SafeVarargs
    public static <T extends Comparable<T>> T max(T t1, T t2, T... args) {
        T max = t1.compareTo(t2) > 0 ? t1 : t2;
        for (T arg : args) {
            max = arg.compareTo(max) > 0 ? arg : max;
        }
        return max;
    }

    /**
     * return min value.
     * T extends Comparable<T>
     */
    @SafeVarargs
    public static <T extends Comparable<T>> T min(T t1, T t2, T... args) {
        T min = t1.compareTo(t2) < 0 ? t1 : t2;
        for (T arg : args) {
            min = arg.compareTo(min) < 0 ? arg : min;
        }
        return min;
    }
}
