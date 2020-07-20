package com.ruhail.inventoryservice.service.dto;

import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the OrderLine entity.
 */
public class OrderLineDTO implements Serializable {

    private Long id;

    private Instant orderDate;

    private Boolean orderStatus;

    private Long productId;

    private Long customerId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Instant orderDate) {
        this.orderDate = orderDate;
    }

    public Boolean isOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Boolean orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OrderLineDTO orderLineDTO = (OrderLineDTO) o;
        if (orderLineDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), orderLineDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OrderLineDTO{" +
            "id=" + getId() +
            ", orderDate='" + getOrderDate() + "'" +
            ", orderStatus='" + isOrderStatus() + "'" +
            ", productId=" + getProductId() +
            ", customerId=" + getCustomerId() +
            "}";
    }
}
