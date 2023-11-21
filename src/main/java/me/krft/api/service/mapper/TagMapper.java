package me.krft.api.service.mapper;

import me.krft.api.domain.Tag;
import me.krft.api.service.dto.TagDTO;

public class TagMapper implements EntityDTOMapper<Tag, TagDTO>{
    @Override
    public TagDTO toDTO(Tag entity) {
        return TagDTO.builder()
                .id(entity.getId())
                .label(entity.getLabel())
                .build();
    }

    @Override
    public Tag toEntity(TagDTO dto) {
        return new Tag().id(dto.getId()).label(dto.getLabel());
    }
}
