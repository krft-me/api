package me.krft.api.service;

import java.util.List;
import java.util.Optional;
import me.krft.api.domain.Order;

/**
 * Service Interface for managing {@link Order}.
 */
public interface OrderService {
    /**
     * Save a order.
     *
     * @param order the entity to save.
     * @return the persisted entity.
     */
    Order save(Order order);

    /**
     * Updates a order.
     *
     * @param order the entity to update.
     * @return the persisted entity.
     */
    Order update(Order order);

    /**
     * Partially updates a order.
     *
     * @param order the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Order> partialUpdate(Order order);

    /**
     * Get all the orders.
     *
     * @return the list of entities.
     */
    List<Order> findAll();

    /**
     * Get all the Order where Review is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<Order> findAllWhereReviewIsNull();

    /**
     * Get the "id" order.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Order> findOne(Long id);

    /**
     * Delete the "id" order.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
