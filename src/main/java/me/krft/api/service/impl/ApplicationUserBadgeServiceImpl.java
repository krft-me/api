package me.krft.api.service.impl;

import java.util.List;
import java.util.Optional;
import me.krft.api.domain.ApplicationUserBadge;
import me.krft.api.repository.ApplicationUserBadgeRepository;
import me.krft.api.service.ApplicationUserBadgeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link me.krft.api.domain.ApplicationUserBadge}.
 */
@Service
@Transactional
public class ApplicationUserBadgeServiceImpl implements ApplicationUserBadgeService {

    private final Logger log = LoggerFactory.getLogger(ApplicationUserBadgeServiceImpl.class);

    private final ApplicationUserBadgeRepository applicationUserBadgeRepository;

    public ApplicationUserBadgeServiceImpl(ApplicationUserBadgeRepository applicationUserBadgeRepository) {
        this.applicationUserBadgeRepository = applicationUserBadgeRepository;
    }

    @Override
    public ApplicationUserBadge save(ApplicationUserBadge applicationUserBadge) {
        log.debug("Request to save ApplicationUserBadge : {}", applicationUserBadge);
        return applicationUserBadgeRepository.save(applicationUserBadge);
    }

    @Override
    public ApplicationUserBadge update(ApplicationUserBadge applicationUserBadge) {
        log.debug("Request to update ApplicationUserBadge : {}", applicationUserBadge);
        return applicationUserBadgeRepository.save(applicationUserBadge);
    }

    @Override
    public Optional<ApplicationUserBadge> partialUpdate(ApplicationUserBadge applicationUserBadge) {
        log.debug("Request to partially update ApplicationUserBadge : {}", applicationUserBadge);

        return applicationUserBadgeRepository
            .findById(applicationUserBadge.getId())
            .map(existingApplicationUserBadge -> {
                if (applicationUserBadge.getObtainedDate() != null) {
                    existingApplicationUserBadge.setObtainedDate(applicationUserBadge.getObtainedDate());
                }

                return existingApplicationUserBadge;
            })
            .map(applicationUserBadgeRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ApplicationUserBadge> findAll() {
        log.debug("Request to get all ApplicationUserBadges");
        return applicationUserBadgeRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ApplicationUserBadge> findOne(Long id) {
        log.debug("Request to get ApplicationUserBadge : {}", id);
        return applicationUserBadgeRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ApplicationUserBadge : {}", id);
        applicationUserBadgeRepository.deleteById(id);
    }
}
