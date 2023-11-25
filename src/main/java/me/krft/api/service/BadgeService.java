package me.krft.api.service;

import java.util.List;
import java.util.Optional;
import me.krft.api.domain.Badge;

/**
 * Service Interface for managing {@link me.krft.api.domain.Badge}.
 */
public interface BadgeService {
    /**
     * Save a badge.
     *
     * @param badge the entity to save.
     * @return the persisted entity.
     */
    Badge save(Badge badge);

    /**
     * Updates a badge.
     *
     * @param badge the entity to update.
     * @return the persisted entity.
     */
    Badge update(Badge badge);

    /**
     * Partially updates a badge.
     *
     * @param badge the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Badge> partialUpdate(Badge badge);

    /**
     * Get all the badges.
     *
     * @return the list of entities.
     */
    List<Badge> findAll();

    /**
     * Get the "id" badge.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Badge> findOne(Long id);

    /**
     * Delete the "id" badge.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
