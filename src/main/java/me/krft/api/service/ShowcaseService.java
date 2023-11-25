package me.krft.api.service;

import java.util.List;
import java.util.Optional;
import me.krft.api.domain.Showcase;

/**
 * Service Interface for managing {@link Showcase}.
 */
public interface ShowcaseService {
    /**
     * Save a showcase.
     *
     * @param showcase the entity to save.
     * @return the persisted entity.
     */
    Showcase save(Showcase showcase);

    /**
     * Updates a showcase.
     *
     * @param showcase the entity to update.
     * @return the persisted entity.
     */
    Showcase update(Showcase showcase);

    /**
     * Partially updates a showcase.
     *
     * @param showcase the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Showcase> partialUpdate(Showcase showcase);

    /**
     * Get all the showcases.
     *
     * @return the list of entities.
     */
    List<Showcase> findAll();

    /**
     * Get the "id" showcase.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Showcase> findOne(Long id);

    /**
     * Delete the "id" showcase.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
