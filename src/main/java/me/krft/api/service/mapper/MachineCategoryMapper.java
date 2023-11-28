package me.krft.api.service.mapper;

import me.krft.api.domain.MachineCategory;
import me.krft.api.service.dto.MachineCategoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class MachineCategoryMapper implements EntityDTOMapper<MachineCategory, MachineCategoryDTO> {

    private final MachineMapper machineMapper;

    @Autowired
    public MachineCategoryMapper(@Lazy MachineMapper machineMapper) {
        this.machineMapper = machineMapper;
    }

    @Override
    public MachineCategory toEntity(MachineCategoryDTO dto) {
        throw new UnsupportedOperationException();
    }

    @Override
    public MachineCategoryDTO toDTO(MachineCategory entity) {
        return MachineCategoryDTO
            .builder()
            .id(entity.getId())
            .label(entity.getLabel())
            .machines(this.machineMapper.toDTOId(entity.getMachines()))
            .build();
    }

    @Override
    public MachineCategoryDTO toDTOId(MachineCategory entity) {
        return MachineCategoryDTO.builder().id(entity.getId()).build();
    }
}
