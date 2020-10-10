package com.miladjafari.dto;

import javax.json.bind.annotation.JsonbProperty;
import java.time.LocalDateTime;

public class ProductArticleDto {

    @JsonbProperty("art_id")
    private String articleId;

    @JsonbProperty("amount_of")
    private String amount;

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
}
