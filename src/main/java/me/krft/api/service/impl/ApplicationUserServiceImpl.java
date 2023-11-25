package me.krft.api.service.impl;

import java.util.List;
import java.util.Optional;
import me.krft.api.domain.ApplicationUser;
import me.krft.api.repository.ApplicationUserRepository;
import me.krft.api.service.ApplicationUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ApplicationUser}.
 */
@Service
@Transactional
public class ApplicationUserServiceImpl implements ApplicationUserService {

    private final Logger log = LoggerFactory.getLogger(ApplicationUserServiceImpl.class);

    private final ApplicationUserRepository applicationUserRepository;

    public ApplicationUserServiceImpl(ApplicationUserRepository applicationUserRepository) {
        this.applicationUserRepository = applicationUserRepository;
    }

    @Override
    public ApplicationUser save(ApplicationUser applicationUser) {
        log.debug("Request to save ApplicationUser : {}", applicationUser);
        return applicationUserRepository.save(applicationUser);
    }

    @Override
    public ApplicationUser update(ApplicationUser applicationUser) {
        log.debug("Request to update ApplicationUser : {}", applicationUser);
        return applicationUserRepository.save(applicationUser);
    }

    @Override
    public Optional<ApplicationUser> partialUpdate(ApplicationUser applicationUser) {
        log.debug("Request to partially update ApplicationUser : {}", applicationUser);

        return applicationUserRepository
            .findById(applicationUser.getId())
            .map(existingApplicationUser -> {
                if (applicationUser.getFirstName() != null) {
                    existingApplicationUser.setFirstName(applicationUser.getFirstName());
                }
                if (applicationUser.getLastName() != null) {
                    existingApplicationUser.setLastName(applicationUser.getLastName());
                }
                if (applicationUser.getUsername() != null) {
                    existingApplicationUser.setUsername(applicationUser.getUsername());
                }

                return existingApplicationUser;
            })
            .map(applicationUserRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ApplicationUser> findAll() {
        log.debug("Request to get all ApplicationUsers");
        return applicationUserRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ApplicationUser> findOne(Long id) {
        log.debug("Request to get ApplicationUser : {}", id);
        return applicationUserRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ApplicationUser : {}", id);
        applicationUserRepository.deleteById(id);
    }
}
