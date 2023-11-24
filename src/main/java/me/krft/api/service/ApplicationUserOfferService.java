package me.krft.api.service;

import java.util.List;
import java.util.Optional;
import me.krft.api.service.dto.ApplicationUserOfferDTO;

/**
 * Service Interface for managing {@link me.krft.api.domain.ApplicationUserOffer}.
 */
public interface ApplicationUserOfferService {
    /**
     * Save a applicationUserOffer.
     *
     * @param applicationUserOfferDTO the entity to save.
     * @return the persisted entity.
     */
    ApplicationUserOfferDTO save(ApplicationUserOfferDTO applicationUserOfferDTO);

    /**
     * Updates a applicationUserOffer.
     *
     * @param applicationUserOfferDTO the entity to update.
     * @return the persisted entity.
     */
    ApplicationUserOfferDTO update(ApplicationUserOfferDTO applicationUserOfferDTO);

    /**
     * Partially updates a applicationUserOffer.
     *
     * @param applicationUserOfferDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ApplicationUserOfferDTO> partialUpdate(ApplicationUserOfferDTO applicationUserOfferDTO);

    /**
     * Get all the applicationUserOffers.
     *
     * @return the list of entities.
     */
    List<ApplicationUserOfferDTO> findAll();

    /**
     * Get the "id" applicationUserOffer.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ApplicationUserOfferDTO> findOne(Long id);

    /**
     * Delete the "id" applicationUserOffer.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
