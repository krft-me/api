package me.krft.api.service.impl;

import java.util.List;
import java.util.Optional;
import me.krft.api.domain.City;
import me.krft.api.repository.CityRepository;
import me.krft.api.service.CityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link City}.
 */
@Service
@Transactional
public class CityServiceImpl implements CityService {

    private final Logger log = LoggerFactory.getLogger(CityServiceImpl.class);

    private final CityRepository cityRepository;

    public CityServiceImpl(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    public City save(City city) {
        log.debug("Request to save City : {}", city);
        return cityRepository.save(city);
    }

    @Override
    public City update(City city) {
        log.debug("Request to update City : {}", city);
        return cityRepository.save(city);
    }

    @Override
    public Optional<City> partialUpdate(City city) {
        log.debug("Request to partially update City : {}", city);

        return cityRepository
            .findById(city.getId())
            .map(existingCity -> {
                if (city.getName() != null) {
                    existingCity.setName(city.getName());
                }
                if (city.getZipCode() != null) {
                    existingCity.setZipCode(city.getZipCode());
                }

                return existingCity;
            })
            .map(cityRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<City> findAll() {
        log.debug("Request to get all Cities");
        return cityRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<City> findOne(Long id) {
        log.debug("Request to get City : {}", id);
        return cityRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete City : {}", id);
        cityRepository.deleteById(id);
    }
}
