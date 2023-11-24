package me.krft.api.service;

import java.util.List;
import java.util.Optional;
import me.krft.api.service.dto.ShowcaseDTO;

/**
 * Service Interface for managing {@link me.krft.api.domain.Showcase}.
 */
public interface ShowcaseService {
    /**
     * Save a showcase.
     *
     * @param showcaseDTO the entity to save.
     * @return the persisted entity.
     */
    ShowcaseDTO save(ShowcaseDTO showcaseDTO);

    /**
     * Updates a showcase.
     *
     * @param showcaseDTO the entity to update.
     * @return the persisted entity.
     */
    ShowcaseDTO update(ShowcaseDTO showcaseDTO);

    /**
     * Partially updates a showcase.
     *
     * @param showcaseDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ShowcaseDTO> partialUpdate(ShowcaseDTO showcaseDTO);

    /**
     * Get all the showcases.
     *
     * @return the list of entities.
     */
    List<ShowcaseDTO> findAll();

    /**
     * Get the "id" showcase.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ShowcaseDTO> findOne(Long id);

    /**
     * Delete the "id" showcase.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
