package br.com.dbsystem.wmsauthorizationserver.domain;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

public class JwtCustomClaimsTokenEnhancer implements TokenEnhancer {

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		if (authentication.getPrincipal() instanceof AuthUser) {
			AuthUser authUser = (AuthUser) authentication.getPrincipal();
			
			Map<String, Object> info = new HashMap<String, Object>();
			info.put("usuario_id", authUser.getId());
			info.put("nome_completo", authUser.getFullName());
			info.put("isHabilitado", authUser.getIsHabilitado());
			
			DefaultOAuth2AccessToken oAuth2AccessToken = (DefaultOAuth2AccessToken) accessToken;
			oAuth2AccessToken.setAdditionalInformation(info);
		}
		
		return accessToken;
	}

}
