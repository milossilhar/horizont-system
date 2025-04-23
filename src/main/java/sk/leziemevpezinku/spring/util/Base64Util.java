package sk.leziemevpezinku.spring.util;

import java.util.Base64;

public class Base64Util {

    /**
     * Encodes bytes to base64-URL string
     * @param src source bytes
     * @return encoded base64 string
     */
    public static String encodeUrlNoPadding(byte[] src) {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(src);
    }

    /**
     *
     * @param src
     * @return
     */
    public static byte[] decodeUrlNoPadding(String src) {
        return Base64.getUrlDecoder().decode(src);
    }
}
