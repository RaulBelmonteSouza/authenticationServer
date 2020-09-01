//package br.com.dbsystem.wmsauthorizationserver.domain;
//
//import java.util.Collection;
//import java.util.List;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Service;
//
//@Service
//public class MyUserDetails implements UserDetails {
//
//	private static final long serialVersionUID = 1L;
//
//	private final Long id;
//    private final String realName;
//    private final String username;
//    private final String password;
//    private final boolean enabled;
//    private final Collection<? extends GrantedAuthority> authorities;
//
//    public MyUserDetails(Usuario usuario) {
//        this.id = usuario.getId();
//        this.realName = usuario.getNome();
//        this.username = usuario.getCodigo().toString();
//        this.password = usuario.getSenha();
//        this.enabled = usuario.isHabilitado();
//        this.authorities = createAuthorities(usuario.getCargos());
//    }
//
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return authorities;
//    }
//
//    public Long getId(){ return id; }
//
//    @Override
//    public String getPassword() {
//        return password;
//    }
//
//    @Override
//    public String getUsername() {
//        return username;
//    }
//
//    public String getRealName() {
//        return realName;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return enabled;
//    }
//
//    private List<? extends GrantedAuthority> createAuthorities(Set<Cargo> cargos) {
//        return getPriviledges(cargos)
//                .stream()
//                .map(SimpleGrantedAuthority::new)
//                .collect(Collectors.toList());
//    }
//
//    private List<String> getPriviledges(Set<Cargo> cargos) {
//        return cargos.stream()
//                .map(Cargo::getPrivilegios)
//                .flatMap(Collection::stream)
//                .distinct()
//                .map(Privilegio::getAuthority)
//                .collect(Collectors.toList());
//    }
//    
//}
