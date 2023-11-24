package me.krft.api.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import me.krft.api.repository.ApplicationUserOfferRepository;
import me.krft.api.service.ApplicationUserOfferService;
import me.krft.api.service.dto.ApplicationUserOfferDTO;
import me.krft.api.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link me.krft.api.domain.ApplicationUserOffer}.
 */
@RestController
@RequestMapping("/api")
public class ApplicationUserOfferResource {

    private final Logger log = LoggerFactory.getLogger(ApplicationUserOfferResource.class);

    private static final String ENTITY_NAME = "krftmeApplicationUserOffer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ApplicationUserOfferService applicationUserOfferService;

    private final ApplicationUserOfferRepository applicationUserOfferRepository;

    public ApplicationUserOfferResource(
        ApplicationUserOfferService applicationUserOfferService,
        ApplicationUserOfferRepository applicationUserOfferRepository
    ) {
        this.applicationUserOfferService = applicationUserOfferService;
        this.applicationUserOfferRepository = applicationUserOfferRepository;
    }

    /**
     * {@code POST  /application-user-offers} : Create a new applicationUserOffer.
     *
     * @param applicationUserOfferDTO the applicationUserOfferDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new applicationUserOfferDTO, or with status {@code 400 (Bad Request)} if the applicationUserOffer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/application-user-offers")
    public ResponseEntity<ApplicationUserOfferDTO> createApplicationUserOffer(
        @Valid @RequestBody ApplicationUserOfferDTO applicationUserOfferDTO
    ) throws URISyntaxException {
        log.debug("REST request to save ApplicationUserOffer : {}", applicationUserOfferDTO);
        if (applicationUserOfferDTO.getId() != null) {
            throw new BadRequestAlertException("A new applicationUserOffer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApplicationUserOfferDTO result = applicationUserOfferService.save(applicationUserOfferDTO);
        return ResponseEntity
            .created(new URI("/api/application-user-offers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /application-user-offers/:id} : Updates an existing applicationUserOffer.
     *
     * @param id the id of the applicationUserOfferDTO to save.
     * @param applicationUserOfferDTO the applicationUserOfferDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated applicationUserOfferDTO,
     * or with status {@code 400 (Bad Request)} if the applicationUserOfferDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the applicationUserOfferDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/application-user-offers/{id}")
    public ResponseEntity<ApplicationUserOfferDTO> updateApplicationUserOffer(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ApplicationUserOfferDTO applicationUserOfferDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ApplicationUserOffer : {}, {}", id, applicationUserOfferDTO);
        if (applicationUserOfferDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, applicationUserOfferDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!applicationUserOfferRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ApplicationUserOfferDTO result = applicationUserOfferService.update(applicationUserOfferDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, applicationUserOfferDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /application-user-offers/:id} : Partial updates given fields of an existing applicationUserOffer, field will ignore if it is null
     *
     * @param id the id of the applicationUserOfferDTO to save.
     * @param applicationUserOfferDTO the applicationUserOfferDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated applicationUserOfferDTO,
     * or with status {@code 400 (Bad Request)} if the applicationUserOfferDTO is not valid,
     * or with status {@code 404 (Not Found)} if the applicationUserOfferDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the applicationUserOfferDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/application-user-offers/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ApplicationUserOfferDTO> partialUpdateApplicationUserOffer(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ApplicationUserOfferDTO applicationUserOfferDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ApplicationUserOffer partially : {}, {}", id, applicationUserOfferDTO);
        if (applicationUserOfferDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, applicationUserOfferDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!applicationUserOfferRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ApplicationUserOfferDTO> result = applicationUserOfferService.partialUpdate(applicationUserOfferDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, applicationUserOfferDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /application-user-offers} : get all the applicationUserOffers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of applicationUserOffers in body.
     */
    @GetMapping("/application-user-offers")
    public List<ApplicationUserOfferDTO> getAllApplicationUserOffers() {
        log.debug("REST request to get all ApplicationUserOffers");
        return applicationUserOfferService.findAll();
    }

    /**
     * {@code GET  /application-user-offers/:id} : get the "id" applicationUserOffer.
     *
     * @param id the id of the applicationUserOfferDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the applicationUserOfferDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/application-user-offers/{id}")
    public ResponseEntity<ApplicationUserOfferDTO> getApplicationUserOffer(@PathVariable Long id) {
        log.debug("REST request to get ApplicationUserOffer : {}", id);
        Optional<ApplicationUserOfferDTO> applicationUserOfferDTO = applicationUserOfferService.findOne(id);
        return ResponseUtil.wrapOrNotFound(applicationUserOfferDTO);
    }

    /**
     * {@code DELETE  /application-user-offers/:id} : delete the "id" applicationUserOffer.
     *
     * @param id the id of the applicationUserOfferDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/application-user-offers/{id}")
    public ResponseEntity<Void> deleteApplicationUserOffer(@PathVariable Long id) {
        log.debug("REST request to delete ApplicationUserOffer : {}", id);
        applicationUserOfferService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
