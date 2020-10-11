package com.miladjafari.service;

import com.miladjafari.dto.ArticleDto;
import com.miladjafari.dto.ServiceResponseDto;
import com.miladjafari.dto.ValidationErrorDto;
import com.miladjafari.entity.Article;
import com.miladjafari.repository.ArticleRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class ArticleService {

    @Inject
    ArticleRepository articleRepository;

    @Inject
    CommonValidator commonValidator;

    public Article findById(Long id) {
        return articleRepository.findById(id);
    }

    @Transactional
    public ServiceResponseDto save(ArticleDto articleDto) {
        ServiceResponseDto.Builder responseBuilder = ServiceResponseDto.builder();

        List<ValidationErrorDto> validationErrors = commonValidator.validateFormat(articleDto);
        if (validationErrors.isEmpty()) {
            String result = createOrUpdate(articleDto);
            responseBuilder.ok(result);
        } else {
            responseBuilder.badRequest().errors(validationErrors);
        }

        return responseBuilder.build();
    }

    private String createOrUpdate(ArticleDto articleDto) {
        String result = "Created";
        if (isNotExist(articleDto)) {
            create(articleDto);
        } else {
            update(articleDto);
            result = "Updated";
        }

        return result;
    }

    private boolean isNotExist(ArticleDto articleDto) {
        return !articleRepository.isExist(Long.valueOf(articleDto.getId()));
    }

    private void create(ArticleDto articleDto) {
        Article article = Article.builder().articleDto(articleDto).build();
        articleRepository.getEntityManager().merge(article);
    }

    private void update(ArticleDto articleDto) {
        Article article = articleRepository.findById(Long.valueOf(articleDto.getId()));
        article.setName(articleDto.getName());

        Integer stock = article.getStock() + Integer.parseInt(articleDto.getStock());
        article.setStock(stock);

        articleRepository.persist(article);
    }

    public List<ArticleDto> findAll() {
        return articleRepository.listAll()
                .stream()
                .map(article -> ArticleDto.builder()
                        .name(article.getName())
                        .stock(article.getStock())
                        .id(article.getId())
                        .build()
                )
                .collect(Collectors.toList());
    }

    public void reduceStock(Article article, Integer requiredArticle) {
        Integer inStock = article.getStock();

        int remainInStock = inStock - requiredArticle;
        if (remainInStock > 0) {
            article.setStock(remainInStock);
            articleRepository.persist(article);
        }
    }
}
