package me.krft.api.service.mapper;

import java.util.Set;
import java.util.stream.Collectors;
import me.krft.api.domain.Machine;
import me.krft.api.service.dto.MachineDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class MachineMapper implements EntityDTOMapper<Machine, MachineDTO> {

    private final OfferMapper offerMapper;

    private final MachineCategoryMapper machineCategoryMapper;

    @Autowired
    public MachineMapper(@Lazy OfferMapper offerMapper, @Lazy MachineCategoryMapper machineCategoryMapper) {
        this.offerMapper = offerMapper;
        this.machineCategoryMapper = machineCategoryMapper;
    }

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
            .offers(this.offerMapper.toDTOId(entity.getOffers()))
            .machineCategory(this.machineCategoryMapper.toDTOId(entity.getCategory()))
            .build();
    }

    @Override
    public MachineDTO toDTOId(Machine entity) {
        return MachineDTO.builder().id(entity.getId()).build();
    }

    public Set<MachineDTO> toDTO(Set<Machine> entities) {
        return entities.stream().map(this::toDTO).collect(Collectors.toSet());
    }

    public Set<MachineDTO> toDTOId(Set<Machine> entities) {
        return entities.stream().map(this::toDTOId).collect(Collectors.toSet());
    }
}
