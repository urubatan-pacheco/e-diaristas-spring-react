package br.com.urubatanpacheco.ediaristas.core.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.urubatanpacheco.ediaristas.core.models.Foto;

public interface FotoRepository extends JpaRepository<Foto, Long> {

    Optional<Foto> findByFilename(String filename);
    
}
