package com.example.ecommerce.ports;

import com.example.ecommerce.domain.PriceProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository

    public interface PriceProductRepository extends JpaRepository<PriceProduct, Long>, CustomPriceProductRepository {

}



