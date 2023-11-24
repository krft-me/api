package me.krft.api.service;

import java.util.List;
import java.util.Optional;
import me.krft.api.service.dto.MachineDTO;

/**
 * Service Interface for managing {@link me.krft.api.domain.Machine}.
 */
public interface MachineService {
    /**
     * Save a machine.
     *
     * @param machineDTO the entity to save.
     * @return the persisted entity.
     */
    MachineDTO save(MachineDTO machineDTO);

    /**
     * Updates a machine.
     *
     * @param machineDTO the entity to update.
     * @return the persisted entity.
     */
    MachineDTO update(MachineDTO machineDTO);

    /**
     * Partially updates a machine.
     *
     * @param machineDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MachineDTO> partialUpdate(MachineDTO machineDTO);

    /**
     * Get all the machines.
     *
     * @return the list of entities.
     */
    List<MachineDTO> findAll();

    /**
     * Get the "id" machine.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MachineDTO> findOne(Long id);

    /**
     * Delete the "id" machine.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
