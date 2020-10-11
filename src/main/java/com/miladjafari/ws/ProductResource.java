package com.miladjafari.ws;

import com.miladjafari.dto.ProductDto;
import com.miladjafari.service.ProductService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/product/")
public class ProductResource {

    @Inject
    ProductService productService;

    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ProductDto> findAll() {
        return productService.findAllAvailableProducts();
    }

}