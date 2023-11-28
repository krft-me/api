package me.krft.api.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import me.krft.api.IntegrationTest;
import me.krft.api.domain.ApplicationUserOffer;
import me.krft.api.domain.Showcase;
import me.krft.api.repository.ShowcaseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ShowcaseResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ShowcaseResourceIT {

    private static final UUID DEFAULT_IMAGE_ID = UUID.randomUUID();
    private static final UUID UPDATED_IMAGE_ID = UUID.randomUUID();

    private static final String ENTITY_API_URL = "/api/showcases";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static final Random random = new Random();
    private static final AtomicLong count = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));

    @Autowired
    private ShowcaseRepository showcaseRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restShowcaseMockMvc;

    private Showcase showcase;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Showcase createEntity(EntityManager em) {
        Showcase showcase = new Showcase().imageId(DEFAULT_IMAGE_ID);
        // Add required entity
        ApplicationUserOffer applicationUserOffer;
        if (TestUtil.findAll(em, ApplicationUserOffer.class).isEmpty()) {
            applicationUserOffer = ApplicationUserOfferResourceIT.createEntity(em);
            em.persist(applicationUserOffer);
            em.flush();
        } else {
            applicationUserOffer = TestUtil.findAll(em, ApplicationUserOffer.class).get(0);
        }
        showcase.setOffer(applicationUserOffer);
        return showcase;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Showcase createUpdatedEntity(EntityManager em) {
        Showcase showcase = new Showcase().imageId(UPDATED_IMAGE_ID);
        // Add required entity
        ApplicationUserOffer applicationUserOffer;
        if (TestUtil.findAll(em, ApplicationUserOffer.class).isEmpty()) {
            applicationUserOffer = ApplicationUserOfferResourceIT.createUpdatedEntity(em);
            em.persist(applicationUserOffer);
            em.flush();
        } else {
            applicationUserOffer = TestUtil.findAll(em, ApplicationUserOffer.class).get(0);
        }
        showcase.setOffer(applicationUserOffer);
        return showcase;
    }

    @BeforeEach
    public void initTest() {
        showcase = createEntity(em);
    }

    @Test
    @Transactional
    void createShowcase() throws Exception {
        int databaseSizeBeforeCreate = showcaseRepository.findAll().size();
        // Create the Showcase
        restShowcaseMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(showcase))
            )
            .andExpect(status().isCreated());

        // Validate the Showcase in the database
        List<Showcase> showcaseList = showcaseRepository.findAll();
        assertThat(showcaseList).hasSize(databaseSizeBeforeCreate + 1);
        Showcase testShowcase = showcaseList.get(showcaseList.size() - 1);
        assertThat(testShowcase.getImageId()).isEqualTo(DEFAULT_IMAGE_ID);
    }

    @Test
    @Transactional
    void createShowcaseWithExistingId() throws Exception {
        // Create the Showcase with an existing ID
        showcase.setId(1L);

        int databaseSizeBeforeCreate = showcaseRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restShowcaseMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(showcase))
            )
            .andExpect(status().isBadRequest());

        // Validate the Showcase in the database
        List<Showcase> showcaseList = showcaseRepository.findAll();
        assertThat(showcaseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkImageIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = showcaseRepository.findAll().size();
        // set the field null
        showcase.setImageId(null);

        // Create the Showcase, which fails.

        restShowcaseMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(showcase))
            )
            .andExpect(status().isBadRequest());

        List<Showcase> showcaseList = showcaseRepository.findAll();
        assertThat(showcaseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllShowcases() throws Exception {
        // Initialize the database
        showcaseRepository.saveAndFlush(showcase);

        // Get all the showcaseList
        restShowcaseMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(showcase.getId().intValue())))
            .andExpect(jsonPath("$.[*].imageId").value(hasItem(DEFAULT_IMAGE_ID.toString())));
    }

    @Test
    @Transactional
    void getShowcase() throws Exception {
        // Initialize the database
        showcaseRepository.saveAndFlush(showcase);

        // Get the showcase
        restShowcaseMockMvc
            .perform(get(ENTITY_API_URL_ID, showcase.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(showcase.getId().intValue()))
            .andExpect(jsonPath("$.imageId").value(DEFAULT_IMAGE_ID.toString()));
    }

    @Test
    @Transactional
    void getNonExistingShowcase() throws Exception {
        // Get the showcase
        restShowcaseMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingShowcase() throws Exception {
        // Initialize the database
        showcaseRepository.saveAndFlush(showcase);

        int databaseSizeBeforeUpdate = showcaseRepository.findAll().size();

        // Update the showcase
        Showcase updatedShowcase = showcaseRepository.findById(showcase.getId()).get();
        // Disconnect from session so that the updates on updatedShowcase are not directly saved in db
        em.detach(updatedShowcase);
        updatedShowcase.imageId(UPDATED_IMAGE_ID);

        restShowcaseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedShowcase.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedShowcase))
            )
            .andExpect(status().isOk());

        // Validate the Showcase in the database
        List<Showcase> showcaseList = showcaseRepository.findAll();
        assertThat(showcaseList).hasSize(databaseSizeBeforeUpdate);
        Showcase testShowcase = showcaseList.get(showcaseList.size() - 1);
        assertThat(testShowcase.getImageId()).isEqualTo(UPDATED_IMAGE_ID);
    }

    @Test
    @Transactional
    void putNonExistingShowcase() throws Exception {
        int databaseSizeBeforeUpdate = showcaseRepository.findAll().size();
        showcase.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restShowcaseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, showcase.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(showcase))
            )
            .andExpect(status().isBadRequest());

        // Validate the Showcase in the database
        List<Showcase> showcaseList = showcaseRepository.findAll();
        assertThat(showcaseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchShowcase() throws Exception {
        int databaseSizeBeforeUpdate = showcaseRepository.findAll().size();
        showcase.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShowcaseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(showcase))
            )
            .andExpect(status().isBadRequest());

        // Validate the Showcase in the database
        List<Showcase> showcaseList = showcaseRepository.findAll();
        assertThat(showcaseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamShowcase() throws Exception {
        int databaseSizeBeforeUpdate = showcaseRepository.findAll().size();
        showcase.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShowcaseMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(showcase))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Showcase in the database
        List<Showcase> showcaseList = showcaseRepository.findAll();
        assertThat(showcaseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateShowcaseWithPatch() throws Exception {
        // Initialize the database
        showcaseRepository.saveAndFlush(showcase);

        int databaseSizeBeforeUpdate = showcaseRepository.findAll().size();

        // Update the showcase using partial update
        Showcase partialUpdatedShowcase = new Showcase();
        partialUpdatedShowcase.setId(showcase.getId());

        partialUpdatedShowcase.imageId(UPDATED_IMAGE_ID);

        restShowcaseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedShowcase.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedShowcase))
            )
            .andExpect(status().isOk());

        // Validate the Showcase in the database
        List<Showcase> showcaseList = showcaseRepository.findAll();
        assertThat(showcaseList).hasSize(databaseSizeBeforeUpdate);
        Showcase testShowcase = showcaseList.get(showcaseList.size() - 1);
        assertThat(testShowcase.getImageId()).isEqualTo(UPDATED_IMAGE_ID);
    }

    @Test
    @Transactional
    void fullUpdateShowcaseWithPatch() throws Exception {
        // Initialize the database
        showcaseRepository.saveAndFlush(showcase);

        int databaseSizeBeforeUpdate = showcaseRepository.findAll().size();

        // Update the showcase using partial update
        Showcase partialUpdatedShowcase = new Showcase();
        partialUpdatedShowcase.setId(showcase.getId());

        partialUpdatedShowcase.imageId(UPDATED_IMAGE_ID);

        restShowcaseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedShowcase.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedShowcase))
            )
            .andExpect(status().isOk());

        // Validate the Showcase in the database
        List<Showcase> showcaseList = showcaseRepository.findAll();
        assertThat(showcaseList).hasSize(databaseSizeBeforeUpdate);
        Showcase testShowcase = showcaseList.get(showcaseList.size() - 1);
        assertThat(testShowcase.getImageId()).isEqualTo(UPDATED_IMAGE_ID);
    }

    @Test
    @Transactional
    void patchNonExistingShowcase() throws Exception {
        int databaseSizeBeforeUpdate = showcaseRepository.findAll().size();
        showcase.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restShowcaseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, showcase.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(showcase))
            )
            .andExpect(status().isBadRequest());

        // Validate the Showcase in the database
        List<Showcase> showcaseList = showcaseRepository.findAll();
        assertThat(showcaseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchShowcase() throws Exception {
        int databaseSizeBeforeUpdate = showcaseRepository.findAll().size();
        showcase.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShowcaseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(showcase))
            )
            .andExpect(status().isBadRequest());

        // Validate the Showcase in the database
        List<Showcase> showcaseList = showcaseRepository.findAll();
        assertThat(showcaseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamShowcase() throws Exception {
        int databaseSizeBeforeUpdate = showcaseRepository.findAll().size();
        showcase.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShowcaseMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(showcase))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Showcase in the database
        List<Showcase> showcaseList = showcaseRepository.findAll();
        assertThat(showcaseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteShowcase() throws Exception {
        // Initialize the database
        showcaseRepository.saveAndFlush(showcase);

        int databaseSizeBeforeDelete = showcaseRepository.findAll().size();

        // Delete the showcase
        restShowcaseMockMvc
            .perform(delete(ENTITY_API_URL_ID, showcase.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Showcase> showcaseList = showcaseRepository.findAll();
        assertThat(showcaseList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
