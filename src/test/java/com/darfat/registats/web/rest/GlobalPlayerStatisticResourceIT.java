package com.darfat.registats.web.rest;

import com.darfat.registats.RegistatsApp;
import com.darfat.registats.domain.GlobalPlayerStatistic;
import com.darfat.registats.repository.GlobalPlayerStatisticRepository;
import com.darfat.registats.repository.search.GlobalPlayerStatisticSearchRepository;
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
 * Integration tests for the {@link GlobalPlayerStatisticResource} REST controller.
 */
@SpringBootTest(classes = RegistatsApp.class)
public class GlobalPlayerStatisticResourceIT {

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
    private GlobalPlayerStatisticRepository globalPlayerStatisticRepository;

    /**
     * This repository is mocked in the com.darfat.registats.repository.search test package.
     *
     * @see com.darfat.registats.repository.search.GlobalPlayerStatisticSearchRepositoryMockConfiguration
     */
    @Autowired
    private GlobalPlayerStatisticSearchRepository mockGlobalPlayerStatisticSearchRepository;

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

    private MockMvc restGlobalPlayerStatisticMockMvc;

    private GlobalPlayerStatistic globalPlayerStatistic;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GlobalPlayerStatisticResource globalPlayerStatisticResource = new GlobalPlayerStatisticResource(globalPlayerStatisticRepository, mockGlobalPlayerStatisticSearchRepository);
        this.restGlobalPlayerStatisticMockMvc = MockMvcBuilders.standaloneSetup(globalPlayerStatisticResource)
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
    public static GlobalPlayerStatistic createEntity(EntityManager em) {
        GlobalPlayerStatistic globalPlayerStatistic = new GlobalPlayerStatistic()
            .valueDouble(DEFAULT_VALUE_DOUBLE)
            .valueLong(DEFAULT_VALUE_LONG)
            .valueString(DEFAULT_VALUE_STRING)
            .description(DEFAULT_DESCRIPTION)
            .name(DEFAULT_NAME);
        return globalPlayerStatistic;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GlobalPlayerStatistic createUpdatedEntity(EntityManager em) {
        GlobalPlayerStatistic globalPlayerStatistic = new GlobalPlayerStatistic()
            .valueDouble(UPDATED_VALUE_DOUBLE)
            .valueLong(UPDATED_VALUE_LONG)
            .valueString(UPDATED_VALUE_STRING)
            .description(UPDATED_DESCRIPTION)
            .name(UPDATED_NAME);
        return globalPlayerStatistic;
    }

    @BeforeEach
    public void initTest() {
        globalPlayerStatistic = createEntity(em);
    }

    @Test
    @Transactional
    public void createGlobalPlayerStatistic() throws Exception {
        int databaseSizeBeforeCreate = globalPlayerStatisticRepository.findAll().size();

        // Create the GlobalPlayerStatistic
        restGlobalPlayerStatisticMockMvc.perform(post("/api/global-player-statistics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(globalPlayerStatistic)))
            .andExpect(status().isCreated());

        // Validate the GlobalPlayerStatistic in the database
        List<GlobalPlayerStatistic> globalPlayerStatisticList = globalPlayerStatisticRepository.findAll();
        assertThat(globalPlayerStatisticList).hasSize(databaseSizeBeforeCreate + 1);
        GlobalPlayerStatistic testGlobalPlayerStatistic = globalPlayerStatisticList.get(globalPlayerStatisticList.size() - 1);
        assertThat(testGlobalPlayerStatistic.getValueDouble()).isEqualTo(DEFAULT_VALUE_DOUBLE);
        assertThat(testGlobalPlayerStatistic.getValueLong()).isEqualTo(DEFAULT_VALUE_LONG);
        assertThat(testGlobalPlayerStatistic.getValueString()).isEqualTo(DEFAULT_VALUE_STRING);
        assertThat(testGlobalPlayerStatistic.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testGlobalPlayerStatistic.getName()).isEqualTo(DEFAULT_NAME);

        // Validate the GlobalPlayerStatistic in Elasticsearch
        verify(mockGlobalPlayerStatisticSearchRepository, times(1)).save(testGlobalPlayerStatistic);
    }

    @Test
    @Transactional
    public void createGlobalPlayerStatisticWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = globalPlayerStatisticRepository.findAll().size();

        // Create the GlobalPlayerStatistic with an existing ID
        globalPlayerStatistic.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGlobalPlayerStatisticMockMvc.perform(post("/api/global-player-statistics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(globalPlayerStatistic)))
            .andExpect(status().isBadRequest());

        // Validate the GlobalPlayerStatistic in the database
        List<GlobalPlayerStatistic> globalPlayerStatisticList = globalPlayerStatisticRepository.findAll();
        assertThat(globalPlayerStatisticList).hasSize(databaseSizeBeforeCreate);

        // Validate the GlobalPlayerStatistic in Elasticsearch
        verify(mockGlobalPlayerStatisticSearchRepository, times(0)).save(globalPlayerStatistic);
    }


    @Test
    @Transactional
    public void getAllGlobalPlayerStatistics() throws Exception {
        // Initialize the database
        globalPlayerStatisticRepository.saveAndFlush(globalPlayerStatistic);

        // Get all the globalPlayerStatisticList
        restGlobalPlayerStatisticMockMvc.perform(get("/api/global-player-statistics?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(globalPlayerStatistic.getId().intValue())))
            .andExpect(jsonPath("$.[*].valueDouble").value(hasItem(DEFAULT_VALUE_DOUBLE.doubleValue())))
            .andExpect(jsonPath("$.[*].valueLong").value(hasItem(DEFAULT_VALUE_LONG.intValue())))
            .andExpect(jsonPath("$.[*].valueString").value(hasItem(DEFAULT_VALUE_STRING)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getGlobalPlayerStatistic() throws Exception {
        // Initialize the database
        globalPlayerStatisticRepository.saveAndFlush(globalPlayerStatistic);

        // Get the globalPlayerStatistic
        restGlobalPlayerStatisticMockMvc.perform(get("/api/global-player-statistics/{id}", globalPlayerStatistic.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(globalPlayerStatistic.getId().intValue()))
            .andExpect(jsonPath("$.valueDouble").value(DEFAULT_VALUE_DOUBLE.doubleValue()))
            .andExpect(jsonPath("$.valueLong").value(DEFAULT_VALUE_LONG.intValue()))
            .andExpect(jsonPath("$.valueString").value(DEFAULT_VALUE_STRING))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    public void getNonExistingGlobalPlayerStatistic() throws Exception {
        // Get the globalPlayerStatistic
        restGlobalPlayerStatisticMockMvc.perform(get("/api/global-player-statistics/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGlobalPlayerStatistic() throws Exception {
        // Initialize the database
        globalPlayerStatisticRepository.saveAndFlush(globalPlayerStatistic);

        int databaseSizeBeforeUpdate = globalPlayerStatisticRepository.findAll().size();

        // Update the globalPlayerStatistic
        GlobalPlayerStatistic updatedGlobalPlayerStatistic = globalPlayerStatisticRepository.findById(globalPlayerStatistic.getId()).get();
        // Disconnect from session so that the updates on updatedGlobalPlayerStatistic are not directly saved in db
        em.detach(updatedGlobalPlayerStatistic);
        updatedGlobalPlayerStatistic
            .valueDouble(UPDATED_VALUE_DOUBLE)
            .valueLong(UPDATED_VALUE_LONG)
            .valueString(UPDATED_VALUE_STRING)
            .description(UPDATED_DESCRIPTION)
            .name(UPDATED_NAME);

        restGlobalPlayerStatisticMockMvc.perform(put("/api/global-player-statistics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedGlobalPlayerStatistic)))
            .andExpect(status().isOk());

        // Validate the GlobalPlayerStatistic in the database
        List<GlobalPlayerStatistic> globalPlayerStatisticList = globalPlayerStatisticRepository.findAll();
        assertThat(globalPlayerStatisticList).hasSize(databaseSizeBeforeUpdate);
        GlobalPlayerStatistic testGlobalPlayerStatistic = globalPlayerStatisticList.get(globalPlayerStatisticList.size() - 1);
        assertThat(testGlobalPlayerStatistic.getValueDouble()).isEqualTo(UPDATED_VALUE_DOUBLE);
        assertThat(testGlobalPlayerStatistic.getValueLong()).isEqualTo(UPDATED_VALUE_LONG);
        assertThat(testGlobalPlayerStatistic.getValueString()).isEqualTo(UPDATED_VALUE_STRING);
        assertThat(testGlobalPlayerStatistic.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testGlobalPlayerStatistic.getName()).isEqualTo(UPDATED_NAME);

        // Validate the GlobalPlayerStatistic in Elasticsearch
        verify(mockGlobalPlayerStatisticSearchRepository, times(1)).save(testGlobalPlayerStatistic);
    }

    @Test
    @Transactional
    public void updateNonExistingGlobalPlayerStatistic() throws Exception {
        int databaseSizeBeforeUpdate = globalPlayerStatisticRepository.findAll().size();

        // Create the GlobalPlayerStatistic

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGlobalPlayerStatisticMockMvc.perform(put("/api/global-player-statistics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(globalPlayerStatistic)))
            .andExpect(status().isBadRequest());

        // Validate the GlobalPlayerStatistic in the database
        List<GlobalPlayerStatistic> globalPlayerStatisticList = globalPlayerStatisticRepository.findAll();
        assertThat(globalPlayerStatisticList).hasSize(databaseSizeBeforeUpdate);

        // Validate the GlobalPlayerStatistic in Elasticsearch
        verify(mockGlobalPlayerStatisticSearchRepository, times(0)).save(globalPlayerStatistic);
    }

    @Test
    @Transactional
    public void deleteGlobalPlayerStatistic() throws Exception {
        // Initialize the database
        globalPlayerStatisticRepository.saveAndFlush(globalPlayerStatistic);

        int databaseSizeBeforeDelete = globalPlayerStatisticRepository.findAll().size();

        // Delete the globalPlayerStatistic
        restGlobalPlayerStatisticMockMvc.perform(delete("/api/global-player-statistics/{id}", globalPlayerStatistic.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GlobalPlayerStatistic> globalPlayerStatisticList = globalPlayerStatisticRepository.findAll();
        assertThat(globalPlayerStatisticList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the GlobalPlayerStatistic in Elasticsearch
        verify(mockGlobalPlayerStatisticSearchRepository, times(1)).deleteById(globalPlayerStatistic.getId());
    }

    @Test
    @Transactional
    public void searchGlobalPlayerStatistic() throws Exception {
        // Initialize the database
        globalPlayerStatisticRepository.saveAndFlush(globalPlayerStatistic);
        when(mockGlobalPlayerStatisticSearchRepository.search(queryStringQuery("id:" + globalPlayerStatistic.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(globalPlayerStatistic), PageRequest.of(0, 1), 1));
        // Search the globalPlayerStatistic
        restGlobalPlayerStatisticMockMvc.perform(get("/api/_search/global-player-statistics?query=id:" + globalPlayerStatistic.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(globalPlayerStatistic.getId().intValue())))
            .andExpect(jsonPath("$.[*].valueDouble").value(hasItem(DEFAULT_VALUE_DOUBLE.doubleValue())))
            .andExpect(jsonPath("$.[*].valueLong").value(hasItem(DEFAULT_VALUE_LONG.intValue())))
            .andExpect(jsonPath("$.[*].valueString").value(hasItem(DEFAULT_VALUE_STRING)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GlobalPlayerStatistic.class);
        GlobalPlayerStatistic globalPlayerStatistic1 = new GlobalPlayerStatistic();
        globalPlayerStatistic1.setId(1L);
        GlobalPlayerStatistic globalPlayerStatistic2 = new GlobalPlayerStatistic();
        globalPlayerStatistic2.setId(globalPlayerStatistic1.getId());
        assertThat(globalPlayerStatistic1).isEqualTo(globalPlayerStatistic2);
        globalPlayerStatistic2.setId(2L);
        assertThat(globalPlayerStatistic1).isNotEqualTo(globalPlayerStatistic2);
        globalPlayerStatistic1.setId(null);
        assertThat(globalPlayerStatistic1).isNotEqualTo(globalPlayerStatistic2);
    }
}
