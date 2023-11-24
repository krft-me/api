package me.krft.api.service;

import java.util.List;
import java.util.Optional;
import me.krft.api.service.dto.BadgeDTO;

/**
 * Service Interface for managing {@link me.krft.api.domain.Badge}.
 */
public interface BadgeService {
    /**
     * Save a badge.
     *
     * @param badgeDTO the entity to save.
     * @return the persisted entity.
     */
    BadgeDTO save(BadgeDTO badgeDTO);

    /**
     * Updates a badge.
     *
     * @param badgeDTO the entity to update.
     * @return the persisted entity.
     */
    BadgeDTO update(BadgeDTO badgeDTO);

    /**
     * Partially updates a badge.
     *
     * @param badgeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BadgeDTO> partialUpdate(BadgeDTO badgeDTO);

    /**
     * Get all the badges.
     *
     * @return the list of entities.
     */
    List<BadgeDTO> findAll();

    /**
     * Get the "id" badge.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BadgeDTO> findOne(Long id);

    /**
     * Delete the "id" badge.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
