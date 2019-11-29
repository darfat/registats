package com.darfat.registats.web.rest;

import com.darfat.registats.RegistatsApp;
import com.darfat.registats.domain.CompetitionTeamStatistic;
import com.darfat.registats.repository.CompetitionTeamStatisticRepository;
import com.darfat.registats.repository.search.CompetitionTeamStatisticSearchRepository;
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
 * Integration tests for the {@link CompetitionTeamStatisticResource} REST controller.
 */
@SpringBootTest(classes = RegistatsApp.class)
public class CompetitionTeamStatisticResourceIT {

    private static final Double DEFAULT_VALUE_DOUBLE = 1D;
    private static final Double UPDATED_VALUE_DOUBLE = 2D;

    private static final Long DEFAULT_VALUE_LONG = 1L;
    private static final Long UPDATED_VALUE_LONG = 2L;

    private static final String DEFAULT_VALUE_STRING = "AAAAAAAAAA";
    private static final String UPDATED_VALUE_STRING = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private CompetitionTeamStatisticRepository competitionTeamStatisticRepository;

    /**
     * This repository is mocked in the com.darfat.registats.repository.search test package.
     *
     * @see com.darfat.registats.repository.search.CompetitionTeamStatisticSearchRepositoryMockConfiguration
     */
    @Autowired
    private CompetitionTeamStatisticSearchRepository mockCompetitionTeamStatisticSearchRepository;

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

    private MockMvc restCompetitionTeamStatisticMockMvc;

