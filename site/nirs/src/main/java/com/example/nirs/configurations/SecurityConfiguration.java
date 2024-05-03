package com.example.nirs.configurations;
import com.example.nirs.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

import static org.springframework.security.config.Customizer.withDefaults;
@Configuration
public class SecurityConfiguration {



    @Bean
    public AuthenticationManager authenticationManager(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(authenticationProvider);
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private RoleRepository roleRepository;

    @Bean
    protected SecurityFilterChain filterChain( HttpSecurity http ) throws Exception {
        http
                .csrf( )
                .disable( )
                .authorizeHttpRequests(
                        authorize -> {
                            try {
                                authorize
                                        .requestMatchers( "/assets/**" ).permitAll( )


                                        .requestMatchers( "/cinema/delete/**" ).hasAuthority( "ADMIN" )
                                        .requestMatchers( "/cinema/add" ).hasAuthority( "ADMIN" )
                                        .requestMatchers( "/cinema/get/**" ).permitAll( )
                                        .requestMatchers( "/cinema/all" ).permitAll( )

                                        .requestMatchers( "/film/get/**" ).permitAll( )
                                        .requestMatchers( "/film/all" ).permitAll( )
                                        .requestMatchers( "/film/add" ).hasAnyAuthority( "ADMIN" )
                                        .requestMatchers( "/film/delete/**" ).hasAnyAuthority( "ADMIN" )
                                        .requestMatchers( "/admin/film/all" ).hasAnyAuthority( "ADMIN", "WORKER" )

                                        .requestMatchers( "/genre/delete/**" ).hasAnyAuthority( "ADMIN" )
                                        .requestMatchers( "/genre/add" ).hasAuthority( "ADMIN" )
                                        .requestMatchers( "/admin/genre/all" ).hasAuthority( "WORKER" )

                                        .requestMatchers( "/hall/add/**" ).hasAuthority( "WORKER" )
                                        .requestMatchers( "/hall/delete/**" ).hasAuthority( "WORKER" )
                                        .requestMatchers( "/admin/hall/all" ).hasAuthority( "WORKER" )
                                        .requestMatchers( "/hall/get/**" ).permitAll( )

                                        .requestMatchers( "/session/delete/**" ).hasAuthority( "WORKER" )
                                        .requestMatchers( "/session/add" ).hasAuthority( "WORKER" )
                                        .requestMatchers( "/admin/session/all" ).hasAuthority( "WORKER" )

                                        .requestMatchers( "/worker/add" ).hasAuthority( "ADMIN" )
                                        .requestMatchers( "/worker/delete/**" ).hasAuthority( "ADMIN" )
                                        .requestMatchers( "/admin/worker/all" ).hasAuthority( "WORKER" )

                                        .requestMatchers( "/admin/users/all" ).hasAuthority( "WORKER" )

                                        .requestMatchers( "/ticket/delete/**" ).hasAuthority( "WORKER" )
                                        .requestMatchers( "/admin/ticket/all" ).hasAuthority( "WORKER" )


                                        .requestMatchers( "/admin/**" ).hasAuthority( "ADMIN" )
                                        .requestMatchers( "/admin/film/all" ).hasAnyAuthority( "ADMIN", "WORKER" )
                                        .requestMatchers( "/admin/menu" ).hasAuthority( "WORKER" )
                                        .requestMatchers( "/data" ).hasAnyAuthority( "ADMIN", "WORKER" )
                                        .requestMatchers( "/register" ).permitAll( )
                                        .requestMatchers( "/cinema/update/**" ).hasAuthority( "ADMIN" )
                                        .requestMatchers( "/genre/update/**" ).hasAuthority( "ADMIN" )
                                        .requestMatchers( "/film/update/**" ).hasAuthority( "ADMIN" )
                                        .requestMatchers( "/hall/update/**" ).hasAuthority( "WORKER" )
                                        .requestMatchers( "/session/update/**" ).hasAuthority( "WORKER" )
                                        .requestMatchers( "/login" ).permitAll( )
                                        .requestMatchers( "/error" ).permitAll( )
                                        .requestMatchers( "/ticket/buy/**" ).authenticated( )
                                        .requestMatchers( "/user/get/**" ).authenticated( )
                                        .requestMatchers( "/" ).permitAll( )
                                        .anyRequest( ).denyAll( )
                                        .and( )
                                        .formLogin( )
                                        .loginPage( "/login" )
                                        .defaultSuccessUrl( "/" )
                                        .permitAll( )
                                        .and( )
                                        .logout( )
                                        .permitAll( )
                                        .logoutSuccessUrl( "/" );
                            } catch( Exception e ) {
                                throw new RuntimeException( e );
                            }
                        }
                );

        return http.build( );
    }

    public void configure( WebSecurity web ) throws Exception {
        web.ignoring( ).requestMatchers( "/resources/**" ).anyRequest( );
    }

}