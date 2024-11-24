package app.web.zyncky.service;

import static org.assertj.core.api.Assertions.*;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest(classes = {SecurityService.class, BCryptPasswordEncoder.class})
class SecurityServiceTest {

    @Autowired
    private SecurityService securityService;

    @Test
    void shouldEncryptPassword() {
        final String password = "password";
        assertThat(securityService.encryptText(password)).isNotNull().isNotBlank();
    }

    @Test
    void shouldVerifyEncryptedPassword() {
        final String password = "password";
        final String encryptedPassword = securityService.encryptText(password);
        assertThat(securityService.verifyText(password, encryptedPassword)).isNotNull().isTrue();
        assertThat(securityService.verifyText(password, password)).isNotNull().isFalse();
    }
}