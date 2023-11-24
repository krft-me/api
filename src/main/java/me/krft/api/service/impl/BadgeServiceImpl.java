package me.krft.api.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import me.krft.api.domain.Badge;
import me.krft.api.repository.BadgeRepository;
import me.krft.api.service.BadgeService;
import me.krft.api.service.dto.BadgeDTO;
import me.krft.api.service.mapper.BadgeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Badge}.
 */
@Service
@Transactional
public class BadgeServiceImpl implements BadgeService {

    private final Logger log = LoggerFactory.getLogger(BadgeServiceImpl.class);

    private final BadgeRepository badgeRepository;

    private final BadgeMapper badgeMapper;

    public BadgeServiceImpl(BadgeRepository badgeRepository, BadgeMapper badgeMapper) {
        this.badgeRepository = badgeRepository;
        this.badgeMapper = badgeMapper;
    }

    @Override
    public BadgeDTO save(BadgeDTO badgeDTO) {
        log.debug("Request to save Badge : {}", badgeDTO);
        Badge badge = badgeMapper.toEntity(badgeDTO);
        badge = badgeRepository.save(badge);
        return badgeMapper.toDto(badge);
    }

    @Override
    public BadgeDTO update(BadgeDTO badgeDTO) {
        log.debug("Request to update Badge : {}", badgeDTO);
        Badge badge = badgeMapper.toEntity(badgeDTO);
        badge = badgeRepository.save(badge);
        return badgeMapper.toDto(badge);
    }

    @Override
    public Optional<BadgeDTO> partialUpdate(BadgeDTO badgeDTO) {
        log.debug("Request to partially update Badge : {}", badgeDTO);

        return badgeRepository
            .findById(badgeDTO.getId())
            .map(existingBadge -> {
                badgeMapper.partialUpdate(existingBadge, badgeDTO);

                return existingBadge;
            })
            .map(badgeRepository::save)
            .map(badgeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BadgeDTO> findAll() {
        log.debug("Request to get all Badges");
        return badgeRepository.findAll().stream().map(badgeMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BadgeDTO> findOne(Long id) {
        log.debug("Request to get Badge : {}", id);
        return badgeRepository.findById(id).map(badgeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Badge : {}", id);
        badgeRepository.deleteById(id);
    }
}
