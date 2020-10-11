package com.miladjafari.dto;

import javax.json.bind.annotation.JsonbProperty;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class ProductDto {

    private String id;

    @NotBlank(message = "name is required")
    private String name;

    private Integer quantity = 0;

    @JsonbProperty("contain_articles")
    private List<ProductArticleDto> productArticles = new ArrayList<>();

    public static Builder builder() {
        return new Builder();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public List<ProductArticleDto> getProductArticles() {
        return productArticles;
    }

    public void setProductArticles(List<ProductArticleDto> productArticles) {
        this.productArticles = productArticles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductDto that = (ProductDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(quantity, that.quantity) &&
                Objects.equals(productArticles, that.productArticles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, quantity, productArticles);
    }

    public static class Builder {
        private final ProductDto instance = new ProductDto();

        public Builder id(Long id) {
            instance.id = id.toString();
            return this;
        }

        public Builder name(String name) {
            instance.name = name;
            return this;
        }

        public Builder addProductArticle(ProductArticleDto productArticleDto) {
            instance.productArticles.add(productArticleDto);
            return this;
        }

        public Builder productArticles(List<ProductArticleDto> productArticleList) {
            instance.productArticles = productArticleList;
            instance.quantity = getQuantityByFindingMinOfAvailableArticleInStock(productArticleList);

            return this;
        }

        private Integer getQuantityByFindingMinOfAvailableArticleInStock(List<ProductArticleDto> productArticleList) {
            return productArticleList.stream()
                    .min(Comparator.comparing(ProductArticleDto::getAvailableArticleInStock))
                    .map(ProductArticleDto::getAvailableArticleInStock)
                    .orElse(0);
        }

        public ProductDto build() {
            return instance;
        }
    }
}
