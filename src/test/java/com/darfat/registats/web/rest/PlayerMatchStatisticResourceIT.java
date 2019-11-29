package com.darfat.registats.web.rest;

import com.darfat.registats.RegistatsApp;
import com.darfat.registats.domain.PlayerMatchStatistic;
import com.darfat.registats.repository.PlayerMatchStatisticRepository;
import com.darfat.registats.repository.search.PlayerMatchStatisticSearchRepository;
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
 * Integration tests for the {@link PlayerMatchStatisticResource} REST controller.
 */
@SpringBootTest(classes = RegistatsApp.class)
public class PlayerMatchStatisticResourceIT {

    private static final Long DEFAULT_DESCRIPTION = 1L;
    private static final Long UPDATED_DESCRIPTION = 2L;

    private static final Long DEFAULT_VALUE = 1L;
    private static final Long UPDATED_VALUE = 2L;

    private static final Double DEFAULT_VALUE_DOUBLE = 1D;
    private static final Double UPDATED_VALUE_DOUBLE = 2D;

    private static final Long DEFAULT_VALUE_LONG = 1L;
    private static final Long UPDATED_VALUE_LONG = 2L;

    private static final String DEFAULT_VALUE_STRING = "AAAAAAAAAA";
    private static final String UPDATED_VALUE_STRING = "BBBBBBBBBB";

    @Autowired
    private PlayerMatchStatisticRepository playerMatchStatisticRepository;

    /**
     * This repository is mocked in the com.darfat.registats.repository.search test package.
     *
     * @see com.darfat.registats.repository.search.PlayerMatchStatisticSearchRepositoryMockConfiguration
     */
    @Autowired
    private PlayerMatchStatisticSearchRepository mockPlayerMatchStatisticSearchRepository;

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

    private MockMvc restPlayerMatchStatisticMockMvc;

