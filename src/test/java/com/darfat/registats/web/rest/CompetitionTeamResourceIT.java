package com.darfat.registats.web.rest;

import com.darfat.registats.RegistatsApp;
import com.darfat.registats.domain.CompetitionTeam;
import com.darfat.registats.repository.CompetitionTeamRepository;
import com.darfat.registats.repository.search.CompetitionTeamSearchRepository;
import com.darfat.registats.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

import static com.darfat.registats.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CompetitionTeamResource} REST controller.
 */
@SpringBootTest(classes = RegistatsApp.class)
public class CompetitionTeamResourceIT {

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private CompetitionTeamRepository competitionTeamRepository;

    /**
     * This repository is mocked in the com.darfat.registats.repository.search test package.
     *
     * @see com.darfat.registats.repository.search.CompetitionTeamSearchRepositoryMockConfiguration
     */
    @Autowired
    private CompetitionTeamSearchRepository mockCompetitionTeamSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restCompetitionTeamMockMvc;

    private CompetitionTeam competitionTeam;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CompetitionTeamResource competitionTeamResource = new CompetitionTeamResource(competitionTeamRepository, mockCompetitionTeamSearchRepository);
        this.restCompetitionTeamMockMvc = MockMvcBuilders.standaloneSetup(competitionTeamResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompetitionTeam createEntity(EntityManager em) {
        CompetitionTeam competitionTeam = new CompetitionTeam()
            .status(DEFAULT_STATUS)
            .description(DEFAULT_DESCRIPTION)
            .active(DEFAULT_ACTIVE);
        return competitionTeam;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompetitionTeam createUpdatedEntity(EntityManager em) {
        CompetitionTeam competitionTeam = new CompetitionTeam()
            .status(UPDATED_STATUS)
            .description(UPDATED_DESCRIPTION)
            .active(UPDATED_ACTIVE);
        return competitionTeam;
    }

    @BeforeEach
    public void initTest() {
        competitionTeam = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompetitionTeam() throws Exception {
        int databaseSizeBeforeCreate = competitionTeamRepository.findAll().size();

        // Create the CompetitionTeam
        restCompetitionTeamMockMvc.perform(post("/api/competition-teams")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(competitionTeam)))
            .andExpect(status().isCreated());

        // Validate the CompetitionTeam in the database
        List<CompetitionTeam> competitionTeamList = competitionTeamRepository.findAll();
        assertThat(competitionTeamList).hasSize(databaseSizeBeforeCreate + 1);
        CompetitionTeam testCompetitionTeam = competitionTeamList.get(competitionTeamList.size() - 1);
        assertThat(testCompetitionTeam.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testCompetitionTeam.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCompetitionTeam.isActive()).isEqualTo(DEFAULT_ACTIVE);

        // Validate the CompetitionTeam in Elasticsearch
        verify(mockCompetitionTeamSearchRepository, times(1)).save(testCompetitionTeam);
    }

    @Test
    @Transactional
    public void createCompetitionTeamWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = competitionTeamRepository.findAll().size();

        // Create the CompetitionTeam with an existing ID
        competitionTeam.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompetitionTeamMockMvc.perform(post("/api/competition-teams")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(competitionTeam)))
            .andExpect(status().isBadRequest());

        // Validate the CompetitionTeam in the database
        List<CompetitionTeam> competitionTeamList = competitionTeamRepository.findAll();
        assertThat(competitionTeamList).hasSize(databaseSizeBeforeCreate);

        // Validate the CompetitionTeam in Elasticsearch
        verify(mockCompetitionTeamSearchRepository, times(0)).save(competitionTeam);
    }


