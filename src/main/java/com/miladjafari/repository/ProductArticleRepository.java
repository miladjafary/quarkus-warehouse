package com.miladjafari.repository;

import com.miladjafari.entity.ProductArticle;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ProductArticleRepository implements PanacheRepositoryBase<ProductArticle, Long> {

}
