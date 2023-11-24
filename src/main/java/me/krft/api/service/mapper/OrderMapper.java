package me.krft.api.service.mapper;

import me.krft.api.domain.ApplicationUser;
import me.krft.api.domain.ApplicationUserOffer;
import me.krft.api.domain.Order;
import me.krft.api.service.dto.ApplicationUserDTO;
import me.krft.api.service.dto.ApplicationUserOfferDTO;
import me.krft.api.service.dto.OrderDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Order} and its DTO {@link OrderDTO}.
 */
@Mapper(componentModel = "spring")
public interface OrderMapper extends EntityMapper<OrderDTO, Order> {
    @Mapping(target = "offer", source = "offer", qualifiedByName = "applicationUserOfferId")
    @Mapping(target = "customer", source = "customer", qualifiedByName = "applicationUserId")
    OrderDTO toDto(Order s);

    @Named("applicationUserOfferId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ApplicationUserOfferDTO toDtoApplicationUserOfferId(ApplicationUserOffer applicationUserOffer);

    @Named("applicationUserId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ApplicationUserDTO toDtoApplicationUserId(ApplicationUser applicationUser);
}
