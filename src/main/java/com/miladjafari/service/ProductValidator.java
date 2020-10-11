package com.miladjafari.service;

import com.miladjafari.dto.ProductArticleDto;
import com.miladjafari.dto.ProductDto;
import com.miladjafari.dto.ReasonCode;
import com.miladjafari.dto.ValidationErrorDto;
import com.miladjafari.repository.ArticleRepository;
import com.miladjafari.repository.ProductRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class ProductValidator {

    @Inject
    CommonValidator commonValidator;

    @Inject
    ArticleRepository articleRepository;

    @Inject
    ProductRepository productRepository;

    public List<ValidationErrorDto> validate(ProductDto productDto) {
        List<ValidationErrorDto> errors = validateFormat(productDto);
        validateBusinessDataIfErrorsIsEmpty(productDto, errors);

        return errors;
    }

    private List<ValidationErrorDto> validateFormat(ProductDto productDto) {
        List<ValidationErrorDto> errors = commonValidator.validateFormat(productDto);
        productDto.getProductArticles().forEach(productArticleDto -> {
            errors.addAll(commonValidator.validateFormat(productArticleDto));
        });

        return errors;
    }

    private void validateBusinessDataIfErrorsIsEmpty(ProductDto productDto, List<ValidationErrorDto> errors) {
        if (errors.isEmpty()) {
            validateProductName(productDto, errors);

            productDto.getProductArticles().forEach(productArticleDto -> {
                validateArticle(productArticleDto, errors);
            });
        }
    }

    private void validateProductName(ProductDto productDto, List<ValidationErrorDto> errors) {
        if (isProductNameExist(productDto.getName())) {
            errors.add(ValidationErrorDto.builder()
                    .code(ReasonCode.PRODUCT_IS_EXIST)
                    .description(String.format("Product with [%s] name is already exist", productDto.getName()))
                    .param("name")
                    .build());
        }
    }

    private boolean isProductNameExist(String productName) {
        return productRepository.findByName(productName).isPresent();
    }

    private void validateArticle(ProductArticleDto productArticleDto, List<ValidationErrorDto> errors) {
        if (isArticleNotExist(productArticleDto)) {
            errors.add(ValidationErrorDto.builder()
                    .code(ReasonCode.ARTICLE_NOT_FOUND)
                    .description(String.format("Article with [%s] id does not exist", productArticleDto.getArticleId()))
                    .param("art_id")
                    .build());
        }
    }

    private boolean isArticleNotExist(ProductArticleDto productArticleDto) {
        return !articleRepository.isExist(Long.valueOf(productArticleDto.getArticleId()));
    }
}
