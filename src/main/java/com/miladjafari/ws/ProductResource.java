package com.miladjafari.ws;

import com.miladjafari.dto.ProductDto;
import com.miladjafari.dto.ServiceResponseDto;
import com.miladjafari.service.ProductService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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

    @PUT
    @Path("sell/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response sell(@PathParam("id") String productId) {
        ServiceResponseDto response = productService.sell(productId);
        return response.getJaxRsResponse();
    }
}