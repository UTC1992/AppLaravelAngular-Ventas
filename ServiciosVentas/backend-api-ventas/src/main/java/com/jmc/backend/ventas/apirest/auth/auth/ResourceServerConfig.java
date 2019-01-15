package com.jmc.backend.ventas.apirest.auth.auth;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Override
	public void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/clientes").permitAll()
		.antMatchers(HttpMethod.GET, "/api/clientes/{id}").hasAnyRole("USER","ADMIN")
		.antMatchers(HttpMethod.POST, "/api/clientes").hasRole("ADMIN")
		.antMatchers("/api/clientes/**").hasRole("ADMIN")
		.antMatchers(HttpMethod.GET, "/api/empresa/modulos/{id}").hasAnyRole("USER","ADMIN")
		.antMatchers(HttpMethod.POST, "/api/empresa").hasRole("ADMIN")
		.antMatchers(HttpMethod.GET, "/api/empresa/admin/{id}").hasRole("ROOT")
		.antMatchers(HttpMethod.GET, "/auth/usuario/roles/{id}").hasAnyRole("USER","ADMIN")
		.anyRequest().authenticated();
	}

	
}
