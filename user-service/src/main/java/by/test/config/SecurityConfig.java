package by.test.config;

import by.test.controller.filters.JwtFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtFilter filter) throws Exception {

        http
            .cors(Customizer.withDefaults())
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .exceptionHandling(exception -> exception
                .authenticationEntryPoint(
                    (request, response, ex) -> response.setStatus(HttpServletResponse.SC_UNAUTHORIZED))
                .accessDeniedHandler(
                    (request, response, ex) -> response.setStatus(HttpServletResponse.SC_FORBIDDEN)))
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/users/**").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/security/token/verification").authenticated()
                .requestMatchers(
                    "/cabinet/registration",
                    "/cabinet/verification",
                    "/cabinet/login").permitAll()
                .requestMatchers(
                    "/cabinet/logout",
                    "/cabinet/change-password",
                    "/cabinet/update",
                    "/cabinet/me").authenticated()
                .anyRequest().authenticated());

        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
