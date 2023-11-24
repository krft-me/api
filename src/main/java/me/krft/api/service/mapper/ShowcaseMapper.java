package me.krft.api.service.mapper;

import me.krft.api.domain.ApplicationUserOffer;
import me.krft.api.domain.Showcase;
import me.krft.api.service.dto.ApplicationUserOfferDTO;
import me.krft.api.service.dto.ShowcaseDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Showcase} and its DTO {@link ShowcaseDTO}.
 */
@Mapper(componentModel = "spring")
public interface ShowcaseMapper extends EntityMapper<ShowcaseDTO, Showcase> {
    @Mapping(target = "applicationUserOffer", source = "applicationUserOffer", qualifiedByName = "applicationUserOfferId")
    ShowcaseDTO toDto(Showcase s);

    @Named("applicationUserOfferId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ApplicationUserOfferDTO toDtoApplicationUserOfferId(ApplicationUserOffer applicationUserOffer);
}