    @Test
    @Transactional
    public void getAllCompetitionTeams() throws Exception {
        // Initialize the database
        competitionTeamRepository.saveAndFlush(competitionTeam);

        // Get all the competitionTeamList
        restCompetitionTeamMockMvc.perform(get("/api/competition-teams?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(competitionTeam.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCompetitionTeam() throws Exception {
        // Initialize the database
        competitionTeamRepository.saveAndFlush(competitionTeam);

        // Get the competitionTeam
        restCompetitionTeamMockMvc.perform(get("/api/competition-teams/{id}", competitionTeam.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(competitionTeam.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCompetitionTeam() throws Exception {
        // Get the competitionTeam
        restCompetitionTeamMockMvc.perform(get("/api/competition-teams/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompetitionTeam() throws Exception {
        // Initialize the database
        competitionTeamRepository.saveAndFlush(competitionTeam);

        int databaseSizeBeforeUpdate = competitionTeamRepository.findAll().size();

        // Update the competitionTeam
        CompetitionTeam updatedCompetitionTeam = competitionTeamRepository.findById(competitionTeam.getId()).get();
        // Disconnect from session so that the updates on updatedCompetitionTeam are not directly saved in db
        em.detach(updatedCompetitionTeam);
        updatedCompetitionTeam
            .status(UPDATED_STATUS)
            .description(UPDATED_DESCRIPTION)
            .active(UPDATED_ACTIVE);

        restCompetitionTeamMockMvc.perform(put("/api/competition-teams")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCompetitionTeam)))
            .andExpect(status().isOk());

        // Validate the CompetitionTeam in the database
        List<CompetitionTeam> competitionTeamList = competitionTeamRepository.findAll();
        assertThat(competitionTeamList).hasSize(databaseSizeBeforeUpdate);
        CompetitionTeam testCompetitionTeam = competitionTeamList.get(competitionTeamList.size() - 1);
        assertThat(testCompetitionTeam.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testCompetitionTeam.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCompetitionTeam.isActive()).isEqualTo(UPDATED_ACTIVE);

        // Validate the CompetitionTeam in Elasticsearch
        verify(mockCompetitionTeamSearchRepository, times(1)).save(testCompetitionTeam);
    }

    @Test
    @Transactional
    public void updateNonExistingCompetitionTeam() throws Exception {
        int databaseSizeBeforeUpdate = competitionTeamRepository.findAll().size();

        // Create the CompetitionTeam

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompetitionTeamMockMvc.perform(put("/api/competition-teams")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(competitionTeam)))
            .andExpect(status().isBadRequest());

        // Validate the CompetitionTeam in the database
        List<CompetitionTeam> competitionTeamList = competitionTeamRepository.findAll();
        assertThat(competitionTeamList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CompetitionTeam in Elasticsearch
        verify(mockCompetitionTeamSearchRepository, times(0)).save(competitionTeam);
    }

    @Test
    @Transactional
    public void deleteCompetitionTeam() throws Exception {
        // Initialize the database
        competitionTeamRepository.saveAndFlush(competitionTeam);

        int databaseSizeBeforeDelete = competitionTeamRepository.findAll().size();

        // Delete the competitionTeam
        restCompetitionTeamMockMvc.perform(delete("/api/competition-teams/{id}", competitionTeam.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CompetitionTeam> competitionTeamList = competitionTeamRepository.findAll();
        assertThat(competitionTeamList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CompetitionTeam in Elasticsearch
        verify(mockCompetitionTeamSearchRepository, times(1)).deleteById(competitionTeam.getId());
    }

    @Test
    @Transactional
    public void searchCompetitionTeam() throws Exception {
        // Initialize the database
        competitionTeamRepository.saveAndFlush(competitionTeam);
        when(mockCompetitionTeamSearchRepository.search(queryStringQuery("id:" + competitionTeam.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(competitionTeam), PageRequest.of(0, 1), 1));
        // Search the competitionTeam
        restCompetitionTeamMockMvc.perform(get("/api/_search/competition-teams?query=id:" + competitionTeam.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(competitionTeam.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompetitionTeam.class);
        CompetitionTeam competitionTeam1 = new CompetitionTeam();
        competitionTeam1.setId(1L);
        CompetitionTeam competitionTeam2 = new CompetitionTeam();
        competitionTeam2.setId(competitionTeam1.getId());
        assertThat(competitionTeam1).isEqualTo(competitionTeam2);
        competitionTeam2.setId(2L);
        assertThat(competitionTeam1).isNotEqualTo(competitionTeam2);
        competitionTeam1.setId(null);
        assertThat(competitionTeam1).isNotEqualTo(competitionTeam2);
    }
}
