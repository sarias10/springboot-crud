package com.sergio.curso.springboot.app.springboot_crud.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SpringSecurityConfig {

    // para que devuelva un componente que se pueda inyectar
    @Bean
    // Password encoder es una interface por lo que no se puede instanciar (CREO)
    PasswordEncoder passwordEncoder() {
        // BCryptPasswordEncoder hereda de PasswordEncoder
        return new BCryptPasswordEncoder(); // si es una clase
    }
}
