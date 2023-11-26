package me.krft.api.service;

import java.util.List;
import java.util.Optional;
import me.krft.api.domain.Tag;

/**
 * Service Interface for managing {@link Tag}.
 */
public interface TagService {
    /**
     * Save a tag.
     *
     * @param tag the entity to save.
     * @return the persisted entity.
     */
    Tag save(Tag tag);

    /**
     * Updates a tag.
     *
     * @param tag the entity to update.
     * @return the persisted entity.
     */
    Tag update(Tag tag);

    /**
     * Partially updates a tag.
     *
     * @param tag the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Tag> partialUpdate(Tag tag);

    /**
     * Get all the tags.
     *
     * @return the list of entities.
     */
    List<Tag> findAll();

    /**
     * Get the "id" tag.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Tag> findOne(Long id);

    /**
     * Delete the "id" tag.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
