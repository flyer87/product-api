package com.ikea.products_api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    public Long id;
    public String name;
    public String desription;
    public String category;
    public double price;
    public String imageUrl;
}
