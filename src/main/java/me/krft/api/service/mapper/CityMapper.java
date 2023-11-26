package me.krft.api.service.mapper;

import java.util.Set;
import java.util.stream.Collectors;
import me.krft.api.domain.City;
import me.krft.api.service.dto.CityDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class CityMapper implements EntityDTOMapper<City, CityDTO> {

    private ApplicationUserMapper applicationUserMapper;

    private RegionMapper regionMapper;

    @Autowired
    public CityMapper(@Lazy ApplicationUserMapper applicationUserMapper, @Lazy RegionMapper regionMapper) {
        this.applicationUserMapper = applicationUserMapper;
        this.regionMapper = regionMapper;
    }

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
            .users(this.applicationUserMapper.toDTOId(entity.getUsers()))
            .region(this.regionMapper.toDTOId(entity.getRegion()))
            .build();
    }

    @Override
    public CityDTO toDTOId(City entity) {
        return CityDTO.builder().id(entity.getId()).build();
    }

    public Set<CityDTO> toDTO(Set<City> entities) {
        return entities.stream().map(this::toDTO).collect(Collectors.toSet());
    }

    public Set<CityDTO> toDTOId(Set<City> entities) {
        return entities.stream().map(this::toDTOId).collect(Collectors.toSet());
    }
}
