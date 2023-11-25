package me.krft.api.service.mapper;

import java.util.Set;
import java.util.stream.Collectors;
import me.krft.api.domain.Region;
import me.krft.api.service.dto.RegionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegionMapper implements EntityDTOMapper<Region, RegionDTO> {

    @Autowired
    private CityMapper cityMapper;

    @Autowired
    private CountryMapper countryMapper;

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
            .cities(this.cityMapper.toDTO(entity.getCities()))
            .country(this.countryMapper.toDTO(entity.getCountry()))
            .build();
    }

    public Set<RegionDTO> toDTO(Set<Region> entities) {
        return entities.stream().map(this::toDTO).collect(Collectors.toSet());
    }
}
