package me.krft.api.service;

import java.util.List;
import java.util.Optional;
import me.krft.api.service.dto.MachineCategoryDTO;

/**
 * Service Interface for managing {@link me.krft.api.domain.MachineCategory}.
 */
public interface MachineCategoryService {
    /**
     * Save a machineCategory.
     *
     * @param machineCategoryDTO the entity to save.
     * @return the persisted entity.
     */
    MachineCategoryDTO save(MachineCategoryDTO machineCategoryDTO);

    /**
     * Updates a machineCategory.
     *
     * @param machineCategoryDTO the entity to update.
     * @return the persisted entity.
     */
    MachineCategoryDTO update(MachineCategoryDTO machineCategoryDTO);

    /**
     * Partially updates a machineCategory.
     *
     * @param machineCategoryDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MachineCategoryDTO> partialUpdate(MachineCategoryDTO machineCategoryDTO);

    /**
     * Get all the machineCategories.
     *
     * @return the list of entities.
     */
    List<MachineCategoryDTO> findAll();

    /**
     * Get the "id" machineCategory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MachineCategoryDTO> findOne(Long id);

    /**
     * Delete the "id" machineCategory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
