package me.krft.api.service.mapper;

import me.krft.api.domain.MachineCategory;
import me.krft.api.service.dto.MachineCategoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MachineCategoryMapper implements EntityDTOMapper<MachineCategory, MachineCategoryDTO> {

    @Autowired
    private MachineMapper machineMapper;

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
            .machines(this.machineMapper.toDTO(entity.getMachines()))
            .build();
    }
}
