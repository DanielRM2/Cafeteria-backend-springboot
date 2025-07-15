package org.urban.springbootcafeteria.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.Arrays;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Value("${app.cors.allowed-origins}")
    private String allowedOrigins;
    @Value("${app.cors.allowed-methods}")
    private String allowedMethods;
    @Value("${app.cors.allowed-headers}")
    private String allowedHeaders;
    @Value("${app.cors.allow-credentials}")
    private boolean allowCredentials;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth

                        // *-*-*-* / Endpoints documentación de la API / *-*-*-* //
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/swagger-resources/**",
                                "/webjars/**",
                                "/uploads/**"
                        ).permitAll()

                        // Endpoints públicos generales
                        .requestMatchers(HttpMethod.GET,
                                "/api/productos/listar-populares",
                                "/api/productos/listar-por-categoria",
                                "/api/metodosEntrega/listar",
                                "/api/inventario/*"
                        ).permitAll()

                        // *-*-*-* / Endpoints Clientes / *-*-*-* //
                        // GET: solo lectura
                        .requestMatchers(HttpMethod.GET,
                                "/api/direcciones-cliente/cliente/*",
                                "/api/pedidos/cliente/*",
                                "/api/pagos/preferencia/*/pedido"
                        ).permitAll()
                        // POST: (registro, login, crear dirección, pedido, pago)
                        .requestMatchers(HttpMethod.POST,
                                "/api/cliente/login",
                                "/api/cliente/registro",
                                "/api/direcciones-cliente",
                                "/api/pedidos",
                                "/api/pagos"
                        ).permitAll()
                        // PUT: actualizar cliente o dirección
                        .requestMatchers(HttpMethod.PUT,
                                "/api/cliente/*",
                                "/api/direcciones-cliente/*",
                                "/api/direcciones-cliente/*/predeterminada"
                        ).permitAll()
                        // DELETE: eliminar cliente o dirección
                        .requestMatchers(HttpMethod.DELETE,
                                "/api/cliente/*",
                                "/api/direcciones-cliente/*"
                        ).permitAll()

                        // *-*-*-* / Endpoints Staff / *-*-*-* //
                        .requestMatchers(
                                "/api/**",
                                "/api/staff/login"
                        ).permitAll()

                        // Rutas protegidas solo para ADMINISTRADOR
                        .requestMatchers(
                                "/api/staff/**"
                        ).hasRole("ADMINISTRADOR")

                        .anyRequest().authenticated()
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(allowedOrigins.split(",")));
        configuration.setAllowedMethods(Arrays.asList(allowedMethods.split(",")));
        configuration.setAllowedHeaders(Arrays.asList(allowedHeaders.split(",")));
        configuration.setAllowCredentials(allowCredentials);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
