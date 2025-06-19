package sk.leziemevpezinku.spring.util;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    public static String getCurrentUser(int maxLength) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof AnonymousAuthenticationToken) {
            return StringUtils.limit("anonymous", maxLength);
        } else {
            return StringUtils.limit(authentication.getName(), maxLength);
        }
    }
}
