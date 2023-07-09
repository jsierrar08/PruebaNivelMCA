package com.prueba.jsr.api.dto;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProductTest {

    Product product;
    @Test
    public void product(){
        product = new Product();
        product.setId(1);
        product.getId();
        product.setName("name");
        product.getName();
        product.setPrice(1.8);
        product.getPrice();
        product.setAvailability(false);
        product.isAvailability();
    }
}