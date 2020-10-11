package com.miladjafari.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ProductsJsonMapperTest {

    @Test
    public void testSuccessMapJsonProductToProductDto() {
        String productJson = "{\"name\":\"Dining Chair\",\"contain_articles\":[{\"art_id\":\"1\",\"amount_of\":\"4\"}]}";
        ProductDto productDto = JsonbBuilder.create().fromJson(productJson, ProductDto.class);
        assertThat(productDto.getName(), equalTo("Dining Chair"));
        assertThat(productDto.getProductArticles().size(), equalTo(1));

        ProductArticleDto productArticle = productDto.getProductArticles().get(0);
        assertThat(productArticle.getArticleId(), equalTo("1"));
        assertThat(productArticle.getAmount(), equalTo("4"));
    }

    @Test
    public void testSuccessMapProductsJsonToProductsDto() {
        String productsJson = "{\"products\":[{\"name\":\"Dining Chair\",\"contain_articles\":[{\"art_id\":\"1\",\"amount_of\":\"4\"}]},{\"name\":\"Dinning Table\",\"contain_articles\":[{\"art_id\":\"1\",\"amount_of\":\"4\"},{\"art_id\":\"2\",\"amount_of\":\"8\"}]}]}";
        ProductListDto productList = JsonbBuilder.create().fromJson(productsJson, ProductListDto.class);

        assertThat(productList, is(notNullValue()));
        assertThat(productList.getProducts().size(), is(2));

        ProductDto productDto1 = productList.getProducts().get(0);
        assertThat(productDto1.getName(), equalTo("Dining Chair"));
        assertThat(productDto1.getProductArticles().size(), equalTo(1));

        ProductArticleDto productArticle1 = productDto1.getProductArticles().get(0);
        assertThat(productArticle1.getArticleId(), equalTo("1"));
        assertThat(productArticle1.getAmount(), equalTo("4"));

        ProductDto productDto2 = productList.getProducts().get(1);
        assertThat(productDto2.getName(), equalTo("Dinning Table"));
        assertThat(productDto2.getProductArticles().size(), equalTo(2));

        ProductArticleDto productArticle21 = productDto2.getProductArticles().get(0);
        assertThat(productArticle21.getArticleId(), equalTo("1"));
        assertThat(productArticle21.getAmount(), equalTo("4"));

        ProductArticleDto productArticle22 = productDto2.getProductArticles().get(1);
        assertThat(productArticle22.getArticleId(), equalTo("2"));
        assertThat(productArticle22.getAmount(), equalTo("8"));
    }

    @Test
    public void testSuccessMapProductJsonIfFieldNameIsNotDefined() {
        String productJson = "{\"name\":\"Dining Chair\",\"contain_articles\":[{\"art_id\":\"1\",\"invalid_field\":\"4\"}]}";
        ProductDto productDto = JsonbBuilder.create().fromJson(productJson, ProductDto.class);
        assertThat(productDto.getName(), equalTo("Dining Chair"));
        assertThat(productDto.getProductArticles().size(), equalTo(1));

        ProductArticleDto productArticle = productDto.getProductArticles().get(0);
        assertThat(productArticle.getArticleId(), equalTo("1"));
        assertThat(productArticle.getAmount(),is(nullValue()));
    }

    @Test
    public void testFailMapProductsJsonIfProductKeyIsMissing() {
        String productsJson = "{\"products_invalid_key\":[{\"name\":\"Dining Chair\",\"contain_articles\":[{\"art_id\":\"1\",\"amount_of\":\"4\"}]},{\"name\":\"Dinning Table\",\"contain_articles\":[{\"art_id\":\"1\",\"amount_of\":\"4\"},{\"art_id\":\"2\",\"amount_of\":\"8\"}]}]}";
        ProductListDto productList = JsonbBuilder.create().fromJson(productsJson, ProductListDto.class);

        assertThat(productList, is(notNullValue()));
        assertThat(productList.getProducts().isEmpty(), is(true));
    }

    @Test
    public void testFailMapInventoryJsonIfThereWouldBeParsError() {
        String productsJson = "{\"products\":[{\"name\":\"Dining Chair\",\"contain_articles\":[{\"art_id\":\"1\",\"amount_of\":\"4\"";
        Assertions.assertThrows(JsonbException.class, () -> {
            JsonbBuilder.create().fromJson(productsJson, ProductListDto.class);
        });
    }
}
