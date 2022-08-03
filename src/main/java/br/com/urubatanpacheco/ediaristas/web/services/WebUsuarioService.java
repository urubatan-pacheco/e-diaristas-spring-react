package br.com.urubatanpacheco.ediaristas.web.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;

import br.com.urubatanpacheco.ediaristas.core.enums.TipoUsuario;
import br.com.urubatanpacheco.ediaristas.core.exceptions.EmailExistenteException;
import br.com.urubatanpacheco.ediaristas.core.exceptions.SenhasNaoConferemException;
import br.com.urubatanpacheco.ediaristas.core.exceptions.UsuarioNaoEncontradoException;
import br.com.urubatanpacheco.ediaristas.core.models.Usuario;
import br.com.urubatanpacheco.ediaristas.core.repositories.UsuarioRepository;
import br.com.urubatanpacheco.ediaristas.web.dtos.UsuarioCadastroForm;
import br.com.urubatanpacheco.ediaristas.web.dtos.UsuarioEdicaoForm;
import br.com.urubatanpacheco.ediaristas.web.mappers.WebUsuarioMapper;

@Service
public class WebUsuarioService {
    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private WebUsuarioMapper mapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Usuario> buscarTodos() {
        return repository.findAll();
    }

    public Usuario buscarPorId(Long id) {
        return repository.findById(id)
        .orElseThrow(() -> new UsuarioNaoEncontradoException(String.format("Usuário com ID %d não encontrado!", id)));
    }

    public UsuarioEdicaoForm buscarFormPorId(Long id) {
        var model = buscarPorId(id);
        return mapper.toForm(model);
    }

    public Usuario cadastrar(UsuarioCadastroForm form) {
        // validar se senha e confirmaçãoSenha são iguais
        var senha = form.getSenha();
        var confirmacaoSenha = form.getConfirmacaoSenha();

        if (senha.equals(confirmacaoSenha) == false) {
            var mensagem = "Os dois campos de senhas não conferem!";
            var fieldError = new FieldError(form.getClass().getName(), "confirmacaoSenha", form.getConfirmacaoSenha(), false, null, null, mensagem);

            throw new SenhasNaoConferemException(mensagem, fieldError);
        }

        var usuario = mapper.toModel(form);

        // encriptar a senha do usuário
        usuario.setSenha( passwordEncoder.encode(usuario.getSenha()));

        // todo usuário cadastrado no Sistema Administrativo é do tipo ADMIN
        // todos estes usuários poderão realizar todas as tarefas do sistema administrativo
        usuario.setTipoUsuario(TipoUsuario.ADMIN);

        // validar emails não duplicados
        validarCamposUnicos(usuario);        
        
        return repository.save(usuario);

    }

    public Usuario editar(UsuarioEdicaoForm form, Long id) {
        var usuario = buscarPorId(id);  

        
        var model = mapper.toModel(form);
        // A edição não altera id, senha e tipoUsuário que devem ser mantidos inalterados
        model.setId(usuario.getId());
        model.setSenha(usuario.getSenha());
        model.setTipoUsuario(usuario.getTipoUsuario());

        // validar emails não duplicados
        validarCamposUnicos(model);

        return repository.save(model);
    }

    public void excluir(Long id) {
        var usuario = buscarPorId(id);
        repository.delete(usuario);
       
    }

    private void validarCamposUnicos(Usuario usuario) {
        if (repository.isEmailJaCadastrado(usuario.getEmail(), usuario.getId())) {
            var mensagem = "Já existe um usuário cadastrado com este e-mail!";
            var fieldError = new FieldError(usuario.getClass().getName(), "email", usuario.getEmail(), false, null, null, mensagem);

            throw new EmailExistenteException(mensagem, fieldError);
        }
    }
}
