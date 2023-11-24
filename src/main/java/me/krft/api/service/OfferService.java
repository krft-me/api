package me.krft.api.service;

import java.util.List;
import java.util.Optional;
import me.krft.api.service.dto.OfferDTO;

/**
 * Service Interface for managing {@link me.krft.api.domain.Offer}.
 */
public interface OfferService {
    /**
     * Save a offer.
     *
     * @param offerDTO the entity to save.
     * @return the persisted entity.
     */
    OfferDTO save(OfferDTO offerDTO);

    /**
     * Updates a offer.
     *
     * @param offerDTO the entity to update.
     * @return the persisted entity.
     */
    OfferDTO update(OfferDTO offerDTO);

    /**
     * Partially updates a offer.
     *
     * @param offerDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<OfferDTO> partialUpdate(OfferDTO offerDTO);

    /**
     * Get all the offers.
     *
     * @return the list of entities.
     */
    List<OfferDTO> findAll();

    /**
     * Get the "id" offer.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OfferDTO> findOne(Long id);

    /**
     * Delete the "id" offer.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
