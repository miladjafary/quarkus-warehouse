package com.miladjafari.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private BigDecimal price;
    private LocalDateTime lastUpdate;

    @OneToMany(mappedBy = "product")
    private List<ProductArticle> productArticles = new ArrayList<>();

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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) &&
                Objects.equals(name, product.name) &&
                Objects.equals(price, product.price) &&
                Objects.equals(lastUpdate, product.lastUpdate) &&
                Objects.equals(productArticles, product.productArticles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, lastUpdate, productArticles);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final Product instance = new Product();

        public Builder name(String name) {
            instance.name = name;
            return this;
        }

        public Builder price(String price) {
            instance.price = new BigDecimal(price);
            return this;
        }

        public Builder lastUpdate(String dateString) {
            instance.lastUpdate = LocalDateTime.parse(dateString);
            return this;
        }

        public Product build() {
            return instance;
        }
    }
}
