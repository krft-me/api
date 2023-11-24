package me.krft.api.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import me.krft.api.domain.MachineCategory;
import me.krft.api.repository.MachineCategoryRepository;
import me.krft.api.service.MachineCategoryService;
import me.krft.api.service.dto.MachineCategoryDTO;
import me.krft.api.service.mapper.MachineCategoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MachineCategory}.
 */
@Service
@Transactional
public class MachineCategoryServiceImpl implements MachineCategoryService {

    private final Logger log = LoggerFactory.getLogger(MachineCategoryServiceImpl.class);

    private final MachineCategoryRepository machineCategoryRepository;

    private final MachineCategoryMapper machineCategoryMapper;

    public MachineCategoryServiceImpl(MachineCategoryRepository machineCategoryRepository, MachineCategoryMapper machineCategoryMapper) {
        this.machineCategoryRepository = machineCategoryRepository;
        this.machineCategoryMapper = machineCategoryMapper;
    }

    @Override
    public MachineCategoryDTO save(MachineCategoryDTO machineCategoryDTO) {
        log.debug("Request to save MachineCategory : {}", machineCategoryDTO);
        MachineCategory machineCategory = machineCategoryMapper.toEntity(machineCategoryDTO);
        machineCategory = machineCategoryRepository.save(machineCategory);
        return machineCategoryMapper.toDto(machineCategory);
    }

    @Override
    public MachineCategoryDTO update(MachineCategoryDTO machineCategoryDTO) {
        log.debug("Request to update MachineCategory : {}", machineCategoryDTO);
        MachineCategory machineCategory = machineCategoryMapper.toEntity(machineCategoryDTO);
        machineCategory = machineCategoryRepository.save(machineCategory);
        return machineCategoryMapper.toDto(machineCategory);
    }

    @Override
    public Optional<MachineCategoryDTO> partialUpdate(MachineCategoryDTO machineCategoryDTO) {
        log.debug("Request to partially update MachineCategory : {}", machineCategoryDTO);

        return machineCategoryRepository
            .findById(machineCategoryDTO.getId())
            .map(existingMachineCategory -> {
                machineCategoryMapper.partialUpdate(existingMachineCategory, machineCategoryDTO);

                return existingMachineCategory;
            })
            .map(machineCategoryRepository::save)
            .map(machineCategoryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MachineCategoryDTO> findAll() {
        log.debug("Request to get all MachineCategories");
        return machineCategoryRepository
            .findAll()
            .stream()
            .map(machineCategoryMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MachineCategoryDTO> findOne(Long id) {
        log.debug("Request to get MachineCategory : {}", id);
        return machineCategoryRepository.findById(id).map(machineCategoryMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete MachineCategory : {}", id);
        machineCategoryRepository.deleteById(id);
    }
}
