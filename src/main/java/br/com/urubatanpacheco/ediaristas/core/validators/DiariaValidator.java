package br.com.urubatanpacheco.ediaristas.core.validators;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

import br.com.urubatanpacheco.ediaristas.core.exceptions.ValidacaoException;
import br.com.urubatanpacheco.ediaristas.core.models.Diaria;
import br.com.urubatanpacheco.ediaristas.core.repositories.UsuarioRepository;
import br.com.urubatanpacheco.ediaristas.core.services.consultaEndereco.adapters.EnderecoService;
import br.com.urubatanpacheco.ediaristas.core.services.consultaEndereco.exceptions.EnderecoServiceException;



@Component
public class DiariaValidator {

    @Autowired
    private EnderecoService enderecoService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public void validar(Diaria diaria) {
        validarHoraTermini(diaria);
        validarTempoAtendimento(diaria);
        validarPreco(diaria);
        validarCep(diaria);
        validarCodigoIbge(diaria);
        validarDisponibilidade(diaria);
        validarDataAtendimento(diaria);
    }


    private void validarHoraTermini(Diaria diaria) {
        var dataAtendimento = diaria.getDataAtendimento();
        var tempoAtendimento = diaria.getTempoAtendimento();

        var dataTermino = dataAtendimento.plusHours(tempoAtendimento);
        var dataLimite = dataAtendimento.withHour(22).withMinute(0).withSecond(0);

        if (dataTermino.isAfter(dataLimite)) {
            var mensagem = "o termino não pode ser depois das 22h";
            var fieldError = new FieldError(diaria.getClass().getName(), "dataAtendimento", diaria.getDataAtendimento(), false, null, null, mensagem);

            throw new ValidacaoException(mensagem, fieldError);
        }

    }

    private void  validarTempoAtendimento(Diaria diaria) {
        var tempoAtendimento = diaria.getTempoAtendimento();
        var tempoTotal = calcularTempoTotal(diaria);

        if (tempoAtendimento != tempoTotal) {
            var mensagem = "tempo de atendimento informado está incorreto";
            var fieldError = new FieldError(diaria.getClass().getName(), "tempoAtendimento", diaria.getTempoAtendimento(), false, null, null, mensagem);

            throw new ValidacaoException(mensagem, fieldError);
        }        

    }

    private Integer calcularTempoTotal(Diaria diaria) {
        var servico = diaria.getServico();

        Integer tempoTotal = 0;
        tempoTotal += diaria.getQuantidadeQuartos() * servico.getHorasQuarto();
        tempoTotal += diaria.getQuantidadeSalas() * servico.getHorasSala();
        tempoTotal += diaria.getQuantidadeCozinhas() * servico.getHorasCozinha();
        tempoTotal += diaria.getQuantidadeBanheiros() * servico.getHorasBanheiro();
        tempoTotal += diaria.getQuantidadeQuintais() * servico.getHorasQuintal();
        tempoTotal += diaria.getQuantidadeOutros() * servico.getHorasOutros();
        return tempoTotal;
    }

    private void validarPreco(Diaria diaria) {
        var preco =  diaria.getPreco();
        var valorTotal = calcularValorTotal(diaria);

        if (preco.compareTo(valorTotal) != 0) {
            var mensagem = "valor informado está incorreto";
            var fieldError = new FieldError(diaria.getClass().getName(), "preco", diaria.getPreco(), false, null, null, mensagem);

            throw new ValidacaoException(mensagem, fieldError);
        }  
    }


    private BigDecimal calcularValorTotal(Diaria diaria) {
        var servico = diaria.getServico();

        var valorMinimo = servico.getValorMinimo();

        var valorTotal = calcularValorDoComodo(servico.getValorQuarto(), diaria.getQuantidadeQuartos())
            .add(calcularValorDoComodo(servico.getValorSala(),diaria.getQuantidadeSalas()))
            .add(calcularValorDoComodo(servico.getValorCozinha(),diaria.getQuantidadeCozinhas()))
            .add(calcularValorDoComodo(servico.getValorBanheiro(),diaria.getQuantidadeBanheiros()))
            .add(calcularValorDoComodo(servico.getValorQuintal(),diaria.getQuantidadeQuintais()))
            .add(calcularValorDoComodo(servico.getValorOutros(),diaria.getQuantidadeOutros()));

        if (valorTotal.compareTo(valorMinimo) < 0) {
            valorTotal = valorMinimo;
        }

        return valorTotal;
    }


    private BigDecimal calcularValorDoComodo(BigDecimal valorComodo, Integer quantidade) {
        var valor =  valorComodo.multiply(new BigDecimal(quantidade));
        return valor;
    }


    private void validarCep(Diaria diaria) {
        var cep  = diaria.getCep();

        try {
            enderecoService.buscarEnderecoPorCep(cep);
        } catch(EnderecoServiceException exception) {
            var mensagem = exception.getLocalizedMessage();
            var fieldError = new FieldError(diaria.getClass().getName(), "cep", diaria.getCep(), false, null, null, mensagem);

            throw new ValidacaoException(mensagem, fieldError);                
        }
    }

    private void validarCodigoIbge(Diaria diaria) {
        var cep  = diaria.getCep();
        var codigoIbge  = diaria.getCodigoIbge();
        var codigoIbgeValido = enderecoService.buscarEnderecoPorCep(cep).getIbge();

        if (!codigoIbge.equals(codigoIbgeValido)) {
            var mensagem = "código ibge inválido";
            var fieldError = new FieldError(diaria.getClass().getName(), "codigoIbge", diaria.getCodigoIbge(), false, null, null, mensagem);

            throw new ValidacaoException(mensagem, fieldError);                
        }
    }

    private void validarDisponibilidade(Diaria diaria) {
        var codigoIbge = diaria.getCodigoIbge();

        var disponibilidade = usuarioRepository.existsByCidadesAtendidasCodigoIbge(codigoIbge);

        if (!disponibilidade) {
            var mensagem = "não há diariastas que atendam ao CEP informado";
            var fieldError = new FieldError(diaria.getClass().getName(), "cep", diaria.getCep(), false, null, null, mensagem);

            throw new ValidacaoException(mensagem, fieldError);                  
        }
    }


    private void validarDataAtendimento(Diaria diaria) {
        var dataAtendimento =  diaria.getDataAtendimento();
        var dataAtendimentoMinima = LocalDateTime.now().plusHours(48);

        if (dataAtendimento.isBefore(dataAtendimentoMinima)) {
            var mensagem = "a data de atendimento deve ter pelo menos 48h à partir de agora";
            var fieldError = new FieldError(diaria.getClass().getName(), "dataAtendimento", diaria.getDataAtendimento(), false, null, null, mensagem);

            throw new ValidacaoException(mensagem, fieldError);
        }        
    }
 
}
