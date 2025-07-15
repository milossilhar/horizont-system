package sk.leziemevpezinku.spring.util;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static org.apache.commons.lang3.StringUtils.*;

public class StringUtils {

    /**
     * Normalizes string, strips accent and makes lower case.
     * @param str string to normalize
     * @return normalized string
     */
    public static String normalize(String str) {
        return lowerCase(stripAccents(str));
    }

    /**
     * Lowers string case
     * @param str given string
     * @return lowered string
     */
    public static String lower(String str) {
        return lowerCase(str);
    }

    public static String strip(String str) {
        return stripAccents(str);
    }

    public static String leftPadding(String str, int size, String pad) {
        return leftPad(str, size, pad);
    }

    public static String tail(String str, int length) {
        return right(str, length);
    }

    public static String urlEncode(String str) {
        return URLEncoder.encode(str, StandardCharsets.UTF_8);
    }

    public static String capitalizeFirst(String str) {
        return capitalize(str);
    }

    public static boolean blank(String str) {
        return isBlank(str);
    }

    public static boolean notBlank(String str) {
        return !isBlank(str);
    }

    public static String defaultValue(String str, String defaultValue) {
        return isBlank(str) ? defaultValue : str;
    }
}
