package br.com.urubatanpacheco.ediaristas.web.controllers;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.urubatanpacheco.ediaristas.core.exceptions.ValidacaoException;
import br.com.urubatanpacheco.ediaristas.web.dtos.AlterarSenhaForm;
import br.com.urubatanpacheco.ediaristas.web.dtos.FlashMessage;
import br.com.urubatanpacheco.ediaristas.web.dtos.UsuarioCadastroForm;
import br.com.urubatanpacheco.ediaristas.web.dtos.UsuarioEdicaoForm;
import br.com.urubatanpacheco.ediaristas.web.services.WebUsuarioService;

@Controller
@RequestMapping("/admin/usuarios")
public class UsuarioController {

    @Autowired
    private WebUsuarioService service;

    @GetMapping
    public ModelAndView buscarTodos() {
        var modelAndView = new ModelAndView("admin/usuario/lista");
        
        modelAndView.addObject("usuarios", service.buscarTodos() );

        return modelAndView;
    }

    @GetMapping("/cadastrar")
    public ModelAndView cadastrar() {
        var modelAndView = new ModelAndView("admin/usuario/cadastro-form");

        modelAndView.addObject("form", new UsuarioCadastroForm() );
        
        return modelAndView;
    }    

    @PostMapping("/cadastrar")
    public String cadastrar(
        @Valid @ModelAttribute("form") UsuarioCadastroForm form, 
        BindingResult result, 
        RedirectAttributes attrs
    ) {
        if (result.hasErrors()) {
            // cadastro-form.html requires atribute "form", que can recover the previous form value
            return "admin/usuario/cadastro-form";        
        }

        try {
        service.cadastrar(form);
        // FlashMessage atrtribute alert tratado no usuario/lista.html
        attrs.addFlashAttribute("alert", new FlashMessage("alert-success","Usuário cadastrado com sucesso!"));

        } catch(ValidacaoException e) {
            result.addError(e.getFieldError());
            return "admin/usuario/cadastro-form";

        }
    

        return "redirect:/admin/usuarios";
    }    

    @GetMapping("/{id}/editar")
    public ModelAndView editar(@PathVariable Long id, RedirectAttributes attrs) {
        var modelAndView = new ModelAndView("admin/usuario/edicao-form");

        modelAndView.addObject("form", service.buscarFormPorId(id) );
        
        return modelAndView;
    }
     
    @PostMapping("/{id}/editar")
    public String editar( 
        @PathVariable Long id, 
        @Valid @ModelAttribute("form") UsuarioEdicaoForm form, 
        BindingResult result, 
        RedirectAttributes attrs
    ) {
        if (result.hasErrors()) {
            // renderizamos a view form reaproveitando o atributo form da view anterior
            // este form também terá acesso ao result
            return "admin/usuario/edicao-form";
        }

        try {
            service.editar(form, id);
            attrs.addFlashAttribute("alert", new FlashMessage("alert-success","Usuário editado com sucesso!"));
        } catch(ValidacaoException e) {
            result.addError(e.getFieldError());
            return "admin/usuario/edicao-form";
        }


        return "redirect:/admin/usuarios";
    }    

    @GetMapping("/{id}/excluir")
    public String excluir( @PathVariable Long id, RedirectAttributes attrs) {
        service.excluir(id);

        attrs.addFlashAttribute("alert", new FlashMessage("alert-success","Usuário excluido com sucesso!"));

        return "redirect:/admin/usuarios";
    }


    @GetMapping("/alterar-senha")
    public ModelAndView alterarSenha(RedirectAttributes attrs) {
        var modelAndView = new ModelAndView("admin/usuario/alterar-senha");

        modelAndView.addObject("form", new AlterarSenhaForm() );
        
        return modelAndView;
    }

    @PostMapping("/alterar-senha")
    public String alterarSenha(
        @Valid @ModelAttribute("form") AlterarSenhaForm form, 
        BindingResult result, 
        RedirectAttributes attrs, 
        Principal principal
    ) {
        if (result.hasErrors()) {
            // alterar-senha.html requires atribute "form", que can recover the previous form value
            return "admin/usuario/alterar-senha";        
        }

        // obtém o user name do usuário logado que no caso é o E-mail do usuário
        var email = principal.getName();
        try {
        service.alterarSenha(form, email);
        // FlashMessage atrtribute alert tratado no usuario/lista.html
        attrs.addFlashAttribute("alert", new FlashMessage("alert-success","Senha alterada com sucesso!"));

        } catch(ValidacaoException e) {
            result.addError(e.getFieldError());
            return "admin/usuario/alterar-senha";

        }
    

        return "redirect:/admin/usuarios/alterar-senha";
    }   

     
}
