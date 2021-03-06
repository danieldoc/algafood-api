package com.algaworks.algafood.core.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import java.util.Collections;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .formLogin().loginPage("/login")
                .and()
                .authorizeRequests()
                    .antMatchers("/oauth/**").authenticated()
                .and()
                .csrf().disable()
                .cors().and()
                .oauth2ResourceServer()
                .jwt().jwtAuthenticationConverter(jwtAuthenticationConverter());
    }

    private JwtAuthenticationConverter jwtAuthenticationConverter() {
        final var jwtAuthenticationConverter = new JwtAuthenticationConverter();

        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwt -> {
            var authorities = jwt.getClaimAsStringList("authorities");

            if (authorities == null)
                authorities = Collections.emptyList();

            var scopesAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
            var grantedAuthorities = scopesAuthoritiesConverter.convert(jwt);

            grantedAuthorities.addAll(authorities.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toSet()));

            return grantedAuthorities;
        });

        return jwtAuthenticationConverter;
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

//    @Bean
//    public JwtDecoder jwtDecoder() {
//        SecretKey secretKey = new SecretKeySpec("Yy#j69bd@2ow8zMIRx4nTv!woJL!LFAj".getBytes(), "HmacSHA256");
//
//        return NimbusJwtDecoder.withSecretKey(secretKey).build();
//    }
}
