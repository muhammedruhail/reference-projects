package com.ruhail.inventoryservice.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Stock entity.
 */
public class StockDTO implements Serializable {

    private Long id;

    private Double stockBuyingPrice;

    private Double grossProfit;

    private Integer units;

    private Long productId;

    private Long categoryId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getStockBuyingPrice() {
        return stockBuyingPrice;
    }

    public void setStockBuyingPrice(Double stockBuyingPrice) {
        this.stockBuyingPrice = stockBuyingPrice;
    }

    public Double getGrossProfit() {
        return grossProfit;
    }

    public void setGrossProfit(Double grossProfit) {
        this.grossProfit = grossProfit;
    }

    public Integer getUnits() {
        return units;
    }

    public void setUnits(Integer units) {
        this.units = units;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        StockDTO stockDTO = (StockDTO) o;
        if (stockDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), stockDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StockDTO{" +
            "id=" + getId() +
            ", stockBuyingPrice=" + getStockBuyingPrice() +
            ", grossProfit=" + getGrossProfit() +
            ", units=" + getUnits() +
            ", product=" + getProductId() +
            ", category=" + getCategoryId() +
            "}";
    }
}
