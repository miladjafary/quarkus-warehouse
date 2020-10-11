package com.miladjafari.dto;

import javax.json.bind.annotation.JsonbProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Objects;

public class ArticleDto {

    @JsonbProperty("art_id")
    @NotBlank(message = "Id is required")
    @Pattern(regexp = "[0-9]+", message = "Id must be only digits")
    private String id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "stock is required")
    @Pattern(regexp = "[0-9]+", message = "stock must be only digits")
    private String stock;

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

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArticleDto that = (ArticleDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(stock, that.stock);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, stock);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final ArticleDto instance = new ArticleDto();

        public Builder id(Long id) {
            instance.id = id.toString();
            return this;
        }

        public Builder id(String id) {
            instance.id = id;
            return this;
        }

        public Builder name(String name) {
            instance.name = name;
            return this;
        }

        public Builder stock(String stock) {
            instance.stock = stock;
            return this;
        }

        public Builder stock(Integer stock) {
            instance.stock = stock.toString();
            return this;
        }

        public ArticleDto build() {
            return instance;
        }
    }
}
