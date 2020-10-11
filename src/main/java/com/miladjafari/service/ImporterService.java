package com.miladjafari.service;

import com.miladjafari.dto.ArticleDto;
import com.miladjafari.dto.ServiceResponseDto;
import com.miladjafari.dto.ValidationErrorDto;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.Response;
import java.util.List;

@ApplicationScoped
public class ImporterService {

    @Inject
    ArticleService articleService;

    public ServiceResponseDto importArticles(List<ArticleDto> articles) {
        ServiceResponseDto.Builder serviceResponseBuilder = ServiceResponseDto.builder().ok();
        JsonArrayBuilder articlesJsonResponseBuilder = Json.createArrayBuilder();

        articles.forEach(article -> {
            ServiceResponseDto serviceResponse = articleService.save(article);
            if (serviceResponse.getStatus().equals(Response.Status.OK)) {
                articlesJsonResponseBuilder.add(serviceResponse.getEntity().toString());
            } else {
                articlesJsonResponseBuilder.add(convertJson(serviceResponse.getErrors()));
                serviceResponseBuilder.badRequest();
            }
        });

        JsonArray articlesJsonResponse = articlesJsonResponseBuilder.build();
        return serviceResponseBuilder.entity(articlesJsonResponse).build();
    }

    private JsonArrayBuilder convertJson(List<ValidationErrorDto> errors) {
        JsonArrayBuilder jsonErrorsBuilder = Json.createArrayBuilder();
        errors.forEach(error -> {
            JsonObjectBuilder errorJson = Json.createObjectBuilder()
                    .add("code", error.getCode().toString())
                    .add("param", error.getParam())
                    .add("description", error.getDescription());
            jsonErrorsBuilder.add(errorJson);
        });

        return jsonErrorsBuilder;
    }
}
