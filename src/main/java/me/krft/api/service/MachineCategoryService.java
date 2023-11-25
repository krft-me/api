package me.krft.api.service;

import java.util.List;
import java.util.Optional;
import me.krft.api.domain.MachineCategory;

/**
 * Service Interface for managing {@link MachineCategory}.
 */
public interface MachineCategoryService {
    /**
     * Save a machineCategory.
     *
     * @param machineCategory the entity to save.
     * @return the persisted entity.
     */
    MachineCategory save(MachineCategory machineCategory);

    /**
     * Updates a machineCategory.
     *
     * @param machineCategory the entity to update.
     * @return the persisted entity.
     */
    MachineCategory update(MachineCategory machineCategory);

    /**
     * Partially updates a machineCategory.
     *
     * @param machineCategory the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MachineCategory> partialUpdate(MachineCategory machineCategory);

    /**
     * Get all the machineCategories.
     *
     * @return the list of entities.
     */
    List<MachineCategory> findAll();

    /**
     * Get the "id" machineCategory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MachineCategory> findOne(Long id);

    /**
     * Delete the "id" machineCategory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
