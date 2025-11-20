package doctorhoai.learn.indentity_service.security;

import doctorhoai.learn.indentity_service.config.filter.JwtRequestFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class SecurityConfiguration {

    private final JwtRequestFilter jwtRequestFilter;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(
                        auth -> auth
                                .requestMatchers("/follow_up_visit/**").permitAll()
                                .requestMatchers("/weekdays/**").permitAll()
                                .requestMatchers("/icd10/**").permitAll()
                                .requestMatchers("/shift/**").permitAll()
                                .requestMatchers("/api/payment/**").permitAll()
                                .requestMatchers("/drug/**").permitAll()
                                .requestMatchers("/upload/**").permitAll()
                                .requestMatchers("/auth/**").permitAll()
                                .requestMatchers("/employee/**").permitAll()
                                .requestMatchers("/patient/**").permitAll()
                                .requestMatchers("/room/**").permitAll()
                                .requestMatchers("/service/**").permitAll()
                                .requestMatchers("/specialization/**").permitAll()
                                .requestMatchers("/actuator/**").permitAll()
                                .requestMatchers("/api/sms/**").permitAll()
                                .anyRequest().authenticated()
                )
                .sessionManagement( sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(
                        exception -> exception
                                .authenticationEntryPoint(customAuthenticationEntryPoint)
                                .accessDeniedHandler(customAccessDeniedHandler)
                )
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Arrays.asList("*")); // Cho phép tất cả
        configuration.setAllowedMethods(Arrays.asList("*")); // Tất cả methods
        configuration.setAllowedHeaders(Arrays.asList("*")); // Tất cả headers
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
