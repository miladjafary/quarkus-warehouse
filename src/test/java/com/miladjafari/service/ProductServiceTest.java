package com.miladjafari.service;

import com.miladjafari.Assert;
import com.miladjafari.dto.*;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import static com.miladjafari.Assert.*;

@QuarkusTest
@Disabled("Until Quarkus cleanup database problem will be fixed")
class ProductServiceTest {
    private static final String VALID_PRODUCT_NAME = "Dining Chair";

    private static final Long VALID_ARTICLE_ID = 3L;
    private static final String VALID_ARTICLE_NAME = "seat";
    private static final Integer VALID_ARTICLE_STOCK = 10;
    private static final Integer VALID_PRODUCT_ARTICLE_AMOUNT = 4;

    @Inject
    ArticleService articleService;

    @Inject
    ProductService productService;

    ArticleDto.Builder articleBuilder = ArticleDto.builder();
    ProductDto.Builder productBuilder = ProductDto.builder();
    ProductArticleDto.Builder productArticleBuilder = ProductArticleDto.builder();

    @BeforeEach
    public void beforeEach() {
        articleBuilder = ArticleDto.builder().id(VALID_ARTICLE_ID).name(VALID_ARTICLE_NAME).stock(VALID_ARTICLE_STOCK);
        ServiceResponseDto serviceResponseDto = articleService.save(articleBuilder.build());

        productArticleBuilder.articleId(VALID_ARTICLE_ID).amount(VALID_PRODUCT_ARTICLE_AMOUNT);
        productBuilder.name(VALID_PRODUCT_NAME).addProductArticle(productArticleBuilder.build());
    }

    @Test
    public void testSuccessSaveProduct() {
        ServiceResponseDto actualResponse = productService.save(productBuilder.build());
        Assert.assertSuccessResponse(actualResponse);
    }

    @Test
    public void testFailSaveProductIfArticleIdIsNotExist() {
        final Long NOT_FOUND_ARTICLE_ID = 1000L;
        final String VALID_PRODUCT_NAME = "Dining Table";

        List<ValidationErrorDto> expectedErrors = new ArrayList<>() {{
            add(ValidationErrorDto.builder().code(ReasonCode.ARTICLE_NOT_FOUND).param("art_id").description("Article with [1000] id does not exist").build());
        }};

        ProductArticleDto productArticleDto = productArticleBuilder.articleId(NOT_FOUND_ARTICLE_ID).build();
        ProductDto productDto = ProductDto.builder()
                .name(VALID_PRODUCT_NAME)
                .addProductArticle(productArticleDto)
                .build();

        ServiceResponseDto actualResponse = productService.save(productDto);
        assertErrorResponse(actualResponse);
        assertValidationErrors(expectedErrors, actualResponse.getErrors());
    }

    @Test
    public void testFailSaveProductIfProductHasInvalidValue() {
        final String INVALID_ARTICLE_ID = "invalidArticleName";
        final String INVALID_PRODUCT_NAME = "";
        final String INVALID_AMOUNT = "invalidAmount";

        List<ValidationErrorDto> expectedErrors = new ArrayList<>() {{
            add(ValidationErrorDto.builder().code(ReasonCode.INVALID_VALUE).param("articleId").description("art_id must be only digits").build());
            add(ValidationErrorDto.builder().code(ReasonCode.INVALID_VALUE).param("amount").description("amount_of must be only digits").build());
            add(ValidationErrorDto.builder().code(ReasonCode.INVALID_VALUE).param("name").description("name is required").build());
        }};

        ProductArticleDto productArticleDto = productArticleBuilder.articleId(INVALID_ARTICLE_ID)
                .amount(INVALID_AMOUNT)
                .build();

        ProductDto productDto = ProductDto.builder()
                .name(INVALID_PRODUCT_NAME)
                .addProductArticle(productArticleDto)
                .build();

        ServiceResponseDto actualResponse = productService.save(productDto);
        assertErrorResponse(actualResponse);
        assertValidationErrors(expectedErrors, actualResponse.getErrors());
    }

    @Test
    public void testFailSaveProductIfProductNameIsExist() {
        final String DUPLICATE_PRODUCT_NAME = "Dining Chair Duplicate";

        String expectedMessage = String.format("Product with [%s] name is already exist", DUPLICATE_PRODUCT_NAME);
        List<ValidationErrorDto> expectedErrors = new ArrayList<>() {{
            add(ValidationErrorDto.builder().code(ReasonCode.PRODUCT_IS_EXIST).param("name").description(expectedMessage).build());
        }};

        ProductDto productDto = productBuilder.name(DUPLICATE_PRODUCT_NAME).build();
        ServiceResponseDto actualResponse = productService.save(productDto);
        assertSuccessResponse(actualResponse);

        actualResponse = productService.save(productDto);
        assertErrorResponse(actualResponse);
        assertValidationErrors(expectedErrors, actualResponse.getErrors());
    }
}