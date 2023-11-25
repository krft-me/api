package me.krft.api.service.impl;

import java.util.List;
import java.util.Optional;
import me.krft.api.domain.OfferCategory;
import me.krft.api.repository.OfferCategoryRepository;
import me.krft.api.service.OfferCategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link me.krft.api.domain.OfferCategory}.
 */
@Service
@Transactional
public class OfferCategoryServiceImpl implements OfferCategoryService {

    private final Logger log = LoggerFactory.getLogger(OfferCategoryServiceImpl.class);

    private final OfferCategoryRepository offerCategoryRepository;

    public OfferCategoryServiceImpl(OfferCategoryRepository offerCategoryRepository) {
        this.offerCategoryRepository = offerCategoryRepository;
    }

    @Override
    public OfferCategory save(OfferCategory offerCategory) {
        log.debug("Request to save OfferCategory : {}", offerCategory);
        return offerCategoryRepository.save(offerCategory);
    }

    @Override
    public OfferCategory update(OfferCategory offerCategory) {
        log.debug("Request to update OfferCategory : {}", offerCategory);
        return offerCategoryRepository.save(offerCategory);
    }

    @Override
    public Optional<OfferCategory> partialUpdate(OfferCategory offerCategory) {
        log.debug("Request to partially update OfferCategory : {}", offerCategory);

        return offerCategoryRepository
            .findById(offerCategory.getId())
            .map(existingOfferCategory -> {
                if (offerCategory.getLabel() != null) {
                    existingOfferCategory.setLabel(offerCategory.getLabel());
                }

                return existingOfferCategory;
            })
            .map(offerCategoryRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OfferCategory> findAll() {
        log.debug("Request to get all OfferCategories");
        return offerCategoryRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OfferCategory> findOne(Long id) {
        log.debug("Request to get OfferCategory : {}", id);
        return offerCategoryRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete OfferCategory : {}", id);
        offerCategoryRepository.deleteById(id);
    }
}
