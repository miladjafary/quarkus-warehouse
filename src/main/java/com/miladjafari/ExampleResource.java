package com.miladjafari;

import com.miladjafari.service.ProductService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/resteasy/hello")
public class ExampleResource {

    @Inject
    ProductService productService;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {

        productService.save();
        return "hello";
    }
}