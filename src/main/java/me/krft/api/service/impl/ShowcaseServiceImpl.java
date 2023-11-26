package me.krft.api.service.impl;

import java.util.List;
import java.util.Optional;
import me.krft.api.domain.Showcase;
import me.krft.api.repository.ShowcaseRepository;
import me.krft.api.service.ShowcaseService;
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

    public ShowcaseServiceImpl(ShowcaseRepository showcaseRepository) {
        this.showcaseRepository = showcaseRepository;
    }

    @Override
    public Showcase save(Showcase showcase) {
        log.debug("Request to save Showcase : {}", showcase);
        return showcaseRepository.save(showcase);
    }

    @Override
    public Showcase update(Showcase showcase) {
        log.debug("Request to update Showcase : {}", showcase);
        return showcaseRepository.save(showcase);
    }

    @Override
    public Optional<Showcase> partialUpdate(Showcase showcase) {
        log.debug("Request to partially update Showcase : {}", showcase);

        return showcaseRepository
            .findById(showcase.getId())
            .map(existingShowcase -> {
                if (showcase.getImageId() != null) {
                    existingShowcase.setImageId(showcase.getImageId());
                }

                return existingShowcase;
            })
            .map(showcaseRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Showcase> findAll() {
        log.debug("Request to get all Showcases");
        return showcaseRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Showcase> findOne(Long id) {
        log.debug("Request to get Showcase : {}", id);
        return showcaseRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Showcase : {}", id);
        showcaseRepository.deleteById(id);
    }
}
