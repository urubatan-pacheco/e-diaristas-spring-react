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
@Constraint(validatedBy = IdadeValidator.class)
public @interface Idade {
    String message() default "deve ter entre {min} e {max} anos";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int min() default 0;
    int max() default Integer.MAX_VALUE;

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
        Idade[] value();
    }

}
