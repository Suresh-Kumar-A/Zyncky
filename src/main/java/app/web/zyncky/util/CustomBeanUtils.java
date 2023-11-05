package app.web.zyncky.util;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomBeanUtils {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public String encodeUsingBcryptPasswordEncoder(String input) {
        return bCryptPasswordEncoder.encode(input);
    }

    public boolean verifyUserPassword(String plainText, String hashString) {
        return bCryptPasswordEncoder.matches(plainText, hashString);
    }

    public String getUserNameFromJWTSession() throws Exception {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }
}
