package sk.leziemevpezinku.spring.util;

public class StringUtils extends org.apache.commons.lang3.StringUtils {

    /**
     * Normalizes string, strips accent and makes lower case.
     * @param str string to normalize
     * @return normalized string
     */
    public static String normalize(String str) {
        return lowerCase(stripAccents(str));
    }
}
