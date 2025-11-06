package com.smartpass.smartpassbackend.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())   // ❌ desactiva CSRF solo para dev
                .cors(cors -> {})               // ✅ habilita CORS
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()   // ⚠️ abre todo (luego restringimos)
                );

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        // ✅ Dominios permitidos
        config.setAllowedOrigins(List.of(
                "http://localhost:4200",                        // desarrollo local
                "http://192.168.18.3:8080",                     // pruebas locales
                "https://smartpass-front-prd.pages.dev",
                "https://c326ae4d.smartpass-front-prd.pages.dev" // producción en Cloudflare
        ));

        // ✅ Métodos HTTP permitidos
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // ✅ Encabezados permitidos
        config.setAllowedHeaders(List.of("Authorization", "Content-Type", "Accept"));

        // ✅ Permitir envío de credenciales (si usas cookies o auth headers)
        config.setAllowCredentials(true);

        // ✅ Tiempo que el navegador cachea la respuesta del preflight
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }

}
