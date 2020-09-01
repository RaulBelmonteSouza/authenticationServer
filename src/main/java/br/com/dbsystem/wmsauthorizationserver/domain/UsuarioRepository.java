package br.com.dbsystem.wmsauthorizationserver.domain;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>, JpaSpecificationExecutor<Usuario> {
	Optional<Usuario> findByCodigo(Long codigo);

	Boolean existsByCodigo(Long codigo);
}