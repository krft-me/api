package me.krft.api.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import me.krft.api.IntegrationTest;
import me.krft.api.domain.Caca;
import me.krft.api.repository.CacaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CacaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CacaResourceIT {

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/cacas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CacaRepository cacaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCacaMockMvc;

    private Caca caca;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Caca createEntity(EntityManager em) {
        Caca caca = new Caca().label(DEFAULT_LABEL);
        return caca;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Caca createUpdatedEntity(EntityManager em) {
        Caca caca = new Caca().label(UPDATED_LABEL);
        return caca;
    }

    @BeforeEach
    public void initTest() {
        caca = createEntity(em);
    }

    @Test
    @Transactional
    void createCaca() throws Exception {
        int databaseSizeBeforeCreate = cacaRepository.findAll().size();
        // Create the Caca
        restCacaMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(caca))
            )
            .andExpect(status().isCreated());

        // Validate the Caca in the database
        List<Caca> cacaList = cacaRepository.findAll();
        assertThat(cacaList).hasSize(databaseSizeBeforeCreate + 1);
        Caca testCaca = cacaList.get(cacaList.size() - 1);
        assertThat(testCaca.getLabel()).isEqualTo(DEFAULT_LABEL);
    }

    @Test
    @Transactional
    void createCacaWithExistingId() throws Exception {
        // Create the Caca with an existing ID
        caca.setId(1L);

        int databaseSizeBeforeCreate = cacaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCacaMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(caca))
            )
            .andExpect(status().isBadRequest());

        // Validate the Caca in the database
        List<Caca> cacaList = cacaRepository.findAll();
        assertThat(cacaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCacas() throws Exception {
        // Initialize the database
        cacaRepository.saveAndFlush(caca);

        // Get all the cacaList
        restCacaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(caca.getId().intValue())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL)));
    }

    @Test
    @Transactional
    void getCaca() throws Exception {
        // Initialize the database
        cacaRepository.saveAndFlush(caca);

        // Get the caca
        restCacaMockMvc
            .perform(get(ENTITY_API_URL_ID, caca.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(caca.getId().intValue()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL));
    }

    @Test
    @Transactional
    void getNonExistingCaca() throws Exception {
        // Get the caca
        restCacaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCaca() throws Exception {
        // Initialize the database
        cacaRepository.saveAndFlush(caca);

        int databaseSizeBeforeUpdate = cacaRepository.findAll().size();

        // Update the caca
        Caca updatedCaca = cacaRepository.findById(caca.getId()).get();
        // Disconnect from session so that the updates on updatedCaca are not directly saved in db
        em.detach(updatedCaca);
        updatedCaca.label(UPDATED_LABEL);

        restCacaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCaca.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCaca))
            )
            .andExpect(status().isOk());

        // Validate the Caca in the database
        List<Caca> cacaList = cacaRepository.findAll();
        assertThat(cacaList).hasSize(databaseSizeBeforeUpdate);
        Caca testCaca = cacaList.get(cacaList.size() - 1);
        assertThat(testCaca.getLabel()).isEqualTo(UPDATED_LABEL);
    }

    @Test
    @Transactional
    void putNonExistingCaca() throws Exception {
        int databaseSizeBeforeUpdate = cacaRepository.findAll().size();
        caca.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCacaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, caca.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(caca))
            )
            .andExpect(status().isBadRequest());

        // Validate the Caca in the database
        List<Caca> cacaList = cacaRepository.findAll();
        assertThat(cacaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCaca() throws Exception {
        int databaseSizeBeforeUpdate = cacaRepository.findAll().size();
        caca.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCacaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(caca))
            )
            .andExpect(status().isBadRequest());

        // Validate the Caca in the database
        List<Caca> cacaList = cacaRepository.findAll();
        assertThat(cacaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCaca() throws Exception {
        int databaseSizeBeforeUpdate = cacaRepository.findAll().size();
        caca.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCacaMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(caca))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Caca in the database
        List<Caca> cacaList = cacaRepository.findAll();
        assertThat(cacaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCacaWithPatch() throws Exception {
        // Initialize the database
        cacaRepository.saveAndFlush(caca);

        int databaseSizeBeforeUpdate = cacaRepository.findAll().size();

        // Update the caca using partial update
        Caca partialUpdatedCaca = new Caca();
        partialUpdatedCaca.setId(caca.getId());

        restCacaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCaca.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCaca))
            )
            .andExpect(status().isOk());

        // Validate the Caca in the database
        List<Caca> cacaList = cacaRepository.findAll();
        assertThat(cacaList).hasSize(databaseSizeBeforeUpdate);
        Caca testCaca = cacaList.get(cacaList.size() - 1);
        assertThat(testCaca.getLabel()).isEqualTo(DEFAULT_LABEL);
    }

    @Test
    @Transactional
    void fullUpdateCacaWithPatch() throws Exception {
        // Initialize the database
        cacaRepository.saveAndFlush(caca);

        int databaseSizeBeforeUpdate = cacaRepository.findAll().size();

        // Update the caca using partial update
        Caca partialUpdatedCaca = new Caca();
        partialUpdatedCaca.setId(caca.getId());

        partialUpdatedCaca.label(UPDATED_LABEL);

        restCacaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCaca.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCaca))
            )
            .andExpect(status().isOk());

        // Validate the Caca in the database
        List<Caca> cacaList = cacaRepository.findAll();
        assertThat(cacaList).hasSize(databaseSizeBeforeUpdate);
        Caca testCaca = cacaList.get(cacaList.size() - 1);
        assertThat(testCaca.getLabel()).isEqualTo(UPDATED_LABEL);
    }

    @Test
    @Transactional
    void patchNonExistingCaca() throws Exception {
        int databaseSizeBeforeUpdate = cacaRepository.findAll().size();
        caca.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCacaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, caca.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(caca))
            )
            .andExpect(status().isBadRequest());

        // Validate the Caca in the database
        List<Caca> cacaList = cacaRepository.findAll();
        assertThat(cacaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCaca() throws Exception {
        int databaseSizeBeforeUpdate = cacaRepository.findAll().size();
        caca.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCacaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(caca))
            )
            .andExpect(status().isBadRequest());

        // Validate the Caca in the database
        List<Caca> cacaList = cacaRepository.findAll();
        assertThat(cacaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCaca() throws Exception {
        int databaseSizeBeforeUpdate = cacaRepository.findAll().size();
        caca.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCacaMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(caca))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Caca in the database
        List<Caca> cacaList = cacaRepository.findAll();
        assertThat(cacaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCaca() throws Exception {
        // Initialize the database
        cacaRepository.saveAndFlush(caca);

        int databaseSizeBeforeDelete = cacaRepository.findAll().size();

        // Delete the caca
        restCacaMockMvc
            .perform(delete(ENTITY_API_URL_ID, caca.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Caca> cacaList = cacaRepository.findAll();
        assertThat(cacaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
