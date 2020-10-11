package com.miladjafari.service;

import com.miladjafari.dto.ArticleDto;
import com.miladjafari.dto.ReasonCode;
import com.miladjafari.dto.ServiceResponseDto;
import com.miladjafari.dto.ValidationErrorDto;
import com.miladjafari.entity.Article;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;

import static com.miladjafari.Assert.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@QuarkusTest
class ArticleServiceTest {

    private static final Long VALID_ARTICLE_ID = 1L;
    private static final String VALID_ARTICLE_NAME = "leg";
    private static final Integer VALID_ARTICLE_STOCK = 12;

    @Inject
    ArticleService articleService;
    ArticleDto.Builder articleBuilder = ArticleDto.builder();

    @BeforeEach
    public void before() {
        articleBuilder = ArticleDto.builder().id(VALID_ARTICLE_ID).name(VALID_ARTICLE_NAME).stock(VALID_ARTICLE_STOCK);
    }

    @Test
    public void testSuccessCreateArticle() {
        ServiceResponseDto actualResponse = articleService.save(articleBuilder.build());

        assertThat(actualResponse, is(notNullValue()));
        assertThat(actualResponse.getStatus(), equalTo(Response.Status.OK));
        assertThat(actualResponse.getEntity(), equalTo("Created"));
        assertThat(actualResponse.getErrors().isEmpty(), is(true));

        Article actualArticle = articleService.findById(VALID_ARTICLE_ID);

        assertThat(actualArticle, is(notNullValue()));
        assertThat(actualArticle.getId(), equalTo(VALID_ARTICLE_ID));
        assertThat(actualArticle.getName(), equalTo(VALID_ARTICLE_NAME));
        assertThat(actualArticle.getStock(), equalTo(VALID_ARTICLE_STOCK));
    }

    @Test
    public void testSuccessUpdateArticle() {
        final Long ARTICLE_ID = 2L;
        final String NEW_ARTICLE_NAME = "Milad";
        final Integer NEW_ARTICLE_STOCK = 20;
        final Integer EXPECTED_ARTICLE_STOCK = NEW_ARTICLE_STOCK + VALID_ARTICLE_STOCK;

        ArticleDto articleCreateRequest = articleBuilder.id(ARTICLE_ID).build();
        ServiceResponseDto createdArticleResponse = articleService.save(articleCreateRequest);
        assertSuccessCreateResponse(createdArticleResponse);

        ArticleDto articleUpdateRequest = articleBuilder.name(NEW_ARTICLE_NAME).stock(NEW_ARTICLE_STOCK).build();
        ServiceResponseDto actualUpdateResponse = articleService.save(articleUpdateRequest);
        assertSuccessUpdateResponse(actualUpdateResponse);

        Article actualArticle = articleService.findById(ARTICLE_ID);

        assertThat(actualArticle, is(notNullValue()));
        assertThat(actualArticle.getId(), equalTo(ARTICLE_ID));
        assertThat(actualArticle.getName(), equalTo(NEW_ARTICLE_NAME));
        assertThat(actualArticle.getStock(), equalTo(EXPECTED_ARTICLE_STOCK));
    }

    @Test
    public void testFailSaveArticleIfArticleHasInvalidValue() {
        final String INVALID_ID = "invalidId";
        final String EMPTY_NAME = "";
        final String INVALID_STOCK = "invalidStock";

        List<ValidationErrorDto> expectedErrors = new ArrayList<>() {{
            add(ValidationErrorDto.builder().code(ReasonCode.INVALID_VALUE).param("id").description("Id must be only digits").build());
            add(ValidationErrorDto.builder().code(ReasonCode.INVALID_VALUE).param("name").description("Name is required").build());
            add(ValidationErrorDto.builder().code(ReasonCode.INVALID_VALUE).param("stock").description("stock must be only digits").build());
        }};

        ArticleDto invalidArticleDto = articleBuilder.id(INVALID_ID).name(EMPTY_NAME).stock(INVALID_STOCK).build();
        ServiceResponseDto actualResponse = articleService.save(invalidArticleDto);

        assertErrorResponse(actualResponse);
        assertValidationErrors(expectedErrors, actualResponse.getErrors());
    }

    private void assertSuccessCreateResponse(ServiceResponseDto actualResponse) {
        assertSuccessResponse(actualResponse);
        assertThat(actualResponse.getEntity(), equalTo("Created"));
    }

    private void assertSuccessUpdateResponse(ServiceResponseDto actualResponse) {
        assertSuccessResponse(actualResponse);
        assertThat(actualResponse.getEntity(), equalTo("Updated"));
    }
}