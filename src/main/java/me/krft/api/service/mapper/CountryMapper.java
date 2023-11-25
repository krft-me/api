package me.krft.api.service.mapper;

import me.krft.api.domain.Country;
import me.krft.api.service.dto.CountryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CountryMapper implements EntityDTOMapper<Country, CountryDTO> {

    @Autowired
    private RegionMapper regionMapper;

    @Override
    public Country toEntity(CountryDTO dto) {
        throw new UnsupportedOperationException();
    }

    @Override
    public CountryDTO toDTO(Country entity) {
        return CountryDTO
            .builder()
            .id(entity.getId())
            .name(entity.getName())
            .isoCode(entity.getIsoCode())
            .regions(this.regionMapper.toDTO(entity.getRegions()))
            .build();
    }
}
