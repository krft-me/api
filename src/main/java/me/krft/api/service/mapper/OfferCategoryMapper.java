package me.krft.api.service.mapper;

import me.krft.api.domain.OfferCategory;
import me.krft.api.service.dto.OfferCategoryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link OfferCategory} and its DTO {@link OfferCategoryDTO}.
 */
@Mapper(componentModel = "spring")
public interface OfferCategoryMapper extends EntityMapper<OfferCategoryDTO, OfferCategory> {}
