package com.miladjafari.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.wildfly.common.Assert;

import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class InventoryJsonMapperTest {

    @Test
    public void testSuccessMapJsonArticleToArticleDto() {
        String articleJson = "{\"art_id\":\"1\",\"name\":\"leg\",\"stock\":\"12\"}";
        ArticleDto articleDto = JsonbBuilder.create().fromJson(articleJson, ArticleDto.class);
        assertThat(articleDto.getId(), equalTo("1"));
        assertThat(articleDto.getName(), equalTo("leg"));
        assertThat(articleDto.getStock(), equalTo("12"));
    }

    @Test
    public void testSuccessMapInventoryJsonToInventoryDto() {
        String inventoryJson = "{\"inventory\":[{\"art_id\":\"1\",\"name\":\"leg\",\"stock\":\"12\"},{\"art_id\":\"2\",\"name\":\"screw\",\"stock\":\"17\"}]}";
        InventoryDto inventoryDto = JsonbBuilder.create().fromJson(inventoryJson, InventoryDto.class);

        assertThat(inventoryDto, is(notNullValue()));
        assertThat(inventoryDto.getArticles().size(), is(2));

        ArticleDto articleDto1 = inventoryDto.getArticles().get(0);
        assertThat(articleDto1.getId(), equalTo("1"));
        assertThat(articleDto1.getName(), equalTo("leg"));
        assertThat(articleDto1.getStock(), equalTo("12"));

        ArticleDto articleDto2 = inventoryDto.getArticles().get(1);
        assertThat(articleDto2.getId(), equalTo("2"));
        assertThat(articleDto2.getName(), equalTo("screw"));
        assertThat(articleDto2.getStock(), equalTo("17"));
    }

    @Test
    public void testSuccessMapInventoryJsonIfFieldNameIsNotDefined() {
        String inventoryJson = "{\"inventory\":[{\"art_id\":\"1\",\"name\":\"leg\",\"invalidField\":\"12\"}]}";
        InventoryDto inventoryDto =  JsonbBuilder.create().fromJson(inventoryJson, InventoryDto.class);

        assertThat(inventoryDto, is(notNullValue()));
        assertThat(inventoryDto.getArticles().size(), is(1));

        ArticleDto articleDto1 = inventoryDto.getArticles().get(0);
        assertThat(articleDto1.getId(), equalTo("1"));
        assertThat(articleDto1.getName(), equalTo("leg"));
        assertThat(articleDto1.getStock(), nullValue());
    }

    @Test
    public void testFailMapInventoryJsonIfInventoryKeyIsMissing() {
        String inventoryJson = "{\"inventory_key_is_missing\":[{\"art_id\":\"1\",\"name\":\"leg\",\"invalidField\":\"12\"}]}";
        InventoryDto inventoryDto =  JsonbBuilder.create().fromJson(inventoryJson, InventoryDto.class);

        assertThat(inventoryDto, is(notNullValue()));
        assertThat(inventoryDto.getArticles().isEmpty(), is(true));
    }

    @Test
    public void testFailMapInventoryJsonIfThereWouldBeParsError() {
        String inventoryJson = "{\"inventory\":[{\"art_id\":\"1\",\"name\":\"leg\",\"stock\":\"12\"]}";
        Assertions.assertThrows(JsonbException.class, () -> {
            JsonbBuilder.create().fromJson(inventoryJson, InventoryDto.class);
        });
    }

}
