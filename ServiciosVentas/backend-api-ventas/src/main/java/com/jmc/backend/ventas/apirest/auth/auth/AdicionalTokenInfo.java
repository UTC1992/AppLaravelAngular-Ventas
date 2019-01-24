package com.jmc.backend.ventas.apirest.auth.auth;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import com.jmc.backend.ventas.apirest.models.entity.Role;
import com.jmc.backend.ventas.apirest.models.entity.Usuario;
import com.jmc.backend.ventas.apirest.models.services.Interfaces.IUsuarioService;

@Component
public class AdicionalTokenInfo implements  TokenEnhancer{
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		Usuario usuarioAuth=usuarioService.findByUsername(authentication.getName());
		Map<String, Object> info= new HashMap<>();
		info.put("nombre_usuario", usuarioAuth.getNombre());
		info.put("user_id", usuarioAuth.getId());
		info.put("apellido_usuario", usuarioAuth.getApellido());
		info.put("email", usuarioAuth.getEmail());
		info.put("empresa", usuarioAuth.getIdEmpresa());
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
		return accessToken;
	}

}
