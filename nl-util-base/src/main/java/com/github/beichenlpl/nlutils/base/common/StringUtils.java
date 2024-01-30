package com.github.beichenlpl.nlutils.base.common;

/**
 * @author beichenlpl
 * @since 2023/08/31
 */
public class StringUtils {

    public static boolean equals(String s1, String s2) {
        return BaseUtils.equals(s1, s2);
    }

    public static String reverse(String s) {
        return new StringBuilder(s).reverse().toString();
    }

    public static boolean isEmptyJava(String s) {
        return BaseUtils.isEmpty(s);
    }

    public static boolean isEmptyJavaCommon(String s) {
        return isEmptyJava(s) || s.equals("null");
    }

    public static boolean isEmptyJs(String s) {
        return BaseUtils.isEmpty(s);
    }

    public static boolean isEmptyJsCommon(String s) {
        return isEmptyJs(s) || s.equals("null") || s.equals("undefined");
    }

    public static boolean isEmptyJavaAfterTrim(String s) {
        return BaseUtils.isNull(s) || isEmptyJava(s.trim());
    }

    public static boolean isEmptyJavaCommonAfterTrim(String s) {
        return isEmptyJavaAfterTrim(s) || s.trim().equals("null");
    }

    public static boolean isEmptyJsAfterTrim(String s) {
        return BaseUtils.isNull(s) || isEmptyJs(s.trim());
    }

    public static boolean isEmptyJsCommonAfterTrim(String s) {
        return isEmptyJsAfterTrim(s) || s.trim().equals("null") || s.trim().equals("undefined");
    }

    public static String insert(String origin, String target, int index) {
        if (index == 0) {
            return target + origin;
        }

        if (index == origin.length() - 1) {
            return origin + target;
        }

        return origin.substring(0, index) + target + origin.substring(index);
    }

    public static String removeFirst(String origin, String target) {
        int i = origin.indexOf(target);
        return remove(origin, target, i);
    }

    public static String removeLast(String origin, String target) {
        int i = origin.lastIndexOf(target);
        return remove(origin, target, i);
    }

    public static String removeAll(String origin, String target) {
        return origin.replace(target, "");
    }
    
    public static String remove(String origin, String target, int index) {
        return index == origin.length() - target.length() - 1 ? origin.substring(0, index) : origin.substring(0, index) + origin.substring(index);
    }
}
