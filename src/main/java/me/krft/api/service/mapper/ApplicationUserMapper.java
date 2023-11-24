package me.krft.api.service.mapper;

import me.krft.api.domain.ApplicationUser;
import me.krft.api.domain.City;
import me.krft.api.domain.User;
import me.krft.api.service.dto.ApplicationUserDTO;
import me.krft.api.service.dto.CityDTO;
import me.krft.api.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ApplicationUser} and its DTO {@link ApplicationUserDTO}.
 */
@Mapper(componentModel = "spring")
public interface ApplicationUserMapper extends EntityMapper<ApplicationUserDTO, ApplicationUser> {
    @Mapping(target = "internalUser", source = "internalUser", qualifiedByName = "userId")
    @Mapping(target = "city", source = "city", qualifiedByName = "cityId")
    ApplicationUserDTO toDto(ApplicationUser s);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);

    @Named("cityId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CityDTO toDtoCityId(City city);
}
