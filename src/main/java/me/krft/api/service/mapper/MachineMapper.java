package me.krft.api.service.mapper;

import java.util.Set;
import java.util.stream.Collectors;
import me.krft.api.domain.Machine;
import me.krft.api.service.dto.MachineDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MachineMapper implements EntityDTOMapper<Machine, MachineDTO> {

    @Autowired
    private OfferMapper offerMapper;

    @Autowired
    private MachineCategoryMapper machineCategoryMapper;

    @Override
    public Machine toEntity(MachineDTO dto) {
        throw new UnsupportedOperationException();
    }

    @Override
    public MachineDTO toDTO(Machine entity) {
        return MachineDTO
            .builder()
            .id(entity.getId())
            .name(entity.getName())
            .offers(this.offerMapper.toDTO(entity.getOffers()))
            .machineCategory(this.machineCategoryMapper.toDTO(entity.getCategory()))
            .build();
    }

    public Set<MachineDTO> toDTO(Set<Machine> entities) {
        return entities.stream().map(this::toDTO).collect(Collectors.toSet());
    }
}
