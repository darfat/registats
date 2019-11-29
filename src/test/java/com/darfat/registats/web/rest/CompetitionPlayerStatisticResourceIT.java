package com.darfat.registats.web.rest;

import com.darfat.registats.RegistatsApp;
import com.darfat.registats.domain.CompetitionPlayerStatistic;
import com.darfat.registats.repository.CompetitionPlayerStatisticRepository;
import com.darfat.registats.repository.search.CompetitionPlayerStatisticSearchRepository;
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
 * Integration tests for the {@link CompetitionPlayerStatisticResource} REST controller.
 */
@SpringBootTest(classes = RegistatsApp.class)
public class CompetitionPlayerStatisticResourceIT {

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
    private CompetitionPlayerStatisticRepository competitionPlayerStatisticRepository;

    /**
     * This repository is mocked in the com.darfat.registats.repository.search test package.
     *
     * @see com.darfat.registats.repository.search.CompetitionPlayerStatisticSearchRepositoryMockConfiguration
     */
    @Autowired
    private CompetitionPlayerStatisticSearchRepository mockCompetitionPlayerStatisticSearchRepository;

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

    private MockMvc restCompetitionPlayerStatisticMockMvc;

    private CompetitionPlayerStatistic competitionPlayerStatistic;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CompetitionPlayerStatisticResource competitionPlayerStatisticResource = new CompetitionPlayerStatisticResource(competitionPlayerStatisticRepository, mockCompetitionPlayerStatisticSearchRepository);
        this.restCompetitionPlayerStatisticMockMvc = MockMvcBuilders.standaloneSetup(competitionPlayerStatisticResource)
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
    public static CompetitionPlayerStatistic createEntity(EntityManager em) {
        CompetitionPlayerStatistic competitionPlayerStatistic = new CompetitionPlayerStatistic()
            .valueDouble(DEFAULT_VALUE_DOUBLE)
            .valueLong(DEFAULT_VALUE_LONG)
            .valueString(DEFAULT_VALUE_STRING)
            .description(DEFAULT_DESCRIPTION)
            .name(DEFAULT_NAME);
        return competitionPlayerStatistic;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompetitionPlayerStatistic createUpdatedEntity(EntityManager em) {
        CompetitionPlayerStatistic competitionPlayerStatistic = new CompetitionPlayerStatistic()
            .valueDouble(UPDATED_VALUE_DOUBLE)
            .valueLong(UPDATED_VALUE_LONG)
            .valueString(UPDATED_VALUE_STRING)
            .description(UPDATED_DESCRIPTION)
            .name(UPDATED_NAME);
        return competitionPlayerStatistic;
    }

    @BeforeEach
    public void initTest() {
        competitionPlayerStatistic = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompetitionPlayerStatistic() throws Exception {
        int databaseSizeBeforeCreate = competitionPlayerStatisticRepository.findAll().size();

        // Create the CompetitionPlayerStatistic
        restCompetitionPlayerStatisticMockMvc.perform(post("/api/competition-player-statistics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(competitionPlayerStatistic)))
            .andExpect(status().isCreated());

        // Validate the CompetitionPlayerStatistic in the database
        List<CompetitionPlayerStatistic> competitionPlayerStatisticList = competitionPlayerStatisticRepository.findAll();
        assertThat(competitionPlayerStatisticList).hasSize(databaseSizeBeforeCreate + 1);
        CompetitionPlayerStatistic testCompetitionPlayerStatistic = competitionPlayerStatisticList.get(competitionPlayerStatisticList.size() - 1);
        assertThat(testCompetitionPlayerStatistic.getValueDouble()).isEqualTo(DEFAULT_VALUE_DOUBLE);
        assertThat(testCompetitionPlayerStatistic.getValueLong()).isEqualTo(DEFAULT_VALUE_LONG);
        assertThat(testCompetitionPlayerStatistic.getValueString()).isEqualTo(DEFAULT_VALUE_STRING);
        assertThat(testCompetitionPlayerStatistic.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCompetitionPlayerStatistic.getName()).isEqualTo(DEFAULT_NAME);

        // Validate the CompetitionPlayerStatistic in Elasticsearch
        verify(mockCompetitionPlayerStatisticSearchRepository, times(1)).save(testCompetitionPlayerStatistic);
    }

    @Test
    @Transactional
    public void createCompetitionPlayerStatisticWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = competitionPlayerStatisticRepository.findAll().size();

        // Create the CompetitionPlayerStatistic with an existing ID
        competitionPlayerStatistic.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompetitionPlayerStatisticMockMvc.perform(post("/api/competition-player-statistics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(competitionPlayerStatistic)))
            .andExpect(status().isBadRequest());

        // Validate the CompetitionPlayerStatistic in the database
        List<CompetitionPlayerStatistic> competitionPlayerStatisticList = competitionPlayerStatisticRepository.findAll();
        assertThat(competitionPlayerStatisticList).hasSize(databaseSizeBeforeCreate);

        // Validate the CompetitionPlayerStatistic in Elasticsearch
        verify(mockCompetitionPlayerStatisticSearchRepository, times(0)).save(competitionPlayerStatistic);
    }


    @Test
    @Transactional
    public void getAllCompetitionPlayerStatistics() throws Exception {
        // Initialize the database
        competitionPlayerStatisticRepository.saveAndFlush(competitionPlayerStatistic);

        // Get all the competitionPlayerStatisticList
        restCompetitionPlayerStatisticMockMvc.perform(get("/api/competition-player-statistics?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(competitionPlayerStatistic.getId().intValue())))
            .andExpect(jsonPath("$.[*].valueDouble").value(hasItem(DEFAULT_VALUE_DOUBLE.doubleValue())))
            .andExpect(jsonPath("$.[*].valueLong").value(hasItem(DEFAULT_VALUE_LONG.intValue())))
            .andExpect(jsonPath("$.[*].valueString").value(hasItem(DEFAULT_VALUE_STRING)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getCompetitionPlayerStatistic() throws Exception {
        // Initialize the database
        competitionPlayerStatisticRepository.saveAndFlush(competitionPlayerStatistic);

        // Get the competitionPlayerStatistic
        restCompetitionPlayerStatisticMockMvc.perform(get("/api/competition-player-statistics/{id}", competitionPlayerStatistic.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(competitionPlayerStatistic.getId().intValue()))
            .andExpect(jsonPath("$.valueDouble").value(DEFAULT_VALUE_DOUBLE.doubleValue()))
            .andExpect(jsonPath("$.valueLong").value(DEFAULT_VALUE_LONG.intValue()))
            .andExpect(jsonPath("$.valueString").value(DEFAULT_VALUE_STRING))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    public void getNonExistingCompetitionPlayerStatistic() throws Exception {
        // Get the competitionPlayerStatistic
        restCompetitionPlayerStatisticMockMvc.perform(get("/api/competition-player-statistics/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompetitionPlayerStatistic() throws Exception {
        // Initialize the database
        competitionPlayerStatisticRepository.saveAndFlush(competitionPlayerStatistic);

        int databaseSizeBeforeUpdate = competitionPlayerStatisticRepository.findAll().size();

        // Update the competitionPlayerStatistic
        CompetitionPlayerStatistic updatedCompetitionPlayerStatistic = competitionPlayerStatisticRepository.findById(competitionPlayerStatistic.getId()).get();
        // Disconnect from session so that the updates on updatedCompetitionPlayerStatistic are not directly saved in db
        em.detach(updatedCompetitionPlayerStatistic);
        updatedCompetitionPlayerStatistic
            .valueDouble(UPDATED_VALUE_DOUBLE)
            .valueLong(UPDATED_VALUE_LONG)
            .valueString(UPDATED_VALUE_STRING)
            .description(UPDATED_DESCRIPTION)
            .name(UPDATED_NAME);

        restCompetitionPlayerStatisticMockMvc.perform(put("/api/competition-player-statistics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCompetitionPlayerStatistic)))
            .andExpect(status().isOk());

        // Validate the CompetitionPlayerStatistic in the database
        List<CompetitionPlayerStatistic> competitionPlayerStatisticList = competitionPlayerStatisticRepository.findAll();
        assertThat(competitionPlayerStatisticList).hasSize(databaseSizeBeforeUpdate);
        CompetitionPlayerStatistic testCompetitionPlayerStatistic = competitionPlayerStatisticList.get(competitionPlayerStatisticList.size() - 1);
        assertThat(testCompetitionPlayerStatistic.getValueDouble()).isEqualTo(UPDATED_VALUE_DOUBLE);
        assertThat(testCompetitionPlayerStatistic.getValueLong()).isEqualTo(UPDATED_VALUE_LONG);
        assertThat(testCompetitionPlayerStatistic.getValueString()).isEqualTo(UPDATED_VALUE_STRING);
        assertThat(testCompetitionPlayerStatistic.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCompetitionPlayerStatistic.getName()).isEqualTo(UPDATED_NAME);

        // Validate the CompetitionPlayerStatistic in Elasticsearch
        verify(mockCompetitionPlayerStatisticSearchRepository, times(1)).save(testCompetitionPlayerStatistic);
    }

    @Test
    @Transactional
    public void updateNonExistingCompetitionPlayerStatistic() throws Exception {
        int databaseSizeBeforeUpdate = competitionPlayerStatisticRepository.findAll().size();

        // Create the CompetitionPlayerStatistic

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompetitionPlayerStatisticMockMvc.perform(put("/api/competition-player-statistics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(competitionPlayerStatistic)))
            .andExpect(status().isBadRequest());

        // Validate the CompetitionPlayerStatistic in the database
        List<CompetitionPlayerStatistic> competitionPlayerStatisticList = competitionPlayerStatisticRepository.findAll();
        assertThat(competitionPlayerStatisticList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CompetitionPlayerStatistic in Elasticsearch
        verify(mockCompetitionPlayerStatisticSearchRepository, times(0)).save(competitionPlayerStatistic);
    }

    @Test
    @Transactional
    public void deleteCompetitionPlayerStatistic() throws Exception {
        // Initialize the database
        competitionPlayerStatisticRepository.saveAndFlush(competitionPlayerStatistic);

        int databaseSizeBeforeDelete = competitionPlayerStatisticRepository.findAll().size();

        // Delete the competitionPlayerStatistic
        restCompetitionPlayerStatisticMockMvc.perform(delete("/api/competition-player-statistics/{id}", competitionPlayerStatistic.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CompetitionPlayerStatistic> competitionPlayerStatisticList = competitionPlayerStatisticRepository.findAll();
        assertThat(competitionPlayerStatisticList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CompetitionPlayerStatistic in Elasticsearch
        verify(mockCompetitionPlayerStatisticSearchRepository, times(1)).deleteById(competitionPlayerStatistic.getId());
    }

    @Test
    @Transactional
    public void searchCompetitionPlayerStatistic() throws Exception {
        // Initialize the database
        competitionPlayerStatisticRepository.saveAndFlush(competitionPlayerStatistic);
        when(mockCompetitionPlayerStatisticSearchRepository.search(queryStringQuery("id:" + competitionPlayerStatistic.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(competitionPlayerStatistic), PageRequest.of(0, 1), 1));
        // Search the competitionPlayerStatistic
        restCompetitionPlayerStatisticMockMvc.perform(get("/api/_search/competition-player-statistics?query=id:" + competitionPlayerStatistic.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(competitionPlayerStatistic.getId().intValue())))
            .andExpect(jsonPath("$.[*].valueDouble").value(hasItem(DEFAULT_VALUE_DOUBLE.doubleValue())))
            .andExpect(jsonPath("$.[*].valueLong").value(hasItem(DEFAULT_VALUE_LONG.intValue())))
            .andExpect(jsonPath("$.[*].valueString").value(hasItem(DEFAULT_VALUE_STRING)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompetitionPlayerStatistic.class);
        CompetitionPlayerStatistic competitionPlayerStatistic1 = new CompetitionPlayerStatistic();
        competitionPlayerStatistic1.setId(1L);
        CompetitionPlayerStatistic competitionPlayerStatistic2 = new CompetitionPlayerStatistic();
        competitionPlayerStatistic2.setId(competitionPlayerStatistic1.getId());
        assertThat(competitionPlayerStatistic1).isEqualTo(competitionPlayerStatistic2);
        competitionPlayerStatistic2.setId(2L);
        assertThat(competitionPlayerStatistic1).isNotEqualTo(competitionPlayerStatistic2);
        competitionPlayerStatistic1.setId(null);
        assertThat(competitionPlayerStatistic1).isNotEqualTo(competitionPlayerStatistic2);
    }
}
