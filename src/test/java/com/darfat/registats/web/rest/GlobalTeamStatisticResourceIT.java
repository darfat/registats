package com.darfat.registats.web.rest;

import com.darfat.registats.RegistatsApp;
import com.darfat.registats.domain.GlobalTeamStatistic;
import com.darfat.registats.repository.GlobalTeamStatisticRepository;
import com.darfat.registats.repository.search.GlobalTeamStatisticSearchRepository;
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
 * Integration tests for the {@link GlobalTeamStatisticResource} REST controller.
 */
@SpringBootTest(classes = RegistatsApp.class)
public class GlobalTeamStatisticResourceIT {

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
    private GlobalTeamStatisticRepository globalTeamStatisticRepository;

    /**
     * This repository is mocked in the com.darfat.registats.repository.search test package.
     *
     * @see com.darfat.registats.repository.search.GlobalTeamStatisticSearchRepositoryMockConfiguration
     */
    @Autowired
    private GlobalTeamStatisticSearchRepository mockGlobalTeamStatisticSearchRepository;

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

    private MockMvc restGlobalTeamStatisticMockMvc;

    private GlobalTeamStatistic globalTeamStatistic;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GlobalTeamStatisticResource globalTeamStatisticResource = new GlobalTeamStatisticResource(globalTeamStatisticRepository, mockGlobalTeamStatisticSearchRepository);
        this.restGlobalTeamStatisticMockMvc = MockMvcBuilders.standaloneSetup(globalTeamStatisticResource)
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
    public static GlobalTeamStatistic createEntity(EntityManager em) {
        GlobalTeamStatistic globalTeamStatistic = new GlobalTeamStatistic()
            .valueDouble(DEFAULT_VALUE_DOUBLE)
            .valueLong(DEFAULT_VALUE_LONG)
            .valueString(DEFAULT_VALUE_STRING)
            .description(DEFAULT_DESCRIPTION)
            .name(DEFAULT_NAME);
        return globalTeamStatistic;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GlobalTeamStatistic createUpdatedEntity(EntityManager em) {
        GlobalTeamStatistic globalTeamStatistic = new GlobalTeamStatistic()
            .valueDouble(UPDATED_VALUE_DOUBLE)
            .valueLong(UPDATED_VALUE_LONG)
            .valueString(UPDATED_VALUE_STRING)
            .description(UPDATED_DESCRIPTION)
            .name(UPDATED_NAME);
        return globalTeamStatistic;
    }

    @BeforeEach
    public void initTest() {
        globalTeamStatistic = createEntity(em);
    }

    @Test
    @Transactional
    public void createGlobalTeamStatistic() throws Exception {
        int databaseSizeBeforeCreate = globalTeamStatisticRepository.findAll().size();

        // Create the GlobalTeamStatistic
        restGlobalTeamStatisticMockMvc.perform(post("/api/global-team-statistics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(globalTeamStatistic)))
            .andExpect(status().isCreated());

        // Validate the GlobalTeamStatistic in the database
        List<GlobalTeamStatistic> globalTeamStatisticList = globalTeamStatisticRepository.findAll();
        assertThat(globalTeamStatisticList).hasSize(databaseSizeBeforeCreate + 1);
        GlobalTeamStatistic testGlobalTeamStatistic = globalTeamStatisticList.get(globalTeamStatisticList.size() - 1);
        assertThat(testGlobalTeamStatistic.getValueDouble()).isEqualTo(DEFAULT_VALUE_DOUBLE);
        assertThat(testGlobalTeamStatistic.getValueLong()).isEqualTo(DEFAULT_VALUE_LONG);
        assertThat(testGlobalTeamStatistic.getValueString()).isEqualTo(DEFAULT_VALUE_STRING);
        assertThat(testGlobalTeamStatistic.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testGlobalTeamStatistic.getName()).isEqualTo(DEFAULT_NAME);

        // Validate the GlobalTeamStatistic in Elasticsearch
        verify(mockGlobalTeamStatisticSearchRepository, times(1)).save(testGlobalTeamStatistic);
    }

    @Test
    @Transactional
    public void createGlobalTeamStatisticWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = globalTeamStatisticRepository.findAll().size();

        // Create the GlobalTeamStatistic with an existing ID
        globalTeamStatistic.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGlobalTeamStatisticMockMvc.perform(post("/api/global-team-statistics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(globalTeamStatistic)))
            .andExpect(status().isBadRequest());

        // Validate the GlobalTeamStatistic in the database
        List<GlobalTeamStatistic> globalTeamStatisticList = globalTeamStatisticRepository.findAll();
        assertThat(globalTeamStatisticList).hasSize(databaseSizeBeforeCreate);

        // Validate the GlobalTeamStatistic in Elasticsearch
        verify(mockGlobalTeamStatisticSearchRepository, times(0)).save(globalTeamStatistic);
    }


    @Test
    @Transactional
    public void getAllGlobalTeamStatistics() throws Exception {
        // Initialize the database
        globalTeamStatisticRepository.saveAndFlush(globalTeamStatistic);

        // Get all the globalTeamStatisticList
        restGlobalTeamStatisticMockMvc.perform(get("/api/global-team-statistics?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(globalTeamStatistic.getId().intValue())))
            .andExpect(jsonPath("$.[*].valueDouble").value(hasItem(DEFAULT_VALUE_DOUBLE.doubleValue())))
            .andExpect(jsonPath("$.[*].valueLong").value(hasItem(DEFAULT_VALUE_LONG.intValue())))
            .andExpect(jsonPath("$.[*].valueString").value(hasItem(DEFAULT_VALUE_STRING)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getGlobalTeamStatistic() throws Exception {
        // Initialize the database
        globalTeamStatisticRepository.saveAndFlush(globalTeamStatistic);

        // Get the globalTeamStatistic
        restGlobalTeamStatisticMockMvc.perform(get("/api/global-team-statistics/{id}", globalTeamStatistic.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(globalTeamStatistic.getId().intValue()))
            .andExpect(jsonPath("$.valueDouble").value(DEFAULT_VALUE_DOUBLE.doubleValue()))
            .andExpect(jsonPath("$.valueLong").value(DEFAULT_VALUE_LONG.intValue()))
            .andExpect(jsonPath("$.valueString").value(DEFAULT_VALUE_STRING))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    public void getNonExistingGlobalTeamStatistic() throws Exception {
        // Get the globalTeamStatistic
        restGlobalTeamStatisticMockMvc.perform(get("/api/global-team-statistics/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGlobalTeamStatistic() throws Exception {
        // Initialize the database
        globalTeamStatisticRepository.saveAndFlush(globalTeamStatistic);

        int databaseSizeBeforeUpdate = globalTeamStatisticRepository.findAll().size();

        // Update the globalTeamStatistic
        GlobalTeamStatistic updatedGlobalTeamStatistic = globalTeamStatisticRepository.findById(globalTeamStatistic.getId()).get();
        // Disconnect from session so that the updates on updatedGlobalTeamStatistic are not directly saved in db
        em.detach(updatedGlobalTeamStatistic);
        updatedGlobalTeamStatistic
            .valueDouble(UPDATED_VALUE_DOUBLE)
            .valueLong(UPDATED_VALUE_LONG)
            .valueString(UPDATED_VALUE_STRING)
            .description(UPDATED_DESCRIPTION)
            .name(UPDATED_NAME);

        restGlobalTeamStatisticMockMvc.perform(put("/api/global-team-statistics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedGlobalTeamStatistic)))
            .andExpect(status().isOk());

        // Validate the GlobalTeamStatistic in the database
        List<GlobalTeamStatistic> globalTeamStatisticList = globalTeamStatisticRepository.findAll();
        assertThat(globalTeamStatisticList).hasSize(databaseSizeBeforeUpdate);
        GlobalTeamStatistic testGlobalTeamStatistic = globalTeamStatisticList.get(globalTeamStatisticList.size() - 1);
        assertThat(testGlobalTeamStatistic.getValueDouble()).isEqualTo(UPDATED_VALUE_DOUBLE);
        assertThat(testGlobalTeamStatistic.getValueLong()).isEqualTo(UPDATED_VALUE_LONG);
        assertThat(testGlobalTeamStatistic.getValueString()).isEqualTo(UPDATED_VALUE_STRING);
        assertThat(testGlobalTeamStatistic.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testGlobalTeamStatistic.getName()).isEqualTo(UPDATED_NAME);

        // Validate the GlobalTeamStatistic in Elasticsearch
        verify(mockGlobalTeamStatisticSearchRepository, times(1)).save(testGlobalTeamStatistic);
    }

    @Test
    @Transactional
    public void updateNonExistingGlobalTeamStatistic() throws Exception {
        int databaseSizeBeforeUpdate = globalTeamStatisticRepository.findAll().size();

        // Create the GlobalTeamStatistic

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGlobalTeamStatisticMockMvc.perform(put("/api/global-team-statistics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(globalTeamStatistic)))
            .andExpect(status().isBadRequest());

        // Validate the GlobalTeamStatistic in the database
        List<GlobalTeamStatistic> globalTeamStatisticList = globalTeamStatisticRepository.findAll();
        assertThat(globalTeamStatisticList).hasSize(databaseSizeBeforeUpdate);

        // Validate the GlobalTeamStatistic in Elasticsearch
        verify(mockGlobalTeamStatisticSearchRepository, times(0)).save(globalTeamStatistic);
    }

    @Test
    @Transactional
    public void deleteGlobalTeamStatistic() throws Exception {
        // Initialize the database
        globalTeamStatisticRepository.saveAndFlush(globalTeamStatistic);

        int databaseSizeBeforeDelete = globalTeamStatisticRepository.findAll().size();

        // Delete the globalTeamStatistic
        restGlobalTeamStatisticMockMvc.perform(delete("/api/global-team-statistics/{id}", globalTeamStatistic.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GlobalTeamStatistic> globalTeamStatisticList = globalTeamStatisticRepository.findAll();
        assertThat(globalTeamStatisticList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the GlobalTeamStatistic in Elasticsearch
        verify(mockGlobalTeamStatisticSearchRepository, times(1)).deleteById(globalTeamStatistic.getId());
    }

    @Test
    @Transactional
    public void searchGlobalTeamStatistic() throws Exception {
        // Initialize the database
        globalTeamStatisticRepository.saveAndFlush(globalTeamStatistic);
        when(mockGlobalTeamStatisticSearchRepository.search(queryStringQuery("id:" + globalTeamStatistic.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(globalTeamStatistic), PageRequest.of(0, 1), 1));
        // Search the globalTeamStatistic
        restGlobalTeamStatisticMockMvc.perform(get("/api/_search/global-team-statistics?query=id:" + globalTeamStatistic.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(globalTeamStatistic.getId().intValue())))
            .andExpect(jsonPath("$.[*].valueDouble").value(hasItem(DEFAULT_VALUE_DOUBLE.doubleValue())))
            .andExpect(jsonPath("$.[*].valueLong").value(hasItem(DEFAULT_VALUE_LONG.intValue())))
            .andExpect(jsonPath("$.[*].valueString").value(hasItem(DEFAULT_VALUE_STRING)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GlobalTeamStatistic.class);
        GlobalTeamStatistic globalTeamStatistic1 = new GlobalTeamStatistic();
        globalTeamStatistic1.setId(1L);
        GlobalTeamStatistic globalTeamStatistic2 = new GlobalTeamStatistic();
        globalTeamStatistic2.setId(globalTeamStatistic1.getId());
        assertThat(globalTeamStatistic1).isEqualTo(globalTeamStatistic2);
        globalTeamStatistic2.setId(2L);
        assertThat(globalTeamStatistic1).isNotEqualTo(globalTeamStatistic2);
        globalTeamStatistic1.setId(null);
        assertThat(globalTeamStatistic1).isNotEqualTo(globalTeamStatistic2);
    }
}
