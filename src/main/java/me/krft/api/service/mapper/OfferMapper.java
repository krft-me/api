package me.krft.api.service.mapper;

import java.util.Set;
import java.util.stream.Collectors;
import me.krft.api.domain.Offer;
import me.krft.api.service.dto.OfferDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OfferMapper implements EntityDTOMapper<Offer, OfferDTO> {

    @Autowired
    private ApplicationUserOfferMapper applicationUserOfferMapper;

    @Autowired
    private MachineMapper machineMapper;

    @Autowired
    private OfferCategoryMapper offerCategoryMapper;

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
            .userOffers(this.applicationUserOfferMapper.toDTO(entity.getUserOffers()))
            .machine(this.machineMapper.toDTO(entity.getMachine()))
            .offerCategory(this.offerCategoryMapper.toDTO(entity.getCategory()))
            .build();
    }

    public Set<OfferDTO> toDTO(Set<Offer> entities) {
        return entities.stream().map(this::toDTO).collect(Collectors.toSet());
    }
}
