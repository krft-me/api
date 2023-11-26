package me.krft.api.service.mapper;

import java.util.Set;
import java.util.stream.Collectors;
import me.krft.api.domain.Showcase;
import me.krft.api.service.dto.ShowcaseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class ShowcaseMapper implements EntityDTOMapper<Showcase, ShowcaseDTO> {

    private final ApplicationUserOfferMapper applicationUserOfferMapper;

    @Autowired
    public ShowcaseMapper(@Lazy ApplicationUserOfferMapper applicationUserOfferMapper) {
        this.applicationUserOfferMapper = applicationUserOfferMapper;
    }

    @Override
    public Showcase toEntity(ShowcaseDTO dto) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ShowcaseDTO toDTO(Showcase entity) {
        return ShowcaseDTO.builder().id(entity.getId()).offer(this.applicationUserOfferMapper.toDTOId(entity.getOffer())).build();
    }

    @Override
    public ShowcaseDTO toDTOId(Showcase entity) {
        return ShowcaseDTO.builder().id(entity.getId()).build();
    }

    public Set<ShowcaseDTO> toDTO(Set<Showcase> entities) {
        return entities.stream().map(this::toDTO).collect(Collectors.toSet());
    }

    public Set<ShowcaseDTO> toDTOId(Set<Showcase> entities) {
        return entities.stream().map(this::toDTOId).collect(Collectors.toSet());
    }
}
