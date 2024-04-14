package com.example.ecommerce.ports;

import com.example.ecommerce.domain.PriceProduct;

import java.time.LocalDateTime;

public interface CustomPriceProductRepository {
    PriceProduct get(int brandId, int productId, LocalDateTime date);
}
