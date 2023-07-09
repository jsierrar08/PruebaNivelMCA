package com.prueba.jsr.api.controllers;

import com.prueba.jsr.api.dto.Product;
import com.prueba.jsr.api.services.ApiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/product")
@Cacheable(value="product")
public class ApiController{

    Logger logger = LoggerFactory.getLogger(ApiController.class);
    @Autowired
    ApiService service;

    @Operation(summary="Get products", description = "Get a list of similar products to the given one", tags="Get")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/JSON", array = @ArraySchema(schema = @Schema(implementation = Product.class)))),
            @ApiResponse(responseCode = "404", description = "Product Not found", content = @Content)
    })
    @GetMapping(value = "/{productId}/similar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getSimilarProducts(@PathVariable("productId") String productId) {
        try{
            logger.info(String.format("Searching related products for ID: %s",productId));
            List<Product> list =  this.service.getSimilarProducts(productId);
            return new ResponseEntity<Object>(list, HttpStatus.OK);
        }catch(Exception e) {
            logger.error("There was an error finding products for given ID");
            return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}