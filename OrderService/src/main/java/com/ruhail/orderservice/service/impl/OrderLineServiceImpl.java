package com.ruhail.orderservice.service.impl;

import com.ruhail.orderservice.service.OrderLineService;
import com.ruhail.orderservice.domain.OrderLine;
import com.ruhail.orderservice.repository.OrderLineRepository;
import com.ruhail.orderservice.service.dto.OrderLineDTO;
import com.ruhail.orderservice.service.mapper.OrderLineMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing OrderLine.
 */
@Service
@Transactional
public class OrderLineServiceImpl implements OrderLineService {

    private final Logger log = LoggerFactory.getLogger(OrderLineServiceImpl.class);

    private final OrderLineRepository orderLineRepository;

    private final OrderLineMapper orderLineMapper;

    public OrderLineServiceImpl(OrderLineRepository orderLineRepository, OrderLineMapper orderLineMapper) {
        this.orderLineRepository = orderLineRepository;
        this.orderLineMapper = orderLineMapper;
    }

    /**
     * Save a orderLine.
     *
     * @param orderLineDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public OrderLineDTO save(OrderLineDTO orderLineDTO) {
        log.debug("Request to save OrderLine : {}", orderLineDTO);

        OrderLine orderLine = orderLineMapper.toEntity(orderLineDTO);
        orderLine = orderLineRepository.save(orderLine);
        return orderLineMapper.toDto(orderLine);
    }

    /**
     * Get all the orderLines.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<OrderLineDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OrderLines");
        return orderLineRepository.findAll(pageable)
            .map(orderLineMapper::toDto);
    }


    /**
     * Get one orderLine by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<OrderLineDTO> findOne(Long id) {
        log.debug("Request to get OrderLine : {}", id);
        return orderLineRepository.findById(id)
            .map(orderLineMapper::toDto);
    }

    /**
     * Delete the orderLine by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete OrderLine : {}", id);
        orderLineRepository.deleteById(id);
    }
}
