package sk.leziemevpezinku.spring.util;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Random;

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

    /**
     * Cuts off string at max length characters.
     * @param str given string
     * @param maxLength max length
     * @return string with length equal to maxLength
     */
    public static String limit(String str, int maxLength) {
        return left(str, maxLength);
    }

    public static String randomSystemName(int maxLength, boolean capitalize) {
        int a = capitalize ? 65 : 97;
        int z = capitalize ? 90 : 122;
        Random random = new Random();
        return random.ints(maxLength, a, z + 1)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
