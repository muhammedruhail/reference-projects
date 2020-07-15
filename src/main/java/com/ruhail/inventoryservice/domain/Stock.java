package com.ruhail.inventoryservice.domain;


import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Stock.
 */
@Entity
@Table(name = "stock")
@Document(indexName = "stock")
public class Stock implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "stock_buying_price")
    private Double stockBuyingPrice;

    @Column(name = "gross_profit")
    private Double grossProfit;

    @Column(name = "units")
    private Integer units;

    @OneToOne    @JoinColumn(unique = true)
    private Product product;

    @OneToOne    @JoinColumn(unique = true)
    private Category category;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getStockBuyingPrice() {
        return stockBuyingPrice;
    }

    public Stock stockBuyingPrice(Double stockBuyingPrice) {
        this.stockBuyingPrice = stockBuyingPrice;
        return this;
    }

    public void setStockBuyingPrice(Double stockBuyingPrice) {
        this.stockBuyingPrice = stockBuyingPrice;
    }

    public Double getGrossProfit() {
        return grossProfit;
    }

    public Stock grossProfit(Double grossProfit) {
        this.grossProfit = grossProfit;
        return this;
    }

    public void setGrossProfit(Double grossProfit) {
        this.grossProfit = grossProfit;
    }

    public Integer getUnits() {
        return units;
    }

    public Stock units(Integer units) {
        this.units = units;
        return this;
    }

    public void setUnits(Integer units) {
        this.units = units;
    }

    public Product getProduct() {
        return product;
    }

    public Stock product(Product product) {
        this.product = product;
        return this;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Category getCategory() {
        return category;
    }

    public Stock category(Category category) {
        this.category = category;
        return this;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Stock stock = (Stock) o;
        if (stock.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), stock.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Stock{" +
            "id=" + getId() +
            ", stockBuyingPrice=" + getStockBuyingPrice() +
            ", grossProfit=" + getGrossProfit() +
            ", units=" + getUnits() +
            "}";
    }
}
