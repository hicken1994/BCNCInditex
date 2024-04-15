package com.example.ecommerce.domain.adapters.dto;


import com.example.ecommerce.application.dto.PriceDto;
import com.example.ecommerce.domain.PriceProduct;
import org.mapstruct.*;


@Mapper(
        componentModel = "spring",
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
                                 nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)

public interface ProductPriceMapper {

    @Mapping(source = "productId", target = "productId")
    @Mapping(source = "brandId", target = "brandId")
    @Mapping(source = "priceList", target = "priceList")
    @Mapping(source = "startDate", target = "startDate")
    @Mapping(source = "endDate", target = "endDate")
    @Mapping(source = "price", target = "price")
    PriceDto priceDto(final PriceProduct priceProduct);

}
