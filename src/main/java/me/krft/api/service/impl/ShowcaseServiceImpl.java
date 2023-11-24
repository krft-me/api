package me.krft.api.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import me.krft.api.domain.Showcase;
import me.krft.api.repository.ShowcaseRepository;
import me.krft.api.service.ShowcaseService;
import me.krft.api.service.dto.ShowcaseDTO;
import me.krft.api.service.mapper.ShowcaseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Showcase}.
 */
@Service
@Transactional
public class ShowcaseServiceImpl implements ShowcaseService {

    private final Logger log = LoggerFactory.getLogger(ShowcaseServiceImpl.class);

    private final ShowcaseRepository showcaseRepository;

    private final ShowcaseMapper showcaseMapper;

    public ShowcaseServiceImpl(ShowcaseRepository showcaseRepository, ShowcaseMapper showcaseMapper) {
        this.showcaseRepository = showcaseRepository;
        this.showcaseMapper = showcaseMapper;
    }

    @Override
    public ShowcaseDTO save(ShowcaseDTO showcaseDTO) {
        log.debug("Request to save Showcase : {}", showcaseDTO);
        Showcase showcase = showcaseMapper.toEntity(showcaseDTO);
        showcase = showcaseRepository.save(showcase);
        return showcaseMapper.toDto(showcase);
    }

    @Override
    public ShowcaseDTO update(ShowcaseDTO showcaseDTO) {
        log.debug("Request to update Showcase : {}", showcaseDTO);
        Showcase showcase = showcaseMapper.toEntity(showcaseDTO);
        showcase = showcaseRepository.save(showcase);
        return showcaseMapper.toDto(showcase);
    }

    @Override
    public Optional<ShowcaseDTO> partialUpdate(ShowcaseDTO showcaseDTO) {
        log.debug("Request to partially update Showcase : {}", showcaseDTO);

        return showcaseRepository
            .findById(showcaseDTO.getId())
            .map(existingShowcase -> {
                showcaseMapper.partialUpdate(existingShowcase, showcaseDTO);

                return existingShowcase;
            })
            .map(showcaseRepository::save)
            .map(showcaseMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ShowcaseDTO> findAll() {
        log.debug("Request to get all Showcases");
        return showcaseRepository.findAll().stream().map(showcaseMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ShowcaseDTO> findOne(Long id) {
        log.debug("Request to get Showcase : {}", id);
        return showcaseRepository.findById(id).map(showcaseMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Showcase : {}", id);
        showcaseRepository.deleteById(id);
    }
}
