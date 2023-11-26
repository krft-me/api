package me.krft.api.service.mapper;

import java.util.Set;
import java.util.stream.Collectors;
import me.krft.api.domain.Region;
import me.krft.api.service.dto.RegionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class RegionMapper implements EntityDTOMapper<Region, RegionDTO> {

    private final CityMapper cityMapper;

    private final CountryMapper countryMapper;

    @Autowired
    public RegionMapper(@Lazy CityMapper cityMapper, @Lazy CountryMapper countryMapper) {
        this.cityMapper = cityMapper;
        this.countryMapper = countryMapper;
    }

    @Override
    public Region toEntity(RegionDTO dto) {
        throw new UnsupportedOperationException();
    }

    @Override
    public RegionDTO toDTO(Region entity) {
        return RegionDTO
            .builder()
            .id(entity.getId())
            .name(entity.getName())
            .cities(this.cityMapper.toDTOId(entity.getCities()))
            .country(this.countryMapper.toDTOId(entity.getCountry()))
            .build();
    }

    @Override
    public RegionDTO toDTOId(Region entity) {
        return RegionDTO.builder().id(entity.getId()).build();
    }

    public Set<RegionDTO> toDTO(Set<Region> entities) {
        return entities.stream().map(this::toDTO).collect(Collectors.toSet());
    }

    public Set<RegionDTO> toDTOId(Set<Region> entities) {
        return entities.stream().map(this::toDTOId).collect(Collectors.toSet());
    }
}