    private PlayerMatchStatistic playerMatchStatistic;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PlayerMatchStatisticResource playerMatchStatisticResource = new PlayerMatchStatisticResource(playerMatchStatisticRepository, mockPlayerMatchStatisticSearchRepository);
        this.restPlayerMatchStatisticMockMvc = MockMvcBuilders.standaloneSetup(playerMatchStatisticResource)
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
    public static PlayerMatchStatistic createEntity(EntityManager em) {
        PlayerMatchStatistic playerMatchStatistic = new PlayerMatchStatistic()
            .description(DEFAULT_DESCRIPTION)
            .value(DEFAULT_VALUE)
            .valueDouble(DEFAULT_VALUE_DOUBLE)
            .valueLong(DEFAULT_VALUE_LONG)
            .valueString(DEFAULT_VALUE_STRING);
        return playerMatchStatistic;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlayerMatchStatistic createUpdatedEntity(EntityManager em) {
        PlayerMatchStatistic playerMatchStatistic = new PlayerMatchStatistic()
            .description(UPDATED_DESCRIPTION)
            .value(UPDATED_VALUE)
            .valueDouble(UPDATED_VALUE_DOUBLE)
            .valueLong(UPDATED_VALUE_LONG)
            .valueString(UPDATED_VALUE_STRING);
        return playerMatchStatistic;
    }

    @BeforeEach
    public void initTest() {
        playerMatchStatistic = createEntity(em);
    }

    @Test
    @Transactional
    public void createPlayerMatchStatistic() throws Exception {
        int databaseSizeBeforeCreate = playerMatchStatisticRepository.findAll().size();

        // Create the PlayerMatchStatistic
        restPlayerMatchStatisticMockMvc.perform(post("/api/player-match-statistics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(playerMatchStatistic)))
            .andExpect(status().isCreated());

        // Validate the PlayerMatchStatistic in the database
        List<PlayerMatchStatistic> playerMatchStatisticList = playerMatchStatisticRepository.findAll();
        assertThat(playerMatchStatisticList).hasSize(databaseSizeBeforeCreate + 1);
        PlayerMatchStatistic testPlayerMatchStatistic = playerMatchStatisticList.get(playerMatchStatisticList.size() - 1);
        assertThat(testPlayerMatchStatistic.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPlayerMatchStatistic.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testPlayerMatchStatistic.getValueDouble()).isEqualTo(DEFAULT_VALUE_DOUBLE);
        assertThat(testPlayerMatchStatistic.getValueLong()).isEqualTo(DEFAULT_VALUE_LONG);
        assertThat(testPlayerMatchStatistic.getValueString()).isEqualTo(DEFAULT_VALUE_STRING);

        // Validate the PlayerMatchStatistic in Elasticsearch
        verify(mockPlayerMatchStatisticSearchRepository, times(1)).save(testPlayerMatchStatistic);
    }

    @Test
    @Transactional
    public void createPlayerMatchStatisticWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = playerMatchStatisticRepository.findAll().size();

        // Create the PlayerMatchStatistic with an existing ID
        playerMatchStatistic.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlayerMatchStatisticMockMvc.perform(post("/api/player-match-statistics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(playerMatchStatistic)))
            .andExpect(status().isBadRequest());

        // Validate the PlayerMatchStatistic in the database
        List<PlayerMatchStatistic> playerMatchStatisticList = playerMatchStatisticRepository.findAll();
        assertThat(playerMatchStatisticList).hasSize(databaseSizeBeforeCreate);

        // Validate the PlayerMatchStatistic in Elasticsearch
        verify(mockPlayerMatchStatisticSearchRepository, times(0)).save(playerMatchStatistic);
    }


    @Test
    @Transactional
    public void getAllPlayerMatchStatistics() throws Exception {
        // Initialize the database
        playerMatchStatisticRepository.saveAndFlush(playerMatchStatistic);

        // Get all the playerMatchStatisticList
        restPlayerMatchStatisticMockMvc.perform(get("/api/player-match-statistics?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(playerMatchStatistic.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.intValue())))
            .andExpect(jsonPath("$.[*].valueDouble").value(hasItem(DEFAULT_VALUE_DOUBLE.doubleValue())))
            .andExpect(jsonPath("$.[*].valueLong").value(hasItem(DEFAULT_VALUE_LONG.intValue())))
            .andExpect(jsonPath("$.[*].valueString").value(hasItem(DEFAULT_VALUE_STRING)));
    }
    
    @Test
    @Transactional
    public void getPlayerMatchStatistic() throws Exception {
        // Initialize the database
        playerMatchStatisticRepository.saveAndFlush(playerMatchStatistic);

        // Get the playerMatchStatistic
        restPlayerMatchStatisticMockMvc.perform(get("/api/player-match-statistics/{id}", playerMatchStatistic.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(playerMatchStatistic.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.intValue()))
            .andExpect(jsonPath("$.valueDouble").value(DEFAULT_VALUE_DOUBLE.doubleValue()))
            .andExpect(jsonPath("$.valueLong").value(DEFAULT_VALUE_LONG.intValue()))
            .andExpect(jsonPath("$.valueString").value(DEFAULT_VALUE_STRING));
    }

    @Test
    @Transactional
    public void getNonExistingPlayerMatchStatistic() throws Exception {
        // Get the playerMatchStatistic
        restPlayerMatchStatisticMockMvc.perform(get("/api/player-match-statistics/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlayerMatchStatistic() throws Exception {
        // Initialize the database
        playerMatchStatisticRepository.saveAndFlush(playerMatchStatistic);

        int databaseSizeBeforeUpdate = playerMatchStatisticRepository.findAll().size();

        // Update the playerMatchStatistic
        PlayerMatchStatistic updatedPlayerMatchStatistic = playerMatchStatisticRepository.findById(playerMatchStatistic.getId()).get();
        // Disconnect from session so that the updates on updatedPlayerMatchStatistic are not directly saved in db
        em.detach(updatedPlayerMatchStatistic);
        updatedPlayerMatchStatistic
            .description(UPDATED_DESCRIPTION)
            .value(UPDATED_VALUE)
            .valueDouble(UPDATED_VALUE_DOUBLE)
            .valueLong(UPDATED_VALUE_LONG)
            .valueString(UPDATED_VALUE_STRING);

        restPlayerMatchStatisticMockMvc.perform(put("/api/player-match-statistics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPlayerMatchStatistic)))
            .andExpect(status().isOk());

        // Validate the PlayerMatchStatistic in the database
        List<PlayerMatchStatistic> playerMatchStatisticList = playerMatchStatisticRepository.findAll();
        assertThat(playerMatchStatisticList).hasSize(databaseSizeBeforeUpdate);
        PlayerMatchStatistic testPlayerMatchStatistic = playerMatchStatisticList.get(playerMatchStatisticList.size() - 1);
        assertThat(testPlayerMatchStatistic.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPlayerMatchStatistic.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testPlayerMatchStatistic.getValueDouble()).isEqualTo(UPDATED_VALUE_DOUBLE);
        assertThat(testPlayerMatchStatistic.getValueLong()).isEqualTo(UPDATED_VALUE_LONG);
        assertThat(testPlayerMatchStatistic.getValueString()).isEqualTo(UPDATED_VALUE_STRING);

        // Validate the PlayerMatchStatistic in Elasticsearch
        verify(mockPlayerMatchStatisticSearchRepository, times(1)).save(testPlayerMatchStatistic);
    }

    @Test
    @Transactional
    public void updateNonExistingPlayerMatchStatistic() throws Exception {
        int databaseSizeBeforeUpdate = playerMatchStatisticRepository.findAll().size();

        // Create the PlayerMatchStatistic

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlayerMatchStatisticMockMvc.perform(put("/api/player-match-statistics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(playerMatchStatistic)))
            .andExpect(status().isBadRequest());

        // Validate the PlayerMatchStatistic in the database
        List<PlayerMatchStatistic> playerMatchStatisticList = playerMatchStatisticRepository.findAll();
        assertThat(playerMatchStatisticList).hasSize(databaseSizeBeforeUpdate);

        // Validate the PlayerMatchStatistic in Elasticsearch
        verify(mockPlayerMatchStatisticSearchRepository, times(0)).save(playerMatchStatistic);
    }

    @Test
    @Transactional
    public void deletePlayerMatchStatistic() throws Exception {
        // Initialize the database
        playerMatchStatisticRepository.saveAndFlush(playerMatchStatistic);

        int databaseSizeBeforeDelete = playerMatchStatisticRepository.findAll().size();

        // Delete the playerMatchStatistic
        restPlayerMatchStatisticMockMvc.perform(delete("/api/player-match-statistics/{id}", playerMatchStatistic.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PlayerMatchStatistic> playerMatchStatisticList = playerMatchStatisticRepository.findAll();
        assertThat(playerMatchStatisticList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the PlayerMatchStatistic in Elasticsearch
        verify(mockPlayerMatchStatisticSearchRepository, times(1)).deleteById(playerMatchStatistic.getId());
    }

    @Test
    @Transactional
    public void searchPlayerMatchStatistic() throws Exception {
        // Initialize the database
        playerMatchStatisticRepository.saveAndFlush(playerMatchStatistic);
        when(mockPlayerMatchStatisticSearchRepository.search(queryStringQuery("id:" + playerMatchStatistic.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(playerMatchStatistic), PageRequest.of(0, 1), 1));
        // Search the playerMatchStatistic
        restPlayerMatchStatisticMockMvc.perform(get("/api/_search/player-match-statistics?query=id:" + playerMatchStatistic.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(playerMatchStatistic.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.intValue())))
            .andExpect(jsonPath("$.[*].valueDouble").value(hasItem(DEFAULT_VALUE_DOUBLE.doubleValue())))
            .andExpect(jsonPath("$.[*].valueLong").value(hasItem(DEFAULT_VALUE_LONG.intValue())))
            .andExpect(jsonPath("$.[*].valueString").value(hasItem(DEFAULT_VALUE_STRING)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlayerMatchStatistic.class);
        PlayerMatchStatistic playerMatchStatistic1 = new PlayerMatchStatistic();
        playerMatchStatistic1.setId(1L);
        PlayerMatchStatistic playerMatchStatistic2 = new PlayerMatchStatistic();
        playerMatchStatistic2.setId(playerMatchStatistic1.getId());
        assertThat(playerMatchStatistic1).isEqualTo(playerMatchStatistic2);
        playerMatchStatistic2.setId(2L);
        assertThat(playerMatchStatistic1).isNotEqualTo(playerMatchStatistic2);
        playerMatchStatistic1.setId(null);
        assertThat(playerMatchStatistic1).isNotEqualTo(playerMatchStatistic2);
    }
}
