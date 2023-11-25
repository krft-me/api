package me.krft.api.service.impl;

import java.util.List;
import java.util.Optional;
import me.krft.api.domain.Badge;
import me.krft.api.repository.BadgeRepository;
import me.krft.api.service.BadgeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link me.krft.api.domain.Badge}.
 */
@Service
@Transactional
public class BadgeServiceImpl implements BadgeService {

    private final Logger log = LoggerFactory.getLogger(BadgeServiceImpl.class);

    private final BadgeRepository badgeRepository;

    public BadgeServiceImpl(BadgeRepository badgeRepository) {
        this.badgeRepository = badgeRepository;
    }

    @Override
    public Badge save(Badge badge) {
        log.debug("Request to save Badge : {}", badge);
        return badgeRepository.save(badge);
    }

    @Override
    public Badge update(Badge badge) {
        log.debug("Request to update Badge : {}", badge);
        return badgeRepository.save(badge);
    }

    @Override
    public Optional<Badge> partialUpdate(Badge badge) {
        log.debug("Request to partially update Badge : {}", badge);

        return badgeRepository
            .findById(badge.getId())
            .map(existingBadge -> {
                if (badge.getLabel() != null) {
                    existingBadge.setLabel(badge.getLabel());
                }
                if (badge.getPicture() != null) {
                    existingBadge.setPicture(badge.getPicture());
                }

                return existingBadge;
            })
            .map(badgeRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Badge> findAll() {
        log.debug("Request to get all Badges");
        return badgeRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Badge> findOne(Long id) {
        log.debug("Request to get Badge : {}", id);
        return badgeRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Badge : {}", id);
        badgeRepository.deleteById(id);
    }
}
