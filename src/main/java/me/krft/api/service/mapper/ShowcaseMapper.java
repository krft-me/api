package me.krft.api.service.mapper;

import java.util.Set;
import java.util.stream.Collectors;
import me.krft.api.domain.Showcase;
import me.krft.api.service.dto.ShowcaseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShowcaseMapper implements EntityDTOMapper<Showcase, ShowcaseDTO> {

    @Autowired
    private ApplicationUserOfferMapper applicationUserOfferMapper;

    @Override
    public Showcase toEntity(ShowcaseDTO dto) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ShowcaseDTO toDTO(Showcase entity) {
        return ShowcaseDTO.builder().id(entity.getId()).offer(this.applicationUserOfferMapper.toDTO(entity.getOffer())).build();
    }

    public Set<ShowcaseDTO> toDTO(Set<Showcase> entities) {
        return entities.stream().map(this::toDTO).collect(Collectors.toSet());
    }
}
