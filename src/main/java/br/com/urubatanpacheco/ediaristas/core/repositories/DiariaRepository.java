package br.com.urubatanpacheco.ediaristas.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.urubatanpacheco.ediaristas.core.models.Diaria;

public interface DiariaRepository extends JpaRepository<Diaria, Long> {

    
}
