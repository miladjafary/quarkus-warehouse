package com.miladjafari.dto;

import javax.json.bind.annotation.JsonbProperty;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

public class ProductDto {

    @NotBlank(message = "name is required")
    private String name;

    @JsonbProperty("contain_articles")
    private List<ProductArticleDto> productArticles = new ArrayList<>();

    public static Builder builder() {
        return new Builder();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ProductArticleDto> getProductArticles() {
        return productArticles;
    }

    public void setProductArticles(List<ProductArticleDto> productArticles) {
        this.productArticles = productArticles;
    }

    public static class Builder {
        private final ProductDto instance = new ProductDto();

        public Builder name(String name) {
            instance.name = name;
            return this;
        }

        public Builder addProductArticle(ProductArticleDto productArticleDto) {
            instance.productArticles.add(productArticleDto);
            return this;
        }

        public ProductDto build() {
            return instance;
        }
    }
}
