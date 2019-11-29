package com.darfat.registats.web.rest;

import com.darfat.registats.RegistatsApp;
import com.darfat.registats.domain.MatchStatistic;
import com.darfat.registats.repository.MatchStatisticRepository;
import com.darfat.registats.repository.search.MatchStatisticSearchRepository;
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
 * Integration tests for the {@link MatchStatisticResource} REST controller.
 */
@SpringBootTest(classes = RegistatsApp.class)
public class MatchStatisticResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Long DEFAULT_VALUE = 1L;
    private static final Long UPDATED_VALUE = 2L;

    private static final Double DEFAULT_VALUE_DOUBLE = 1D;
    private static final Double UPDATED_VALUE_DOUBLE = 2D;

    private static final Long DEFAULT_VALUE_LONG = 1L;
    private static final Long UPDATED_VALUE_LONG = 2L;

    private static final String DEFAULT_VALUE_STRING = "AAAAAAAAAA";
    private static final String UPDATED_VALUE_STRING = "BBBBBBBBBB";

    @Autowired
    private MatchStatisticRepository matchStatisticRepository;

    /**
     * This repository is mocked in the com.darfat.registats.repository.search test package.
     *
     * @see com.darfat.registats.repository.search.MatchStatisticSearchRepositoryMockConfiguration
     */
    @Autowired
    private MatchStatisticSearchRepository mockMatchStatisticSearchRepository;

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

    private MockMvc restMatchStatisticMockMvc;

    private MatchStatistic matchStatistic;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MatchStatisticResource matchStatisticResource = new MatchStatisticResource(matchStatisticRepository, mockMatchStatisticSearchRepository);
        this.restMatchStatisticMockMvc = MockMvcBuilders.standaloneSetup(matchStatisticResource)
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
    public static MatchStatistic createEntity(EntityManager em) {
        MatchStatistic matchStatistic = new MatchStatistic()
            .description(DEFAULT_DESCRIPTION)
            .value(DEFAULT_VALUE)
            .valueDouble(DEFAULT_VALUE_DOUBLE)
            .valueLong(DEFAULT_VALUE_LONG)
            .valueString(DEFAULT_VALUE_STRING);
        return matchStatistic;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MatchStatistic createUpdatedEntity(EntityManager em) {
        MatchStatistic matchStatistic = new MatchStatistic()
            .description(UPDATED_DESCRIPTION)
            .value(UPDATED_VALUE)
            .valueDouble(UPDATED_VALUE_DOUBLE)
            .valueLong(UPDATED_VALUE_LONG)
            .valueString(UPDATED_VALUE_STRING);
        return matchStatistic;
    }

    @BeforeEach
    public void initTest() {
        matchStatistic = createEntity(em);
    }

    @Test
    @Transactional
    public void createMatchStatistic() throws Exception {
        int databaseSizeBeforeCreate = matchStatisticRepository.findAll().size();

        // Create the MatchStatistic
        restMatchStatisticMockMvc.perform(post("/api/match-statistics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(matchStatistic)))
            .andExpect(status().isCreated());

        // Validate the MatchStatistic in the database
        List<MatchStatistic> matchStatisticList = matchStatisticRepository.findAll();
        assertThat(matchStatisticList).hasSize(databaseSizeBeforeCreate + 1);
        MatchStatistic testMatchStatistic = matchStatisticList.get(matchStatisticList.size() - 1);
        assertThat(testMatchStatistic.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testMatchStatistic.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testMatchStatistic.getValueDouble()).isEqualTo(DEFAULT_VALUE_DOUBLE);
        assertThat(testMatchStatistic.getValueLong()).isEqualTo(DEFAULT_VALUE_LONG);
        assertThat(testMatchStatistic.getValueString()).isEqualTo(DEFAULT_VALUE_STRING);

        // Validate the MatchStatistic in Elasticsearch
        verify(mockMatchStatisticSearchRepository, times(1)).save(testMatchStatistic);
    }

    @Test
    @Transactional
    public void createMatchStatisticWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = matchStatisticRepository.findAll().size();

        // Create the MatchStatistic with an existing ID
        matchStatistic.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMatchStatisticMockMvc.perform(post("/api/match-statistics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(matchStatistic)))
            .andExpect(status().isBadRequest());

        // Validate the MatchStatistic in the database
        List<MatchStatistic> matchStatisticList = matchStatisticRepository.findAll();
        assertThat(matchStatisticList).hasSize(databaseSizeBeforeCreate);

        // Validate the MatchStatistic in Elasticsearch
        verify(mockMatchStatisticSearchRepository, times(0)).save(matchStatistic);
    }


    @Test
    @Transactional
    public void getAllMatchStatistics() throws Exception {
        // Initialize the database
        matchStatisticRepository.saveAndFlush(matchStatistic);

        // Get all the matchStatisticList
        restMatchStatisticMockMvc.perform(get("/api/match-statistics?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(matchStatistic.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.intValue())))
            .andExpect(jsonPath("$.[*].valueDouble").value(hasItem(DEFAULT_VALUE_DOUBLE.doubleValue())))
            .andExpect(jsonPath("$.[*].valueLong").value(hasItem(DEFAULT_VALUE_LONG.intValue())))
            .andExpect(jsonPath("$.[*].valueString").value(hasItem(DEFAULT_VALUE_STRING)));
    }
    
    @Test
    @Transactional
    public void getMatchStatistic() throws Exception {
        // Initialize the database
        matchStatisticRepository.saveAndFlush(matchStatistic);

        // Get the matchStatistic
        restMatchStatisticMockMvc.perform(get("/api/match-statistics/{id}", matchStatistic.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(matchStatistic.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.intValue()))
            .andExpect(jsonPath("$.valueDouble").value(DEFAULT_VALUE_DOUBLE.doubleValue()))
            .andExpect(jsonPath("$.valueLong").value(DEFAULT_VALUE_LONG.intValue()))
            .andExpect(jsonPath("$.valueString").value(DEFAULT_VALUE_STRING));
    }

    @Test
    @Transactional
    public void getNonExistingMatchStatistic() throws Exception {
        // Get the matchStatistic
        restMatchStatisticMockMvc.perform(get("/api/match-statistics/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMatchStatistic() throws Exception {
        // Initialize the database
        matchStatisticRepository.saveAndFlush(matchStatistic);

        int databaseSizeBeforeUpdate = matchStatisticRepository.findAll().size();

        // Update the matchStatistic
        MatchStatistic updatedMatchStatistic = matchStatisticRepository.findById(matchStatistic.getId()).get();
        // Disconnect from session so that the updates on updatedMatchStatistic are not directly saved in db
        em.detach(updatedMatchStatistic);
        updatedMatchStatistic
            .description(UPDATED_DESCRIPTION)
            .value(UPDATED_VALUE)
            .valueDouble(UPDATED_VALUE_DOUBLE)
            .valueLong(UPDATED_VALUE_LONG)
            .valueString(UPDATED_VALUE_STRING);

        restMatchStatisticMockMvc.perform(put("/api/match-statistics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMatchStatistic)))
            .andExpect(status().isOk());

        // Validate the MatchStatistic in the database
        List<MatchStatistic> matchStatisticList = matchStatisticRepository.findAll();
        assertThat(matchStatisticList).hasSize(databaseSizeBeforeUpdate);
        MatchStatistic testMatchStatistic = matchStatisticList.get(matchStatisticList.size() - 1);
        assertThat(testMatchStatistic.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMatchStatistic.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testMatchStatistic.getValueDouble()).isEqualTo(UPDATED_VALUE_DOUBLE);
        assertThat(testMatchStatistic.getValueLong()).isEqualTo(UPDATED_VALUE_LONG);
        assertThat(testMatchStatistic.getValueString()).isEqualTo(UPDATED_VALUE_STRING);

        // Validate the MatchStatistic in Elasticsearch
        verify(mockMatchStatisticSearchRepository, times(1)).save(testMatchStatistic);
    }

    @Test
    @Transactional
    public void updateNonExistingMatchStatistic() throws Exception {
        int databaseSizeBeforeUpdate = matchStatisticRepository.findAll().size();

        // Create the MatchStatistic

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMatchStatisticMockMvc.perform(put("/api/match-statistics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(matchStatistic)))
            .andExpect(status().isBadRequest());

        // Validate the MatchStatistic in the database
        List<MatchStatistic> matchStatisticList = matchStatisticRepository.findAll();
        assertThat(matchStatisticList).hasSize(databaseSizeBeforeUpdate);

        // Validate the MatchStatistic in Elasticsearch
        verify(mockMatchStatisticSearchRepository, times(0)).save(matchStatistic);
    }

    @Test
    @Transactional
    public void deleteMatchStatistic() throws Exception {
        // Initialize the database
        matchStatisticRepository.saveAndFlush(matchStatistic);

        int databaseSizeBeforeDelete = matchStatisticRepository.findAll().size();

        // Delete the matchStatistic
        restMatchStatisticMockMvc.perform(delete("/api/match-statistics/{id}", matchStatistic.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MatchStatistic> matchStatisticList = matchStatisticRepository.findAll();
        assertThat(matchStatisticList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the MatchStatistic in Elasticsearch
        verify(mockMatchStatisticSearchRepository, times(1)).deleteById(matchStatistic.getId());
    }

    @Test
    @Transactional
    public void searchMatchStatistic() throws Exception {
        // Initialize the database
        matchStatisticRepository.saveAndFlush(matchStatistic);
        when(mockMatchStatisticSearchRepository.search(queryStringQuery("id:" + matchStatistic.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(matchStatistic), PageRequest.of(0, 1), 1));
        // Search the matchStatistic
        restMatchStatisticMockMvc.perform(get("/api/_search/match-statistics?query=id:" + matchStatistic.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(matchStatistic.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.intValue())))
            .andExpect(jsonPath("$.[*].valueDouble").value(hasItem(DEFAULT_VALUE_DOUBLE.doubleValue())))
            .andExpect(jsonPath("$.[*].valueLong").value(hasItem(DEFAULT_VALUE_LONG.intValue())))
            .andExpect(jsonPath("$.[*].valueString").value(hasItem(DEFAULT_VALUE_STRING)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MatchStatistic.class);
        MatchStatistic matchStatistic1 = new MatchStatistic();
        matchStatistic1.setId(1L);
        MatchStatistic matchStatistic2 = new MatchStatistic();
        matchStatistic2.setId(matchStatistic1.getId());
        assertThat(matchStatistic1).isEqualTo(matchStatistic2);
        matchStatistic2.setId(2L);
        assertThat(matchStatistic1).isNotEqualTo(matchStatistic2);
        matchStatistic1.setId(null);
        assertThat(matchStatistic1).isNotEqualTo(matchStatistic2);
    }
}
