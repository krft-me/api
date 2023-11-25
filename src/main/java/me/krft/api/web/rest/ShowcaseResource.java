package me.krft.api.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import me.krft.api.domain.Showcase;
import me.krft.api.repository.ShowcaseRepository;
import me.krft.api.service.ShowcaseService;
import me.krft.api.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link me.krft.api.domain.Showcase}.
 */
@RestController
@RequestMapping("/api/showcases")
public class ShowcaseResource {

    private final Logger log = LoggerFactory.getLogger(ShowcaseResource.class);

    private static final String ENTITY_NAME = "krftmeShowcase";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ShowcaseService showcaseService;

    private final ShowcaseRepository showcaseRepository;

    public ShowcaseResource(ShowcaseService showcaseService, ShowcaseRepository showcaseRepository) {
        this.showcaseService = showcaseService;
        this.showcaseRepository = showcaseRepository;
    }

    /**
     * {@code POST  /showcases} : Create a new showcase.
     *
     * @param showcase the showcase to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new showcase, or with status {@code 400 (Bad Request)} if the showcase has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Showcase> createShowcase(@Valid @RequestBody Showcase showcase) throws URISyntaxException {
        log.debug("REST request to save Showcase : {}", showcase);
        if (showcase.getId() != null) {
            throw new BadRequestAlertException("A new showcase cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Showcase result = showcaseService.save(showcase);
        return ResponseEntity
            .created(new URI("/api/showcases/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /showcases/:id} : Updates an existing showcase.
     *
     * @param id the id of the showcase to save.
     * @param showcase the showcase to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated showcase,
     * or with status {@code 400 (Bad Request)} if the showcase is not valid,
     * or with status {@code 500 (Internal Server Error)} if the showcase couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Showcase> updateShowcase(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Showcase showcase
    ) throws URISyntaxException {
        log.debug("REST request to update Showcase : {}, {}", id, showcase);
        if (showcase.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, showcase.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!showcaseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Showcase result = showcaseService.update(showcase);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, showcase.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /showcases/:id} : Partial updates given fields of an existing showcase, field will ignore if it is null
     *
     * @param id the id of the showcase to save.
     * @param showcase the showcase to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated showcase,
     * or with status {@code 400 (Bad Request)} if the showcase is not valid,
     * or with status {@code 404 (Not Found)} if the showcase is not found,
     * or with status {@code 500 (Internal Server Error)} if the showcase couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Showcase> partialUpdateShowcase(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Showcase showcase
    ) throws URISyntaxException {
        log.debug("REST request to partial update Showcase partially : {}, {}", id, showcase);
        if (showcase.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, showcase.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!showcaseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Showcase> result = showcaseService.partialUpdate(showcase);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, showcase.getId().toString())
        );
    }

    /**
     * {@code GET  /showcases} : get all the showcases.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of showcases in body.
     */
    @GetMapping("")
    public List<Showcase> getAllShowcases() {
        log.debug("REST request to get all Showcases");
        return showcaseService.findAll();
    }

    /**
     * {@code GET  /showcases/:id} : get the "id" showcase.
     *
     * @param id the id of the showcase to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the showcase, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Showcase> getShowcase(@PathVariable Long id) {
        log.debug("REST request to get Showcase : {}", id);
        Optional<Showcase> showcase = showcaseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(showcase);
    }

    /**
     * {@code DELETE  /showcases/:id} : delete the "id" showcase.
     *
     * @param id the id of the showcase to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShowcase(@PathVariable Long id) {
        log.debug("REST request to delete Showcase : {}", id);
        showcaseService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
