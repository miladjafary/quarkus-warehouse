package com.miladjafari.dto;

import javax.validation.ConstraintViolation;
import java.util.Objects;

public class ValidationErrorDto {
    private ReasonCode code;
    private String param;
    private String description;

    public ReasonCode getCode() {
        return code;
    }

    public String getParam() {
        return param;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ValidationErrorDto that = (ValidationErrorDto) o;
        return code == that.code &&
                Objects.equals(param, that.param) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, param, description);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private ValidationErrorDto instance = new ValidationErrorDto();

        public Builder code(ReasonCode code) {
            instance.code = code;
            return this;
        }
        public Builder param(String param) {
            instance.param = param;
            return this;
        }

        public Builder description(String description) {
            instance.description = description;
            return this;
        }

        public <T> Builder constraintViolation(ConstraintViolation<T> constraintViolation) {
            instance.code = ReasonCode.INVALID_VALUE;
            instance.param = constraintViolation.getPropertyPath().toString();
            instance.description = constraintViolation.getMessage();

            return this;
        }

        public ValidationErrorDto build() {
            return instance;
        }
    }
}
