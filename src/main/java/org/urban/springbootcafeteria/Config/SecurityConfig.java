package org.urban.springbootcafeteria.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Habilita CORS con la configuración personalizada definida más abajo
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                // Desactiva la protección CSRF (útil en APIs REST sin sesiones/cookies)
                .csrf(csrf -> csrf.disable())

                // Configura autorización para todas las peticiones HTTP
                .authorizeHttpRequests(auth -> auth
                        // Permite todas las solicitudes sin requerir autenticación
                        .anyRequest().permitAll()
                );

        // Construye y devuelve la cadena de filtros de seguridad
        return http.build();
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Define los orígenes permitidos para acceder a la API
        configuration.setAllowedOrigins(List.of(
                "http://localhost:5173",      // React dev server
                "http://127.0.0.1:5173",      // Otra forma de localhost
                "http://localhost:8080"       // El mismo backend si hay redirecciones
        ));

        // Métodos HTTP permitidos
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // Permitir todos los headers en las peticiones
        configuration.setAllowedHeaders(List.of("*"));

        // Permite el envío de cookies o headers de autorización entre frontend y backend
        configuration.setAllowCredentials(true);

        // Asocia la configuración de CORS con todas las rutas del backend
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    // Bean para codificar contraseñas con el algoritmo BCrypt
    @Bean
    public BCryptPasswordEncoder codificadorContrasena() {
        return new BCryptPasswordEncoder();
    }
}