package me.krft.api.service;

import java.util.List;
import java.util.Optional;
import me.krft.api.service.dto.ApplicationUserBadgeDTO;

/**
 * Service Interface for managing {@link me.krft.api.domain.ApplicationUserBadge}.
 */
public interface ApplicationUserBadgeService {
    /**
     * Save a applicationUserBadge.
     *
     * @param applicationUserBadgeDTO the entity to save.
     * @return the persisted entity.
     */
    ApplicationUserBadgeDTO save(ApplicationUserBadgeDTO applicationUserBadgeDTO);

    /**
     * Updates a applicationUserBadge.
     *
     * @param applicationUserBadgeDTO the entity to update.
     * @return the persisted entity.
     */
    ApplicationUserBadgeDTO update(ApplicationUserBadgeDTO applicationUserBadgeDTO);

    /**
     * Partially updates a applicationUserBadge.
     *
     * @param applicationUserBadgeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ApplicationUserBadgeDTO> partialUpdate(ApplicationUserBadgeDTO applicationUserBadgeDTO);

    /**
     * Get all the applicationUserBadges.
     *
     * @return the list of entities.
     */
    List<ApplicationUserBadgeDTO> findAll();

    /**
     * Get the "id" applicationUserBadge.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ApplicationUserBadgeDTO> findOne(Long id);

    /**
     * Delete the "id" applicationUserBadge.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
