package com.ruhail.inventoryservice.domain;


import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A OrderLine.
 */
@Entity
@Table(name = "order_line")
@Document(indexName = "orderline")
public class OrderLine implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_date")
    private Instant orderDate;

    @Column(name = "order_status")
    private Boolean orderStatus;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "customer_id")
    private Long customerId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getOrderDate() {
        return orderDate;
    }

    public OrderLine orderDate(Instant orderDate) {
        this.orderDate = orderDate;
        return this;
    }

    public void setOrderDate(Instant orderDate) {
        this.orderDate = orderDate;
    }

    public Boolean isOrderStatus() {
        return orderStatus;
    }

    public OrderLine orderStatus(Boolean orderStatus) {
        this.orderStatus = orderStatus;
        return this;
    }

    public void setOrderStatus(Boolean orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Long getProductId() {
        return productId;
    }

    public OrderLine productId(Long productId) {
        this.productId = productId;
        return this;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public OrderLine customerId(Long customerId) {
        this.customerId = customerId;
        return this;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
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
        OrderLine orderLine = (OrderLine) o;
        if (orderLine.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), orderLine.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OrderLine{" +
            "id=" + getId() +
            ", orderDate='" + getOrderDate() + "'" +
            ", orderStatus='" + isOrderStatus() + "'" +
            ", productId=" + getProductId() +
            ", customerId=" + getCustomerId() +
            "}";
    }
}
