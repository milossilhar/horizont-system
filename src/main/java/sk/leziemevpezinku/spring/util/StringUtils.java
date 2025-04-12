package sk.leziemevpezinku.spring.util;

import static org.apache.commons.lang3.StringUtils.lowerCase;
import static org.apache.commons.lang3.StringUtils.stripAccents;

public class StringUtils {

    /**
     * Normalizes string, strips accent and makes lower case.
     * @param str string to normalize
     * @return normalized string
     */
    public static String normalize(String str) {
        return lowerCase(stripAccents(str));
    }
}
