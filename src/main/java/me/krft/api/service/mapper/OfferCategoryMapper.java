package me.krft.api.service.mapper;

import me.krft.api.domain.OfferCategory;
import me.krft.api.service.dto.OfferCategoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class OfferCategoryMapper implements EntityDTOMapper<OfferCategory, OfferCategoryDTO> {

    private final OfferMapper offerMapper;

    @Autowired
    public OfferCategoryMapper(@Lazy OfferMapper offerMapper) {
        this.offerMapper = offerMapper;
    }

    @Override
    public OfferCategory toEntity(OfferCategoryDTO dto) {
        throw new UnsupportedOperationException();
    }

    @Override
    public OfferCategoryDTO toDTO(OfferCategory entity) {
        return OfferCategoryDTO
            .builder()
            .id(entity.getId())
            .label(entity.getLabel())
            .offers(this.offerMapper.toDTOId(entity.getOffers()))
            .build();
    }

    @Override
    public OfferCategoryDTO toDTOId(OfferCategory entity) {
        return OfferCategoryDTO.builder().id(entity.getId()).build();
    }
}
