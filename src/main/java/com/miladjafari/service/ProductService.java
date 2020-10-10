package com.miladjafari.service;

import com.miladjafari.entity.Article;
import com.miladjafari.entity.Product;
import com.miladjafari.entity.ProductArticle;
import com.miladjafari.repository.ArticleRepository;
import com.miladjafari.repository.ProductArticleRepository;
import com.miladjafari.repository.ProductRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

@ApplicationScoped
public class ProductService {

    @Inject
    ProductArticleRepository productArticleRepository;

    @Inject
    ProductRepository productRepository;

    @Inject
    ArticleRepository articleRepository;

    @Transactional
    public void save() {
        Product product = Product.builder().name("Milad").price("10.0").build();
        Article article = Article.builder().name("Legs").stock("12").build();
        ProductArticle productArticle = ProductArticle.builder().amount("4").product(product).inventory(article).build();

        productRepository.persist(product);
        articleRepository.persist(article);
        productArticleRepository.persist(productArticle);
    }
}
