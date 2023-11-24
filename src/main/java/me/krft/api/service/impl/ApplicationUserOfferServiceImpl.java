package me.krft.api.service.impl;

import java.util.LinkedList;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ApplicationUserOffer}.
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
    public ApplicationUserOfferDTO save(ApplicationUserOfferDTO applicationUserOfferDTO) {
        log.debug("Request to save ApplicationUserOffer : {}", applicationUserOfferDTO);
        ApplicationUserOffer applicationUserOffer = applicationUserOfferMapper.toEntity(applicationUserOfferDTO);
        applicationUserOffer = applicationUserOfferRepository.save(applicationUserOffer);
        return applicationUserOfferMapper.toDto(applicationUserOffer);
    }

    @Override
    public ApplicationUserOfferDTO update(ApplicationUserOfferDTO applicationUserOfferDTO) {
        log.debug("Request to update ApplicationUserOffer : {}", applicationUserOfferDTO);
        ApplicationUserOffer applicationUserOffer = applicationUserOfferMapper.toEntity(applicationUserOfferDTO);
        applicationUserOffer = applicationUserOfferRepository.save(applicationUserOffer);
        return applicationUserOfferMapper.toDto(applicationUserOffer);
    }

    @Override
    public Optional<ApplicationUserOfferDTO> partialUpdate(ApplicationUserOfferDTO applicationUserOfferDTO) {
        log.debug("Request to partially update ApplicationUserOffer : {}", applicationUserOfferDTO);

        return applicationUserOfferRepository
            .findById(applicationUserOfferDTO.getId())
            .map(existingApplicationUserOffer -> {
                applicationUserOfferMapper.partialUpdate(existingApplicationUserOffer, applicationUserOfferDTO);

                return existingApplicationUserOffer;
            })
            .map(applicationUserOfferRepository::save)
            .map(applicationUserOfferMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ApplicationUserOfferDTO> findAll() {
        log.debug("Request to get all ApplicationUserOffers");
        return applicationUserOfferRepository
            .findAll()
            .stream()
            .map(applicationUserOfferMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ApplicationUserOfferDTO> findOne(Long id) {
        log.debug("Request to get ApplicationUserOffer : {}", id);
        return applicationUserOfferRepository.findById(id).map(applicationUserOfferMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ApplicationUserOffer : {}", id);
        applicationUserOfferRepository.deleteById(id);
    }
}
