package com.miladjafari.repository;

import com.miladjafari.entity.Article;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import java.util.Optional;

@ApplicationScoped
public class ArticleRepository implements PanacheRepositoryBase<Article, Long> {

    public Boolean isExist(Long articleId) {
        Optional<Article> article = findByIdOptional(articleId);
        return article.isPresent();
    }
}
