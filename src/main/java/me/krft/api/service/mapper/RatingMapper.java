package me.krft.api.service.mapper;

import me.krft.api.domain.Rating;
import me.krft.api.service.dto.RatingDTO;

public class RatingMapper implements EntityDTOMapper<Rating, RatingDTO> {
    @Override
    public RatingDTO toDTO(Rating entity) {
        return RatingDTO.builder()
                .id(entity.getId())
                .rate(entity.getRate())
                .comment(entity.getComment())
                .build();
    }

    @Override
    public Rating toEntity(RatingDTO dto) {
        return new Rating().id(dto.getId()).rate(dto.getRate()).comment(dto.getComment());
    }
}
