package com.miladjafari.repository;

import com.miladjafari.entity.Article;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ArticleRepository implements PanacheRepositoryBase<Article, Long> {

}
