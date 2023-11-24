package me.krft.api.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import me.krft.api.repository.MachineCategoryRepository;
import me.krft.api.service.MachineCategoryService;
import me.krft.api.service.dto.MachineCategoryDTO;
import me.krft.api.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link me.krft.api.domain.MachineCategory}.
 */
@RestController
@RequestMapping("/api")
public class MachineCategoryResource {

    private final Logger log = LoggerFactory.getLogger(MachineCategoryResource.class);

    private static final String ENTITY_NAME = "krftmeMachineCategory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MachineCategoryService machineCategoryService;

    private final MachineCategoryRepository machineCategoryRepository;

    public MachineCategoryResource(MachineCategoryService machineCategoryService, MachineCategoryRepository machineCategoryRepository) {
        this.machineCategoryService = machineCategoryService;
        this.machineCategoryRepository = machineCategoryRepository;
    }

    /**
     * {@code POST  /machine-categories} : Create a new machineCategory.
     *
     * @param machineCategoryDTO the machineCategoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new machineCategoryDTO, or with status {@code 400 (Bad Request)} if the machineCategory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/machine-categories")
    public ResponseEntity<MachineCategoryDTO> createMachineCategory(@Valid @RequestBody MachineCategoryDTO machineCategoryDTO)
        throws URISyntaxException {
        log.debug("REST request to save MachineCategory : {}", machineCategoryDTO);
        if (machineCategoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new machineCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MachineCategoryDTO result = machineCategoryService.save(machineCategoryDTO);
        return ResponseEntity
            .created(new URI("/api/machine-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /machine-categories/:id} : Updates an existing machineCategory.
     *
     * @param id the id of the machineCategoryDTO to save.
     * @param machineCategoryDTO the machineCategoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated machineCategoryDTO,
     * or with status {@code 400 (Bad Request)} if the machineCategoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the machineCategoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/machine-categories/{id}")
    public ResponseEntity<MachineCategoryDTO> updateMachineCategory(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody MachineCategoryDTO machineCategoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to update MachineCategory : {}, {}", id, machineCategoryDTO);
        if (machineCategoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, machineCategoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!machineCategoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MachineCategoryDTO result = machineCategoryService.update(machineCategoryDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, machineCategoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /machine-categories/:id} : Partial updates given fields of an existing machineCategory, field will ignore if it is null
     *
     * @param id the id of the machineCategoryDTO to save.
     * @param machineCategoryDTO the machineCategoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated machineCategoryDTO,
     * or with status {@code 400 (Bad Request)} if the machineCategoryDTO is not valid,
     * or with status {@code 404 (Not Found)} if the machineCategoryDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the machineCategoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/machine-categories/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MachineCategoryDTO> partialUpdateMachineCategory(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody MachineCategoryDTO machineCategoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update MachineCategory partially : {}, {}", id, machineCategoryDTO);
        if (machineCategoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, machineCategoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!machineCategoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MachineCategoryDTO> result = machineCategoryService.partialUpdate(machineCategoryDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, machineCategoryDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /machine-categories} : get all the machineCategories.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of machineCategories in body.
     */
    @GetMapping("/machine-categories")
    public List<MachineCategoryDTO> getAllMachineCategories() {
        log.debug("REST request to get all MachineCategories");
        return machineCategoryService.findAll();
    }

    /**
     * {@code GET  /machine-categories/:id} : get the "id" machineCategory.
     *
     * @param id the id of the machineCategoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the machineCategoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/machine-categories/{id}")
    public ResponseEntity<MachineCategoryDTO> getMachineCategory(@PathVariable Long id) {
        log.debug("REST request to get MachineCategory : {}", id);
        Optional<MachineCategoryDTO> machineCategoryDTO = machineCategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(machineCategoryDTO);
    }

    /**
     * {@code DELETE  /machine-categories/:id} : delete the "id" machineCategory.
     *
     * @param id the id of the machineCategoryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/machine-categories/{id}")
    public ResponseEntity<Void> deleteMachineCategory(@PathVariable Long id) {
        log.debug("REST request to delete MachineCategory : {}", id);
        machineCategoryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
