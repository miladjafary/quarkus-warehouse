package com.miladjafari.service;

import com.miladjafari.dto.ArticleDto;
import com.miladjafari.dto.ValidationErrorDto;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class ArticleValidator {

    @Inject
    Validator validator;

    public List<ValidationErrorDto> validate(ArticleDto articleDto) {
        List<ValidationErrorDto> errors = validateFormat(articleDto);
        return errors;
    }

    public <T> List<ValidationErrorDto> validateFormat(T stockUpdateRequestDto) {
        Set<ConstraintViolation<T>> validations = validator.validate(stockUpdateRequestDto);
        return validations.stream()
                .map(cv -> ValidationErrorDto.builder().constraintViolation(cv).build())
                .collect(Collectors.toList());
    }
}
