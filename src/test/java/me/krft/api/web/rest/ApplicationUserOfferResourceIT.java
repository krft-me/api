package me.krft.api.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import me.krft.api.IntegrationTest;
import me.krft.api.domain.ApplicationUserOffer;
import me.krft.api.repository.ApplicationUserOfferRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ApplicationUserOfferResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ApplicationUserOfferResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/application-user-offers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ApplicationUserOfferRepository applicationUserOfferRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restApplicationUserOfferMockMvc;

    private ApplicationUserOffer applicationUserOffer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApplicationUserOffer createEntity(EntityManager em) {
        ApplicationUserOffer applicationUserOffer = new ApplicationUserOffer().description(DEFAULT_DESCRIPTION);
        return applicationUserOffer;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApplicationUserOffer createUpdatedEntity(EntityManager em) {
        ApplicationUserOffer applicationUserOffer = new ApplicationUserOffer().description(UPDATED_DESCRIPTION);
        return applicationUserOffer;
    }

    @BeforeEach
    public void initTest() {
        applicationUserOffer = createEntity(em);
    }

    @Test
    @Transactional
    void createApplicationUserOffer() throws Exception {
        int databaseSizeBeforeCreate = applicationUserOfferRepository.findAll().size();
        // Create the ApplicationUserOffer
        restApplicationUserOfferMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(applicationUserOffer))
            )
            .andExpect(status().isCreated());

        // Validate the ApplicationUserOffer in the database
        List<ApplicationUserOffer> applicationUserOfferList = applicationUserOfferRepository.findAll();
        assertThat(applicationUserOfferList).hasSize(databaseSizeBeforeCreate + 1);
        ApplicationUserOffer testApplicationUserOffer = applicationUserOfferList.get(applicationUserOfferList.size() - 1);
        assertThat(testApplicationUserOffer.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createApplicationUserOfferWithExistingId() throws Exception {
        // Create the ApplicationUserOffer with an existing ID
        applicationUserOffer.setId(1L);

        int databaseSizeBeforeCreate = applicationUserOfferRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restApplicationUserOfferMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(applicationUserOffer))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApplicationUserOffer in the database
        List<ApplicationUserOffer> applicationUserOfferList = applicationUserOfferRepository.findAll();
        assertThat(applicationUserOfferList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationUserOfferRepository.findAll().size();
        // set the field null
        applicationUserOffer.setDescription(null);

        // Create the ApplicationUserOffer, which fails.

        restApplicationUserOfferMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(applicationUserOffer))
            )
            .andExpect(status().isBadRequest());

        List<ApplicationUserOffer> applicationUserOfferList = applicationUserOfferRepository.findAll();
        assertThat(applicationUserOfferList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllApplicationUserOffers() throws Exception {
        // Initialize the database
        applicationUserOfferRepository.saveAndFlush(applicationUserOffer);

        // Get all the applicationUserOfferList
        restApplicationUserOfferMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(applicationUserOffer.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getApplicationUserOffer() throws Exception {
        // Initialize the database
        applicationUserOfferRepository.saveAndFlush(applicationUserOffer);

        // Get the applicationUserOffer
        restApplicationUserOfferMockMvc
            .perform(get(ENTITY_API_URL_ID, applicationUserOffer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(applicationUserOffer.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingApplicationUserOffer() throws Exception {
        // Get the applicationUserOffer
        restApplicationUserOfferMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingApplicationUserOffer() throws Exception {
        // Initialize the database
        applicationUserOfferRepository.saveAndFlush(applicationUserOffer);

        int databaseSizeBeforeUpdate = applicationUserOfferRepository.findAll().size();

        // Update the applicationUserOffer
        ApplicationUserOffer updatedApplicationUserOffer = applicationUserOfferRepository.findById(applicationUserOffer.getId()).get();
        // Disconnect from session so that the updates on updatedApplicationUserOffer are not directly saved in db
        em.detach(updatedApplicationUserOffer);
        updatedApplicationUserOffer.description(UPDATED_DESCRIPTION);

        restApplicationUserOfferMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedApplicationUserOffer.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedApplicationUserOffer))
            )
            .andExpect(status().isOk());

        // Validate the ApplicationUserOffer in the database
        List<ApplicationUserOffer> applicationUserOfferList = applicationUserOfferRepository.findAll();
        assertThat(applicationUserOfferList).hasSize(databaseSizeBeforeUpdate);
        ApplicationUserOffer testApplicationUserOffer = applicationUserOfferList.get(applicationUserOfferList.size() - 1);
        assertThat(testApplicationUserOffer.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingApplicationUserOffer() throws Exception {
        int databaseSizeBeforeUpdate = applicationUserOfferRepository.findAll().size();
        applicationUserOffer.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApplicationUserOfferMockMvc
            .perform(
                put(ENTITY_API_URL_ID, applicationUserOffer.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(applicationUserOffer))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApplicationUserOffer in the database
        List<ApplicationUserOffer> applicationUserOfferList = applicationUserOfferRepository.findAll();
        assertThat(applicationUserOfferList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchApplicationUserOffer() throws Exception {
        int databaseSizeBeforeUpdate = applicationUserOfferRepository.findAll().size();
        applicationUserOffer.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApplicationUserOfferMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(applicationUserOffer))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApplicationUserOffer in the database
        List<ApplicationUserOffer> applicationUserOfferList = applicationUserOfferRepository.findAll();
        assertThat(applicationUserOfferList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamApplicationUserOffer() throws Exception {
        int databaseSizeBeforeUpdate = applicationUserOfferRepository.findAll().size();
        applicationUserOffer.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApplicationUserOfferMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(applicationUserOffer))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ApplicationUserOffer in the database
        List<ApplicationUserOffer> applicationUserOfferList = applicationUserOfferRepository.findAll();
        assertThat(applicationUserOfferList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateApplicationUserOfferWithPatch() throws Exception {
        // Initialize the database
        applicationUserOfferRepository.saveAndFlush(applicationUserOffer);

        int databaseSizeBeforeUpdate = applicationUserOfferRepository.findAll().size();

        // Update the applicationUserOffer using partial update
        ApplicationUserOffer partialUpdatedApplicationUserOffer = new ApplicationUserOffer();
        partialUpdatedApplicationUserOffer.setId(applicationUserOffer.getId());

        partialUpdatedApplicationUserOffer.description(UPDATED_DESCRIPTION);

        restApplicationUserOfferMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApplicationUserOffer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedApplicationUserOffer))
            )
            .andExpect(status().isOk());

        // Validate the ApplicationUserOffer in the database
        List<ApplicationUserOffer> applicationUserOfferList = applicationUserOfferRepository.findAll();
        assertThat(applicationUserOfferList).hasSize(databaseSizeBeforeUpdate);
        ApplicationUserOffer testApplicationUserOffer = applicationUserOfferList.get(applicationUserOfferList.size() - 1);
        assertThat(testApplicationUserOffer.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateApplicationUserOfferWithPatch() throws Exception {
        // Initialize the database
        applicationUserOfferRepository.saveAndFlush(applicationUserOffer);

        int databaseSizeBeforeUpdate = applicationUserOfferRepository.findAll().size();

        // Update the applicationUserOffer using partial update
        ApplicationUserOffer partialUpdatedApplicationUserOffer = new ApplicationUserOffer();
        partialUpdatedApplicationUserOffer.setId(applicationUserOffer.getId());

        partialUpdatedApplicationUserOffer.description(UPDATED_DESCRIPTION);

        restApplicationUserOfferMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApplicationUserOffer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedApplicationUserOffer))
            )
            .andExpect(status().isOk());

        // Validate the ApplicationUserOffer in the database
        List<ApplicationUserOffer> applicationUserOfferList = applicationUserOfferRepository.findAll();
        assertThat(applicationUserOfferList).hasSize(databaseSizeBeforeUpdate);
        ApplicationUserOffer testApplicationUserOffer = applicationUserOfferList.get(applicationUserOfferList.size() - 1);
        assertThat(testApplicationUserOffer.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingApplicationUserOffer() throws Exception {
        int databaseSizeBeforeUpdate = applicationUserOfferRepository.findAll().size();
        applicationUserOffer.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApplicationUserOfferMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, applicationUserOffer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(applicationUserOffer))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApplicationUserOffer in the database
        List<ApplicationUserOffer> applicationUserOfferList = applicationUserOfferRepository.findAll();
        assertThat(applicationUserOfferList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchApplicationUserOffer() throws Exception {
        int databaseSizeBeforeUpdate = applicationUserOfferRepository.findAll().size();
        applicationUserOffer.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApplicationUserOfferMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(applicationUserOffer))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApplicationUserOffer in the database
        List<ApplicationUserOffer> applicationUserOfferList = applicationUserOfferRepository.findAll();
        assertThat(applicationUserOfferList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamApplicationUserOffer() throws Exception {
        int databaseSizeBeforeUpdate = applicationUserOfferRepository.findAll().size();
        applicationUserOffer.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApplicationUserOfferMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(applicationUserOffer))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ApplicationUserOffer in the database
        List<ApplicationUserOffer> applicationUserOfferList = applicationUserOfferRepository.findAll();
        assertThat(applicationUserOfferList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteApplicationUserOffer() throws Exception {
        // Initialize the database
        applicationUserOfferRepository.saveAndFlush(applicationUserOffer);

        int databaseSizeBeforeDelete = applicationUserOfferRepository.findAll().size();

        // Delete the applicationUserOffer
        restApplicationUserOfferMockMvc
            .perform(delete(ENTITY_API_URL_ID, applicationUserOffer.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ApplicationUserOffer> applicationUserOfferList = applicationUserOfferRepository.findAll();
        assertThat(applicationUserOfferList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
