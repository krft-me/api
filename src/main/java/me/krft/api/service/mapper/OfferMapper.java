package me.krft.api.service.mapper;

import me.krft.api.domain.Offer;
import me.krft.api.domain.OfferCategory;
import me.krft.api.service.dto.OfferCategoryDTO;
import me.krft.api.service.dto.OfferDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Offer} and its DTO {@link OfferDTO}.
 */
@Mapper(componentModel = "spring")
public interface OfferMapper extends EntityMapper<OfferDTO, Offer> {
    @Mapping(target = "category", source = "category", qualifiedByName = "offerCategoryId")
    OfferDTO toDto(Offer s);

    @Named("offerCategoryId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OfferCategoryDTO toDtoOfferCategoryId(OfferCategory offerCategory);
}
