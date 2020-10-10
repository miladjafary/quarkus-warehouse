package com.miladjafari.dto;

import javax.json.bind.annotation.JsonbProperty;
import java.util.ArrayList;
import java.util.List;

public class InventoryDto {

    @JsonbProperty("inventory")
    private List<ArticleDto> articles = new ArrayList<>();

    public List<ArticleDto> getArticles() {
        return articles;
    }

    public void setArticles(List<ArticleDto> articles) {
        this.articles = articles;
    }
}
