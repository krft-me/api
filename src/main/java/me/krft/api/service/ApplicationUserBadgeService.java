package me.krft.api.service;

import java.util.List;
import java.util.Optional;
import me.krft.api.domain.ApplicationUserBadge;

/**
 * Service Interface for managing {@link me.krft.api.domain.ApplicationUserBadge}.
 */
public interface ApplicationUserBadgeService {
    /**
     * Save a applicationUserBadge.
     *
     * @param applicationUserBadge the entity to save.
     * @return the persisted entity.
     */
    ApplicationUserBadge save(ApplicationUserBadge applicationUserBadge);

    /**
     * Updates a applicationUserBadge.
     *
     * @param applicationUserBadge the entity to update.
     * @return the persisted entity.
     */
    ApplicationUserBadge update(ApplicationUserBadge applicationUserBadge);

    /**
     * Partially updates a applicationUserBadge.
     *
     * @param applicationUserBadge the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ApplicationUserBadge> partialUpdate(ApplicationUserBadge applicationUserBadge);

    /**
     * Get all the applicationUserBadges.
     *
     * @return the list of entities.
     */
    List<ApplicationUserBadge> findAll();

    /**
     * Get the "id" applicationUserBadge.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ApplicationUserBadge> findOne(Long id);

    /**
     * Delete the "id" applicationUserBadge.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
