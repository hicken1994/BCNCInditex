package com.example.ecommerce.domain.adapters;

import com.example.ecommerce.domain.PriceProduct;
import com.example.ecommerce.ports.CustomPriceProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;

@Repository
@Slf4j
@RequiredArgsConstructor
public class PriceProductRepositoryImpl implements CustomPriceProductRepository {
    @PersistenceContext
    private EntityManager entityManager;
    public static final String TABLE = "PRICES";

    private final String querySelect = String.format(
            "SELECT * ".concat(
                    "FROM %s \n").concat(
                    "WHERE Brand_id = ?1 \n").concat(
                    "AND Product_Id = ?2 \n").concat(
                    "AND ?3 BETWEEN Start_Date AND End_Date\n").concat(
                    "ORDER BY priority DESC LIMIT 1"),
            TABLE);

    public PriceProduct get(int brandId, int productId, LocalDateTime date) {
        try {
            return getProduct(brandId, productId, date);
        } catch (Exception e) {
            log.error("Error when retrieving data for brandId: {}, productId: {}, and Start_Date: {}", brandId, productId, date, e);
            return null;
        }
    }

    public  PriceProduct getProduct(int brandId, int productId, LocalDateTime date) {
        var pricesList = entityManager
                .createNativeQuery(querySelect, PriceProduct.class)
                .setParameter(1, brandId)
                .setParameter(2, productId)
                .setParameter(3, date)
                .getSingleResult();

            return (PriceProduct) pricesList;
        }
    }
