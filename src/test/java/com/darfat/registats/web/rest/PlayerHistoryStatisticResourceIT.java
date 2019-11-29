package com.darfat.registats.web.rest;

import com.darfat.registats.RegistatsApp;
import com.darfat.registats.domain.PlayerHistoryStatistic;
import com.darfat.registats.repository.PlayerHistoryStatisticRepository;
import com.darfat.registats.repository.search.PlayerHistoryStatisticSearchRepository;
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
 * Integration tests for the {@link PlayerHistoryStatisticResource} REST controller.
 */
@SpringBootTest(classes = RegistatsApp.class)
public class PlayerHistoryStatisticResourceIT {

    private static final Long DEFAULT_VALUE = 1L;
    private static final Long UPDATED_VALUE = 2L;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private PlayerHistoryStatisticRepository playerHistoryStatisticRepository;

    /**
     * This repository is mocked in the com.darfat.registats.repository.search test package.
     *
     * @see com.darfat.registats.repository.search.PlayerHistoryStatisticSearchRepositoryMockConfiguration
     */
    @Autowired
    private PlayerHistoryStatisticSearchRepository mockPlayerHistoryStatisticSearchRepository;

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

    private MockMvc restPlayerHistoryStatisticMockMvc;

    private PlayerHistoryStatistic playerHistoryStatistic;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PlayerHistoryStatisticResource playerHistoryStatisticResource = new PlayerHistoryStatisticResource(playerHistoryStatisticRepository, mockPlayerHistoryStatisticSearchRepository);
        this.restPlayerHistoryStatisticMockMvc = MockMvcBuilders.standaloneSetup(playerHistoryStatisticResource)
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
    public static PlayerHistoryStatistic createEntity(EntityManager em) {
        PlayerHistoryStatistic playerHistoryStatistic = new PlayerHistoryStatistic()
            .value(DEFAULT_VALUE)
            .description(DEFAULT_DESCRIPTION);
        return playerHistoryStatistic;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlayerHistoryStatistic createUpdatedEntity(EntityManager em) {
        PlayerHistoryStatistic playerHistoryStatistic = new PlayerHistoryStatistic()
            .value(UPDATED_VALUE)
            .description(UPDATED_DESCRIPTION);
        return playerHistoryStatistic;
    }

    @BeforeEach
    public void initTest() {
        playerHistoryStatistic = createEntity(em);
    }

    @Test
    @Transactional
    public void createPlayerHistoryStatistic() throws Exception {
        int databaseSizeBeforeCreate = playerHistoryStatisticRepository.findAll().size();

        // Create the PlayerHistoryStatistic
        restPlayerHistoryStatisticMockMvc.perform(post("/api/player-history-statistics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(playerHistoryStatistic)))
            .andExpect(status().isCreated());

        // Validate the PlayerHistoryStatistic in the database
        List<PlayerHistoryStatistic> playerHistoryStatisticList = playerHistoryStatisticRepository.findAll();
        assertThat(playerHistoryStatisticList).hasSize(databaseSizeBeforeCreate + 1);
        PlayerHistoryStatistic testPlayerHistoryStatistic = playerHistoryStatisticList.get(playerHistoryStatisticList.size() - 1);
        assertThat(testPlayerHistoryStatistic.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testPlayerHistoryStatistic.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the PlayerHistoryStatistic in Elasticsearch
        verify(mockPlayerHistoryStatisticSearchRepository, times(1)).save(testPlayerHistoryStatistic);
    }

    @Test
    @Transactional
    public void createPlayerHistoryStatisticWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = playerHistoryStatisticRepository.findAll().size();

        // Create the PlayerHistoryStatistic with an existing ID
        playerHistoryStatistic.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlayerHistoryStatisticMockMvc.perform(post("/api/player-history-statistics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(playerHistoryStatistic)))
            .andExpect(status().isBadRequest());

        // Validate the PlayerHistoryStatistic in the database
        List<PlayerHistoryStatistic> playerHistoryStatisticList = playerHistoryStatisticRepository.findAll();
        assertThat(playerHistoryStatisticList).hasSize(databaseSizeBeforeCreate);

        // Validate the PlayerHistoryStatistic in Elasticsearch
        verify(mockPlayerHistoryStatisticSearchRepository, times(0)).save(playerHistoryStatistic);
    }


    @Test
    @Transactional
    public void getAllPlayerHistoryStatistics() throws Exception {
        // Initialize the database
        playerHistoryStatisticRepository.saveAndFlush(playerHistoryStatistic);

        // Get all the playerHistoryStatisticList
        restPlayerHistoryStatisticMockMvc.perform(get("/api/player-history-statistics?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(playerHistoryStatistic.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getPlayerHistoryStatistic() throws Exception {
        // Initialize the database
        playerHistoryStatisticRepository.saveAndFlush(playerHistoryStatistic);

        // Get the playerHistoryStatistic
        restPlayerHistoryStatisticMockMvc.perform(get("/api/player-history-statistics/{id}", playerHistoryStatistic.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(playerHistoryStatistic.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    public void getNonExistingPlayerHistoryStatistic() throws Exception {
        // Get the playerHistoryStatistic
        restPlayerHistoryStatisticMockMvc.perform(get("/api/player-history-statistics/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlayerHistoryStatistic() throws Exception {
        // Initialize the database
        playerHistoryStatisticRepository.saveAndFlush(playerHistoryStatistic);

        int databaseSizeBeforeUpdate = playerHistoryStatisticRepository.findAll().size();

        // Update the playerHistoryStatistic
        PlayerHistoryStatistic updatedPlayerHistoryStatistic = playerHistoryStatisticRepository.findById(playerHistoryStatistic.getId()).get();
        // Disconnect from session so that the updates on updatedPlayerHistoryStatistic are not directly saved in db
        em.detach(updatedPlayerHistoryStatistic);
        updatedPlayerHistoryStatistic
            .value(UPDATED_VALUE)
            .description(UPDATED_DESCRIPTION);

        restPlayerHistoryStatisticMockMvc.perform(put("/api/player-history-statistics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPlayerHistoryStatistic)))
            .andExpect(status().isOk());

        // Validate the PlayerHistoryStatistic in the database
        List<PlayerHistoryStatistic> playerHistoryStatisticList = playerHistoryStatisticRepository.findAll();
        assertThat(playerHistoryStatisticList).hasSize(databaseSizeBeforeUpdate);
        PlayerHistoryStatistic testPlayerHistoryStatistic = playerHistoryStatisticList.get(playerHistoryStatisticList.size() - 1);
        assertThat(testPlayerHistoryStatistic.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testPlayerHistoryStatistic.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the PlayerHistoryStatistic in Elasticsearch
        verify(mockPlayerHistoryStatisticSearchRepository, times(1)).save(testPlayerHistoryStatistic);
    }

    @Test
    @Transactional
    public void updateNonExistingPlayerHistoryStatistic() throws Exception {
        int databaseSizeBeforeUpdate = playerHistoryStatisticRepository.findAll().size();

        // Create the PlayerHistoryStatistic

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlayerHistoryStatisticMockMvc.perform(put("/api/player-history-statistics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(playerHistoryStatistic)))
            .andExpect(status().isBadRequest());

        // Validate the PlayerHistoryStatistic in the database
        List<PlayerHistoryStatistic> playerHistoryStatisticList = playerHistoryStatisticRepository.findAll();
        assertThat(playerHistoryStatisticList).hasSize(databaseSizeBeforeUpdate);

        // Validate the PlayerHistoryStatistic in Elasticsearch
        verify(mockPlayerHistoryStatisticSearchRepository, times(0)).save(playerHistoryStatistic);
    }

    @Test
    @Transactional
    public void deletePlayerHistoryStatistic() throws Exception {
        // Initialize the database
        playerHistoryStatisticRepository.saveAndFlush(playerHistoryStatistic);

        int databaseSizeBeforeDelete = playerHistoryStatisticRepository.findAll().size();

        // Delete the playerHistoryStatistic
        restPlayerHistoryStatisticMockMvc.perform(delete("/api/player-history-statistics/{id}", playerHistoryStatistic.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PlayerHistoryStatistic> playerHistoryStatisticList = playerHistoryStatisticRepository.findAll();
        assertThat(playerHistoryStatisticList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the PlayerHistoryStatistic in Elasticsearch
        verify(mockPlayerHistoryStatisticSearchRepository, times(1)).deleteById(playerHistoryStatistic.getId());
    }

    @Test
    @Transactional
    public void searchPlayerHistoryStatistic() throws Exception {
        // Initialize the database
        playerHistoryStatisticRepository.saveAndFlush(playerHistoryStatistic);
        when(mockPlayerHistoryStatisticSearchRepository.search(queryStringQuery("id:" + playerHistoryStatistic.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(playerHistoryStatistic), PageRequest.of(0, 1), 1));
        // Search the playerHistoryStatistic
        restPlayerHistoryStatisticMockMvc.perform(get("/api/_search/player-history-statistics?query=id:" + playerHistoryStatistic.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(playerHistoryStatistic.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlayerHistoryStatistic.class);
        PlayerHistoryStatistic playerHistoryStatistic1 = new PlayerHistoryStatistic();
        playerHistoryStatistic1.setId(1L);
        PlayerHistoryStatistic playerHistoryStatistic2 = new PlayerHistoryStatistic();
        playerHistoryStatistic2.setId(playerHistoryStatistic1.getId());
        assertThat(playerHistoryStatistic1).isEqualTo(playerHistoryStatistic2);
        playerHistoryStatistic2.setId(2L);
        assertThat(playerHistoryStatistic1).isNotEqualTo(playerHistoryStatistic2);
        playerHistoryStatistic1.setId(null);
        assertThat(playerHistoryStatistic1).isNotEqualTo(playerHistoryStatistic2);
    }
}
