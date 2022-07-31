package br.com.urubatanpacheco.ediaristas.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.urubatanpacheco.ediaristas.core.models.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
}
