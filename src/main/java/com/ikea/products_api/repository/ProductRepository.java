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
        addProduct(new Product(null, "Vågstranda", "Bed mattresses", "Vågstranda 160x200", 2999.99, "https://www.ikea.com/se/sv/images/products/vagstranda-pocketresarmadrass-fast-ljusbla__1150860_pe884902_s5.jpg?f=xl"));
        addProduct(new Product(null, "Tustna", "Bed mattresses", "Tustna 120x200", 2699.99, "https://www.ikea.com/se/sv/images/products/tustna-baeddmadrass-vit__0636884_pe698073_s5.jpg?f=xl"));
        addProduct(new Product(null, "Tuddal", "Bed mattresses", "Tuddal 90x200", 2500, "https://www.ikea.com/se/sv/images/products/tuddal-baeddmadrass-vit__0641074_pe700238_s5.jpg?f=xl"));
        addProduct(new Product(null, "Dunvik", "Continental beds", "Dunvik 120x200", 3655, "https://www.ikea.com/se/sv/images/products/dunvik-kontinentalsaeng-valevag-medium-fast-tuddal-gunnared-beige__0794065_pe765491_s5.jpg?f=xl"));
        addProduct(new Product(null, "Sabovik", "Continental beds", "Sabovik 90x200", 4755, "https://www.ikea.com/se/sv/images/products/saeboevik-kontinentalsaeng-fast-vissle-gra__0891216_pe782251_s5.jpg?f=xl"));
    }

    public Optional<Product> findById(Long id){
       return products.stream().filter(product -> product.getId().equals(id)).findFirst();
    }

    public Product addProduct(Product product){
        if (product.getId() == null) {
            product.setId(counter.getAndIncrement());
        }
        products.add(product);
        return product;
    }

    public List<Product> searchByName(String inputName) {
       return products.stream().filter(product -> matchByName(product.getName(), inputName)).toList();
    }

    private boolean matchByName(String text, String inputText) {
        int distance = calculateDistance(text.toLowerCase(), inputText.toLowerCase());
        return distance <= Math.max(text.length(), inputText.length()) / 3;
    }

    // source: https://github.com/crwohlfeil/damerau-levenshtein/blob/master/src/main/java/com/codeweasel/DamerauLevenshtein.java
    private int calculateDistance(CharSequence source, CharSequence target) {
        if (source == null || target == null) {
            throw new IllegalArgumentException("Parameter must not be null");
        }
        int sourceLength = source.length();
        int targetLength = target.length();
        if (sourceLength == 0) return targetLength;
        if (targetLength == 0) return sourceLength;
        int[][] dist = new int[sourceLength + 1][targetLength + 1];
        for (int i = 0; i < sourceLength + 1; i++) {
            dist[i][0] = i;
        }
        for (int j = 0; j < targetLength + 1; j++) {
            dist[0][j] = j;
        }
        for (int i = 1; i < sourceLength + 1; i++) {
            for (int j = 1; j < targetLength + 1; j++) {
                int cost = source.charAt(i - 1) == target.charAt(j - 1) ? 0 : 1;
                dist[i][j] = Math.min(Math.min(dist[i - 1][j] + 1, dist[i][j - 1] + 1), dist[i - 1][j - 1] + cost);
                if (i > 1 &&
                        j > 1 &&
                        source.charAt(i - 1) == target.charAt(j - 2) &&
                        source.charAt(i - 2) == target.charAt(j - 1)) {
                    dist[i][j] = Math.min(dist[i][j], dist[i - 2][j - 2] + cost);
                }
            }
        }
        return dist[sourceLength][targetLength];
    }
}
