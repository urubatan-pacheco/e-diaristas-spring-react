package br.com.urubatanpacheco.ediaristas.core.validators;


import java.time.LocalDateTime;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class HoraDepoisDeValidator implements ConstraintValidator<HoraDepoisDe, LocalDateTime> {
    private int min;
    private int max;

    
    @Override
    public void initialize(HoraDepoisDe constraintAnnotation) {
        min = constraintAnnotation.min();
        max = constraintAnnotation.max();
        validarParametros();
        ConstraintValidator.super.initialize(constraintAnnotation);
    }


    @Override
    public boolean isValid(LocalDateTime value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // por padrão
        }

        var hora =  value.getHour();
        
        return hora >= min && hora <= max;
    }
    
    private void validarParametros() {
        if (min < HoraDepoisDe.HORA_MIN) {
            throw new IllegalArgumentException("O parâmetro min não pode ser menor que {HoraDepoisDe.HORA_MIN}");
        }
        if (max < HoraDepoisDe.HORA_MIN) {
            throw new IllegalArgumentException("O parâmetro max não pode ser menor que {HoraDepoisDe.HORA_MIN}");
        }
        if (max > HoraDepoisDe.HORA_MAX) {
            throw new IllegalArgumentException("O parâmetro max não pode ser maior que {HoraDepoisDe.HORA_MAX}");
        }
        if (min > HoraDepoisDe.HORA_MAX) {
            throw new IllegalArgumentException("O parâmetro min não pode ser maior que {HoraDepoisDe.HORA_MAX}");
        }
        if (max < min) {
            throw new IllegalArgumentException("O parâmetro max não pode ser menor que o min");
        }

    }

}
