package com.ruhail.inventoryservice.service.dto;

import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Product entity.
 */
public class ProductDTO implements Serializable {

    private Long id;

    private Instant createdDate;

    private Double productBuyingPrice;

    private String productName;

    private Double producrSellingPrice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Double getProductBuyingPrice() {
        return productBuyingPrice;
    }

    public void setProductBuyingPrice(Double productBuyingPrice) {
        this.productBuyingPrice = productBuyingPrice;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getProducrSellingPrice() {
        return producrSellingPrice;
    }

    public void setProducrSellingPrice(Double producrSellingPrice) {
        this.producrSellingPrice = producrSellingPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProductDTO productDTO = (ProductDTO) o;
        if (productDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), productDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProductDTO{" +
            "id=" + getId() +
            ", createdDate='" + getCreatedDate() + "'" +
            ", productBuyingPrice=" + getProductBuyingPrice() +
            ", productName='" + getProductName() + "'" +
            ", producrSellingPrice=" + getProducrSellingPrice() +
            "}";
    }
}
