package me.krft.api.service;

import java.util.List;
import java.util.Optional;
import me.krft.api.domain.OfferCategory;

/**
 * Service Interface for managing {@link OfferCategory}.
 */
public interface OfferCategoryService {
    /**
     * Save a offerCategory.
     *
     * @param offerCategory the entity to save.
     * @return the persisted entity.
     */
    OfferCategory save(OfferCategory offerCategory);

    /**
     * Updates a offerCategory.
     *
     * @param offerCategory the entity to update.
     * @return the persisted entity.
     */
    OfferCategory update(OfferCategory offerCategory);

    /**
     * Partially updates a offerCategory.
     *
     * @param offerCategory the entity to update partially.
     * @return the persisted entity.
     */
    Optional<OfferCategory> partialUpdate(OfferCategory offerCategory);

    /**
     * Get all the offerCategories.
     *
     * @return the list of entities.
     */
    List<OfferCategory> findAll();

    /**
     * Get the "id" offerCategory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OfferCategory> findOne(Long id);

    /**
     * Delete the "id" offerCategory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
