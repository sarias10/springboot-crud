package com.sergio.curso.springboot.app.springboot_crud.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfig {

    // para que devuelva un componente que se pueda inyectar
    @Bean
    // Password encoder es una interface por lo que no se puede instanciar (CREO)
    PasswordEncoder passwordEncoder() {
        // BCryptPasswordEncoder hereda de PasswordEncoder
        return new BCryptPasswordEncoder(); // si es una clase
    }

    // por defecto spring bloquea todos los endpoints
    // nuevo metodo que va a devolver un security filter chain para dar permisos, denegar permisos
    @Bean
    // se inyecta automaticamente un objeto HttpSecurity (de spring) para dar seguridad a las peticiones http
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests((authz) -> authz
        .requestMatchers("/api/users").permitAll() // solo la ruta /users permite hacer peticiones
        .anyRequest().authenticated())// todas las demas rutas requieren autenticación
        .csrf(config -> config.disable()) // esto es un token, valor secreto unico que genera una aplicación del lado del servidor. para evitar un exploit malicioso que ejecuta comandos no autorizados. evita vulnerabilidad en la aplicación sobretodo en formularios
        .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Configura la política de sesión como STATELESS, es decir, no se guarda sesión en el servidor.
                                                                                                            // Cada request debe venir con su autenticación (por ejemplo, un JWT).
        .build();// construye y devuelve el objeto SecurityFilterChain con toda la configuración
    }
}
