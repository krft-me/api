package me.krft.api.service;

import java.util.List;
import java.util.Optional;
import me.krft.api.domain.ApplicationUserOffer;

/**
 * Service Interface for managing {@link ApplicationUserOffer}.
 */
public interface ApplicationUserOfferService {
    /**
     * Save a applicationUserOffer.
     *
     * @param applicationUserOffer the entity to save.
     * @return the persisted entity.
     */
    ApplicationUserOffer save(ApplicationUserOffer applicationUserOffer);

    /**
     * Updates a applicationUserOffer.
     *
     * @param applicationUserOffer the entity to update.
     * @return the persisted entity.
     */
    ApplicationUserOffer update(ApplicationUserOffer applicationUserOffer);

    /**
     * Partially updates a applicationUserOffer.
     *
     * @param applicationUserOffer the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ApplicationUserOffer> partialUpdate(ApplicationUserOffer applicationUserOffer);

    /**
     * Get all the applicationUserOffers.
     *
     * @return the list of entities.
     */
    List<ApplicationUserOffer> findAll();

    /**
     * Get the "id" applicationUserOffer.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ApplicationUserOffer> findOne(Long id);

    /**
     * Delete the "id" applicationUserOffer.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
