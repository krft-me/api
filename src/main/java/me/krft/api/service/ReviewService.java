package me.krft.api.service;

import java.util.List;
import java.util.Optional;
import me.krft.api.domain.Review;

/**
 * Service Interface for managing {@link Review}.
 */
public interface ReviewService {
    /**
     * Save a review.
     *
     * @param review the entity to save.
     * @return the persisted entity.
     */
    Review save(Review review);

    /**
     * Updates a review.
     *
     * @param review the entity to update.
     * @return the persisted entity.
     */
    Review update(Review review);

    /**
     * Partially updates a review.
     *
     * @param review the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Review> partialUpdate(Review review);

    /**
     * Get all the reviews.
     *
     * @return the list of entities.
     */
    List<Review> findAll();
    /**
     * Get all the Review where Order is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<Review> findAllWhereOrderIsNull();

    /**
     * Get the "id" review.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Review> findOne(Long id);

    /**
     * Delete the "id" review.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
