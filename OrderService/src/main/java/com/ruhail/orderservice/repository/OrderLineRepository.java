package com.ruhail.orderservice.repository;

import com.ruhail.orderservice.domain.OrderLine;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the OrderLine entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrderLineRepository extends JpaRepository<OrderLine, Long> {

}
