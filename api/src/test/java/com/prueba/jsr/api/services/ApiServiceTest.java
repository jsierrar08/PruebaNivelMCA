package com.prueba.jsr.api.services;

import com.prueba.jsr.api.dto.Product;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.net.MalformedURLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@SpringBootTest
@TestPropertySource("classpath:application.properties")
public class ApiServiceTest {

    private ApiService service = new ApiService();

    @Before
    public void setup() {
        initMocks(this);
    }

    @Test
    public void getSimilarProducts() {
        assertEquals(new ArrayList<Product>(),service.getSimilarProducts(anyString()));
    }

    @Test
    public void getSimilarIds() {
        assertEquals(new ArrayList<String>(),service.getSimilarIds(anyString()));
    }

    @Test
    public void getProductDetail() {
        assertEquals(new Product(),service.getProductDetail(anyString()));
    }
}