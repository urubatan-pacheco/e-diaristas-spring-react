package br.com.urubatanpacheco.ediaristas.core.services.consultaEndereco.adapters;

import br.com.urubatanpacheco.ediaristas.core.services.consultaEndereco.dtos.EnderecoResponse;
import br.com.urubatanpacheco.ediaristas.core.services.consultaEndereco.exceptions.EnderecoServiceException;

public interface EnderecoService {
    EnderecoResponse buscarEnderecoPorCep(String cep) throws EnderecoServiceException;
}
