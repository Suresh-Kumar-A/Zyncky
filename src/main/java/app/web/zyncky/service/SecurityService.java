package app.web.zyncky.service;

import jakarta.annotation.Nonnull;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Data
public class SecurityService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Nonnull
    public String encryptText(@Nonnull String password) {
        return bCryptPasswordEncoder.encode(password);
    }

    public boolean verifyText(String rawText, String encryptedText) {
        return bCryptPasswordEncoder.matches(rawText, encryptedText);
    }
}
