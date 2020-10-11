package com.miladjafari.service;

import com.miladjafari.dto.ValidationErrorDto;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class CommonValidator {

    @Inject
    Validator validator;

    public <T> List<ValidationErrorDto> validateFormat(T object) {
        Set<ConstraintViolation<T>> validations = validator.validate(object);
        return validations.stream()
                .map(cv -> ValidationErrorDto.builder().constraintViolation(cv).build())
                .collect(Collectors.toList());
    }
}
