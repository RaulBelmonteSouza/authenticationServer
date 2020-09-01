package br.com.dbsystem.wmsauthorizationserver.core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

//TODO: Mudar o nome dessa classe para WebSecurityConfig e apagar todas as classes de configuração de segurando que estão sendo usadas pelo thymeleaf quando a migração para o angular estiver pronta
@Configuration
@EnableAuthorizationServer
public class ResourceServerConfig extends WebSecurityConfigurerAdapter {

	@Override
	public void configure(final WebSecurity web) {
		web.ignoring().antMatchers(HttpMethod.OPTIONS);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
}
