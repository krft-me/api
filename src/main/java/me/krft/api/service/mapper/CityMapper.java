package me.krft.api.service.mapper;

import java.util.Set;
import java.util.stream.Collectors;
import me.krft.api.domain.City;
import me.krft.api.service.dto.CityDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CityMapper implements EntityDTOMapper<City, CityDTO> {

    @Autowired
    private ApplicationUserMapper applicationUserMapper;

    @Autowired
    private RegionMapper regionMapper;

    @Override
    public City toEntity(CityDTO dto) {
        throw new UnsupportedOperationException();
    }

    @Override
    public CityDTO toDTO(City entity) {
        return CityDTO
            .builder()
            .id(entity.getId())
            .name(entity.getName())
            .zipCode(entity.getZipCode())
            .users(this.applicationUserMapper.toDTO(entity.getUsers()))
            .region(this.regionMapper.toDTO(entity.getRegion()))
            .build();
    }

    public Set<CityDTO> toDTO(Set<City> entities) {
        return entities.stream().map(this::toDTO).collect(Collectors.toSet());
    }
}
