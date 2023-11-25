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
import me.krft.api.domain.OfferCategory;
import me.krft.api.repository.OfferCategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link OfferCategoryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OfferCategoryResourceIT {

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/offer-categories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OfferCategoryRepository offerCategoryRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOfferCategoryMockMvc;

    private OfferCategory offerCategory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OfferCategory createEntity(EntityManager em) {
        OfferCategory offerCategory = new OfferCategory().label(DEFAULT_LABEL);
        return offerCategory;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OfferCategory createUpdatedEntity(EntityManager em) {
        OfferCategory offerCategory = new OfferCategory().label(UPDATED_LABEL);
        return offerCategory;
    }

    @BeforeEach
    public void initTest() {
        offerCategory = createEntity(em);
    }

    @Test
    @Transactional
    void createOfferCategory() throws Exception {
        int databaseSizeBeforeCreate = offerCategoryRepository.findAll().size();
        // Create the OfferCategory
        restOfferCategoryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(offerCategory))
            )
            .andExpect(status().isCreated());

        // Validate the OfferCategory in the database
        List<OfferCategory> offerCategoryList = offerCategoryRepository.findAll();
        assertThat(offerCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        OfferCategory testOfferCategory = offerCategoryList.get(offerCategoryList.size() - 1);
        assertThat(testOfferCategory.getLabel()).isEqualTo(DEFAULT_LABEL);
    }

    @Test
    @Transactional
    void createOfferCategoryWithExistingId() throws Exception {
        // Create the OfferCategory with an existing ID
        offerCategory.setId(1L);

        int databaseSizeBeforeCreate = offerCategoryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOfferCategoryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(offerCategory))
            )
            .andExpect(status().isBadRequest());

        // Validate the OfferCategory in the database
        List<OfferCategory> offerCategoryList = offerCategoryRepository.findAll();
        assertThat(offerCategoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkLabelIsRequired() throws Exception {
        int databaseSizeBeforeTest = offerCategoryRepository.findAll().size();
        // set the field null
        offerCategory.setLabel(null);

        // Create the OfferCategory, which fails.

        restOfferCategoryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(offerCategory))
            )
            .andExpect(status().isBadRequest());

        List<OfferCategory> offerCategoryList = offerCategoryRepository.findAll();
        assertThat(offerCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllOfferCategories() throws Exception {
        // Initialize the database
        offerCategoryRepository.saveAndFlush(offerCategory);

        // Get all the offerCategoryList
        restOfferCategoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(offerCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL)));
    }

    @Test
    @Transactional
    void getOfferCategory() throws Exception {
        // Initialize the database
        offerCategoryRepository.saveAndFlush(offerCategory);

        // Get the offerCategory
        restOfferCategoryMockMvc
            .perform(get(ENTITY_API_URL_ID, offerCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(offerCategory.getId().intValue()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL));
    }

    @Test
    @Transactional
    void getNonExistingOfferCategory() throws Exception {
        // Get the offerCategory
        restOfferCategoryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOfferCategory() throws Exception {
        // Initialize the database
        offerCategoryRepository.saveAndFlush(offerCategory);

        int databaseSizeBeforeUpdate = offerCategoryRepository.findAll().size();

        // Update the offerCategory
        OfferCategory updatedOfferCategory = offerCategoryRepository.findById(offerCategory.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedOfferCategory are not directly saved in db
        em.detach(updatedOfferCategory);
        updatedOfferCategory.label(UPDATED_LABEL);

        restOfferCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedOfferCategory.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedOfferCategory))
            )
            .andExpect(status().isOk());

        // Validate the OfferCategory in the database
        List<OfferCategory> offerCategoryList = offerCategoryRepository.findAll();
        assertThat(offerCategoryList).hasSize(databaseSizeBeforeUpdate);
        OfferCategory testOfferCategory = offerCategoryList.get(offerCategoryList.size() - 1);
        assertThat(testOfferCategory.getLabel()).isEqualTo(UPDATED_LABEL);
    }

    @Test
    @Transactional
    void putNonExistingOfferCategory() throws Exception {
        int databaseSizeBeforeUpdate = offerCategoryRepository.findAll().size();
        offerCategory.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOfferCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, offerCategory.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(offerCategory))
            )
            .andExpect(status().isBadRequest());

        // Validate the OfferCategory in the database
        List<OfferCategory> offerCategoryList = offerCategoryRepository.findAll();
        assertThat(offerCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOfferCategory() throws Exception {
        int databaseSizeBeforeUpdate = offerCategoryRepository.findAll().size();
        offerCategory.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOfferCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(offerCategory))
            )
            .andExpect(status().isBadRequest());

        // Validate the OfferCategory in the database
        List<OfferCategory> offerCategoryList = offerCategoryRepository.findAll();
        assertThat(offerCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOfferCategory() throws Exception {
        int databaseSizeBeforeUpdate = offerCategoryRepository.findAll().size();
        offerCategory.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOfferCategoryMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(offerCategory))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OfferCategory in the database
        List<OfferCategory> offerCategoryList = offerCategoryRepository.findAll();
        assertThat(offerCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOfferCategoryWithPatch() throws Exception {
        // Initialize the database
        offerCategoryRepository.saveAndFlush(offerCategory);

        int databaseSizeBeforeUpdate = offerCategoryRepository.findAll().size();

        // Update the offerCategory using partial update
        OfferCategory partialUpdatedOfferCategory = new OfferCategory();
        partialUpdatedOfferCategory.setId(offerCategory.getId());

        restOfferCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOfferCategory.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOfferCategory))
            )
            .andExpect(status().isOk());

        // Validate the OfferCategory in the database
        List<OfferCategory> offerCategoryList = offerCategoryRepository.findAll();
        assertThat(offerCategoryList).hasSize(databaseSizeBeforeUpdate);
        OfferCategory testOfferCategory = offerCategoryList.get(offerCategoryList.size() - 1);
        assertThat(testOfferCategory.getLabel()).isEqualTo(DEFAULT_LABEL);
    }

    @Test
    @Transactional
    void fullUpdateOfferCategoryWithPatch() throws Exception {
        // Initialize the database
        offerCategoryRepository.saveAndFlush(offerCategory);

        int databaseSizeBeforeUpdate = offerCategoryRepository.findAll().size();

        // Update the offerCategory using partial update
        OfferCategory partialUpdatedOfferCategory = new OfferCategory();
        partialUpdatedOfferCategory.setId(offerCategory.getId());

        partialUpdatedOfferCategory.label(UPDATED_LABEL);

        restOfferCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOfferCategory.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOfferCategory))
            )
            .andExpect(status().isOk());

        // Validate the OfferCategory in the database
        List<OfferCategory> offerCategoryList = offerCategoryRepository.findAll();
        assertThat(offerCategoryList).hasSize(databaseSizeBeforeUpdate);
        OfferCategory testOfferCategory = offerCategoryList.get(offerCategoryList.size() - 1);
        assertThat(testOfferCategory.getLabel()).isEqualTo(UPDATED_LABEL);
    }

    @Test
    @Transactional
    void patchNonExistingOfferCategory() throws Exception {
        int databaseSizeBeforeUpdate = offerCategoryRepository.findAll().size();
        offerCategory.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOfferCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, offerCategory.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(offerCategory))
            )
            .andExpect(status().isBadRequest());

        // Validate the OfferCategory in the database
        List<OfferCategory> offerCategoryList = offerCategoryRepository.findAll();
        assertThat(offerCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOfferCategory() throws Exception {
        int databaseSizeBeforeUpdate = offerCategoryRepository.findAll().size();
        offerCategory.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOfferCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(offerCategory))
            )
            .andExpect(status().isBadRequest());

        // Validate the OfferCategory in the database
        List<OfferCategory> offerCategoryList = offerCategoryRepository.findAll();
        assertThat(offerCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOfferCategory() throws Exception {
        int databaseSizeBeforeUpdate = offerCategoryRepository.findAll().size();
        offerCategory.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOfferCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(offerCategory))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OfferCategory in the database
        List<OfferCategory> offerCategoryList = offerCategoryRepository.findAll();
        assertThat(offerCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOfferCategory() throws Exception {
        // Initialize the database
        offerCategoryRepository.saveAndFlush(offerCategory);

        int databaseSizeBeforeDelete = offerCategoryRepository.findAll().size();

        // Delete the offerCategory
        restOfferCategoryMockMvc
            .perform(delete(ENTITY_API_URL_ID, offerCategory.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OfferCategory> offerCategoryList = offerCategoryRepository.findAll();
        assertThat(offerCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
