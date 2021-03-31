package com.algaworks.algafood.core.security.authorizationserver;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import javax.sql.DataSource;
import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;
import java.util.Arrays;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private static final String SCOPE_READ = "READ";
    private static final String SCOPE_WRITE = "WRITE";

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtKeyStoreProperties jwtKeyStoreProperties;

    @Autowired
    private DataSource dataSource;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        final int UMA_HORA_SEGUNDOS = 60 * 60;
//        final int UM_DIA_SEGUNDOS = UMA_HORA_SEGUNDOS * 24;

        clients.jdbc(dataSource);
//                .inMemory()
//                    .withClient("algafood-web-client")
//                    .secret(passwordEncoder.encode("web123"))
//                    .authorizedGrantTypes("password", "refresh_token")
//                    .scopes(SCOPE_WRITE, SCOPE_READ)
//                    .accessTokenValiditySeconds(UMA_HORA_SEGUNDOS * 6) // 6 horas
//                    .refreshTokenValiditySeconds(UM_DIA_SEGUNDOS * 15) // 15 dias
//
////                http://localhost:8081/oauth/authorize?response_type=code&client_id=foodanalytics&redirect_uri=http://analytics.algafood.com
//
////                PKCE
////                Plain
////                http://localhost:8081/oauth/authorize?response_type=code&client_id=foodanalytics&redirect_uri=http://analytics.algafood.com&code_challenge=teste1234&code_challenge_method=plain
////                SH256
////                http://localhost:8081/oauth/authorize?response_type=code&client_id=foodanalytics&redirect_uri=http://analytics.algafood.com&code_challenge=teste1234&code_challenge_method=s256
//                .and()
//                    .withClient("foodanalytics")
//                    .secret(passwordEncoder.encode(""))
//                    .authorizedGrantTypes("authorization_code")
//                    .scopes(SCOPE_WRITE, SCOPE_READ)
//                    .redirectUris("http://analytics.algafood.com")
//
//                // http://localhost:8081/oauth/authorize?response_type=token&client_id=webadmin&state=abc&redirect_uri=http://web-client.com
//                .and()
//                    .withClient("webadmin")
//                    .authorizedGrantTypes("implicit")
//                    .scopes(SCOPE_WRITE, SCOPE_READ)
//                    .redirectUris("http://web-client.com")
//
//                .and()
//                    .withClient("faturamento")
//                    .secret(passwordEncoder.encode("faturamento123"))
//                    .authorizedGrantTypes("client_credentials")
//                    .scopes(SCOPE_WRITE, SCOPE_READ)
//
//                .and()
//                    .withClient("check-token")
//                    .secret(passwordEncoder.encode("123web"));
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
//        security.checkTokenAccess("isAuthenticated()");
        security.checkTokenAccess("permitAll()")
                .tokenKeyAccess("permitAll()") // libera endpoint para visualizacao da chave publica
                .allowFormAuthenticationForClients();
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        var enhancerChain = new TokenEnhancerChain();
        enhancerChain.setTokenEnhancers(Arrays.asList(new JwtCustomClaimsTokenEnhancer(), jwtAccessTokenConverter()));

        endpoints
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
                .authorizationCodeServices(new JdbcAuthorizationCodeServices(this.dataSource))
                .reuseRefreshTokens(false)
                .accessTokenConverter(jwtAccessTokenConverter())
                .tokenEnhancer(enhancerChain)
                .approvalStore(approvalStore(endpoints.getTokenStore()))
                .tokenGranter(tokenGranter(endpoints));
    }

    private ApprovalStore approvalStore(TokenStore tokenStore) {
        final var approvalStore = new TokenApprovalStore();
        approvalStore.setTokenStore(tokenStore);

        return approvalStore;
    }

    @Bean
    public JWKSet jwkSet() {
        final RSAKey rsaKey = new RSAKey.Builder((RSAPublicKey) keyPair().getPublic())
                .keyUse(KeyUse.SIGNATURE)
                .algorithm(JWSAlgorithm.RS256)
                .keyID("algafood-key-id")
                .build();

        return new JWKSet(rsaKey);
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        var jwtAccessTokenConverter = new JwtAccessTokenConverter();

//        jwtAccessTokenConverter.setSigningKey("Yy#j69bd@2ow8zMIRx4nTv!woJL!LFAj");

        jwtAccessTokenConverter.setKeyPair(keyPair());

        return jwtAccessTokenConverter;
    }

    private KeyPair keyPair() {
        final var keyStorePass = jwtKeyStoreProperties.getPassword();
        final var keyPairAlias = jwtKeyStoreProperties.getKeypairAlias();

        final var keyStoreKeyFactory = new KeyStoreKeyFactory(jwtKeyStoreProperties.getJksLocation(), keyStorePass.toCharArray());
        return keyStoreKeyFactory.getKeyPair(keyPairAlias);
    }

    private TokenGranter tokenGranter(AuthorizationServerEndpointsConfigurer endpoints) {
        var pkceAuthorizationCodeTokenGranter = new PkceAuthorizationCodeTokenGranter(endpoints.getTokenServices(),
                endpoints.getAuthorizationCodeServices(), endpoints.getClientDetailsService(),
                endpoints.getOAuth2RequestFactory());

        var granters = Arrays.asList(
                pkceAuthorizationCodeTokenGranter, endpoints.getTokenGranter());

        return new CompositeTokenGranter(granters);
    }
}
