package app.web.zyncky.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import app.web.zyncky.constant.RoleEnum;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private static final String[] GLOBAL_EXCLUDE_API_PATTERN = new String[] {
            "/version", "/create-account", "/login"
    };

    private static final String USER_API_PATTERN = "/users/**";

    private static final String[] ALLOWED_USER_API_ROLES = new String[] { RoleEnum.USER.name(), RoleEnum.ADMIN.name() };

    /**
     * Note: If 'server.servlet.context-path' is set then the same
     * value does not need to be provided in the security filter chain's request
     * matcher pattern.
     * 
     * @param http
     * @return
     * @throws Exception throws generic Exception if any error occurs
     * 
     * 
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(GLOBAL_EXCLUDE_API_PATTERN).permitAll();
                    auth.requestMatchers(USER_API_PATTERN).hasAnyRole(ALLOWED_USER_API_ROLES);
                    auth.anyRequest().authenticated();
                })
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(withDefaults())
                .build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
