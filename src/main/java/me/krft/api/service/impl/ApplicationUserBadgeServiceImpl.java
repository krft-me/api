package me.krft.api.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import me.krft.api.domain.ApplicationUserBadge;
import me.krft.api.repository.ApplicationUserBadgeRepository;
import me.krft.api.service.ApplicationUserBadgeService;
import me.krft.api.service.dto.ApplicationUserBadgeDTO;
import me.krft.api.service.mapper.ApplicationUserBadgeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ApplicationUserBadge}.
 */
@Service
@Transactional
public class ApplicationUserBadgeServiceImpl implements ApplicationUserBadgeService {

    private final Logger log = LoggerFactory.getLogger(ApplicationUserBadgeServiceImpl.class);

    private final ApplicationUserBadgeRepository applicationUserBadgeRepository;

    private final ApplicationUserBadgeMapper applicationUserBadgeMapper;

    public ApplicationUserBadgeServiceImpl(
        ApplicationUserBadgeRepository applicationUserBadgeRepository,
        ApplicationUserBadgeMapper applicationUserBadgeMapper
    ) {
        this.applicationUserBadgeRepository = applicationUserBadgeRepository;
        this.applicationUserBadgeMapper = applicationUserBadgeMapper;
    }

    @Override
    public ApplicationUserBadgeDTO save(ApplicationUserBadgeDTO applicationUserBadgeDTO) {
        log.debug("Request to save ApplicationUserBadge : {}", applicationUserBadgeDTO);
        ApplicationUserBadge applicationUserBadge = applicationUserBadgeMapper.toEntity(applicationUserBadgeDTO);
        applicationUserBadge = applicationUserBadgeRepository.save(applicationUserBadge);
        return applicationUserBadgeMapper.toDto(applicationUserBadge);
    }

    @Override
    public ApplicationUserBadgeDTO update(ApplicationUserBadgeDTO applicationUserBadgeDTO) {
        log.debug("Request to update ApplicationUserBadge : {}", applicationUserBadgeDTO);
        ApplicationUserBadge applicationUserBadge = applicationUserBadgeMapper.toEntity(applicationUserBadgeDTO);
        applicationUserBadge = applicationUserBadgeRepository.save(applicationUserBadge);
        return applicationUserBadgeMapper.toDto(applicationUserBadge);
    }

    @Override
    public Optional<ApplicationUserBadgeDTO> partialUpdate(ApplicationUserBadgeDTO applicationUserBadgeDTO) {
        log.debug("Request to partially update ApplicationUserBadge : {}", applicationUserBadgeDTO);

        return applicationUserBadgeRepository
            .findById(applicationUserBadgeDTO.getId())
            .map(existingApplicationUserBadge -> {
                applicationUserBadgeMapper.partialUpdate(existingApplicationUserBadge, applicationUserBadgeDTO);

                return existingApplicationUserBadge;
            })
            .map(applicationUserBadgeRepository::save)
            .map(applicationUserBadgeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ApplicationUserBadgeDTO> findAll() {
        log.debug("Request to get all ApplicationUserBadges");
        return applicationUserBadgeRepository
            .findAll()
            .stream()
            .map(applicationUserBadgeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ApplicationUserBadgeDTO> findOne(Long id) {
        log.debug("Request to get ApplicationUserBadge : {}", id);
        return applicationUserBadgeRepository.findById(id).map(applicationUserBadgeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ApplicationUserBadge : {}", id);
        applicationUserBadgeRepository.deleteById(id);
    }
}
