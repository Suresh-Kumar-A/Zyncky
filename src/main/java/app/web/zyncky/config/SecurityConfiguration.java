package app.web.zyncky.config;

import static org.springframework.security.config.Customizer.withDefaults;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import app.web.zyncky.constant.RoleEnum;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private static final String[] GLOBAL_EXCLUDE_API_PATTERN = new String[] {
            "/version", "/create-account", "/login"
    };

    private static final String USER_API_PATTERN = "/users/**";

    private static final String[] ALLOWED_USER_API_ROLES = new String[] { RoleEnum.USER.name(), RoleEnum.ADMIN.name() };

    private final JwtTokenFilter jwtTokenFilter;

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
                .cors(withDefaults())
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(GLOBAL_EXCLUDE_API_PATTERN).permitAll();
                    auth.requestMatchers(USER_API_PATTERN).hasAnyRole(ALLOWED_USER_API_ROLES);
                    auth.anyRequest().authenticated();
                })
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(withDefaults())
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
