package com.miladjafari.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class ProductArticle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer amount;
    private LocalDateTime lastUpdate;

    @ManyToOne
    private Product product;

    @ManyToOne
    private Article article;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    @PrePersist
    @PreUpdate
    public void onPersist() {
        lastUpdate = LocalDateTime.now();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final ProductArticle instance = new ProductArticle();

        public Builder product(Product product) {
            instance.product = product;
            return this;
        }

        public Builder amount(String amount) {
            instance.amount = Integer.valueOf(amount);
            return this;
        }

        public Builder article(Article article) {
            instance.article = article;
            return this;
        }

        public Builder lastUpdate(String dateString) {
            instance.lastUpdate = LocalDateTime.parse(dateString);
            return this;
        }

        public ProductArticle build() {
            return instance;
        }
    }
}
