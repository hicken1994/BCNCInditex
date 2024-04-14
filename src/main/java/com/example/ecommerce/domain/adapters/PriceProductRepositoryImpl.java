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
    private final String TABLE_NAME = "PRICES";

    private final String QUERY_GET_PRICE_PRODUCT = String.format(
            "SELECT * " +
                    "FROM %s \n" +
                    "WHERE Brand_id = ?1 \n" +
                    "AND Product_Id = ?2 \n" +
                    "AND ?3 BETWEEN Start_Date AND End_Date\n" +
                    "ORDER BY priority DESC LIMIT 1",
            TABLE_NAME);

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
                .createNativeQuery(QUERY_GET_PRICE_PRODUCT, PriceProduct.class)
                .setParameter(1, brandId)
                .setParameter(2, productId)
                .setParameter(3, date)
                .getSingleResult();

            return (PriceProduct) pricesList;
        }
    }
