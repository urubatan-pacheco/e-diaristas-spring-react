package br.com.urubatanpacheco.ediaristas.web.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.urubatanpacheco.ediaristas.core.enums.TipoUsuario;
import br.com.urubatanpacheco.ediaristas.core.exceptions.UsuarioNaoEncontradoException;
import br.com.urubatanpacheco.ediaristas.core.models.Usuario;
import br.com.urubatanpacheco.ediaristas.core.repositories.UsuarioRepository;
import br.com.urubatanpacheco.ediaristas.web.dtos.UsuarioCadastroForm;
import br.com.urubatanpacheco.ediaristas.web.mappers.WebUsuarioMapper;

@Service
public class WebUsuarioService {
    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private WebUsuarioMapper mapper;

    public List<Usuario> buscarTodos() {
        return repository.findAll();
    }

    public Usuario buscarPorId(Long id) {
        return repository.findById(id)
        .orElseThrow(() -> new UsuarioNaoEncontradoException(String.format("Usuário com ID %d não encontrado!", id)));
    }

    public Usuario cadastrar(UsuarioCadastroForm form) {
        var usuario = mapper.toModel(form);

        // todo usuário cadastrado no Sistema Administrativo é do tipo ADMIN
        // todos estes usuários poderão realizar todas as tarefas do sistema administrativo
        usuario.setTipoUsuario(TipoUsuario.ADMIN);

        // TOBEDONE: validar se senha e confirmaçãoSenha são iguais

        return repository.save(usuario);

    }

    public void excluir(Long id) {
        var usuario = buscarPorId(id);
        repository.delete(usuario);
       
    }
}
