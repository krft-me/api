package me.krft.api.service.impl;

import java.util.List;
import java.util.Optional;
import me.krft.api.domain.Machine;
import me.krft.api.repository.MachineRepository;
import me.krft.api.service.MachineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link me.krft.api.domain.Machine}.
 */
@Service
@Transactional
public class MachineServiceImpl implements MachineService {

    private final Logger log = LoggerFactory.getLogger(MachineServiceImpl.class);

    private final MachineRepository machineRepository;

    public MachineServiceImpl(MachineRepository machineRepository) {
        this.machineRepository = machineRepository;
    }

    @Override
    public Machine save(Machine machine) {
        log.debug("Request to save Machine : {}", machine);
        return machineRepository.save(machine);
    }

    @Override
    public Machine update(Machine machine) {
        log.debug("Request to update Machine : {}", machine);
        return machineRepository.save(machine);
    }

    @Override
    public Optional<Machine> partialUpdate(Machine machine) {
        log.debug("Request to partially update Machine : {}", machine);

        return machineRepository
            .findById(machine.getId())
            .map(existingMachine -> {
                if (machine.getName() != null) {
                    existingMachine.setName(machine.getName());
                }

                return existingMachine;
            })
            .map(machineRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Machine> findAll() {
        log.debug("Request to get all Machines");
        return machineRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Machine> findOne(Long id) {
        log.debug("Request to get Machine : {}", id);
        return machineRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Machine : {}", id);
        machineRepository.deleteById(id);
    }
}
