package com.prueba.jsr.api.dto;

import lombok.Data;
import org.springframework.lang.NonNull;

@Data
public class Product {

    @NonNull private int id;
    @NonNull private String name;
    @NonNull private double price;
    @NonNull private boolean availability;

    public Product(){
    }
    public Product(int id, String name, double price, boolean availability){

        this.id = id;
        this.name = name;
        this.price = price;
        this.availability = availability;

    }

}