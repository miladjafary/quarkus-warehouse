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

@ApplicationScoped
public class ArticleService {

    @Inject
    ArticleRepository articleRepository;

    @Inject
    ArticleValidator articleValidator;

    public Article findById(Long id) {
        return articleRepository.findById(id);
    }

    @Transactional
    public ServiceResponseDto save(ArticleDto articleDto) {
        ServiceResponseDto.Builder responseBuilder = ServiceResponseDto.builder();

        List<ValidationErrorDto> validationErrors = articleValidator.validate(articleDto);
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
}
