package me.krft.api.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import me.krft.api.domain.Offer;
import me.krft.api.repository.OfferRepository;
import me.krft.api.service.OfferService;
import me.krft.api.service.dto.OfferDTO;
import me.krft.api.service.mapper.OfferMapper;
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

    private final OfferMapper offerMapper;

    public OfferServiceImpl(OfferRepository offerRepository, OfferMapper offerMapper) {
        this.offerRepository = offerRepository;
        this.offerMapper = offerMapper;
    }

    @Override
    public OfferDTO save(OfferDTO offerDTO) {
        log.debug("Request to save Offer : {}", offerDTO);
        Offer offer = offerMapper.toEntity(offerDTO);
        offer = offerRepository.save(offer);
        return offerMapper.toDto(offer);
    }

    @Override
    public OfferDTO update(OfferDTO offerDTO) {
        log.debug("Request to update Offer : {}", offerDTO);
        Offer offer = offerMapper.toEntity(offerDTO);
        offer = offerRepository.save(offer);
        return offerMapper.toDto(offer);
    }

    @Override
    public Optional<OfferDTO> partialUpdate(OfferDTO offerDTO) {
        log.debug("Request to partially update Offer : {}", offerDTO);

        return offerRepository
            .findById(offerDTO.getId())
            .map(existingOffer -> {
                offerMapper.partialUpdate(existingOffer, offerDTO);

                return existingOffer;
            })
            .map(offerRepository::save)
            .map(offerMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OfferDTO> findAll() {
        log.debug("Request to get all Offers");
        return offerRepository.findAll().stream().map(offerMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OfferDTO> findOne(Long id) {
        log.debug("Request to get Offer : {}", id);
        return offerRepository.findById(id).map(offerMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Offer : {}", id);
        offerRepository.deleteById(id);
    }
}
