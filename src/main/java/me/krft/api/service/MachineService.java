package me.krft.api.service;

import java.util.List;
import java.util.Optional;
import me.krft.api.domain.Machine;

/**
 * Service Interface for managing {@link Machine}.
 */
public interface MachineService {
    /**
     * Save a machine.
     *
     * @param machine the entity to save.
     * @return the persisted entity.
     */
    Machine save(Machine machine);

    /**
     * Updates a machine.
     *
     * @param machine the entity to update.
     * @return the persisted entity.
     */
    Machine update(Machine machine);

    /**
     * Partially updates a machine.
     *
     * @param machine the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Machine> partialUpdate(Machine machine);

    /**
     * Get all the machines.
     *
     * @return the list of entities.
     */
    List<Machine> findAll();

    /**
     * Get the "id" machine.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Machine> findOne(Long id);

    /**
     * Delete the "id" machine.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
