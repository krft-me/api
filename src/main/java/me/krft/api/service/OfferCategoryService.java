package me.krft.api.service;

import java.util.List;
import java.util.Optional;
import me.krft.api.service.dto.OfferCategoryDTO;

/**
 * Service Interface for managing {@link me.krft.api.domain.OfferCategory}.
 */
public interface OfferCategoryService {
    /**
     * Save a offerCategory.
     *
     * @param offerCategoryDTO the entity to save.
     * @return the persisted entity.
     */
    OfferCategoryDTO save(OfferCategoryDTO offerCategoryDTO);

    /**
     * Updates a offerCategory.
     *
     * @param offerCategoryDTO the entity to update.
     * @return the persisted entity.
     */
    OfferCategoryDTO update(OfferCategoryDTO offerCategoryDTO);

    /**
     * Partially updates a offerCategory.
     *
     * @param offerCategoryDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<OfferCategoryDTO> partialUpdate(OfferCategoryDTO offerCategoryDTO);

    /**
     * Get all the offerCategories.
     *
     * @return the list of entities.
     */
    List<OfferCategoryDTO> findAll();

    /**
     * Get the "id" offerCategory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OfferCategoryDTO> findOne(Long id);

    /**
     * Delete the "id" offerCategory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
