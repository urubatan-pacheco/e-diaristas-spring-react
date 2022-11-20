package br.com.urubatanpacheco.ediaristas.core.validators;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = HoraDepoisDeValidator.class)
public @interface HoraDepoisDe {
    String message() default "deve ser entre {min} e {max} horas";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    final static int HORA_MIN = 0;
    final static int HORA_MAX = 23;

    int min() default HORA_MIN;
    int max() default HORA_MAX;

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ 
        ElementType.TYPE,     
        ElementType.FIELD,    
        ElementType.METHOD,    
        ElementType.PARAMETER,    
        ElementType.CONSTRUCTOR,    
//        ElementType.LOCAL_VARIABLE,    
        ElementType.ANNOTATION_TYPE,    
  //      ElementType.PACKAGE,    
  //      ElementType.TYPE_PARAMETER,    
        ElementType.TYPE_USE
        //,ElementType.MODULE
        //,ElementType.RECORD_COMPONENT 
    })
    @Documented
    @interface List {
        HoraDepoisDe[] value();
    }    
}
