package com.miladjafari.service;

import com.miladjafari.dto.ProductArticleDto;
import com.miladjafari.dto.ProductDto;
import com.miladjafari.dto.ServiceResponseDto;
import com.miladjafari.dto.ValidationErrorDto;
import com.miladjafari.entity.Article;
import com.miladjafari.entity.Product;
import com.miladjafari.entity.ProductArticle;
import com.miladjafari.repository.ProductArticleRepository;
import com.miladjafari.repository.ProductRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class ProductService {

    @Inject
    ProductValidator productValidator;

    @Inject
    ProductRepository productRepository;

    @Inject
    ProductArticleRepository productArticleRepository;

    @Inject
    ArticleService articleService;

    @Transactional
    public ServiceResponseDto save(ProductDto productDto) {
        ServiceResponseDto.Builder responseBuilder = ServiceResponseDto.builder();

        List<ValidationErrorDto> errors = productValidator.validate(productDto);
        if (errors.isEmpty()) {
            persistProduct(productDto);
            responseBuilder.ok();
        } else {
            responseBuilder.badRequest().errors(errors);
        }

        return responseBuilder.build();
    }

    private void persistProduct(ProductDto productDto) {
        Product product = Product.builder().name(productDto.getName()).build();
        productRepository.persist(product);

        saveProductArticles(productDto.getProductArticles(), product);
    }

    private void saveProductArticles(List<ProductArticleDto> productArticleDtoList, Product product) {
        productArticleDtoList.forEach(productArticleDto-> {
            ProductArticle productArticle = ProductArticle.builder()
                    .amount(productArticleDto.getAmount())
                    .article(findArticle(productArticleDto.getArticleId()))
                    .product(product)
                    .build();

            productArticleRepository.persist(productArticle);
        });
    }

    private Article findArticle(String articleId) {
        return articleService.findById(Long.valueOf(articleId));
    }
}
