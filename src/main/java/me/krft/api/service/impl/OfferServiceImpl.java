package me.krft.api.service.impl;

import java.util.List;
import java.util.Optional;
import me.krft.api.domain.Offer;
import me.krft.api.repository.OfferRepository;
import me.krft.api.service.OfferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Offer}.
 */
@Service
@Transactional
public class OfferServiceImpl implements OfferService {

    private final Logger log = LoggerFactory.getLogger(OfferServiceImpl.class);

    private final OfferRepository offerRepository;

    public OfferServiceImpl(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    @Override
    public Offer save(Offer offer) {
        log.debug("Request to save Offer : {}", offer);
        return offerRepository.save(offer);
    }

    @Override
    public Offer update(Offer offer) {
        log.debug("Request to update Offer : {}", offer);
        return offerRepository.save(offer);
    }

    @Override
    public Optional<Offer> partialUpdate(Offer offer) {
        log.debug("Request to partially update Offer : {}", offer);

        return offerRepository
            .findById(offer.getId())
            .map(existingOffer -> {
                if (offer.getName() != null) {
                    existingOffer.setName(offer.getName());
                }

                return existingOffer;
            })
            .map(offerRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Offer> findAll() {
        log.debug("Request to get all Offers");
        return offerRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Offer> findOne(Long id) {
        log.debug("Request to get Offer : {}", id);
        return offerRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Offer : {}", id);
        offerRepository.deleteById(id);
    }
}
