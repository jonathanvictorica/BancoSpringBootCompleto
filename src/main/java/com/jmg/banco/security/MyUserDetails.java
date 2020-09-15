package com.jmg.banco.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetails implements UserDetailsService {

	private UsuarioSecurity usuario;

	public MyUserDetails() {
//		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//		encoder.encode("123"); 
//		Para generar la siguiente clave se uso las anteriores lineas.
		String clave = "$2a$10$FQBfYhANuBUMFwH1UiZ77OFdFLDTVpfCD97fdBODCAi40Iut2m7Fa";
		List<RolesSecurity> roles = new ArrayList<RolesSecurity>();
		roles.add(RolesSecurity.ROLE_ADMIN);
		this.usuario = new UsuarioSecurity(1L, "usrbanco", clave, roles);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		final User user = userRepository.findByUsername(username);
//
//		if (user == null) {
//			throw new UsernameNotFoundException("User '" + username + "' not found");
//		}

		if (!username.equalsIgnoreCase(usuario.getNombre())) {
			throw new UsernameNotFoundException("User '" + username + "' not found");
		}

		return org.springframework.security.core.userdetails.User.withUsername(username)//
				.password(usuario.getPassword())//
				.authorities(usuario.getRoles())//
				.accountExpired(false)//
				.accountLocked(false)//
				.credentialsExpired(false)//
				.disabled(false)//
				.build();
	}

}
