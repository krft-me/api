package me.krft.api.service.mapper;

import java.util.Set;
import java.util.stream.Collectors;
import me.krft.api.domain.Tag;
import me.krft.api.service.dto.TagDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class TagMapper implements EntityDTOMapper<Tag, TagDTO> {

    private final ApplicationUserOfferMapper applicationUserOfferMapper;

    @Autowired
    public TagMapper(@Lazy ApplicationUserOfferMapper applicationUserOfferMapper) {
        this.applicationUserOfferMapper = applicationUserOfferMapper;
    }

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
            .offers(this.applicationUserOfferMapper.toDTOId(entity.getOffers()))
            .build();
    }

    @Override
    public TagDTO toDTOId(Tag entity) {
        return TagDTO.builder().id(entity.getId()).build();
    }

    public Set<TagDTO> toDTO(Set<Tag> entities) {
        return entities.stream().map(this::toDTO).collect(Collectors.toSet());
    }

    public Set<TagDTO> toDTOId(Set<Tag> entities) {
        return entities.stream().map(this::toDTOId).collect(Collectors.toSet());
    }

    public TagDTO toDTOLabel(Tag tag) {
        return TagDTO.builder().id(tag.getId()).label(tag.getLabel()).build();
    }

    public Set<TagDTO> toDTOLabel(Set<Tag> tags) {
        return tags.stream().map(this::toDTOLabel).collect(Collectors.toSet());
    }
}
