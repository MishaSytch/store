package store.backend.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import store.backend.security.filter.JwtAuthenticationFilter;
import store.backend.security.role.Role;
import store.backend.security.service.CustomerService;

import java.util.Arrays;
import java.util.Collections;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomerService customerService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                // Своего рода отключение CORS (разрешение запросов со всех доменов)
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration corsConfiguration = new CorsConfiguration();
                    corsConfiguration.setAllowedOriginPatterns(Collections.singletonList("*"));
                    corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                    corsConfiguration.setAllowedHeaders(Collections.singletonList("*"));
                    corsConfiguration.setAllowCredentials(true);

                    return corsConfiguration;
                }))
                // Настройка доступа к конечным точкам
                .authorizeHttpRequests(request -> request
                        // Можно указать конкретный путь, * - 1 уровень вложенности, ** - любое количество уровней вложенности
                        .mvcMatchers(HttpMethod.POST, "/auth/sign-up/**", "/auth/sign-in/**").permitAll()

                        .mvcMatchers(HttpMethod.DELETE, "/product/**").hasRole(Role.ADMIN.name())
                        .mvcMatchers(HttpMethod.POST, "/product/**").hasRole(Role.ADMIN.name())
                        .mvcMatchers(HttpMethod.PUT, "/product/**").hasRole(Role.ADMIN.name())

                        .mvcMatchers(HttpMethod.DELETE, "/category/**").hasRole(Role.ADMIN.name())
                        .mvcMatchers(HttpMethod.POST, "/category/**").hasRole(Role.ADMIN.name())
                        .mvcMatchers(HttpMethod.PUT, "/category/**").hasRole(Role.ADMIN.name())

                        .mvcMatchers("/image/**").hasRole(Role.ADMIN.name())

                        .mvcMatchers("/price/**").hasRole(Role.ADMIN.name())

                        .mvcMatchers("/sku/**").hasRole(Role.ADMIN.name())

                        .mvcMatchers( "/account/customer/**").hasRole(Role.CUSTOMER.name())

                        .mvcMatchers( "/order/**").hasRole(Role.ADMIN.name())
                        .mvcMatchers(HttpMethod.GET, "/order/**").hasRole(Role.CUSTOMER.name())

                        .anyRequest().authenticated())
                .sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customerService.userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }

}
