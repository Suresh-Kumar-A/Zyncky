package app.web.zyncky.util;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomBeanUtils {

    public String encodeUsingBcryptPasswordEncoder(String input) {
        // return bCryptPasswordEncoder.encode(input);
        return input;
    }
    
}
