package com.miladjafari.dto;

import com.miladjafari.entity.ProductArticle;

import javax.json.bind.annotation.JsonbProperty;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ProductDto {

    private String name;

    @JsonbProperty("contain_articles")
    private List<ProductArticle> productArticles = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ProductArticle> getProductArticles() {
        return productArticles;
    }

    public void setProductArticles(List<ProductArticle> productArticles) {
        this.productArticles = productArticles;
    }
}
