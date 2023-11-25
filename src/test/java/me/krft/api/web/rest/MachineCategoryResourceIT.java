package me.krft.api.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import me.krft.api.IntegrationTest;
import me.krft.api.domain.MachineCategory;
import me.krft.api.repository.MachineCategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link MachineCategoryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MachineCategoryResourceIT {

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/machine-categories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MachineCategoryRepository machineCategoryRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMachineCategoryMockMvc;

    private MachineCategory machineCategory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MachineCategory createEntity(EntityManager em) {
        MachineCategory machineCategory = new MachineCategory().label(DEFAULT_LABEL);
        return machineCategory;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MachineCategory createUpdatedEntity(EntityManager em) {
        MachineCategory machineCategory = new MachineCategory().label(UPDATED_LABEL);
        return machineCategory;
    }

    @BeforeEach
    public void initTest() {
        machineCategory = createEntity(em);
    }

    @Test
    @Transactional
    void createMachineCategory() throws Exception {
        int databaseSizeBeforeCreate = machineCategoryRepository.findAll().size();
        // Create the MachineCategory
        restMachineCategoryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(machineCategory))
            )
            .andExpect(status().isCreated());

        // Validate the MachineCategory in the database
        List<MachineCategory> machineCategoryList = machineCategoryRepository.findAll();
        assertThat(machineCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        MachineCategory testMachineCategory = machineCategoryList.get(machineCategoryList.size() - 1);
        assertThat(testMachineCategory.getLabel()).isEqualTo(DEFAULT_LABEL);
    }

    @Test
    @Transactional
    void createMachineCategoryWithExistingId() throws Exception {
        // Create the MachineCategory with an existing ID
        machineCategory.setId(1L);

        int databaseSizeBeforeCreate = machineCategoryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMachineCategoryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(machineCategory))
            )
            .andExpect(status().isBadRequest());

        // Validate the MachineCategory in the database
        List<MachineCategory> machineCategoryList = machineCategoryRepository.findAll();
        assertThat(machineCategoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkLabelIsRequired() throws Exception {
        int databaseSizeBeforeTest = machineCategoryRepository.findAll().size();
        // set the field null
        machineCategory.setLabel(null);

        // Create the MachineCategory, which fails.

        restMachineCategoryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(machineCategory))
            )
            .andExpect(status().isBadRequest());

        List<MachineCategory> machineCategoryList = machineCategoryRepository.findAll();
        assertThat(machineCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMachineCategories() throws Exception {
        // Initialize the database
        machineCategoryRepository.saveAndFlush(machineCategory);

        // Get all the machineCategoryList
        restMachineCategoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(machineCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL)));
    }

    @Test
    @Transactional
    void getMachineCategory() throws Exception {
        // Initialize the database
        machineCategoryRepository.saveAndFlush(machineCategory);

        // Get the machineCategory
        restMachineCategoryMockMvc
            .perform(get(ENTITY_API_URL_ID, machineCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(machineCategory.getId().intValue()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL));
    }

    @Test
    @Transactional
    void getNonExistingMachineCategory() throws Exception {
        // Get the machineCategory
        restMachineCategoryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMachineCategory() throws Exception {
        // Initialize the database
        machineCategoryRepository.saveAndFlush(machineCategory);

        int databaseSizeBeforeUpdate = machineCategoryRepository.findAll().size();

        // Update the machineCategory
        MachineCategory updatedMachineCategory = machineCategoryRepository.findById(machineCategory.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedMachineCategory are not directly saved in db
        em.detach(updatedMachineCategory);
        updatedMachineCategory.label(UPDATED_LABEL);

        restMachineCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMachineCategory.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMachineCategory))
            )
            .andExpect(status().isOk());

        // Validate the MachineCategory in the database
        List<MachineCategory> machineCategoryList = machineCategoryRepository.findAll();
        assertThat(machineCategoryList).hasSize(databaseSizeBeforeUpdate);
        MachineCategory testMachineCategory = machineCategoryList.get(machineCategoryList.size() - 1);
        assertThat(testMachineCategory.getLabel()).isEqualTo(UPDATED_LABEL);
    }

    @Test
    @Transactional
    void putNonExistingMachineCategory() throws Exception {
        int databaseSizeBeforeUpdate = machineCategoryRepository.findAll().size();
        machineCategory.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMachineCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, machineCategory.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(machineCategory))
            )
            .andExpect(status().isBadRequest());

        // Validate the MachineCategory in the database
        List<MachineCategory> machineCategoryList = machineCategoryRepository.findAll();
        assertThat(machineCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMachineCategory() throws Exception {
        int databaseSizeBeforeUpdate = machineCategoryRepository.findAll().size();
        machineCategory.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMachineCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(machineCategory))
            )
            .andExpect(status().isBadRequest());

        // Validate the MachineCategory in the database
        List<MachineCategory> machineCategoryList = machineCategoryRepository.findAll();
        assertThat(machineCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMachineCategory() throws Exception {
        int databaseSizeBeforeUpdate = machineCategoryRepository.findAll().size();
        machineCategory.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMachineCategoryMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(machineCategory))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MachineCategory in the database
        List<MachineCategory> machineCategoryList = machineCategoryRepository.findAll();
        assertThat(machineCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMachineCategoryWithPatch() throws Exception {
        // Initialize the database
        machineCategoryRepository.saveAndFlush(machineCategory);

        int databaseSizeBeforeUpdate = machineCategoryRepository.findAll().size();

        // Update the machineCategory using partial update
        MachineCategory partialUpdatedMachineCategory = new MachineCategory();
        partialUpdatedMachineCategory.setId(machineCategory.getId());

        restMachineCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMachineCategory.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMachineCategory))
            )
            .andExpect(status().isOk());

        // Validate the MachineCategory in the database
        List<MachineCategory> machineCategoryList = machineCategoryRepository.findAll();
        assertThat(machineCategoryList).hasSize(databaseSizeBeforeUpdate);
        MachineCategory testMachineCategory = machineCategoryList.get(machineCategoryList.size() - 1);
        assertThat(testMachineCategory.getLabel()).isEqualTo(DEFAULT_LABEL);
    }

    @Test
    @Transactional
    void fullUpdateMachineCategoryWithPatch() throws Exception {
        // Initialize the database
        machineCategoryRepository.saveAndFlush(machineCategory);

        int databaseSizeBeforeUpdate = machineCategoryRepository.findAll().size();

        // Update the machineCategory using partial update
        MachineCategory partialUpdatedMachineCategory = new MachineCategory();
        partialUpdatedMachineCategory.setId(machineCategory.getId());

        partialUpdatedMachineCategory.label(UPDATED_LABEL);

        restMachineCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMachineCategory.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMachineCategory))
            )
            .andExpect(status().isOk());

        // Validate the MachineCategory in the database
        List<MachineCategory> machineCategoryList = machineCategoryRepository.findAll();
        assertThat(machineCategoryList).hasSize(databaseSizeBeforeUpdate);
        MachineCategory testMachineCategory = machineCategoryList.get(machineCategoryList.size() - 1);
        assertThat(testMachineCategory.getLabel()).isEqualTo(UPDATED_LABEL);
    }

    @Test
    @Transactional
    void patchNonExistingMachineCategory() throws Exception {
        int databaseSizeBeforeUpdate = machineCategoryRepository.findAll().size();
        machineCategory.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMachineCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, machineCategory.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(machineCategory))
            )
            .andExpect(status().isBadRequest());

        // Validate the MachineCategory in the database
        List<MachineCategory> machineCategoryList = machineCategoryRepository.findAll();
        assertThat(machineCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMachineCategory() throws Exception {
        int databaseSizeBeforeUpdate = machineCategoryRepository.findAll().size();
        machineCategory.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMachineCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(machineCategory))
            )
            .andExpect(status().isBadRequest());

        // Validate the MachineCategory in the database
        List<MachineCategory> machineCategoryList = machineCategoryRepository.findAll();
        assertThat(machineCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMachineCategory() throws Exception {
        int databaseSizeBeforeUpdate = machineCategoryRepository.findAll().size();
        machineCategory.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMachineCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(machineCategory))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MachineCategory in the database
        List<MachineCategory> machineCategoryList = machineCategoryRepository.findAll();
        assertThat(machineCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMachineCategory() throws Exception {
        // Initialize the database
        machineCategoryRepository.saveAndFlush(machineCategory);

        int databaseSizeBeforeDelete = machineCategoryRepository.findAll().size();

        // Delete the machineCategory
        restMachineCategoryMockMvc
            .perform(delete(ENTITY_API_URL_ID, machineCategory.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MachineCategory> machineCategoryList = machineCategoryRepository.findAll();
        assertThat(machineCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
