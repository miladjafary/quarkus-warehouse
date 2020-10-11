package com.miladjafari.dto;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ServiceResponseDto {

    private Object entity;
    private List<ValidationErrorDto> errors = new ArrayList<>();

    private Response.Status status;
    private Response jaxResponse;

    public Object getEntity() {
        return entity;
    }

    public Response.Status getStatus() {
        return status;
    }

    public List<ValidationErrorDto> getErrors() {
        return errors;
    }

    public Response getJaxRsResponse() {
       return jaxResponse;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceResponseDto that = (ServiceResponseDto) o;
        return Objects.equals(entity, that.entity) &&
                status == that.status &&
                Objects.equals(errors, that.errors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entity, status, errors);
    }

    public static class Builder {
        private ServiceResponseDto instance = new ServiceResponseDto();

        public Builder entity(Object entity) {
            instance.entity = entity;
            return this;
        }

        public Builder status(Response.Status status) {
            instance.status = status;
            return this;
        }

        public Builder ok() {
            status(Response.Status.OK);
            return this;
        }

        public Builder ok(Object entity) {
            ok();
            entity(entity);
            return this;
        }

        public Builder badRequest() {
            status(Response.Status.BAD_REQUEST);
            return this;
        }

        public Builder notFound() {
            status(Response.Status.NOT_FOUND);
            return this;
        }

        public Builder error(ValidationErrorDto error){
            instance.errors.add(error);
            return this;
        }
        public Builder errors(List<ValidationErrorDto> errors) {
            instance.errors = errors;
            return this;
        }

        public void buildJaxRsResponse() {
            Response.ResponseBuilder responseBuilder = Response.status(instance.status);

            if (!instance.errors.isEmpty()) {
                responseBuilder.entity(instance.errors);
            } else if (Optional.ofNullable(instance.entity).isPresent()) {
                responseBuilder.entity(instance.entity);
            }

            instance.jaxResponse = responseBuilder.build();
        }

        public ServiceResponseDto build() {
            buildJaxRsResponse();
            return instance;
        }
    }
}
