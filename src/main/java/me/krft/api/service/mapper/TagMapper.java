package me.krft.api.service.mapper;

import java.util.Set;
import java.util.stream.Collectors;
import me.krft.api.domain.Tag;
import me.krft.api.service.dto.TagDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagMapper implements EntityDTOMapper<Tag, TagDTO> {

    @Autowired
    private ApplicationUserOfferMapper applicationUserOfferMapper;

    @Override
    public Tag toEntity(TagDTO dto) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TagDTO toDTO(Tag entity) {
        return TagDTO
            .builder()
            .id(entity.getId())
            .label(entity.getLabel())
            .offers(this.applicationUserOfferMapper.toDTO(entity.getOffers()))
            .build();
    }

    public Set<TagDTO> toDTO(Set<Tag> entities) {
        return entities.stream().map(this::toDTO).collect(Collectors.toSet());
    }
}
