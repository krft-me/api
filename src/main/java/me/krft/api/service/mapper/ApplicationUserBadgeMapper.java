package me.krft.api.service.mapper;

import me.krft.api.domain.ApplicationUser;
import me.krft.api.domain.ApplicationUserBadge;
import me.krft.api.domain.Badge;
import me.krft.api.service.dto.ApplicationUserBadgeDTO;
import me.krft.api.service.dto.ApplicationUserDTO;
import me.krft.api.service.dto.BadgeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ApplicationUserBadge} and its DTO {@link ApplicationUserBadgeDTO}.
 */
@Mapper(componentModel = "spring")
public interface ApplicationUserBadgeMapper extends EntityMapper<ApplicationUserBadgeDTO, ApplicationUserBadge> {
    @Mapping(target = "user", source = "user", qualifiedByName = "applicationUserId")
    @Mapping(target = "badge", source = "badge", qualifiedByName = "badgeId")
    ApplicationUserBadgeDTO toDto(ApplicationUserBadge s);

    @Named("applicationUserId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ApplicationUserDTO toDtoApplicationUserId(ApplicationUser applicationUser);

    @Named("badgeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    BadgeDTO toDtoBadgeId(Badge badge);
}
