package com.ruhail.inventoryservice.domain;


import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A Product.
 */
@Entity
@Table(name = "product")
@Document(indexName = "product")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "product_buying_price")
    private Double productBuyingPrice;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "producr_selling_price")
    private Double producrSellingPrice;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public Product createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Double getProductBuyingPrice() {
        return productBuyingPrice;
    }

    public Product productBuyingPrice(Double productBuyingPrice) {
        this.productBuyingPrice = productBuyingPrice;
        return this;
    }

    public void setProductBuyingPrice(Double productBuyingPrice) {
        this.productBuyingPrice = productBuyingPrice;
    }

    public String getProductName() {
        return productName;
    }

    public Product productName(String productName) {
        this.productName = productName;
        return this;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getProducrSellingPrice() {
        return producrSellingPrice;
    }

    public Product producrSellingPrice(Double producrSellingPrice) {
        this.producrSellingPrice = producrSellingPrice;
        return this;
    }

    public void setProducrSellingPrice(Double producrSellingPrice) {
        this.producrSellingPrice = producrSellingPrice;
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
        Product product = (Product) o;
        if (product.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), product.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Product{" +
            "id=" + getId() +
            ", createdDate='" + getCreatedDate() + "'" +
            ", productBuyingPrice=" + getProductBuyingPrice() +
            ", productName='" + getProductName() + "'" +
            ", producrSellingPrice=" + getProducrSellingPrice() +
            "}";
    }
}
