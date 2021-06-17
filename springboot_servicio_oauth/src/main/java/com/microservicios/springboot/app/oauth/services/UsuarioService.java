package com.microservicios.springboot.app.oauth.services;

import com.microservicios.springboot.app.commons.usuarios.models.entity.Usuario;
import com.microservicios.springboot.app.oauth.clients.UsuarioFeignClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

//se usa service por el esteretipo por la semántica
@Service
public class UsuarioService implements UserDetailsService {

    private Logger log = LoggerFactory.getLogger( UsuarioService.class );

    private final UsuarioFeignClient client;

    @Autowired
    public UsuarioService(UsuarioFeignClient client) {
        this.client = client;
    }

    @Override
    //userDetails es un tipo de interfaz que representa un usuario de Spring Security(usuario autenticado)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = client.findByUsername( username );
    //En caso de que no exista el usuario
        if (usuario == null){
            log.error( "Error en el login, no existe el usuario '"+username+"' en el sistema" );
            throw new UsernameNotFoundException( "Error en el login, no existe el usuario '"+username+"' en el sistema" );
        }
        List<GrantedAuthority> authorities = usuario.getRoles()
                .stream()
                .map( role -> new SimpleGrantedAuthority( role.getNombre()))
                //mostrar el nombre del rol del usuario autenticado
                .peek( authority -> log.info( "Role: " + authority.getAuthority() ) )
                .collect(Collectors.toList());
        log.info( "Usuario autenticado: " + username );

        return new User( usuario.getUsername(), usuario.getPassword(), usuario.getEnabled(),
                true, true, true, authorities );
    }
}
