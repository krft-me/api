package me.krft.api.service.impl;

import java.util.List;
import java.util.Optional;
import me.krft.api.domain.MachineCategory;
import me.krft.api.repository.MachineCategoryRepository;
import me.krft.api.service.MachineCategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link me.krft.api.domain.MachineCategory}.
 */
@Service
@Transactional
public class MachineCategoryServiceImpl implements MachineCategoryService {

    private final Logger log = LoggerFactory.getLogger(MachineCategoryServiceImpl.class);

    private final MachineCategoryRepository machineCategoryRepository;

    public MachineCategoryServiceImpl(MachineCategoryRepository machineCategoryRepository) {
        this.machineCategoryRepository = machineCategoryRepository;
    }

    @Override
    public MachineCategory save(MachineCategory machineCategory) {
        log.debug("Request to save MachineCategory : {}", machineCategory);
        return machineCategoryRepository.save(machineCategory);
    }

    @Override
    public MachineCategory update(MachineCategory machineCategory) {
        log.debug("Request to update MachineCategory : {}", machineCategory);
        return machineCategoryRepository.save(machineCategory);
    }

    @Override
    public Optional<MachineCategory> partialUpdate(MachineCategory machineCategory) {
        log.debug("Request to partially update MachineCategory : {}", machineCategory);

        return machineCategoryRepository
            .findById(machineCategory.getId())
            .map(existingMachineCategory -> {
                if (machineCategory.getLabel() != null) {
                    existingMachineCategory.setLabel(machineCategory.getLabel());
                }

                return existingMachineCategory;
            })
            .map(machineCategoryRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MachineCategory> findAll() {
        log.debug("Request to get all MachineCategories");
        return machineCategoryRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MachineCategory> findOne(Long id) {
        log.debug("Request to get MachineCategory : {}", id);
        return machineCategoryRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete MachineCategory : {}", id);
        machineCategoryRepository.deleteById(id);
    }
}
