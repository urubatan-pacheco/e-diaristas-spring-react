package br.com.urubatanpacheco.ediaristas.core.services.consultaEndereco.providers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.urubatanpacheco.ediaristas.core.services.consultaEndereco.adapters.EnderecoService;
import br.com.urubatanpacheco.ediaristas.core.services.consultaEndereco.dtos.EnderecoResponse;
import br.com.urubatanpacheco.ediaristas.core.services.consultaEndereco.exceptions.EnderecoServiceException;

@Service
public class ViaCepService implements EnderecoService {

    private static final String URL_TEMPLATE = "http://viacep.com.br/ws/{cep}/json/";

    private final RestTemplate restTemplate =  new RestTemplate();

    @Override
    public EnderecoResponse buscarEnderecoPorCep(String cep) throws EnderecoServiceException {
        var url = UriComponentsBuilder
            .fromUriString(URL_TEMPLATE)
            .buildAndExpand(cep)
            .toString();
        ResponseEntity<EnderecoResponse> response;
        try {
            response = restTemplate.getForEntity(url, EnderecoResponse.class);
        } catch(HttpClientErrorException.BadRequest htmlException) {
            throw new EnderecoServiceException("O CEP informado é inválido!");
        } 

        if (response.getBody().getCep() == null) {
            throw new EnderecoServiceException("O CEP informado não foi encontrado!");
        }
        return response.getBody();
    }
    
}
