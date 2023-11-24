package me.krft.api.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import me.krft.api.domain.OfferCategory;
import me.krft.api.repository.OfferCategoryRepository;
import me.krft.api.service.OfferCategoryService;
import me.krft.api.service.dto.OfferCategoryDTO;
import me.krft.api.service.mapper.OfferCategoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link OfferCategory}.
 */
@Service
@Transactional
public class OfferCategoryServiceImpl implements OfferCategoryService {

    private final Logger log = LoggerFactory.getLogger(OfferCategoryServiceImpl.class);

    private final OfferCategoryRepository offerCategoryRepository;

    private final OfferCategoryMapper offerCategoryMapper;

    public OfferCategoryServiceImpl(OfferCategoryRepository offerCategoryRepository, OfferCategoryMapper offerCategoryMapper) {
        this.offerCategoryRepository = offerCategoryRepository;
        this.offerCategoryMapper = offerCategoryMapper;
    }

    @Override
    public OfferCategoryDTO save(OfferCategoryDTO offerCategoryDTO) {
        log.debug("Request to save OfferCategory : {}", offerCategoryDTO);
        OfferCategory offerCategory = offerCategoryMapper.toEntity(offerCategoryDTO);
        offerCategory = offerCategoryRepository.save(offerCategory);
        return offerCategoryMapper.toDto(offerCategory);
    }

    @Override
    public OfferCategoryDTO update(OfferCategoryDTO offerCategoryDTO) {
        log.debug("Request to update OfferCategory : {}", offerCategoryDTO);
        OfferCategory offerCategory = offerCategoryMapper.toEntity(offerCategoryDTO);
        offerCategory = offerCategoryRepository.save(offerCategory);
        return offerCategoryMapper.toDto(offerCategory);
    }

    @Override
    public Optional<OfferCategoryDTO> partialUpdate(OfferCategoryDTO offerCategoryDTO) {
        log.debug("Request to partially update OfferCategory : {}", offerCategoryDTO);

        return offerCategoryRepository
            .findById(offerCategoryDTO.getId())
            .map(existingOfferCategory -> {
                offerCategoryMapper.partialUpdate(existingOfferCategory, offerCategoryDTO);

                return existingOfferCategory;
            })
            .map(offerCategoryRepository::save)
            .map(offerCategoryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OfferCategoryDTO> findAll() {
        log.debug("Request to get all OfferCategories");
        return offerCategoryRepository.findAll().stream().map(offerCategoryMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OfferCategoryDTO> findOne(Long id) {
        log.debug("Request to get OfferCategory : {}", id);
        return offerCategoryRepository.findById(id).map(offerCategoryMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete OfferCategory : {}", id);
        offerCategoryRepository.deleteById(id);
    }
}
