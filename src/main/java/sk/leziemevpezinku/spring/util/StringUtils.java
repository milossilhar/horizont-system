package sk.leziemevpezinku.spring.util;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.Locale;

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

    public static String strip(String str) {
        return stripAccents(str);
    }

    public static String leftPadding(String val, int size, String pad) {
        return leftPad(val, size, pad);
    }

    public static String formatCurrency(BigDecimal value) {
        return DecimalFormat.getCurrencyInstance(Locale.getDefault()).format(value);
    }

    public static String urlEncode(String str) {
        return URLEncoder.encode(str, StandardCharsets.UTF_8);
    }
}
