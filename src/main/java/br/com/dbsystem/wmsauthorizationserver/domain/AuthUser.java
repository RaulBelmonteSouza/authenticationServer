package br.com.dbsystem.wmsauthorizationserver.domain;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;

@Getter
public class AuthUser extends User {

	private static final long serialVersionUID = 1l;

	private Long id;
	private String fullName;
	private Boolean isHabilitado;
	
	public AuthUser(Usuario usuario) {
		super(usuario.getCodigo().toString(), usuario.getSenha(), createAuthorities(usuario.getCargos()));
		this.fullName = usuario.getNome();
		this.id = usuario.getId();
		this.isHabilitado = usuario.isHabilitado();
	}

	private static List<? extends GrantedAuthority> createAuthorities(Set<Cargo> cargos) {
		return getPriviledges(cargos).stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
	}

	private static List<String> getPriviledges(Set<Cargo> cargos) {
		return cargos.stream().map(Cargo::getPrivilegios).flatMap(Collection::stream).distinct()
				.map(Privilegio::getAuthority).collect(Collectors.toList());
	}

}
