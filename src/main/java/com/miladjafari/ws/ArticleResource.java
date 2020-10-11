package com.miladjafari.ws;

import com.miladjafari.dto.ArticleDto;
import com.miladjafari.service.ArticleService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/article")
public class ArticleResource {

    @Inject
    ArticleService articleService;

    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ArticleDto> findAll() {
        return articleService.findAll();
    }
}