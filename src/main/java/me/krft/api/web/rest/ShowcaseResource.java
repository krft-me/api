package me.krft.api.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import me.krft.api.repository.ShowcaseRepository;
import me.krft.api.service.ShowcaseService;
import me.krft.api.service.dto.ShowcaseDTO;
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
@RequestMapping("/api")
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
     * @param showcaseDTO the showcaseDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new showcaseDTO, or with status {@code 400 (Bad Request)} if the showcase has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/showcases")
    public ResponseEntity<ShowcaseDTO> createShowcase(@Valid @RequestBody ShowcaseDTO showcaseDTO) throws URISyntaxException {
        log.debug("REST request to save Showcase : {}", showcaseDTO);
        if (showcaseDTO.getId() != null) {
            throw new BadRequestAlertException("A new showcase cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ShowcaseDTO result = showcaseService.save(showcaseDTO);
        return ResponseEntity
            .created(new URI("/api/showcases/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /showcases/:id} : Updates an existing showcase.
     *
     * @param id the id of the showcaseDTO to save.
     * @param showcaseDTO the showcaseDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated showcaseDTO,
     * or with status {@code 400 (Bad Request)} if the showcaseDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the showcaseDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/showcases/{id}")
    public ResponseEntity<ShowcaseDTO> updateShowcase(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ShowcaseDTO showcaseDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Showcase : {}, {}", id, showcaseDTO);
        if (showcaseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, showcaseDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!showcaseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ShowcaseDTO result = showcaseService.update(showcaseDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, showcaseDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /showcases/:id} : Partial updates given fields of an existing showcase, field will ignore if it is null
     *
     * @param id the id of the showcaseDTO to save.
     * @param showcaseDTO the showcaseDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated showcaseDTO,
     * or with status {@code 400 (Bad Request)} if the showcaseDTO is not valid,
     * or with status {@code 404 (Not Found)} if the showcaseDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the showcaseDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/showcases/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ShowcaseDTO> partialUpdateShowcase(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ShowcaseDTO showcaseDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Showcase partially : {}, {}", id, showcaseDTO);
        if (showcaseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, showcaseDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!showcaseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ShowcaseDTO> result = showcaseService.partialUpdate(showcaseDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, showcaseDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /showcases} : get all the showcases.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of showcases in body.
     */
    @GetMapping("/showcases")
    public List<ShowcaseDTO> getAllShowcases() {
        log.debug("REST request to get all Showcases");
        return showcaseService.findAll();
    }

    /**
     * {@code GET  /showcases/:id} : get the "id" showcase.
     *
     * @param id the id of the showcaseDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the showcaseDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/showcases/{id}")
    public ResponseEntity<ShowcaseDTO> getShowcase(@PathVariable Long id) {
        log.debug("REST request to get Showcase : {}", id);
        Optional<ShowcaseDTO> showcaseDTO = showcaseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(showcaseDTO);
    }

    /**
     * {@code DELETE  /showcases/:id} : delete the "id" showcase.
     *
     * @param id the id of the showcaseDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/showcases/{id}")
    public ResponseEntity<Void> deleteShowcase(@PathVariable Long id) {
        log.debug("REST request to delete Showcase : {}", id);
        showcaseService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
