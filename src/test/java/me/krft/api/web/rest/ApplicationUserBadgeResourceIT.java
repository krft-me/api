package me.krft.api.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import me.krft.api.IntegrationTest;
import me.krft.api.domain.ApplicationUser;
import me.krft.api.domain.ApplicationUserBadge;
import me.krft.api.repository.ApplicationUserBadgeRepository;
import me.krft.api.service.dto.ApplicationUserBadgeDTO;
import me.krft.api.service.mapper.ApplicationUserBadgeMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ApplicationUserBadgeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ApplicationUserBadgeResourceIT {

    private static final Instant DEFAULT_OBTAINED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_OBTAINED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/application-user-badges";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ApplicationUserBadgeRepository applicationUserBadgeRepository;

    @Autowired
    private ApplicationUserBadgeMapper applicationUserBadgeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restApplicationUserBadgeMockMvc;

    private ApplicationUserBadge applicationUserBadge;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApplicationUserBadge createEntity(EntityManager em) {
        ApplicationUserBadge applicationUserBadge = new ApplicationUserBadge().obtainedDate(DEFAULT_OBTAINED_DATE);
        // Add required entity
        ApplicationUser applicationUser;
        if (TestUtil.findAll(em, ApplicationUser.class).isEmpty()) {
            applicationUser = ApplicationUserResourceIT.createEntity(em);
            em.persist(applicationUser);
            em.flush();
        } else {
            applicationUser = TestUtil.findAll(em, ApplicationUser.class).get(0);
        }
        applicationUserBadge.setUser(applicationUser);
        return applicationUserBadge;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApplicationUserBadge createUpdatedEntity(EntityManager em) {
        ApplicationUserBadge applicationUserBadge = new ApplicationUserBadge().obtainedDate(UPDATED_OBTAINED_DATE);
        // Add required entity
        ApplicationUser applicationUser;
        if (TestUtil.findAll(em, ApplicationUser.class).isEmpty()) {
            applicationUser = ApplicationUserResourceIT.createUpdatedEntity(em);
            em.persist(applicationUser);
            em.flush();
        } else {
            applicationUser = TestUtil.findAll(em, ApplicationUser.class).get(0);
        }
        applicationUserBadge.setUser(applicationUser);
        return applicationUserBadge;
    }

    @BeforeEach
    public void initTest() {
        applicationUserBadge = createEntity(em);
    }

    @Test
    @Transactional
    void createApplicationUserBadge() throws Exception {
        int databaseSizeBeforeCreate = applicationUserBadgeRepository.findAll().size();
        // Create the ApplicationUserBadge
        ApplicationUserBadgeDTO applicationUserBadgeDTO = applicationUserBadgeMapper.toDto(applicationUserBadge);
        restApplicationUserBadgeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(applicationUserBadgeDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ApplicationUserBadge in the database
        List<ApplicationUserBadge> applicationUserBadgeList = applicationUserBadgeRepository.findAll();
        assertThat(applicationUserBadgeList).hasSize(databaseSizeBeforeCreate + 1);
        ApplicationUserBadge testApplicationUserBadge = applicationUserBadgeList.get(applicationUserBadgeList.size() - 1);
        assertThat(testApplicationUserBadge.getObtainedDate()).isEqualTo(DEFAULT_OBTAINED_DATE);
    }

    @Test
    @Transactional
    void createApplicationUserBadgeWithExistingId() throws Exception {
        // Create the ApplicationUserBadge with an existing ID
        applicationUserBadge.setId(1L);
        ApplicationUserBadgeDTO applicationUserBadgeDTO = applicationUserBadgeMapper.toDto(applicationUserBadge);

        int databaseSizeBeforeCreate = applicationUserBadgeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restApplicationUserBadgeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(applicationUserBadgeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApplicationUserBadge in the database
        List<ApplicationUserBadge> applicationUserBadgeList = applicationUserBadgeRepository.findAll();
        assertThat(applicationUserBadgeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkObtainedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationUserBadgeRepository.findAll().size();
        // set the field null
        applicationUserBadge.setObtainedDate(null);

        // Create the ApplicationUserBadge, which fails.
        ApplicationUserBadgeDTO applicationUserBadgeDTO = applicationUserBadgeMapper.toDto(applicationUserBadge);

        restApplicationUserBadgeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(applicationUserBadgeDTO))
            )
            .andExpect(status().isBadRequest());

        List<ApplicationUserBadge> applicationUserBadgeList = applicationUserBadgeRepository.findAll();
        assertThat(applicationUserBadgeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllApplicationUserBadges() throws Exception {
        // Initialize the database
        applicationUserBadgeRepository.saveAndFlush(applicationUserBadge);

        // Get all the applicationUserBadgeList
        restApplicationUserBadgeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(applicationUserBadge.getId().intValue())))
            .andExpect(jsonPath("$.[*].obtainedDate").value(hasItem(DEFAULT_OBTAINED_DATE.toString())));
    }

    @Test
    @Transactional
    void getApplicationUserBadge() throws Exception {
        // Initialize the database
        applicationUserBadgeRepository.saveAndFlush(applicationUserBadge);

        // Get the applicationUserBadge
        restApplicationUserBadgeMockMvc
            .perform(get(ENTITY_API_URL_ID, applicationUserBadge.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(applicationUserBadge.getId().intValue()))
            .andExpect(jsonPath("$.obtainedDate").value(DEFAULT_OBTAINED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingApplicationUserBadge() throws Exception {
        // Get the applicationUserBadge
        restApplicationUserBadgeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingApplicationUserBadge() throws Exception {
        // Initialize the database
        applicationUserBadgeRepository.saveAndFlush(applicationUserBadge);

        int databaseSizeBeforeUpdate = applicationUserBadgeRepository.findAll().size();

        // Update the applicationUserBadge
        ApplicationUserBadge updatedApplicationUserBadge = applicationUserBadgeRepository.findById(applicationUserBadge.getId()).get();
        // Disconnect from session so that the updates on updatedApplicationUserBadge are not directly saved in db
        em.detach(updatedApplicationUserBadge);
        updatedApplicationUserBadge.obtainedDate(UPDATED_OBTAINED_DATE);
        ApplicationUserBadgeDTO applicationUserBadgeDTO = applicationUserBadgeMapper.toDto(updatedApplicationUserBadge);

        restApplicationUserBadgeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, applicationUserBadgeDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(applicationUserBadgeDTO))
            )
            .andExpect(status().isOk());

        // Validate the ApplicationUserBadge in the database
        List<ApplicationUserBadge> applicationUserBadgeList = applicationUserBadgeRepository.findAll();
        assertThat(applicationUserBadgeList).hasSize(databaseSizeBeforeUpdate);
        ApplicationUserBadge testApplicationUserBadge = applicationUserBadgeList.get(applicationUserBadgeList.size() - 1);
        assertThat(testApplicationUserBadge.getObtainedDate()).isEqualTo(UPDATED_OBTAINED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingApplicationUserBadge() throws Exception {
        int databaseSizeBeforeUpdate = applicationUserBadgeRepository.findAll().size();
        applicationUserBadge.setId(count.incrementAndGet());

        // Create the ApplicationUserBadge
        ApplicationUserBadgeDTO applicationUserBadgeDTO = applicationUserBadgeMapper.toDto(applicationUserBadge);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApplicationUserBadgeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, applicationUserBadgeDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(applicationUserBadgeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApplicationUserBadge in the database
        List<ApplicationUserBadge> applicationUserBadgeList = applicationUserBadgeRepository.findAll();
        assertThat(applicationUserBadgeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchApplicationUserBadge() throws Exception {
        int databaseSizeBeforeUpdate = applicationUserBadgeRepository.findAll().size();
        applicationUserBadge.setId(count.incrementAndGet());

        // Create the ApplicationUserBadge
        ApplicationUserBadgeDTO applicationUserBadgeDTO = applicationUserBadgeMapper.toDto(applicationUserBadge);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApplicationUserBadgeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(applicationUserBadgeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApplicationUserBadge in the database
        List<ApplicationUserBadge> applicationUserBadgeList = applicationUserBadgeRepository.findAll();
        assertThat(applicationUserBadgeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamApplicationUserBadge() throws Exception {
        int databaseSizeBeforeUpdate = applicationUserBadgeRepository.findAll().size();
        applicationUserBadge.setId(count.incrementAndGet());

        // Create the ApplicationUserBadge
        ApplicationUserBadgeDTO applicationUserBadgeDTO = applicationUserBadgeMapper.toDto(applicationUserBadge);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApplicationUserBadgeMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(applicationUserBadgeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ApplicationUserBadge in the database
        List<ApplicationUserBadge> applicationUserBadgeList = applicationUserBadgeRepository.findAll();
        assertThat(applicationUserBadgeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateApplicationUserBadgeWithPatch() throws Exception {
        // Initialize the database
        applicationUserBadgeRepository.saveAndFlush(applicationUserBadge);

        int databaseSizeBeforeUpdate = applicationUserBadgeRepository.findAll().size();

        // Update the applicationUserBadge using partial update
        ApplicationUserBadge partialUpdatedApplicationUserBadge = new ApplicationUserBadge();
        partialUpdatedApplicationUserBadge.setId(applicationUserBadge.getId());

        partialUpdatedApplicationUserBadge.obtainedDate(UPDATED_OBTAINED_DATE);

        restApplicationUserBadgeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApplicationUserBadge.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedApplicationUserBadge))
            )
            .andExpect(status().isOk());

        // Validate the ApplicationUserBadge in the database
        List<ApplicationUserBadge> applicationUserBadgeList = applicationUserBadgeRepository.findAll();
        assertThat(applicationUserBadgeList).hasSize(databaseSizeBeforeUpdate);
        ApplicationUserBadge testApplicationUserBadge = applicationUserBadgeList.get(applicationUserBadgeList.size() - 1);
        assertThat(testApplicationUserBadge.getObtainedDate()).isEqualTo(UPDATED_OBTAINED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateApplicationUserBadgeWithPatch() throws Exception {
        // Initialize the database
        applicationUserBadgeRepository.saveAndFlush(applicationUserBadge);

        int databaseSizeBeforeUpdate = applicationUserBadgeRepository.findAll().size();

        // Update the applicationUserBadge using partial update
        ApplicationUserBadge partialUpdatedApplicationUserBadge = new ApplicationUserBadge();
        partialUpdatedApplicationUserBadge.setId(applicationUserBadge.getId());

        partialUpdatedApplicationUserBadge.obtainedDate(UPDATED_OBTAINED_DATE);

        restApplicationUserBadgeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApplicationUserBadge.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedApplicationUserBadge))
            )
            .andExpect(status().isOk());

        // Validate the ApplicationUserBadge in the database
        List<ApplicationUserBadge> applicationUserBadgeList = applicationUserBadgeRepository.findAll();
        assertThat(applicationUserBadgeList).hasSize(databaseSizeBeforeUpdate);
        ApplicationUserBadge testApplicationUserBadge = applicationUserBadgeList.get(applicationUserBadgeList.size() - 1);
        assertThat(testApplicationUserBadge.getObtainedDate()).isEqualTo(UPDATED_OBTAINED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingApplicationUserBadge() throws Exception {
        int databaseSizeBeforeUpdate = applicationUserBadgeRepository.findAll().size();
        applicationUserBadge.setId(count.incrementAndGet());

        // Create the ApplicationUserBadge
        ApplicationUserBadgeDTO applicationUserBadgeDTO = applicationUserBadgeMapper.toDto(applicationUserBadge);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApplicationUserBadgeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, applicationUserBadgeDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(applicationUserBadgeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApplicationUserBadge in the database
        List<ApplicationUserBadge> applicationUserBadgeList = applicationUserBadgeRepository.findAll();
        assertThat(applicationUserBadgeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchApplicationUserBadge() throws Exception {
        int databaseSizeBeforeUpdate = applicationUserBadgeRepository.findAll().size();
        applicationUserBadge.setId(count.incrementAndGet());

        // Create the ApplicationUserBadge
        ApplicationUserBadgeDTO applicationUserBadgeDTO = applicationUserBadgeMapper.toDto(applicationUserBadge);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApplicationUserBadgeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(applicationUserBadgeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApplicationUserBadge in the database
        List<ApplicationUserBadge> applicationUserBadgeList = applicationUserBadgeRepository.findAll();
        assertThat(applicationUserBadgeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamApplicationUserBadge() throws Exception {
        int databaseSizeBeforeUpdate = applicationUserBadgeRepository.findAll().size();
        applicationUserBadge.setId(count.incrementAndGet());

        // Create the ApplicationUserBadge
        ApplicationUserBadgeDTO applicationUserBadgeDTO = applicationUserBadgeMapper.toDto(applicationUserBadge);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApplicationUserBadgeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(applicationUserBadgeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ApplicationUserBadge in the database
        List<ApplicationUserBadge> applicationUserBadgeList = applicationUserBadgeRepository.findAll();
        assertThat(applicationUserBadgeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteApplicationUserBadge() throws Exception {
        // Initialize the database
        applicationUserBadgeRepository.saveAndFlush(applicationUserBadge);

        int databaseSizeBeforeDelete = applicationUserBadgeRepository.findAll().size();

        // Delete the applicationUserBadge
        restApplicationUserBadgeMockMvc
            .perform(delete(ENTITY_API_URL_ID, applicationUserBadge.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ApplicationUserBadge> applicationUserBadgeList = applicationUserBadgeRepository.findAll();
        assertThat(applicationUserBadgeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
