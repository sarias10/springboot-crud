package com.sergio.curso.springboot.app.springboot_crud.validation;

import org.springframework.util.StringUtils;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

// validaciones personalizadas con anotaciones
public class RequiredValidation implements ConstraintValidator<IsRequired, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext arg1) {
        //return (value != null && !value.isEmpty() && !value.isBlank()); // si todo se cumple devuelve true, sino, devuelve false
        return StringUtils.hasText(value); //valida que sea distinto de nulo y distinto de espacios en blanco
    }

}
