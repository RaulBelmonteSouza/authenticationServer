package br.com.dbsystem.wmsauthorizationserver.core;

import javax.validation.constraints.NotBlank;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import lombok.Setter;

import lombok.Getter;

@Validated
@Component
@ConfigurationProperties("wms.jwt.keystore")
@Getter
@Setter
public class JwtKeyStoreProperties {
	
	@NotBlank
	private String path;
	
	@NotBlank
	private String password;
	
	@NotBlank
	private String keypairAlias;

}
