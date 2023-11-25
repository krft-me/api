package me.krft.api.service;

import java.util.List;
import java.util.Optional;
import me.krft.api.domain.ApplicationUser;

/**
 * Service Interface for managing {@link ApplicationUser}.
 */
public interface ApplicationUserService {
    /**
     * Save a applicationUser.
     *
     * @param applicationUser the entity to save.
     * @return the persisted entity.
     */
    ApplicationUser save(ApplicationUser applicationUser);

    /**
     * Updates a applicationUser.
     *
     * @param applicationUser the entity to update.
     * @return the persisted entity.
     */
    ApplicationUser update(ApplicationUser applicationUser);

    /**
     * Partially updates a applicationUser.
     *
     * @param applicationUser the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ApplicationUser> partialUpdate(ApplicationUser applicationUser);

    /**
     * Get all the applicationUsers.
     *
     * @return the list of entities.
     */
    List<ApplicationUser> findAll();

    /**
     * Get the "id" applicationUser.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ApplicationUser> findOne(Long id);

    /**
     * Delete the "id" applicationUser.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
