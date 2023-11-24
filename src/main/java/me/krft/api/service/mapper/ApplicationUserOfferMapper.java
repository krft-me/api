package me.krft.api.service.mapper;

import me.krft.api.domain.ApplicationUser;
import me.krft.api.domain.ApplicationUserOffer;
import me.krft.api.service.dto.ApplicationUserDTO;
import me.krft.api.service.dto.ApplicationUserOfferDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ApplicationUserOffer} and its DTO {@link ApplicationUserOfferDTO}.
 */
@Mapper(componentModel = "spring")
public interface ApplicationUserOfferMapper extends EntityMapper<ApplicationUserOfferDTO, ApplicationUserOffer> {
    @Mapping(target = "applicationUser", source = "applicationUser", qualifiedByName = "applicationUserId")
    ApplicationUserOfferDTO toDto(ApplicationUserOffer s);

    @Named("applicationUserId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ApplicationUserDTO toDtoApplicationUserId(ApplicationUser applicationUser);
}
