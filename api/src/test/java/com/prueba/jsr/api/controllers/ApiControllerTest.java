package com.prueba.jsr.api.controllers;


import com.prueba.jsr.api.dto.Product;
import com.prueba.jsr.api.services.ApiService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@SpringBootTest
public class ApiControllerTest {

    @InjectMocks
    private ApiController controller;
    @Mock
    ApiService service;

    @Before
    public void setup(){
        initMocks(this);
    }
    @Test
    public void getSimilarProductsOK() {
        when(service.getSimilarProducts(anyString())).thenReturn(new ArrayList<Product>());
        when(service.getSimilarIds(anyString())).thenReturn(new ArrayList<String>());
        when(service.getProductDetail(anyString())).thenReturn(new Product());

        assertEquals(ResponseEntity.ok(new ArrayList<Product>()),controller.getSimilarProducts(anyString()));
    }

    @Test(expected = Exception.class)
    public void getSimilarProductsKO() {
        when(service.getSimilarProducts(anyString())).thenThrow(new Exception());

        assertEquals(ResponseEntity.notFound(),controller.getSimilarProducts(anyString()));
    }
}