package me.krft.api.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import me.krft.api.domain.ApplicationUserOffer;
import me.krft.api.repository.ApplicationUserOfferRepository;
import me.krft.api.service.ApplicationUserOfferService;
import me.krft.api.service.dto.ApplicationUserOfferDTO;
import me.krft.api.service.mapper.ApplicationUserOfferMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link me.krft.api.domain.ApplicationUserOffer}.
 */
@Service
@Transactional
public class ApplicationUserOfferServiceImpl implements ApplicationUserOfferService {

    private final Logger log = LoggerFactory.getLogger(ApplicationUserOfferServiceImpl.class);

    private final ApplicationUserOfferRepository applicationUserOfferRepository;

    private final ApplicationUserOfferMapper applicationUserOfferMapper;

    public ApplicationUserOfferServiceImpl(
        ApplicationUserOfferRepository applicationUserOfferRepository,
        ApplicationUserOfferMapper applicationUserOfferMapper
    ) {
        this.applicationUserOfferRepository = applicationUserOfferRepository;
        this.applicationUserOfferMapper = applicationUserOfferMapper;
    }

    @Override
    public ApplicationUserOffer save(ApplicationUserOffer applicationUserOffer) {
        log.debug("Request to save ApplicationUserOffer : {}", applicationUserOffer);
        return applicationUserOfferRepository.save(applicationUserOffer);
    }

    @Override
    public ApplicationUserOffer update(ApplicationUserOffer applicationUserOffer) {
        log.debug("Request to update ApplicationUserOffer : {}", applicationUserOffer);
        return applicationUserOfferRepository.save(applicationUserOffer);
    }

    @Override
    public Optional<ApplicationUserOffer> partialUpdate(ApplicationUserOffer applicationUserOffer) {
        log.debug("Request to partially update ApplicationUserOffer : {}", applicationUserOffer);

        return applicationUserOfferRepository
            .findById(applicationUserOffer.getId())
            .map(existingApplicationUserOffer -> {
                if (applicationUserOffer.getDescription() != null) {
                    existingApplicationUserOffer.setDescription(applicationUserOffer.getDescription());
                }
                if (applicationUserOffer.getPrice() != null) {
                    existingApplicationUserOffer.setPrice(applicationUserOffer.getPrice());
                }
                if (applicationUserOffer.getActive() != null) {
                    existingApplicationUserOffer.setActive(applicationUserOffer.getActive());
                }

                return existingApplicationUserOffer;
            })
            .map(applicationUserOfferRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ApplicationUserOffer> findAll() {
        log.debug("Request to get all ApplicationUserOffers");
        return applicationUserOfferRepository.findAll();
    }

    public Page<ApplicationUserOffer> findAllWithEagerRelationships(Pageable pageable) {
        return applicationUserOfferRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ApplicationUserOffer> findOne(Long id) {
        log.debug("Request to get ApplicationUserOffer : {}", id);
        return applicationUserOfferRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ApplicationUserOffer : {}", id);
        applicationUserOfferRepository.deleteById(id);
    }

    @Override
    public List<ApplicationUserOfferDTO> testMapper() {
        log.debug("Request to get all ApplicationUserOfferDTO");
        return applicationUserOfferRepository.findAll().stream().map(this.applicationUserOfferMapper::toDTO).collect(Collectors.toList());
    }
}
