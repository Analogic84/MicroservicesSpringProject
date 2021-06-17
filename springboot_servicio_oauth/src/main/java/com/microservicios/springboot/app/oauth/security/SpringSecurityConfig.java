package com.microservicios.springboot.app.oauth.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration                     //se hereda de la clase abstracta webSecurity...
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    //inyectar el user Detail Service
    private final UserDetailsService usuarioService;
    @Autowired
    public SpringSecurityConfig(UserDetailsService usuarioService) {
        this.usuarioService = usuarioService;
    }

    //implementación del método que crea componente de Spring, retornamos la instancia
    // y anotamos con Bean para que se guarde en el contenedor de Spring
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(  );
    }

    @Override
    @Autowired
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService( this.usuarioService ).passwordEncoder( passwordEncoder() );
                                                        //usamos BCrypt para encriptar contraseña
    }

    //método que crea componente de Spring
    @Override
    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
}
