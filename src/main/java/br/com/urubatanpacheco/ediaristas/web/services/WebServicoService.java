package br.com.urubatanpacheco.ediaristas.web.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.urubatanpacheco.ediaristas.core.exceptions.ServicoNaoEncontradoException;
import br.com.urubatanpacheco.ediaristas.core.models.Servico;
import br.com.urubatanpacheco.ediaristas.core.repositories.ServicoRepository;
import br.com.urubatanpacheco.ediaristas.web.dtos.ServicoForm;
import br.com.urubatanpacheco.ediaristas.web.mappers.WebServicoMapper;

@Service
public class WebServicoService {
    
    @Autowired
    private ServicoRepository repository;

    public List<Servico> buscarTodos() {
        return repository.findAll();
    }

    public Servico cadastrar(ServicoForm form) {
        var model = WebServicoMapper.toModel(form);

        return repository.save(model);
    }

    public Servico buscarPorId(Long id) {
        var modelOptional = repository.findById(id);

        if (modelOptional.isPresent()) {
            return modelOptional.get();
        }

        var message =  String.format("Servico com ID %d não encontrado", id);
        throw new ServicoNaoEncontradoException(message);
    }

    public Servico editar(ServicoForm form, Long id) {
        var servico = buscarPorId(id);

        var model = WebServicoMapper.toModel(form);
        model.setId(servico.getId());

        return repository.save(model);
    }

    public void excluir(Long id) {
        var model = buscarPorId(id);

        repository.delete(model);
    }


}
