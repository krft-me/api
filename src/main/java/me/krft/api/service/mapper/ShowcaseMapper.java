package me.krft.api.service.mapper;

import me.krft.api.domain.Showcase;
import me.krft.api.service.dto.ShowcaseDTO;

public class ShowcaseMapper implements EntityDTOMapper<Showcase, ShowcaseDTO>{
    @Override
    public ShowcaseDTO toDTO(Showcase entity) {
        return ShowcaseDTO.builder()
                .id(entity.getId())
                .imageId(entity.getImageId())
                .build();
    }

    @Override
    public Showcase toEntity(ShowcaseDTO dto) {
        return new Showcase().id(dto.getId()).imageId(dto.getImageId());
    }
}
