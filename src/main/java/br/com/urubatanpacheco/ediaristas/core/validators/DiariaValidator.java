package br.com.urubatanpacheco.ediaristas.core.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

import br.com.urubatanpacheco.ediaristas.core.exceptions.ValidacaoException;
import br.com.urubatanpacheco.ediaristas.core.models.Diaria;


@Component
public class DiariaValidator {

    public void validar(Diaria diaria) {
        validarHoraTermini(diaria);
    }

    private void validarHoraTermini(Diaria diaria) {
        var dataAtendimento = diaria.getDataAtendimento();
        var tempoAtendimento = diaria.getTempoAtendimento();

        var dataTermino = dataAtendimento.plusHours(tempoAtendimento);
        var dataLimite = dataAtendimento.withHour(22).withMinute(0).withSecond(0);

        if (dataTermino.isAfter(dataLimite)) {
            var mensagem = "termino ão pode ser depois das 22h";
            var fieldError = new FieldError(diaria.getClass().getName(), "dataAtendimento", diaria.getDataAtendimento(), false, null, null, mensagem);

            throw new ValidacaoException(mensagem, fieldError);
        }

    }


}
