package br.com.dbsystem.wmsauthorizationserver.domain;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Transactional
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public UserDetailsServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {

        final Optional<Usuario> byCodigo = usuarioRepository.findByCodigo(Long.valueOf(username));

        return byCodigo
                .map(AuthUser::new)
                .orElseThrow(() -> new UsernameNotFoundException(username + "não encontrado!"));
    }

}
