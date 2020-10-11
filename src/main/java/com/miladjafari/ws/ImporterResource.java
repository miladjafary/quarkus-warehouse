package com.miladjafari.ws;

import com.miladjafari.dto.InventoryDto;
import com.miladjafari.dto.MultipartBody;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import javax.json.bind.JsonbBuilder;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Path("/importer")
public class ImporterResource {

    @POST
    @Path("/inventory")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.TEXT_PLAIN)
    public String importInventory(@MultipartForm MultipartBody requestBody) {
        String inventoryJsonData = toString(requestBody.getFile());

        InventoryDto inventoryDto = JsonbBuilder.create().fromJson(inventoryJsonData, InventoryDto.class);
        return inventoryJsonData;
    }

    public String toString(InputStream file) {
        return new BufferedReader(new InputStreamReader(file, StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.joining("\n"));
    }
}