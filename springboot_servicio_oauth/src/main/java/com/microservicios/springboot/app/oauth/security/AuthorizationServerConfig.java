package com.microservicios.springboot.app.oauth.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

private final BCryptPasswordEncoder passwordEncoder;
private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthorizationServerConfig(BCryptPasswordEncoder passwordEncoder, AuthenticationManager authenticationManagerBean, AuthenticationManager authenticationManager) {
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    //configuración los permisos que va a tener el endpoint del servidor de config
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        super.configure( security );
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory().withClient( "frontendapp" )
                .secret( passwordEncoder.encode(  "12345") )
        .scopes( "read", "write" )
        .authorizedGrantTypes( "password" )
        .accessTokenValiditySeconds( 3600 )
        .refreshTokenValiditySeconds( 3600 );
        //si quisiera mas clientes front pues con el .and() los agregamos
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager( authenticationManager )
                //storage que guarda los datos del token
        .tokenStore( tokenStore() )
        .accessTokenConverter( accessTokenConverter() );
    }

    public TokenStore tokenStore() {

        return new JwtTokenStore(accessTokenConverter());

    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
      JwtAccessTokenConverter tokenConverter = new JwtAccessTokenConverter();
      tokenConverter.setSigningKey( "algun_codigo_secreto_aeiou" );
              return tokenConverter;

    }
}
