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
import me.krft.api.domain.Offer;
import me.krft.api.domain.OfferCategory;
import me.krft.api.repository.OfferRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link OfferResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OfferResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/offers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOfferMockMvc;

    private Offer offer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Offer createEntity(EntityManager em) {
        Offer offer = new Offer().name(DEFAULT_NAME);
        // Add required entity
        OfferCategory offerCategory;
        if (TestUtil.findAll(em, OfferCategory.class).isEmpty()) {
            offerCategory = OfferCategoryResourceIT.createEntity(em);
            em.persist(offerCategory);
            em.flush();
        } else {
            offerCategory = TestUtil.findAll(em, OfferCategory.class).get(0);
        }
        offer.setCategory(offerCategory);
        return offer;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Offer createUpdatedEntity(EntityManager em) {
        Offer offer = new Offer().name(UPDATED_NAME);
        // Add required entity
        OfferCategory offerCategory;
        if (TestUtil.findAll(em, OfferCategory.class).isEmpty()) {
            offerCategory = OfferCategoryResourceIT.createUpdatedEntity(em);
            em.persist(offerCategory);
            em.flush();
        } else {
            offerCategory = TestUtil.findAll(em, OfferCategory.class).get(0);
        }
        offer.setCategory(offerCategory);
        return offer;
    }

    @BeforeEach
    public void initTest() {
        offer = createEntity(em);
    }

    @Test
    @Transactional
    void createOffer() throws Exception {
        int databaseSizeBeforeCreate = offerRepository.findAll().size();
        // Create the Offer
        restOfferMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(offer))
            )
            .andExpect(status().isCreated());

        // Validate the Offer in the database
        List<Offer> offerList = offerRepository.findAll();
        assertThat(offerList).hasSize(databaseSizeBeforeCreate + 1);
        Offer testOffer = offerList.get(offerList.size() - 1);
        assertThat(testOffer.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createOfferWithExistingId() throws Exception {
        // Create the Offer with an existing ID
        offer.setId(1L);

        int databaseSizeBeforeCreate = offerRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOfferMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(offer))
            )
            .andExpect(status().isBadRequest());

        // Validate the Offer in the database
        List<Offer> offerList = offerRepository.findAll();
        assertThat(offerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = offerRepository.findAll().size();
        // set the field null
        offer.setName(null);

        // Create the Offer, which fails.

        restOfferMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(offer))
            )
            .andExpect(status().isBadRequest());

        List<Offer> offerList = offerRepository.findAll();
        assertThat(offerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllOffers() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get all the offerList
        restOfferMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(offer.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getOffer() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get the offer
        restOfferMockMvc
            .perform(get(ENTITY_API_URL_ID, offer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(offer.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingOffer() throws Exception {
        // Get the offer
        restOfferMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOffer() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        int databaseSizeBeforeUpdate = offerRepository.findAll().size();

        // Update the offer
        Offer updatedOffer = offerRepository.findById(offer.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedOffer are not directly saved in db
        em.detach(updatedOffer);
        updatedOffer.name(UPDATED_NAME);

        restOfferMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedOffer.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedOffer))
            )
            .andExpect(status().isOk());

        // Validate the Offer in the database
        List<Offer> offerList = offerRepository.findAll();
        assertThat(offerList).hasSize(databaseSizeBeforeUpdate);
        Offer testOffer = offerList.get(offerList.size() - 1);
        assertThat(testOffer.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingOffer() throws Exception {
        int databaseSizeBeforeUpdate = offerRepository.findAll().size();
        offer.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOfferMockMvc
            .perform(
                put(ENTITY_API_URL_ID, offer.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(offer))
            )
            .andExpect(status().isBadRequest());

        // Validate the Offer in the database
        List<Offer> offerList = offerRepository.findAll();
        assertThat(offerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOffer() throws Exception {
        int databaseSizeBeforeUpdate = offerRepository.findAll().size();
        offer.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOfferMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(offer))
            )
            .andExpect(status().isBadRequest());

        // Validate the Offer in the database
        List<Offer> offerList = offerRepository.findAll();
        assertThat(offerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOffer() throws Exception {
        int databaseSizeBeforeUpdate = offerRepository.findAll().size();
        offer.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOfferMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(offer))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Offer in the database
        List<Offer> offerList = offerRepository.findAll();
        assertThat(offerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOfferWithPatch() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        int databaseSizeBeforeUpdate = offerRepository.findAll().size();

        // Update the offer using partial update
        Offer partialUpdatedOffer = new Offer();
        partialUpdatedOffer.setId(offer.getId());

        partialUpdatedOffer.name(UPDATED_NAME);

        restOfferMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOffer.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOffer))
            )
            .andExpect(status().isOk());

        // Validate the Offer in the database
        List<Offer> offerList = offerRepository.findAll();
        assertThat(offerList).hasSize(databaseSizeBeforeUpdate);
        Offer testOffer = offerList.get(offerList.size() - 1);
        assertThat(testOffer.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void fullUpdateOfferWithPatch() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        int databaseSizeBeforeUpdate = offerRepository.findAll().size();

        // Update the offer using partial update
        Offer partialUpdatedOffer = new Offer();
        partialUpdatedOffer.setId(offer.getId());

        partialUpdatedOffer.name(UPDATED_NAME);

        restOfferMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOffer.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOffer))
            )
            .andExpect(status().isOk());

        // Validate the Offer in the database
        List<Offer> offerList = offerRepository.findAll();
        assertThat(offerList).hasSize(databaseSizeBeforeUpdate);
        Offer testOffer = offerList.get(offerList.size() - 1);
        assertThat(testOffer.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingOffer() throws Exception {
        int databaseSizeBeforeUpdate = offerRepository.findAll().size();
        offer.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOfferMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, offer.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(offer))
            )
            .andExpect(status().isBadRequest());

        // Validate the Offer in the database
        List<Offer> offerList = offerRepository.findAll();
        assertThat(offerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOffer() throws Exception {
        int databaseSizeBeforeUpdate = offerRepository.findAll().size();
        offer.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOfferMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(offer))
            )
            .andExpect(status().isBadRequest());

        // Validate the Offer in the database
        List<Offer> offerList = offerRepository.findAll();
        assertThat(offerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOffer() throws Exception {
        int databaseSizeBeforeUpdate = offerRepository.findAll().size();
        offer.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOfferMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(offer))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Offer in the database
        List<Offer> offerList = offerRepository.findAll();
        assertThat(offerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOffer() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        int databaseSizeBeforeDelete = offerRepository.findAll().size();

        // Delete the offer
        restOfferMockMvc
            .perform(delete(ENTITY_API_URL_ID, offer.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Offer> offerList = offerRepository.findAll();
        assertThat(offerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
