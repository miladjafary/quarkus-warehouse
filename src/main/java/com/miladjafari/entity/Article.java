package com.miladjafari.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Integer stock;
    private LocalDateTime lastUpdate;

    @OneToMany(mappedBy = "article")
    private List<ProductArticle> productArticles = new ArrayList<>();

    public static Builder builder() {
        return new Builder();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public List<ProductArticle> getProductArticles() {
        return productArticles;
    }

    public void setProductArticles(List<ProductArticle> productArticles) {
        this.productArticles = productArticles;
    }

    @PrePersist
    @PreUpdate
    public void onPersist() {
        lastUpdate = LocalDateTime.now();
    }

    public static class Builder {
        private final Article instance = new Article();

        public Builder name(String name) {
            instance.name = name;
            return this;
        }

        public Builder stock(String stock) {
            instance.stock = Integer.valueOf(stock);
            return this;
        }

        public Builder lastUpdate(String dateString) {
            instance.lastUpdate = LocalDateTime.parse(dateString);
            return this;
        }

        public Article build() {
            return instance;
        }
    }
}
