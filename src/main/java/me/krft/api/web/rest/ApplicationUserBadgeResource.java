package me.krft.api.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import me.krft.api.domain.ApplicationUserBadge;
import me.krft.api.repository.ApplicationUserBadgeRepository;
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
 * REST controller for managing {@link me.krft.api.domain.ApplicationUserBadge}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ApplicationUserBadgeResource {

    private final Logger log = LoggerFactory.getLogger(ApplicationUserBadgeResource.class);

    private static final String ENTITY_NAME = "krftmeApplicationUserBadge";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ApplicationUserBadgeRepository applicationUserBadgeRepository;

    public ApplicationUserBadgeResource(ApplicationUserBadgeRepository applicationUserBadgeRepository) {
        this.applicationUserBadgeRepository = applicationUserBadgeRepository;
    }

    /**
     * {@code POST  /application-user-badges} : Create a new applicationUserBadge.
     *
     * @param applicationUserBadge the applicationUserBadge to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new applicationUserBadge, or with status {@code 400 (Bad Request)} if the applicationUserBadge has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/application-user-badges")
    public ResponseEntity<ApplicationUserBadge> createApplicationUserBadge(@Valid @RequestBody ApplicationUserBadge applicationUserBadge)
        throws URISyntaxException {
        log.debug("REST request to save ApplicationUserBadge : {}", applicationUserBadge);
        if (applicationUserBadge.getId() != null) {
            throw new BadRequestAlertException("A new applicationUserBadge cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApplicationUserBadge result = applicationUserBadgeRepository.save(applicationUserBadge);
        return ResponseEntity
            .created(new URI("/api/application-user-badges/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /application-user-badges/:id} : Updates an existing applicationUserBadge.
     *
     * @param id the id of the applicationUserBadge to save.
     * @param applicationUserBadge the applicationUserBadge to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated applicationUserBadge,
     * or with status {@code 400 (Bad Request)} if the applicationUserBadge is not valid,
     * or with status {@code 500 (Internal Server Error)} if the applicationUserBadge couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/application-user-badges/{id}")
    public ResponseEntity<ApplicationUserBadge> updateApplicationUserBadge(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ApplicationUserBadge applicationUserBadge
    ) throws URISyntaxException {
        log.debug("REST request to update ApplicationUserBadge : {}, {}", id, applicationUserBadge);
        if (applicationUserBadge.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, applicationUserBadge.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!applicationUserBadgeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ApplicationUserBadge result = applicationUserBadgeRepository.save(applicationUserBadge);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, applicationUserBadge.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /application-user-badges/:id} : Partial updates given fields of an existing applicationUserBadge, field will ignore if it is null
     *
     * @param id the id of the applicationUserBadge to save.
     * @param applicationUserBadge the applicationUserBadge to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated applicationUserBadge,
     * or with status {@code 400 (Bad Request)} if the applicationUserBadge is not valid,
     * or with status {@code 404 (Not Found)} if the applicationUserBadge is not found,
     * or with status {@code 500 (Internal Server Error)} if the applicationUserBadge couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/application-user-badges/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ApplicationUserBadge> partialUpdateApplicationUserBadge(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ApplicationUserBadge applicationUserBadge
    ) throws URISyntaxException {
        log.debug("REST request to partial update ApplicationUserBadge partially : {}, {}", id, applicationUserBadge);
        if (applicationUserBadge.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, applicationUserBadge.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!applicationUserBadgeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ApplicationUserBadge> result = applicationUserBadgeRepository
            .findById(applicationUserBadge.getId())
            .map(existingApplicationUserBadge -> {
                if (applicationUserBadge.getObtentionDate() != null) {
                    existingApplicationUserBadge.setObtentionDate(applicationUserBadge.getObtentionDate());
                }

                return existingApplicationUserBadge;
            })
            .map(applicationUserBadgeRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, applicationUserBadge.getId().toString())
        );
    }

    /**
     * {@code GET  /application-user-badges} : get all the applicationUserBadges.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of applicationUserBadges in body.
     */
    @GetMapping("/application-user-badges")
    public List<ApplicationUserBadge> getAllApplicationUserBadges() {
        log.debug("REST request to get all ApplicationUserBadges");
        return applicationUserBadgeRepository.findAll();
    }

    /**
     * {@code GET  /application-user-badges/:id} : get the "id" applicationUserBadge.
     *
     * @param id the id of the applicationUserBadge to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the applicationUserBadge, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/application-user-badges/{id}")
    public ResponseEntity<ApplicationUserBadge> getApplicationUserBadge(@PathVariable Long id) {
        log.debug("REST request to get ApplicationUserBadge : {}", id);
        Optional<ApplicationUserBadge> applicationUserBadge = applicationUserBadgeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(applicationUserBadge);
    }

    /**
     * {@code DELETE  /application-user-badges/:id} : delete the "id" applicationUserBadge.
     *
     * @param id the id of the applicationUserBadge to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/application-user-badges/{id}")
    public ResponseEntity<Void> deleteApplicationUserBadge(@PathVariable Long id) {
        log.debug("REST request to delete ApplicationUserBadge : {}", id);
        applicationUserBadgeRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
