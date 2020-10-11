package com.miladjafari.dto;

import javax.json.bind.annotation.JsonbProperty;
import java.util.ArrayList;
import java.util.List;

public class ProductListDto {

    @JsonbProperty("products")
    private List<ProductDto> products = new ArrayList<>();

    public List<ProductDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDto> products) {
        this.products = products;
    }
}
