package br.com.urubatanpacheco.ediaristas.core.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import br.com.urubatanpacheco.ediaristas.core.exceptions.DiariaNaoEncontrada;
import br.com.urubatanpacheco.ediaristas.core.exceptions.UsuarioNaoEncontradoException;
import br.com.urubatanpacheco.ediaristas.core.models.Diaria;
import br.com.urubatanpacheco.ediaristas.core.models.Usuario;
import br.com.urubatanpacheco.ediaristas.core.repositories.DiariaRepository;
import br.com.urubatanpacheco.ediaristas.core.repositories.UsuarioRepository;

@Component
public class SecurityUtils {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private DiariaRepository diariaRepository;

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public String getEmailUsuarioLogado() {
        return getAuthentication().getName();
    }

    public Usuario getUsuarioLogado() {
        var email = getEmailUsuarioLogado();
        return usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new UsuarioNaoEncontradoException(String.format("Usuário com email %s não encontrado", email)));
    }

    public Boolean isClienteDaDiaria(Long id) {
        
        var diaria = buscarDiariaPorId(id);

        var usuarioLogado = getUsuarioLogado();

        System.out.println(String.format("Diaria(%d) Usuario(%d) Diaria.Usuario(%d)",id, usuarioLogado.getId(), diaria.getCliente().getId()));
        System.out.println(String.format("Usuario.getTipo(%s)",usuarioLogado.getTipoUsuario().name()));
        if (!usuarioLogado.isCliente()) {
            return false;
        }

        return diaria.getCliente().equals(usuarioLogado);
    }


    private Diaria buscarDiariaPorId(Long id) {
        var mensagem = String.format("Diária com id %d não encontrada", id);
        return diariaRepository.findById(id).orElseThrow(() -> new DiariaNaoEncontrada(mensagem));
        }     
}

