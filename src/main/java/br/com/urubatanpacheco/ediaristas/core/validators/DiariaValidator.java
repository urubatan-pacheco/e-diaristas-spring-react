package br.com.urubatanpacheco.ediaristas.core.validators;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

import br.com.urubatanpacheco.ediaristas.core.exceptions.ValidacaoException;
import br.com.urubatanpacheco.ediaristas.core.models.Diaria;



@Component
public class DiariaValidator {

    public void validar(Diaria diaria) {
        validarHoraTermini(diaria);
        validarTempoAtendimento(diaria);
        validarPreco(diaria);
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
            var fieldError = new FieldError(diaria.getClass().getName(), "tempoAtendimento", diaria.getDataAtendimento(), false, null, null, mensagem);

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
            var fieldError = new FieldError(diaria.getClass().getName(), "valor", diaria.getDataAtendimento(), false, null, null, mensagem);

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
}
