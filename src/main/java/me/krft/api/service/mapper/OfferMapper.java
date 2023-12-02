package me.krft.api.service.mapper;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import me.krft.api.domain.Offer;
import me.krft.api.service.dto.OfferDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class OfferMapper implements EntityDTOMapper<Offer, OfferDTO> {

    private final ApplicationUserOfferMapper applicationUserOfferMapper;

    private final MachineMapper machineMapper;

    private final OfferCategoryMapper offerCategoryMapper;

    @Autowired
    public OfferMapper(
        @Lazy ApplicationUserOfferMapper applicationUserOfferMapper,
        @Lazy MachineMapper machineMapper,
        @Lazy OfferCategoryMapper offerCategoryMapper
    ) {
        this.applicationUserOfferMapper = applicationUserOfferMapper;
        this.machineMapper = machineMapper;
        this.offerCategoryMapper = offerCategoryMapper;
    }

    @Override
    public Offer toEntity(OfferDTO dto) {
        throw new UnsupportedOperationException();
    }

    @Override
    public OfferDTO toDTO(Offer entity) {
        return OfferDTO
            .builder()
            .id(entity.getId())
            .name(entity.getName())
            .userOffers(this.applicationUserOfferMapper.toDTOId(entity.getUserOffers()))
            .machine(this.machineMapper.toDTOId(entity.getMachine()))
            .offerCategory(this.offerCategoryMapper.toDTOId(entity.getCategory()))
            .build();
    }

    @Override
    public OfferDTO toDTOId(Offer entity) {
        return OfferDTO.builder().id(entity.getId()).build();
    }

    public Set<OfferDTO> toDTO(Set<Offer> entities) {
        return entities.stream().map(this::toDTO).collect(Collectors.toSet());
    }

    public Set<OfferDTO> toDTOId(Set<Offer> entities) {
        return entities.stream().map(this::toDTOId).collect(Collectors.toSet());
    }

    public OfferDTO toDTOMachineName(Offer offer) {
        OfferDTO.OfferDTOBuilder<?, ?> offerDTOBuilder = OfferDTO.builder().id(offer.getId());

        Optional.ofNullable(offer.getMachine()).ifPresent(machine -> offerDTOBuilder.machine(this.machineMapper.toDTOName(machine)));

        return offerDTOBuilder.build();
    }
}
