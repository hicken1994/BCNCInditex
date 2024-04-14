package com.example.ecommerce.application;

import com.example.ecommerce.domain.PriceProduct;
import com.example.ecommerce.exceptions.InditexParametersNotValid;
import com.example.ecommerce.exceptions.InditexPriceNotFound;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface GetPricePerProduct {

    PriceProduct get(int marcaId, int productoId, LocalDateTime date)
        throws InditexPriceNotFound, InditexParametersNotValid;
}
