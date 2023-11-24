package me.krft.api.service.mapper;

import me.krft.api.domain.City;
import me.krft.api.domain.Region;
import me.krft.api.service.dto.CityDTO;
import me.krft.api.service.dto.RegionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link City} and its DTO {@link CityDTO}.
 */
@Mapper(componentModel = "spring")
public interface CityMapper extends EntityMapper<CityDTO, City> {
    @Mapping(target = "region", source = "region", qualifiedByName = "regionId")
    CityDTO toDto(City s);

    @Named("regionId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RegionDTO toDtoRegionId(Region region);
}
