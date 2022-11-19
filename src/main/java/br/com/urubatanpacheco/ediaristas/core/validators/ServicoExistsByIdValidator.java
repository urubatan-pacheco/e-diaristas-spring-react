package br.com.urubatanpacheco.ediaristas.core.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.urubatanpacheco.ediaristas.core.repositories.ServicoRepository;

@Component
public class ServicoExistsByIdValidator implements ConstraintValidator<ServicoExistsById, Long> {

    @Autowired
    private ServicoRepository repository;

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        return repository.existsById(value);
    }
}
