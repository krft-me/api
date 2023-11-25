package me.krft.api.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import me.krft.api.domain.OfferCategory;
import me.krft.api.repository.OfferCategoryRepository;
import me.krft.api.service.OfferCategoryService;
import me.krft.api.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link me.krft.api.domain.OfferCategory}.
 */
@RestController
@RequestMapping("/api/offer-categories")
public class OfferCategoryResource {

    private final Logger log = LoggerFactory.getLogger(OfferCategoryResource.class);

    private static final String ENTITY_NAME = "krftmeOfferCategory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OfferCategoryService offerCategoryService;

    private final OfferCategoryRepository offerCategoryRepository;

    public OfferCategoryResource(OfferCategoryService offerCategoryService, OfferCategoryRepository offerCategoryRepository) {
        this.offerCategoryService = offerCategoryService;
        this.offerCategoryRepository = offerCategoryRepository;
    }

    /**
     * {@code POST  /offer-categories} : Create a new offerCategory.
     *
     * @param offerCategory the offerCategory to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new offerCategory, or with status {@code 400 (Bad Request)} if the offerCategory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<OfferCategory> createOfferCategory(@Valid @RequestBody OfferCategory offerCategory) throws URISyntaxException {
        log.debug("REST request to save OfferCategory : {}", offerCategory);
        if (offerCategory.getId() != null) {
            throw new BadRequestAlertException("A new offerCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OfferCategory result = offerCategoryService.save(offerCategory);
        return ResponseEntity
            .created(new URI("/api/offer-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /offer-categories/:id} : Updates an existing offerCategory.
     *
     * @param id the id of the offerCategory to save.
     * @param offerCategory the offerCategory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated offerCategory,
     * or with status {@code 400 (Bad Request)} if the offerCategory is not valid,
     * or with status {@code 500 (Internal Server Error)} if the offerCategory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<OfferCategory> updateOfferCategory(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody OfferCategory offerCategory
    ) throws URISyntaxException {
        log.debug("REST request to update OfferCategory : {}, {}", id, offerCategory);
        if (offerCategory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, offerCategory.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!offerCategoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OfferCategory result = offerCategoryService.update(offerCategory);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, offerCategory.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /offer-categories/:id} : Partial updates given fields of an existing offerCategory, field will ignore if it is null
     *
     * @param id the id of the offerCategory to save.
     * @param offerCategory the offerCategory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated offerCategory,
     * or with status {@code 400 (Bad Request)} if the offerCategory is not valid,
     * or with status {@code 404 (Not Found)} if the offerCategory is not found,
     * or with status {@code 500 (Internal Server Error)} if the offerCategory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OfferCategory> partialUpdateOfferCategory(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody OfferCategory offerCategory
    ) throws URISyntaxException {
        log.debug("REST request to partial update OfferCategory partially : {}, {}", id, offerCategory);
        if (offerCategory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, offerCategory.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!offerCategoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OfferCategory> result = offerCategoryService.partialUpdate(offerCategory);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, offerCategory.getId().toString())
        );
    }

    /**
     * {@code GET  /offer-categories} : get all the offerCategories.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of offerCategories in body.
     */
    @GetMapping("")
    public List<OfferCategory> getAllOfferCategories() {
        log.debug("REST request to get all OfferCategories");
        return offerCategoryService.findAll();
    }

    /**
     * {@code GET  /offer-categories/:id} : get the "id" offerCategory.
     *
     * @param id the id of the offerCategory to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the offerCategory, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<OfferCategory> getOfferCategory(@PathVariable Long id) {
        log.debug("REST request to get OfferCategory : {}", id);
        Optional<OfferCategory> offerCategory = offerCategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(offerCategory);
    }

    /**
     * {@code DELETE  /offer-categories/:id} : delete the "id" offerCategory.
     *
     * @param id the id of the offerCategory to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOfferCategory(@PathVariable Long id) {
        log.debug("REST request to delete OfferCategory : {}", id);
        offerCategoryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
