package com.miladjafari.service;

import com.miladjafari.dto.ProductArticleDto;
import com.miladjafari.dto.ProductDto;
import com.miladjafari.dto.ReasonCode;
import com.miladjafari.dto.ValidationErrorDto;
import com.miladjafari.entity.Product;
import com.miladjafari.repository.ArticleRepository;
import com.miladjafari.repository.ProductRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public List<ValidationErrorDto> validate(String productId) {
        List<ValidationErrorDto> errors = validateProductIdFormat(productId);
        validateBusinessDataOfProductIdIfErrorIsEmpty(productId, errors);

        return errors;
    }

    private List<ValidationErrorDto> validateProductIdFormat(String productId) {
        List<ValidationErrorDto> errors = new ArrayList<>();

        Optional<String> productIdOptional = Optional.ofNullable(productId);
        productIdOptional.ifPresent(id -> {
            if (!id.matches("[0-9]+")) {
                errors.add(ValidationErrorDto.builder()
                        .code(ReasonCode.INVALID_VALUE)
                        .description("Product id must be only digit")
                        .param("productId")
                        .build());
            }
        });

        if (productIdOptional.isEmpty()) {
            errors.add(ValidationErrorDto.builder()
                    .code(ReasonCode.INVALID_VALUE)
                    .description("Product id must be only digit")
                    .param("productId")
                    .build()
            );
        }

        return errors;
    }

    private void validateBusinessDataOfProductIdIfErrorIsEmpty(String id, List<ValidationErrorDto> errors) {
        if (errors.isEmpty()) {
            Optional<Product> product = productRepository.findByIdOptional(Long.valueOf(id));
            if (product.isEmpty()) {
                errors.add(ValidationErrorDto.builder()
                        .code(ReasonCode.PRODUCT_NOT_FOUND)
                        .description("Product not found")
                        .build());
            }

            validateAbilityToSupplyRequiredArticles(product, errors);
        }
    }

    private void validateAbilityToSupplyRequiredArticles(
            Optional<Product> productOptional,
            List<ValidationErrorDto> errors
    ) {
        productOptional.ifPresent(product -> {
            product.getProductArticles().forEach(productArticle -> {
                Integer requiredArticle = productArticle.getAmount();
                Integer inStock = productArticle.getArticle().getStock();
                int supplyAbility = inStock - requiredArticle;

                if (supplyAbility < 0) {
                    String errorMessage = String.format(
                            "Insufficient article [%s]. Required [%s], in stock [%s]",
                            productArticle.getArticle().getName(),
                            requiredArticle,
                            inStock
                    );

                    errors.add(ValidationErrorDto.builder()
                            .code(ReasonCode.INSUFFICIENT_ARTICLE)
                            .description(errorMessage)
                            .build());
                }
            });
        });
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
