package me.krft.api.service.mapper;

import me.krft.api.domain.ApplicationUserOffer;
import me.krft.api.domain.Review;
import me.krft.api.service.dto.ApplicationUserOfferDTO;
import me.krft.api.service.dto.ReviewDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Review} and its DTO {@link ReviewDTO}.
 */
@Mapper(componentModel = "spring")
public interface ReviewMapper extends EntityMapper<ReviewDTO, Review> {
    @Mapping(target = "applicationUserOffer", source = "applicationUserOffer", qualifiedByName = "applicationUserOfferId")
    ReviewDTO toDto(Review s);

    @Named("applicationUserOfferId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ApplicationUserOfferDTO toDtoApplicationUserOfferId(ApplicationUserOffer applicationUserOffer);
}
