package com.miladjafari.dto;

import com.miladjafari.entity.ProductArticle;

import javax.json.bind.annotation.JsonbProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Objects;

public class ProductArticleDto {

    @JsonbProperty("art_id")
    @NotBlank(message = "art_id is required")
    @Pattern(regexp = "[0-9]+", message = "art_id must be only digits")
    private String articleId;

    @JsonbProperty("amount_of")
    @NotBlank(message = "amount_of is required")
    @Pattern(regexp = "[0-9]+", message = "amount_of must be only digits")
    private String amount;

    @JsonbProperty("in_stock")
    private Integer inStock = 0;

    @JsonbProperty("available_article_in_stock")
    private Integer availableArticleInStock = 0;

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Integer getInStock() {
        return inStock;
    }

    public void setInStock(Integer inStock) {
        this.inStock = inStock;
    }

    public Integer getAvailableArticleInStock() {
        return availableArticleInStock;
    }

    public void setAvailableArticleInStock(Integer availableArticleInStock) {
        this.availableArticleInStock = availableArticleInStock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductArticleDto that = (ProductArticleDto) o;
        return Objects.equals(articleId, that.articleId) &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(inStock, that.inStock) &&
                Objects.equals(availableArticleInStock, that.availableArticleInStock);
    }

    @Override
    public int hashCode() {
        return Objects.hash(articleId, amount, inStock, availableArticleInStock);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final ProductArticleDto instance = new ProductArticleDto();

        public Builder articleId(Long articleId) {
            instance.articleId = articleId.toString();
            return this;
        }

        public Builder articleId(String articleId) {
            instance.articleId = articleId;
            return this;
        }

        public Builder amount(Integer amount) {
            instance.amount = amount.toString();
            return this;
        }

        public Builder amount(String amount) {
            instance.amount = amount;
            return this;
        }

        public Builder productArticle(ProductArticle productArticle) {
            articleId(productArticle.getArticle().getId());
            amount(productArticle.getAmount());

            instance.inStock = productArticle.getArticle().getStock();
            instance.availableArticleInStock = getAmountOfAvailableArticleInStock(productArticle);

            return this;
        }

        private Integer getAmountOfAvailableArticleInStock(ProductArticle productArticle) {
            int amountOfAvailableArticleInStock = 0;

            Integer inStock = productArticle.getArticle().getStock();
            Integer required = productArticle.getAmount();

            if (required <= inStock) {
                amountOfAvailableArticleInStock = inStock / required;
            }

            return amountOfAvailableArticleInStock;
        }

        public ProductArticleDto build() {
            return instance;
        }
    }

}
