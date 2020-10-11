package com.miladjafari.dto;

import javax.json.bind.annotation.JsonbProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class ProductArticleDto {

    @JsonbProperty("art_id")
    @NotBlank(message = "art_id is required")
    @Pattern(regexp = "[0-9]+", message = "art_id must be only digits")
    private String articleId;

    @JsonbProperty("amount_of")
    @NotBlank(message = "amount_of is required")
    @Pattern(regexp = "[0-9]+", message = "amount_of must be only digits")
    private String amount;

    public static Builder builder() {
        return new Builder();
    }

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

        public ProductArticleDto build() {
            return instance;
        }
    }

}
