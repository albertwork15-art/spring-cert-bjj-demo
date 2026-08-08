package tournamentbjj.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

// =========================================================================
// 🎯 CONCEPTOS CLAVE DEL EXAMEN (SPRING SECURITY CONFIGURATION)
// 1. @Configuration: Registra esta clase como fuente de definición de Beans.
// 2. @EnableWebSecurity: Habilita la integración de Spring Security con Spring MVC.
// 3. @EnableMethodSecurity: Activa la seguridad a nivel de métodos (ej. @PreAuthorize).
//    - Reemplaza a las antiguas anotaciones @EnableGlobalMethodSecurity y @EnableGlobalAuthentication.
// =========================================================================
@Configuration
@EnableWebSecurity
@EnableMethodSecurity // Habilita anotaciones como @PreAuthorize("hasRole('ADMIN')") en los métodos
public class SecurityConfig {

    // =========================================================================
    // 🎯 CONCEPTO DEL EXAMEN: SecurityFilterChain (La Cadena de Filtros)
    // - Spring Security funciona basándose en una cadena de filtros (Servlet Filters).
    // - Este Bean define qué peticiones requieren autenticación, cuáles están públicas
    //   y qué método de inicio de sesión se utiliza (ej. Basic Auth o Form Login).
    // =========================================================================
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Deshabilitamos CSRF (Cross-Site Request Forgery) solo para simplificar pruebas en REST.
            // Examen: Por defecto, Spring Security HABILITA la protección CSRF para métodos POST/PUT/DELETE.
            .csrf(csrf -> csrf.disable())
            
            .authorizeHttpRequests(auth -> auth
                // Permite acceso libre a todas las peticiones GET en la API de competidores (Público)
                .requestMatchers(HttpMethod.GET, "/api/competidores/**").permitAll()
                
                // Exige que cualquier petición POST (crear competidor) sea realizada por un ADMIN
                .requestMatchers(HttpMethod.POST, "/api/competidores/**").hasRole("ADMIN")
                
                // El resto de peticiones requieren que el usuario esté autenticado
                .anyRequest().authenticated()
            )
            // Habilita la autenticación HTTP Basic (envío de usuario/contraseña en los headers).
            // Examen: Basic Auth no mantiene sesión (es stateless), ideal para APIs REST sencillas.
            .httpBasic(withDefaults());

        return http.build();
    }

    // =========================================================================
    // 🎯 CONCEPTO DEL EXAMEN: UserDetailsService & PasswordEncoder
    // - UserDetailsService es la interfaz core de Spring Security para cargar datos de usuario.
    // - InMemoryUserDetailsManager es una implementación que guarda usuarios en memoria RAM (para demos/pruebas).
    // - PasswordEncoder es OBLIGATORIO: Spring Security prohíbe almacenar contraseñas en texto plano.
    //   BCryptPasswordEncoder es la implementación estándar recomendada por defecto (usa Hash con sal).
    // =========================================================================
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        // Creamos un usuario común con rol 'USER'
        UserDetails user = User.builder()
                .username("user")
                .password(passwordEncoder.encode("user123")) // Cifra la contraseña con BCrypt
                .roles("USER") // Equivale al Authority "ROLE_USER" (Spring añade el prefijo ROLE_ automáticamente)
                .build();

        // Creamos un administrador con rol 'ADMIN'
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin123"))
                .roles("ADMIN") // Equivale al Authority "ROLE_ADMIN"
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Algoritmo de hash seguro para las contraseñas
    }
}
