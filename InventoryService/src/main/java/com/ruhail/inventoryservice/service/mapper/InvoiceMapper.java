package com.ruhail.inventoryservice.service.mapper;

import com.ruhail.inventoryservice.domain.*;
import com.ruhail.inventoryservice.service.dto.InvoiceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Invoice and its DTO InvoiceDTO.
 */
@Mapper(componentModel = "spring", uses = {OrderLineMapper.class})
public interface InvoiceMapper extends EntityMapper<InvoiceDTO, Invoice> {

    @Mapping(source = "orderLine.id", target = "orderLineId")
    InvoiceDTO toDto(Invoice invoice);

    @Mapping(source = "orderLineId", target = "orderLine")
    Invoice toEntity(InvoiceDTO invoiceDTO);

    default Invoice fromId(Long id) {
        if (id == null) {
            return null;
        }
        Invoice invoice = new Invoice();
        invoice.setId(id);
        return invoice;
    }
}
