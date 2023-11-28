package me.krft.api.service;

import java.util.List;
import java.util.Optional;
import me.krft.api.domain.ApplicationUserOffer;
import me.krft.api.service.dto.ApplicationUserOfferDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
     * Get all the applicationUserOffers with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ApplicationUserOffer> findAllWithEagerRelationships(Pageable pageable);

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

    List<ApplicationUserOfferDTO> testMapper();

    List<ApplicationUserOfferDTO> getApplicationUserOffersCards();
}
