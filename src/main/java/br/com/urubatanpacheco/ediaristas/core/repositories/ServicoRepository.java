package br.com.urubatanpacheco.ediaristas.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.urubatanpacheco.ediaristas.core.models.Servico;

public interface ServicoRepository extends JpaRepository<Servico, Long> {
     
    
}
