package me.krft.api.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import me.krft.api.domain.Order;
import me.krft.api.repository.OrderRepository;
import me.krft.api.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Order}.
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order save(Order order) {
        log.debug("Request to save Order : {}", order);
        return orderRepository.save(order);
    }

    @Override
    public Order update(Order order) {
        log.debug("Request to update Order : {}", order);
        return orderRepository.save(order);
    }

    @Override
    public Optional<Order> partialUpdate(Order order) {
        log.debug("Request to partially update Order : {}", order);

        return orderRepository
            .findById(order.getId())
            .map(existingOrder -> {
                if (order.getDate() != null) {
                    existingOrder.setDate(order.getDate());
                }
                if (order.getState() != null) {
                    existingOrder.setState(order.getState());
                }

                return existingOrder;
            })
            .map(orderRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> findAll() {
        log.debug("Request to get all Orders");
        return orderRepository.findAll();
    }

    /**
     * Get all the orders where Review is {@code null}.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Order> findAllWhereReviewIsNull() {
        log.debug("Request to get all orders where Review is null");
        return StreamSupport
            .stream(orderRepository.findAll().spliterator(), false)
            .filter(order -> order.getReview() == null)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Order> findOne(Long id) {
        log.debug("Request to get Order : {}", id);
        return orderRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Order : {}", id);
        orderRepository.deleteById(id);
    }
}
