package app.web.zyncky.util;

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

}
