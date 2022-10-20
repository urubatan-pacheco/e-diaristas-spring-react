package br.com.urubatanpacheco.ediaristas.core.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.urubatanpacheco.ediaristas.core.models.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findByCpf(String cpf);

    Optional<Usuario> findByChavePix(String chavePix);

    Page<Usuario> findByCidadesAtendidasCodigoIbge(String codigoIbge, Pageable pageable);

    Boolean existsByCidadesAtendidasCodigoIbge(String codigoIbge);

    @Query(
        """
            SELECT 
                AVG(u.reputacao)
            FROM     
                Usuario u
            WHERE
                u.tipoUsuario = br.com.urubatanpacheco.ediaristas.core.enums.TipoUsuario.DIARISTA
        """
    )
    Double getMediaRuputacaoDiarista();


    default Boolean isCpfJaCadastrado(Usuario usuario){
        if (usuario.getCpf() == null) {
            return false;
        }

        return findByCpf(usuario.getCpf())
            .map(usuarioEncontrado -> !usuarioEncontrado.getId().equals(usuario.getId()))
            .orElse(false);

    }

    default Boolean isChavePixJaCadastrado(Usuario usuario){
        if (usuario.getChavePix() == null) {
            return false;
        }

        return findByChavePix(usuario.getChavePix())
            .map(usuarioEncontrado -> !usuarioEncontrado.getId().equals(usuario.getId()))
            .orElse(false);

    }

    default Boolean isEmailJaCadastrado(Usuario usuario) {
        if (usuario.getEmail() == null) {
            return false;
        }

        return findByEmail(usuario.getEmail())
            .map(usuarioEncontrado -> !usuarioEncontrado.getId().equals(usuario.getId()))
            .orElse(false);

    }

}
