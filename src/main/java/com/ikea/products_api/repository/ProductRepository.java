package com.ikea.products_api.repository;

import com.ikea.products_api.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ProductRepository {
    private final List<Product> products = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong(1);

    public ProductRepository() {
        // Add some mock data
        save(new Product(null, "Vågstranda", "Bed mattresses", "Vågstranda 160x200", 2999.99, "https://www.ikea.com/se/sv/images/products/vagstranda-pocketresarmadrass-fast-ljusbla__1150860_pe884902_s5.jpg?f=xl"));
        save(new Product(null, "Tustna", "Bed mattresses", "Tustna 120x200", 2699.99, "https://www.ikea.com/se/sv/images/products/tustna-baeddmadrass-vit__0636884_pe698073_s5.jpg?f=xl"));
        save(new Product(null, "Tuddal", "Bed mattresses", "Tuddal 90x200", 2500, "https://www.ikea.com/se/sv/images/products/tuddal-baeddmadrass-vit__0641074_pe700238_s5.jpg?f=xl"));
        save(new Product(null, "Dunvik", "Continental beds", "Dunvik 120x200", 3655, "https://www.ikea.com/se/sv/images/products/dunvik-kontinentalsaeng-valevag-medium-fast-tuddal-gunnared-beige__0794065_pe765491_s5.jpg?f=xl"));
        save(new Product(null, "Sabovik", "Continental beds", "Sabovik 90x200", 4755, "https://www.ikea.com/se/sv/images/products/saeboevik-kontinentalsaeng-fast-vissle-gra__0891216_pe782251_s5.jpg?f=xl"));
    }

    public Optional<Product> findById(Long id){
       return products.stream().filter(product -> product.getId().equals(id)).findFirst();
    }

    public Product save(Product product){
        if (product.getId() == null) {
            product.setId(counter.getAndIncrement());
        }
        products.add(product);
        return product;
    }
}
