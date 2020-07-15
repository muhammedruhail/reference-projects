package com.ruhail.inventoryservice.service.mapper;

import com.ruhail.inventoryservice.domain.*;
import com.ruhail.inventoryservice.service.dto.StockDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Stock and its DTO StockDTO.
 */
@Mapper(componentModel = "spring", uses = {ProductMapper.class, CategoryMapper.class})
public interface StockMapper extends EntityMapper<StockDTO, Stock> {

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "category.id", target = "categoryId")
    StockDTO toDto(Stock stock);

    @Mapping(source = "productId", target = "product")
    @Mapping(source = "categoryId", target = "category")
    Stock toEntity(StockDTO stockDTO);

    default Stock fromId(Long id) {
        if (id == null) {
            return null;
        }
        Stock stock = new Stock();
        stock.setId(id);
        return stock;
    }
}
