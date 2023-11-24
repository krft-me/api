package me.krft.api.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import me.krft.api.repository.OfferRepository;
import me.krft.api.service.OfferService;
import me.krft.api.service.dto.OfferDTO;
import me.krft.api.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link me.krft.api.domain.Offer}.
 */
@RestController
@RequestMapping("/api")
public class OfferResource {

    private final Logger log = LoggerFactory.getLogger(OfferResource.class);

    private static final String ENTITY_NAME = "krftmeOffer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OfferService offerService;

    private final OfferRepository offerRepository;

    public OfferResource(OfferService offerService, OfferRepository offerRepository) {
        this.offerService = offerService;
        this.offerRepository = offerRepository;
    }

    /**
     * {@code POST  /offers} : Create a new offer.
     *
     * @param offerDTO the offerDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new offerDTO, or with status {@code 400 (Bad Request)} if the offer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/offers")
    public ResponseEntity<OfferDTO> createOffer(@Valid @RequestBody OfferDTO offerDTO) throws URISyntaxException {
        log.debug("REST request to save Offer : {}", offerDTO);
        if (offerDTO.getId() != null) {
            throw new BadRequestAlertException("A new offer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OfferDTO result = offerService.save(offerDTO);
        return ResponseEntity
            .created(new URI("/api/offers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /offers/:id} : Updates an existing offer.
     *
     * @param id the id of the offerDTO to save.
     * @param offerDTO the offerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated offerDTO,
     * or with status {@code 400 (Bad Request)} if the offerDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the offerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/offers/{id}")
    public ResponseEntity<OfferDTO> updateOffer(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody OfferDTO offerDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Offer : {}, {}", id, offerDTO);
        if (offerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, offerDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!offerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OfferDTO result = offerService.update(offerDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, offerDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /offers/:id} : Partial updates given fields of an existing offer, field will ignore if it is null
     *
     * @param id the id of the offerDTO to save.
     * @param offerDTO the offerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated offerDTO,
     * or with status {@code 400 (Bad Request)} if the offerDTO is not valid,
     * or with status {@code 404 (Not Found)} if the offerDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the offerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/offers/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OfferDTO> partialUpdateOffer(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody OfferDTO offerDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Offer partially : {}, {}", id, offerDTO);
        if (offerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, offerDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!offerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OfferDTO> result = offerService.partialUpdate(offerDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, offerDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /offers} : get all the offers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of offers in body.
     */
    @GetMapping("/offers")
    public List<OfferDTO> getAllOffers() {
        log.debug("REST request to get all Offers");
        return offerService.findAll();
    }

    /**
     * {@code GET  /offers/:id} : get the "id" offer.
     *
     * @param id the id of the offerDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the offerDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/offers/{id}")
    public ResponseEntity<OfferDTO> getOffer(@PathVariable Long id) {
        log.debug("REST request to get Offer : {}", id);
        Optional<OfferDTO> offerDTO = offerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(offerDTO);
    }

    /**
     * {@code DELETE  /offers/:id} : delete the "id" offer.
     *
     * @param id the id of the offerDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/offers/{id}")
    public ResponseEntity<Void> deleteOffer(@PathVariable Long id) {
        log.debug("REST request to delete Offer : {}", id);
        offerService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
