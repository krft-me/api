package me.krft.api.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import me.krft.api.domain.Badge;
import me.krft.api.repository.BadgeRepository;
import me.krft.api.service.BadgeService;
import me.krft.api.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link me.krft.api.domain.Badge}.
 */
@RestController
@RequestMapping("/api/badges")
public class BadgeResource {

    private final Logger log = LoggerFactory.getLogger(BadgeResource.class);

    private static final String ENTITY_NAME = "krftmeBadge";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BadgeService badgeService;

    private final BadgeRepository badgeRepository;

    public BadgeResource(BadgeService badgeService, BadgeRepository badgeRepository) {
        this.badgeService = badgeService;
        this.badgeRepository = badgeRepository;
    }

    /**
     * {@code POST  /badges} : Create a new badge.
     *
     * @param badge the badge to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new badge, or with status {@code 400 (Bad Request)} if the badge has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Badge> createBadge(@Valid @RequestBody Badge badge) throws URISyntaxException {
        log.debug("REST request to save Badge : {}", badge);
        if (badge.getId() != null) {
            throw new BadRequestAlertException("A new badge cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Badge result = badgeService.save(badge);
        return ResponseEntity
            .created(new URI("/api/badges/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /badges/:id} : Updates an existing badge.
     *
     * @param id the id of the badge to save.
     * @param badge the badge to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated badge,
     * or with status {@code 400 (Bad Request)} if the badge is not valid,
     * or with status {@code 500 (Internal Server Error)} if the badge couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Badge> updateBadge(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Badge badge)
        throws URISyntaxException {
        log.debug("REST request to update Badge : {}, {}", id, badge);
        if (badge.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, badge.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!badgeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Badge result = badgeService.update(badge);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, badge.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /badges/:id} : Partial updates given fields of an existing badge, field will ignore if it is null
     *
     * @param id the id of the badge to save.
     * @param badge the badge to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated badge,
     * or with status {@code 400 (Bad Request)} if the badge is not valid,
     * or with status {@code 404 (Not Found)} if the badge is not found,
     * or with status {@code 500 (Internal Server Error)} if the badge couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Badge> partialUpdateBadge(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Badge badge
    ) throws URISyntaxException {
        log.debug("REST request to partial update Badge partially : {}, {}", id, badge);
        if (badge.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, badge.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!badgeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Badge> result = badgeService.partialUpdate(badge);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, badge.getId().toString())
        );
    }

    /**
     * {@code GET  /badges} : get all the badges.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of badges in body.
     */
    @GetMapping("")
    public List<Badge> getAllBadges() {
        log.debug("REST request to get all Badges");
        return badgeService.findAll();
    }

    /**
     * {@code GET  /badges/:id} : get the "id" badge.
     *
     * @param id the id of the badge to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the badge, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Badge> getBadge(@PathVariable Long id) {
        log.debug("REST request to get Badge : {}", id);
        Optional<Badge> badge = badgeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(badge);
    }

    /**
     * {@code DELETE  /badges/:id} : delete the "id" badge.
     *
     * @param id the id of the badge to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBadge(@PathVariable Long id) {
        log.debug("REST request to delete Badge : {}", id);
        badgeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
