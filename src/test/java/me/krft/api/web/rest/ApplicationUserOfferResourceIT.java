package me.krft.api.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import me.krft.api.IntegrationTest;
import me.krft.api.domain.ApplicationUser;
import me.krft.api.domain.ApplicationUserOffer;
import me.krft.api.domain.Offer;
import me.krft.api.repository.ApplicationUserOfferRepository;
import me.krft.api.service.ApplicationUserOfferService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ApplicationUserOfferResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ApplicationUserOfferResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_PRICE = 0;
    private static final Integer UPDATED_PRICE = 1;

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final String ENTITY_API_URL = "/api/application-user-offers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static final Random random = new Random();
    private static final AtomicLong count = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));

    @Autowired
    private ApplicationUserOfferRepository applicationUserOfferRepository;

    @Mock
    private ApplicationUserOfferRepository applicationUserOfferRepositoryMock;

    @Mock
    private ApplicationUserOfferService applicationUserOfferServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restApplicationUserOfferMockMvc;

    private ApplicationUserOffer applicationUserOffer;

    /**
     * Create an entity for this test.
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApplicationUserOffer createEntity(EntityManager em) {
        ApplicationUserOffer applicationUserOffer = new ApplicationUserOffer()
            .description(DEFAULT_DESCRIPTION)
            .price(DEFAULT_PRICE)
            .active(DEFAULT_ACTIVE);
        // Add required entity
        ApplicationUser applicationUser;
        if (TestUtil.findAll(em, ApplicationUser.class).isEmpty()) {
            applicationUser = ApplicationUserResourceIT.createEntity(em);
            em.persist(applicationUser);
            em.flush();
        } else {
            applicationUser = TestUtil.findAll(em, ApplicationUser.class).get(0);
        }
        applicationUserOffer.setProvider(applicationUser);
        // Add required entity
        Offer offer;
        if (TestUtil.findAll(em, Offer.class).isEmpty()) {
            offer = OfferResourceIT.createEntity(em);
            em.persist(offer);
            em.flush();
        } else {
            offer = TestUtil.findAll(em, Offer.class).get(0);
        }
        applicationUserOffer.setOffer(offer);
        return applicationUserOffer;
    }

    /**
     * Create an updated entity for this test.
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApplicationUserOffer createUpdatedEntity(EntityManager em) {
        ApplicationUserOffer applicationUserOffer = new ApplicationUserOffer()
            .description(UPDATED_DESCRIPTION)
            .price(UPDATED_PRICE)
            .active(UPDATED_ACTIVE);
        // Add required entity
        ApplicationUser applicationUser;
        if (TestUtil.findAll(em, ApplicationUser.class).isEmpty()) {
            applicationUser = ApplicationUserResourceIT.createUpdatedEntity(em);
            em.persist(applicationUser);
            em.flush();
        } else {
            applicationUser = TestUtil.findAll(em, ApplicationUser.class).get(0);
        }
        applicationUserOffer.setProvider(applicationUser);
        // Add required entity
        Offer offer;
        if (TestUtil.findAll(em, Offer.class).isEmpty()) {
            offer = OfferResourceIT.createUpdatedEntity(em);
            em.persist(offer);
            em.flush();
        } else {
            offer = TestUtil.findAll(em, Offer.class).get(0);
        }
        applicationUserOffer.setOffer(offer);
        return applicationUserOffer;
    }

    @BeforeEach
    public void initTest() {
        applicationUserOffer = createEntity(em);
    }

    @Test
    @Transactional
    void createApplicationUserOffer() throws Exception {
        int databaseSizeBeforeCreate = applicationUserOfferRepository.findAllWithEagerRelationships().size();
        // Create the ApplicationUserOffer
        restApplicationUserOfferMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(applicationUserOffer))
            )
            .andExpect(status().isCreated());

        // Validate the ApplicationUserOffer in the database
        List<ApplicationUserOffer> applicationUserOfferList = applicationUserOfferRepository.findAllWithEagerRelationships();
        assertThat(applicationUserOfferList).hasSize(databaseSizeBeforeCreate + 1);
        ApplicationUserOffer testApplicationUserOffer = applicationUserOfferList.get(applicationUserOfferList.size() - 1);
        assertThat(testApplicationUserOffer.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testApplicationUserOffer.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testApplicationUserOffer.getActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    void createApplicationUserOfferWithExistingId() throws Exception {
        // Create the ApplicationUserOffer with an existing ID
        applicationUserOffer.setId(1L);

        int databaseSizeBeforeCreate = applicationUserOfferRepository.findAllWithEagerRelationships().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restApplicationUserOfferMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(applicationUserOffer))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApplicationUserOffer in the database
        List<ApplicationUserOffer> applicationUserOfferList = applicationUserOfferRepository.findAllWithEagerRelationships();
        assertThat(applicationUserOfferList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationUserOfferRepository.findAllWithEagerRelationships().size();
        // set the field null
        applicationUserOffer.setDescription(null);

        // Create the ApplicationUserOffer, which fails.

        restApplicationUserOfferMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(applicationUserOffer))
            )
            .andExpect(status().isBadRequest());

        List<ApplicationUserOffer> applicationUserOfferList = applicationUserOfferRepository.findAllWithEagerRelationships();
        assertThat(applicationUserOfferList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationUserOfferRepository.findAllWithEagerRelationships().size();
        // set the field null
        applicationUserOffer.setPrice(null);

        // Create the ApplicationUserOffer, which fails.

        restApplicationUserOfferMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(applicationUserOffer))
            )
            .andExpect(status().isBadRequest());

        List<ApplicationUserOffer> applicationUserOfferList = applicationUserOfferRepository.findAllWithEagerRelationships();
        assertThat(applicationUserOfferList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationUserOfferRepository.findAllWithEagerRelationships().size();
        // set the field null
        applicationUserOffer.setActive(null);

        // Create the ApplicationUserOffer, which fails.

        restApplicationUserOfferMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(applicationUserOffer))
            )
            .andExpect(status().isBadRequest());

        List<ApplicationUserOffer> applicationUserOfferList = applicationUserOfferRepository.findAllWithEagerRelationships();
        assertThat(applicationUserOfferList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllApplicationUserOffers() throws Exception {
        // Initialize the database
        applicationUserOfferRepository.save(applicationUserOffer);

        // Get all the applicationUserOfferList
        restApplicationUserOfferMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(applicationUserOffer.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllApplicationUserOffersWithEagerRelationshipsIsEnabled() throws Exception {
        when(applicationUserOfferServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl<>(new ArrayList<>()));

        restApplicationUserOfferMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(applicationUserOfferServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllApplicationUserOffersWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(applicationUserOfferServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl<>(new ArrayList<>()));

        restApplicationUserOfferMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(applicationUserOfferRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getApplicationUserOffer() throws Exception {
        // Initialize the database
        applicationUserOfferRepository.save(applicationUserOffer);

        // Get the applicationUserOffer
        restApplicationUserOfferMockMvc
            .perform(get(ENTITY_API_URL_ID, applicationUserOffer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(applicationUserOffer.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE));
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
        applicationUserOfferRepository.save(applicationUserOffer);

        int databaseSizeBeforeUpdate = applicationUserOfferRepository.findAllWithEagerRelationships().size();

        // Update the applicationUserOffer
        ApplicationUserOffer updatedApplicationUserOffer = applicationUserOfferRepository.findById(applicationUserOffer.getId()).get();
        // Disconnect from session so that the updates on updatedApplicationUserOffer are not directly saved in db
        em.detach(updatedApplicationUserOffer);
        updatedApplicationUserOffer.description(UPDATED_DESCRIPTION).price(UPDATED_PRICE).active(UPDATED_ACTIVE);

        restApplicationUserOfferMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedApplicationUserOffer.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedApplicationUserOffer))
            )
            .andExpect(status().isOk());

        // Validate the ApplicationUserOffer in the database
        List<ApplicationUserOffer> applicationUserOfferList = applicationUserOfferRepository.findAllWithEagerRelationships();
        assertThat(applicationUserOfferList).hasSize(databaseSizeBeforeUpdate);
        ApplicationUserOffer testApplicationUserOffer = applicationUserOfferList.get(applicationUserOfferList.size() - 1);
        assertThat(testApplicationUserOffer.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testApplicationUserOffer.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testApplicationUserOffer.getActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void putNonExistingApplicationUserOffer() throws Exception {
        int databaseSizeBeforeUpdate = applicationUserOfferRepository.findAllWithEagerRelationships().size();
        applicationUserOffer.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApplicationUserOfferMockMvc
            .perform(
                put(ENTITY_API_URL_ID, applicationUserOffer.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(applicationUserOffer))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApplicationUserOffer in the database
        List<ApplicationUserOffer> applicationUserOfferList = applicationUserOfferRepository.findAllWithEagerRelationships();
        assertThat(applicationUserOfferList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchApplicationUserOffer() throws Exception {
        int databaseSizeBeforeUpdate = applicationUserOfferRepository.findAllWithEagerRelationships().size();
        applicationUserOffer.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApplicationUserOfferMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(applicationUserOffer))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApplicationUserOffer in the database
        List<ApplicationUserOffer> applicationUserOfferList = applicationUserOfferRepository.findAllWithEagerRelationships();
        assertThat(applicationUserOfferList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamApplicationUserOffer() throws Exception {
        int databaseSizeBeforeUpdate = applicationUserOfferRepository.findAllWithEagerRelationships().size();
        applicationUserOffer.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApplicationUserOfferMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(applicationUserOffer))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ApplicationUserOffer in the database
        List<ApplicationUserOffer> applicationUserOfferList = applicationUserOfferRepository.findAllWithEagerRelationships();
        assertThat(applicationUserOfferList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateApplicationUserOfferWithPatch() throws Exception {
        // Initialize the database
        applicationUserOfferRepository.save(applicationUserOffer);

        int databaseSizeBeforeUpdate = applicationUserOfferRepository.findAllWithEagerRelationships().size();

        // Update the applicationUserOffer using partial update
        ApplicationUserOffer partialUpdatedApplicationUserOffer = new ApplicationUserOffer();
        partialUpdatedApplicationUserOffer.setId(applicationUserOffer.getId());

        partialUpdatedApplicationUserOffer.description(UPDATED_DESCRIPTION).price(UPDATED_PRICE).active(UPDATED_ACTIVE);

        restApplicationUserOfferMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApplicationUserOffer.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedApplicationUserOffer))
            )
            .andExpect(status().isOk());

        // Validate the ApplicationUserOffer in the database
        List<ApplicationUserOffer> applicationUserOfferList = applicationUserOfferRepository.findAllWithEagerRelationships();
        assertThat(applicationUserOfferList).hasSize(databaseSizeBeforeUpdate);
        ApplicationUserOffer testApplicationUserOffer = applicationUserOfferList.get(applicationUserOfferList.size() - 1);
        assertThat(testApplicationUserOffer.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testApplicationUserOffer.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testApplicationUserOffer.getActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void fullUpdateApplicationUserOfferWithPatch() throws Exception {
        // Initialize the database
        applicationUserOfferRepository.save(applicationUserOffer);

        int databaseSizeBeforeUpdate = applicationUserOfferRepository.findAllWithEagerRelationships().size();

        // Update the applicationUserOffer using partial update
        ApplicationUserOffer partialUpdatedApplicationUserOffer = new ApplicationUserOffer();
        partialUpdatedApplicationUserOffer.setId(applicationUserOffer.getId());

        partialUpdatedApplicationUserOffer.description(UPDATED_DESCRIPTION).price(UPDATED_PRICE).active(UPDATED_ACTIVE);

        restApplicationUserOfferMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApplicationUserOffer.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedApplicationUserOffer))
            )
            .andExpect(status().isOk());

        // Validate the ApplicationUserOffer in the database
        List<ApplicationUserOffer> applicationUserOfferList = applicationUserOfferRepository.findAllWithEagerRelationships();
        assertThat(applicationUserOfferList).hasSize(databaseSizeBeforeUpdate);
        ApplicationUserOffer testApplicationUserOffer = applicationUserOfferList.get(applicationUserOfferList.size() - 1);
        assertThat(testApplicationUserOffer.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testApplicationUserOffer.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testApplicationUserOffer.getActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void patchNonExistingApplicationUserOffer() throws Exception {
        int databaseSizeBeforeUpdate = applicationUserOfferRepository.findAllWithEagerRelationships().size();
        applicationUserOffer.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApplicationUserOfferMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, applicationUserOffer.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(applicationUserOffer))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApplicationUserOffer in the database
        List<ApplicationUserOffer> applicationUserOfferList = applicationUserOfferRepository.findAllWithEagerRelationships();
        assertThat(applicationUserOfferList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchApplicationUserOffer() throws Exception {
        int databaseSizeBeforeUpdate = applicationUserOfferRepository.findAllWithEagerRelationships().size();
        applicationUserOffer.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApplicationUserOfferMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(applicationUserOffer))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApplicationUserOffer in the database
        List<ApplicationUserOffer> applicationUserOfferList = applicationUserOfferRepository.findAllWithEagerRelationships();
        assertThat(applicationUserOfferList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamApplicationUserOffer() throws Exception {
        int databaseSizeBeforeUpdate = applicationUserOfferRepository.findAllWithEagerRelationships().size();
        applicationUserOffer.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApplicationUserOfferMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(applicationUserOffer))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ApplicationUserOffer in the database
        List<ApplicationUserOffer> applicationUserOfferList = applicationUserOfferRepository.findAllWithEagerRelationships();
        assertThat(applicationUserOfferList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteApplicationUserOffer() throws Exception {
        // Initialize the database
        applicationUserOfferRepository.save(applicationUserOffer);

        int databaseSizeBeforeDelete = applicationUserOfferRepository.findAllWithEagerRelationships().size();

        // Delete the applicationUserOffer
        restApplicationUserOfferMockMvc
            .perform(delete(ENTITY_API_URL_ID, applicationUserOffer.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ApplicationUserOffer> applicationUserOfferList = applicationUserOfferRepository.findAllWithEagerRelationships();
        assertThat(applicationUserOfferList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
