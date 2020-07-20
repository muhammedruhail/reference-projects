package com.ruhail.inventoryservice.service.mapper;

import com.ruhail.inventoryservice.domain.*;
import com.ruhail.inventoryservice.service.dto.OrderLineDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity OrderLine and its DTO OrderLineDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OrderLineMapper extends EntityMapper<OrderLineDTO, OrderLine> {



    default OrderLine fromId(Long id) {
        if (id == null) {
            return null;
        }
        OrderLine orderLine = new OrderLine();
        orderLine.setId(id);
        return orderLine;
    }
}
