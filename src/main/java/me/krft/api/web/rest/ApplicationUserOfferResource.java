package me.krft.api.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import me.krft.api.domain.ApplicationUserOffer;
import me.krft.api.repository.ApplicationUserOfferRepository;
import me.krft.api.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link me.krft.api.domain.ApplicationUserOffer}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ApplicationUserOfferResource {

    private final Logger log = LoggerFactory.getLogger(ApplicationUserOfferResource.class);

    private static final String ENTITY_NAME = "krftmeApplicationUserOffer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ApplicationUserOfferRepository applicationUserOfferRepository;

    public ApplicationUserOfferResource(ApplicationUserOfferRepository applicationUserOfferRepository) {
        this.applicationUserOfferRepository = applicationUserOfferRepository;
    }

    /**
     * {@code POST  /application-user-offers} : Create a new applicationUserOffer.
     *
     * @param applicationUserOffer the applicationUserOffer to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new applicationUserOffer, or with status {@code 400 (Bad Request)} if the applicationUserOffer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/application-user-offers")
    public ResponseEntity<ApplicationUserOffer> createApplicationUserOffer(@Valid @RequestBody ApplicationUserOffer applicationUserOffer)
        throws URISyntaxException {
        log.debug("REST request to save ApplicationUserOffer : {}", applicationUserOffer);
        if (applicationUserOffer.getId() != null) {
            throw new BadRequestAlertException("A new applicationUserOffer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApplicationUserOffer result = applicationUserOfferRepository.save(applicationUserOffer);
        return ResponseEntity
            .created(new URI("/api/application-user-offers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /application-user-offers/:id} : Updates an existing applicationUserOffer.
     *
     * @param id the id of the applicationUserOffer to save.
     * @param applicationUserOffer the applicationUserOffer to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated applicationUserOffer,
     * or with status {@code 400 (Bad Request)} if the applicationUserOffer is not valid,
     * or with status {@code 500 (Internal Server Error)} if the applicationUserOffer couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/application-user-offers/{id}")
    public ResponseEntity<ApplicationUserOffer> updateApplicationUserOffer(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ApplicationUserOffer applicationUserOffer
    ) throws URISyntaxException {
        log.debug("REST request to update ApplicationUserOffer : {}, {}", id, applicationUserOffer);
        if (applicationUserOffer.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, applicationUserOffer.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!applicationUserOfferRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ApplicationUserOffer result = applicationUserOfferRepository.save(applicationUserOffer);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, applicationUserOffer.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /application-user-offers/:id} : Partial updates given fields of an existing applicationUserOffer, field will ignore if it is null
     *
     * @param id the id of the applicationUserOffer to save.
     * @param applicationUserOffer the applicationUserOffer to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated applicationUserOffer,
     * or with status {@code 400 (Bad Request)} if the applicationUserOffer is not valid,
     * or with status {@code 404 (Not Found)} if the applicationUserOffer is not found,
     * or with status {@code 500 (Internal Server Error)} if the applicationUserOffer couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/application-user-offers/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ApplicationUserOffer> partialUpdateApplicationUserOffer(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ApplicationUserOffer applicationUserOffer
    ) throws URISyntaxException {
        log.debug("REST request to partial update ApplicationUserOffer partially : {}, {}", id, applicationUserOffer);
        if (applicationUserOffer.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, applicationUserOffer.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!applicationUserOfferRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ApplicationUserOffer> result = applicationUserOfferRepository
            .findById(applicationUserOffer.getId())
            .map(existingApplicationUserOffer -> {
                if (applicationUserOffer.getDescription() != null) {
                    existingApplicationUserOffer.setDescription(applicationUserOffer.getDescription());
                }

                return existingApplicationUserOffer;
            })
            .map(applicationUserOfferRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, applicationUserOffer.getId().toString())
        );
    }

    /**
     * {@code GET  /application-user-offers} : get all the applicationUserOffers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of applicationUserOffers in body.
     */
    @GetMapping("/application-user-offers")
    public List<ApplicationUserOffer> getAllApplicationUserOffers() {
        log.debug("REST request to get all ApplicationUserOffers");
        return applicationUserOfferRepository.findAll();
    }

    /**
     * {@code GET  /application-user-offers/:id} : get the "id" applicationUserOffer.
     *
     * @param id the id of the applicationUserOffer to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the applicationUserOffer, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/application-user-offers/{id}")
    public ResponseEntity<ApplicationUserOffer> getApplicationUserOffer(@PathVariable Long id) {
        log.debug("REST request to get ApplicationUserOffer : {}", id);
        Optional<ApplicationUserOffer> applicationUserOffer = applicationUserOfferRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(applicationUserOffer);
    }

    /**
     * {@code DELETE  /application-user-offers/:id} : delete the "id" applicationUserOffer.
     *
     * @param id the id of the applicationUserOffer to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/application-user-offers/{id}")
    public ResponseEntity<Void> deleteApplicationUserOffer(@PathVariable Long id) {
        log.debug("REST request to delete ApplicationUserOffer : {}", id);
        applicationUserOfferRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
