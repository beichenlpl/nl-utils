package com.github.beichenlpl.nlutils.base.system;

import java.util.Map;

/**
 * @author beichenlpl
 * @since 2023/09/06
 */
public class SystemUtils {

    public static Map<String, String> getAllEnv() {
        return System.getenv();
    }

    public static String getEnv(String envKey) {
        return getAllEnv().get(envKey);
    }

    public static String getJavaVersion() {
        return System.getProperty("java.version");
    }

    public static String getJavaRuntimeVersion() {
        return System.getProperty("java.runtime.version");
    }

    public static String getOsName() {
        return System.getProperty("os.name");
    }

    public static String getOsVersion() {
        return System.getProperty("os.version");
    }

    public static String getOsArch() {
        return System.getProperty("os.arch");
    }

    public static String getJavaHome() {
        return System.getProperty("java.home");
    }

    public static String getUserDir() {
        return System.getProperty("user.dir");
    }

    public static String getUserName() {
        return System.getProperty("user.name");
    }

    public static String getUserLanguage() {
        return System.getProperty("user.language");
    }

    public static String getUserCountry() {
        return System.getProperty("user.country");
    }

    public static String getUserVariant() {
        return System.getProperty("user.variant");
    }

    public static String getFileSeparator() {
        return System.getProperty("file.separator");
    }

    public static String getPathSeparator() {
        return System.getProperty("path.separator");
    }

    public static String getLineSeparator() {
        return System.getProperty("line.separator");
    }
}