    private CompetitionTeamStatistic competitionTeamStatistic;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CompetitionTeamStatisticResource competitionTeamStatisticResource = new CompetitionTeamStatisticResource(competitionTeamStatisticRepository, mockCompetitionTeamStatisticSearchRepository);
        this.restCompetitionTeamStatisticMockMvc = MockMvcBuilders.standaloneSetup(competitionTeamStatisticResource)
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
    public static CompetitionTeamStatistic createEntity(EntityManager em) {
        CompetitionTeamStatistic competitionTeamStatistic = new CompetitionTeamStatistic()
            .valueDouble(DEFAULT_VALUE_DOUBLE)
            .valueLong(DEFAULT_VALUE_LONG)
            .valueString(DEFAULT_VALUE_STRING)
            .description(DEFAULT_DESCRIPTION)
            .name(DEFAULT_NAME);
        return competitionTeamStatistic;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompetitionTeamStatistic createUpdatedEntity(EntityManager em) {
        CompetitionTeamStatistic competitionTeamStatistic = new CompetitionTeamStatistic()
            .valueDouble(UPDATED_VALUE_DOUBLE)
            .valueLong(UPDATED_VALUE_LONG)
            .valueString(UPDATED_VALUE_STRING)
            .description(UPDATED_DESCRIPTION)
            .name(UPDATED_NAME);
        return competitionTeamStatistic;
    }

    @BeforeEach
    public void initTest() {
        competitionTeamStatistic = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompetitionTeamStatistic() throws Exception {
        int databaseSizeBeforeCreate = competitionTeamStatisticRepository.findAll().size();

        // Create the CompetitionTeamStatistic
        restCompetitionTeamStatisticMockMvc.perform(post("/api/competition-team-statistics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(competitionTeamStatistic)))
            .andExpect(status().isCreated());

        // Validate the CompetitionTeamStatistic in the database
        List<CompetitionTeamStatistic> competitionTeamStatisticList = competitionTeamStatisticRepository.findAll();
        assertThat(competitionTeamStatisticList).hasSize(databaseSizeBeforeCreate + 1);
        CompetitionTeamStatistic testCompetitionTeamStatistic = competitionTeamStatisticList.get(competitionTeamStatisticList.size() - 1);
        assertThat(testCompetitionTeamStatistic.getValueDouble()).isEqualTo(DEFAULT_VALUE_DOUBLE);
        assertThat(testCompetitionTeamStatistic.getValueLong()).isEqualTo(DEFAULT_VALUE_LONG);
        assertThat(testCompetitionTeamStatistic.getValueString()).isEqualTo(DEFAULT_VALUE_STRING);
        assertThat(testCompetitionTeamStatistic.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCompetitionTeamStatistic.getName()).isEqualTo(DEFAULT_NAME);

        // Validate the CompetitionTeamStatistic in Elasticsearch
        verify(mockCompetitionTeamStatisticSearchRepository, times(1)).save(testCompetitionTeamStatistic);
    }

    @Test
    @Transactional
    public void createCompetitionTeamStatisticWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = competitionTeamStatisticRepository.findAll().size();

        // Create the CompetitionTeamStatistic with an existing ID
        competitionTeamStatistic.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompetitionTeamStatisticMockMvc.perform(post("/api/competition-team-statistics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(competitionTeamStatistic)))
            .andExpect(status().isBadRequest());

        // Validate the CompetitionTeamStatistic in the database
        List<CompetitionTeamStatistic> competitionTeamStatisticList = competitionTeamStatisticRepository.findAll();
        assertThat(competitionTeamStatisticList).hasSize(databaseSizeBeforeCreate);

        // Validate the CompetitionTeamStatistic in Elasticsearch
        verify(mockCompetitionTeamStatisticSearchRepository, times(0)).save(competitionTeamStatistic);
    }


    @Test
    @Transactional
    public void getAllCompetitionTeamStatistics() throws Exception {
        // Initialize the database
        competitionTeamStatisticRepository.saveAndFlush(competitionTeamStatistic);

        // Get all the competitionTeamStatisticList
        restCompetitionTeamStatisticMockMvc.perform(get("/api/competition-team-statistics?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(competitionTeamStatistic.getId().intValue())))
            .andExpect(jsonPath("$.[*].valueDouble").value(hasItem(DEFAULT_VALUE_DOUBLE.doubleValue())))
            .andExpect(jsonPath("$.[*].valueLong").value(hasItem(DEFAULT_VALUE_LONG.intValue())))
            .andExpect(jsonPath("$.[*].valueString").value(hasItem(DEFAULT_VALUE_STRING)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getCompetitionTeamStatistic() throws Exception {
        // Initialize the database
        competitionTeamStatisticRepository.saveAndFlush(competitionTeamStatistic);

        // Get the competitionTeamStatistic
        restCompetitionTeamStatisticMockMvc.perform(get("/api/competition-team-statistics/{id}", competitionTeamStatistic.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(competitionTeamStatistic.getId().intValue()))
            .andExpect(jsonPath("$.valueDouble").value(DEFAULT_VALUE_DOUBLE.doubleValue()))
            .andExpect(jsonPath("$.valueLong").value(DEFAULT_VALUE_LONG.intValue()))
            .andExpect(jsonPath("$.valueString").value(DEFAULT_VALUE_STRING))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    public void getNonExistingCompetitionTeamStatistic() throws Exception {
        // Get the competitionTeamStatistic
        restCompetitionTeamStatisticMockMvc.perform(get("/api/competition-team-statistics/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompetitionTeamStatistic() throws Exception {
        // Initialize the database
        competitionTeamStatisticRepository.saveAndFlush(competitionTeamStatistic);

        int databaseSizeBeforeUpdate = competitionTeamStatisticRepository.findAll().size();

        // Update the competitionTeamStatistic
        CompetitionTeamStatistic updatedCompetitionTeamStatistic = competitionTeamStatisticRepository.findById(competitionTeamStatistic.getId()).get();
        // Disconnect from session so that the updates on updatedCompetitionTeamStatistic are not directly saved in db
        em.detach(updatedCompetitionTeamStatistic);
        updatedCompetitionTeamStatistic
            .valueDouble(UPDATED_VALUE_DOUBLE)
            .valueLong(UPDATED_VALUE_LONG)
            .valueString(UPDATED_VALUE_STRING)
            .description(UPDATED_DESCRIPTION)
            .name(UPDATED_NAME);

        restCompetitionTeamStatisticMockMvc.perform(put("/api/competition-team-statistics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCompetitionTeamStatistic)))
            .andExpect(status().isOk());

        // Validate the CompetitionTeamStatistic in the database
        List<CompetitionTeamStatistic> competitionTeamStatisticList = competitionTeamStatisticRepository.findAll();
        assertThat(competitionTeamStatisticList).hasSize(databaseSizeBeforeUpdate);
        CompetitionTeamStatistic testCompetitionTeamStatistic = competitionTeamStatisticList.get(competitionTeamStatisticList.size() - 1);
        assertThat(testCompetitionTeamStatistic.getValueDouble()).isEqualTo(UPDATED_VALUE_DOUBLE);
        assertThat(testCompetitionTeamStatistic.getValueLong()).isEqualTo(UPDATED_VALUE_LONG);
        assertThat(testCompetitionTeamStatistic.getValueString()).isEqualTo(UPDATED_VALUE_STRING);
        assertThat(testCompetitionTeamStatistic.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCompetitionTeamStatistic.getName()).isEqualTo(UPDATED_NAME);

        // Validate the CompetitionTeamStatistic in Elasticsearch
        verify(mockCompetitionTeamStatisticSearchRepository, times(1)).save(testCompetitionTeamStatistic);
    }

    @Test
    @Transactional
    public void updateNonExistingCompetitionTeamStatistic() throws Exception {
        int databaseSizeBeforeUpdate = competitionTeamStatisticRepository.findAll().size();

        // Create the CompetitionTeamStatistic

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompetitionTeamStatisticMockMvc.perform(put("/api/competition-team-statistics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(competitionTeamStatistic)))
            .andExpect(status().isBadRequest());

        // Validate the CompetitionTeamStatistic in the database
        List<CompetitionTeamStatistic> competitionTeamStatisticList = competitionTeamStatisticRepository.findAll();
        assertThat(competitionTeamStatisticList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CompetitionTeamStatistic in Elasticsearch
        verify(mockCompetitionTeamStatisticSearchRepository, times(0)).save(competitionTeamStatistic);
    }

    @Test
    @Transactional
    public void deleteCompetitionTeamStatistic() throws Exception {
        // Initialize the database
        competitionTeamStatisticRepository.saveAndFlush(competitionTeamStatistic);

        int databaseSizeBeforeDelete = competitionTeamStatisticRepository.findAll().size();

        // Delete the competitionTeamStatistic
        restCompetitionTeamStatisticMockMvc.perform(delete("/api/competition-team-statistics/{id}", competitionTeamStatistic.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CompetitionTeamStatistic> competitionTeamStatisticList = competitionTeamStatisticRepository.findAll();
        assertThat(competitionTeamStatisticList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CompetitionTeamStatistic in Elasticsearch
        verify(mockCompetitionTeamStatisticSearchRepository, times(1)).deleteById(competitionTeamStatistic.getId());
    }

    @Test
    @Transactional
    public void searchCompetitionTeamStatistic() throws Exception {
        // Initialize the database
        competitionTeamStatisticRepository.saveAndFlush(competitionTeamStatistic);
        when(mockCompetitionTeamStatisticSearchRepository.search(queryStringQuery("id:" + competitionTeamStatistic.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(competitionTeamStatistic), PageRequest.of(0, 1), 1));
        // Search the competitionTeamStatistic
        restCompetitionTeamStatisticMockMvc.perform(get("/api/_search/competition-team-statistics?query=id:" + competitionTeamStatistic.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(competitionTeamStatistic.getId().intValue())))
            .andExpect(jsonPath("$.[*].valueDouble").value(hasItem(DEFAULT_VALUE_DOUBLE.doubleValue())))
            .andExpect(jsonPath("$.[*].valueLong").value(hasItem(DEFAULT_VALUE_LONG.intValue())))
            .andExpect(jsonPath("$.[*].valueString").value(hasItem(DEFAULT_VALUE_STRING)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompetitionTeamStatistic.class);
        CompetitionTeamStatistic competitionTeamStatistic1 = new CompetitionTeamStatistic();
        competitionTeamStatistic1.setId(1L);
        CompetitionTeamStatistic competitionTeamStatistic2 = new CompetitionTeamStatistic();
        competitionTeamStatistic2.setId(competitionTeamStatistic1.getId());
        assertThat(competitionTeamStatistic1).isEqualTo(competitionTeamStatistic2);
        competitionTeamStatistic2.setId(2L);
        assertThat(competitionTeamStatistic1).isNotEqualTo(competitionTeamStatistic2);
        competitionTeamStatistic1.setId(null);
        assertThat(competitionTeamStatistic1).isNotEqualTo(competitionTeamStatistic2);
    }
}
