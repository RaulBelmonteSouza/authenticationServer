package br.com.dbsystem.wmsauthorizationserver.core;

import java.security.KeyPair;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
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
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import br.com.dbsystem.wmsauthorizationserver.domain.JwtCustomClaimsTokenEnhancer;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private UserDetailsService userDetailsService;
    
    @Value("${wms.jwt.secret}")
    private String secret;
    
    @Autowired
    private JwtKeyStoreProperties jwtKeyStoreProperties;
    
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
	                .withClient("wms-angular")
	                .secret(passwordEncoder.encode("wms-angular-password"))//$2y$12$VzKeYUBFkqSffJD2bU1EQuoEtgh4EwE2mWCvegYIB8ILizgKZBvrm
	                .authorizedGrantTypes("password", "refresh_token")
	                .scopes("write", "read")
	                .accessTokenValiditySeconds(1 * 60 * 60)//uma hora
	                .refreshTokenValiditySeconds(30 * 24 * 60 * 60) //30 dias
        		.and()
        			.withClient("1478963")
        			.secret(passwordEncoder.encode("V)gh=)e.$7`Rm&YQ"))
        			.authorizedGrantTypes("client_credentials")
        			.scopes("write", "read")
        			.accessTokenValiditySeconds(1 * 60 * 60)//uma hora
    			.and()
    				.withClient("wms-angular-client")
    				.secret(passwordEncoder.encode(""))
    				.authorizedGrantTypes("authorization_code")
    				.scopes("write", "read")
    				.redirectUris("http://localhost");
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.checkTokenAccess("permitAll()")
        .tokenKeyAccess("permitAll()")
        .allowFormAuthenticationForClients();
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    	getTokenEnhancer();
        
    	endpoints.authenticationManager(authenticationManager)
        	.userDetailsService(userDetailsService)
        	.reuseRefreshTokens(false)
        	.accessTokenConverter(jwtAccessTokenConverter())
        	.tokenEnhancer(getTokenEnhancer())
        	.approvalStore(approvalStore(endpoints.getTokenStore()))
        	.tokenGranter(tokenGranter(endpoints));
    }

	private TokenEnhancerChain getTokenEnhancer() {
		TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
		enhancerChain.setTokenEnhancers(
				Arrays.asList(new JwtCustomClaimsTokenEnhancer(), jwtAccessTokenConverter()));
		return enhancerChain;
	}
    
    private ApprovalStore approvalStore(TokenStore tokenStore) {
    	TokenApprovalStore approvalStore = new TokenApprovalStore();
    	approvalStore.setTokenStore(tokenStore);
    	
    	return approvalStore;
    }
    
    private TokenGranter tokenGranter(AuthorizationServerEndpointsConfigurer endpoints) {
		PkceAuthorizationCodeTokenGranter pkceAuthorizationCodeTokenGranter = new PkceAuthorizationCodeTokenGranter(endpoints.getTokenServices(),
				endpoints.getAuthorizationCodeServices(), endpoints.getClientDetailsService(),
				endpoints.getOAuth2RequestFactory());
		
		List<TokenGranter> granters = Arrays.asList(pkceAuthorizationCodeTokenGranter, endpoints.getTokenGranter());
		
		return new CompositeTokenGranter(granters);
	}
    
    @Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter() {
		JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
		
		ClassPathResource jksResource = new ClassPathResource(jwtKeyStoreProperties.getPath());
		String keyStorePass = jwtKeyStoreProperties.getPassword();
		String keyPairAlias = jwtKeyStoreProperties.getKeypairAlias();
		
		KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(jksResource, keyStorePass.toCharArray());
		 KeyPair keyPair = keyStoreKeyFactory.getKeyPair(keyPairAlias);
		
		jwtAccessTokenConverter.setKeyPair(keyPair);
		
		return jwtAccessTokenConverter;
	}
}
