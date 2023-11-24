package me.krft.api.service.mapper;

import me.krft.api.domain.Country;
import me.krft.api.domain.Region;
import me.krft.api.service.dto.CountryDTO;
import me.krft.api.service.dto.RegionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Region} and its DTO {@link RegionDTO}.
 */
@Mapper(componentModel = "spring")
public interface RegionMapper extends EntityMapper<RegionDTO, Region> {
    @Mapping(target = "country", source = "country", qualifiedByName = "countryId")
    RegionDTO toDto(Region s);

    @Named("countryId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CountryDTO toDtoCountryId(Country country);
}
