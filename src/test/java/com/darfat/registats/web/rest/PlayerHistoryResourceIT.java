package com.darfat.registats.web.rest;

import com.darfat.registats.RegistatsApp;
import com.darfat.registats.domain.PlayerHistory;
import com.darfat.registats.repository.PlayerHistoryRepository;
import com.darfat.registats.repository.search.PlayerHistorySearchRepository;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link PlayerHistoryResource} REST controller.
 */
@SpringBootTest(classes = RegistatsApp.class)
public class PlayerHistoryResourceIT {

    private static final Instant DEFAULT_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_APPEARANCE = 1L;
    private static final Long UPDATED_APPEARANCE = 2L;

    private static final Long DEFAULT_MINUTE_PLAYED = 1L;
    private static final Long UPDATED_MINUTE_PLAYED = 2L;

    private static final Long DEFAULT_GOALS = 1L;
    private static final Long UPDATED_GOALS = 2L;

    private static final Long DEFAULT_ASSISTS = 1L;
    private static final Long UPDATED_ASSISTS = 2L;

    private static final Long DEFAULT_AVERAGE_RATING = 1L;
    private static final Long UPDATED_AVERAGE_RATING = 2L;

    @Autowired
    private PlayerHistoryRepository playerHistoryRepository;

    /**
     * This repository is mocked in the com.darfat.registats.repository.search test package.
     *
     * @see com.darfat.registats.repository.search.PlayerHistorySearchRepositoryMockConfiguration
     */
    @Autowired
    private PlayerHistorySearchRepository mockPlayerHistorySearchRepository;

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

    private MockMvc restPlayerHistoryMockMvc;

    private PlayerHistory playerHistory;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PlayerHistoryResource playerHistoryResource = new PlayerHistoryResource(playerHistoryRepository, mockPlayerHistorySearchRepository);
        this.restPlayerHistoryMockMvc = MockMvcBuilders.standaloneSetup(playerHistoryResource)
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
    public static PlayerHistory createEntity(EntityManager em) {
        PlayerHistory playerHistory = new PlayerHistory()
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .appearance(DEFAULT_APPEARANCE)
            .minutePlayed(DEFAULT_MINUTE_PLAYED)
            .goals(DEFAULT_GOALS)
            .assists(DEFAULT_ASSISTS)
            .averageRating(DEFAULT_AVERAGE_RATING);
        return playerHistory;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlayerHistory createUpdatedEntity(EntityManager em) {
        PlayerHistory playerHistory = new PlayerHistory()
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .appearance(UPDATED_APPEARANCE)
            .minutePlayed(UPDATED_MINUTE_PLAYED)
            .goals(UPDATED_GOALS)
            .assists(UPDATED_ASSISTS)
            .averageRating(UPDATED_AVERAGE_RATING);
        return playerHistory;
    }

    @BeforeEach
    public void initTest() {
        playerHistory = createEntity(em);
    }

    @Test
    @Transactional
    public void createPlayerHistory() throws Exception {
        int databaseSizeBeforeCreate = playerHistoryRepository.findAll().size();

        // Create the PlayerHistory
        restPlayerHistoryMockMvc.perform(post("/api/player-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(playerHistory)))
            .andExpect(status().isCreated());

        // Validate the PlayerHistory in the database
        List<PlayerHistory> playerHistoryList = playerHistoryRepository.findAll();
        assertThat(playerHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        PlayerHistory testPlayerHistory = playerHistoryList.get(playerHistoryList.size() - 1);
        assertThat(testPlayerHistory.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testPlayerHistory.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testPlayerHistory.getAppearance()).isEqualTo(DEFAULT_APPEARANCE);
        assertThat(testPlayerHistory.getMinutePlayed()).isEqualTo(DEFAULT_MINUTE_PLAYED);
        assertThat(testPlayerHistory.getGoals()).isEqualTo(DEFAULT_GOALS);
        assertThat(testPlayerHistory.getAssists()).isEqualTo(DEFAULT_ASSISTS);
        assertThat(testPlayerHistory.getAverageRating()).isEqualTo(DEFAULT_AVERAGE_RATING);

        // Validate the PlayerHistory in Elasticsearch
        verify(mockPlayerHistorySearchRepository, times(1)).save(testPlayerHistory);
    }

    @Test
    @Transactional
    public void createPlayerHistoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = playerHistoryRepository.findAll().size();

        // Create the PlayerHistory with an existing ID
        playerHistory.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlayerHistoryMockMvc.perform(post("/api/player-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(playerHistory)))
            .andExpect(status().isBadRequest());

        // Validate the PlayerHistory in the database
        List<PlayerHistory> playerHistoryList = playerHistoryRepository.findAll();
        assertThat(playerHistoryList).hasSize(databaseSizeBeforeCreate);

        // Validate the PlayerHistory in Elasticsearch
        verify(mockPlayerHistorySearchRepository, times(0)).save(playerHistory);
    }


    @Test
    @Transactional
    public void getAllPlayerHistories() throws Exception {
        // Initialize the database
        playerHistoryRepository.saveAndFlush(playerHistory);

        // Get all the playerHistoryList
        restPlayerHistoryMockMvc.perform(get("/api/player-histories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(playerHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].appearance").value(hasItem(DEFAULT_APPEARANCE.intValue())))
            .andExpect(jsonPath("$.[*].minutePlayed").value(hasItem(DEFAULT_MINUTE_PLAYED.intValue())))
            .andExpect(jsonPath("$.[*].goals").value(hasItem(DEFAULT_GOALS.intValue())))
            .andExpect(jsonPath("$.[*].assists").value(hasItem(DEFAULT_ASSISTS.intValue())))
            .andExpect(jsonPath("$.[*].averageRating").value(hasItem(DEFAULT_AVERAGE_RATING.intValue())));
    }
    
    @Test
    @Transactional
    public void getPlayerHistory() throws Exception {
        // Initialize the database
        playerHistoryRepository.saveAndFlush(playerHistory);

        // Get the playerHistory
        restPlayerHistoryMockMvc.perform(get("/api/player-histories/{id}", playerHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(playerHistory.getId().intValue()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.appearance").value(DEFAULT_APPEARANCE.intValue()))
            .andExpect(jsonPath("$.minutePlayed").value(DEFAULT_MINUTE_PLAYED.intValue()))
            .andExpect(jsonPath("$.goals").value(DEFAULT_GOALS.intValue()))
            .andExpect(jsonPath("$.assists").value(DEFAULT_ASSISTS.intValue()))
            .andExpect(jsonPath("$.averageRating").value(DEFAULT_AVERAGE_RATING.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPlayerHistory() throws Exception {
        // Get the playerHistory
        restPlayerHistoryMockMvc.perform(get("/api/player-histories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlayerHistory() throws Exception {
        // Initialize the database
        playerHistoryRepository.saveAndFlush(playerHistory);

        int databaseSizeBeforeUpdate = playerHistoryRepository.findAll().size();

        // Update the playerHistory
        PlayerHistory updatedPlayerHistory = playerHistoryRepository.findById(playerHistory.getId()).get();
        // Disconnect from session so that the updates on updatedPlayerHistory are not directly saved in db
        em.detach(updatedPlayerHistory);
        updatedPlayerHistory
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .appearance(UPDATED_APPEARANCE)
            .minutePlayed(UPDATED_MINUTE_PLAYED)
            .goals(UPDATED_GOALS)
            .assists(UPDATED_ASSISTS)
            .averageRating(UPDATED_AVERAGE_RATING);

        restPlayerHistoryMockMvc.perform(put("/api/player-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPlayerHistory)))
            .andExpect(status().isOk());

        // Validate the PlayerHistory in the database
        List<PlayerHistory> playerHistoryList = playerHistoryRepository.findAll();
        assertThat(playerHistoryList).hasSize(databaseSizeBeforeUpdate);
        PlayerHistory testPlayerHistory = playerHistoryList.get(playerHistoryList.size() - 1);
        assertThat(testPlayerHistory.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testPlayerHistory.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testPlayerHistory.getAppearance()).isEqualTo(UPDATED_APPEARANCE);
        assertThat(testPlayerHistory.getMinutePlayed()).isEqualTo(UPDATED_MINUTE_PLAYED);
        assertThat(testPlayerHistory.getGoals()).isEqualTo(UPDATED_GOALS);
        assertThat(testPlayerHistory.getAssists()).isEqualTo(UPDATED_ASSISTS);
        assertThat(testPlayerHistory.getAverageRating()).isEqualTo(UPDATED_AVERAGE_RATING);

        // Validate the PlayerHistory in Elasticsearch
        verify(mockPlayerHistorySearchRepository, times(1)).save(testPlayerHistory);
    }

    @Test
    @Transactional
    public void updateNonExistingPlayerHistory() throws Exception {
        int databaseSizeBeforeUpdate = playerHistoryRepository.findAll().size();

        // Create the PlayerHistory

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlayerHistoryMockMvc.perform(put("/api/player-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(playerHistory)))
            .andExpect(status().isBadRequest());

        // Validate the PlayerHistory in the database
        List<PlayerHistory> playerHistoryList = playerHistoryRepository.findAll();
        assertThat(playerHistoryList).hasSize(databaseSizeBeforeUpdate);

        // Validate the PlayerHistory in Elasticsearch
        verify(mockPlayerHistorySearchRepository, times(0)).save(playerHistory);
    }

    @Test
    @Transactional
    public void deletePlayerHistory() throws Exception {
        // Initialize the database
        playerHistoryRepository.saveAndFlush(playerHistory);

        int databaseSizeBeforeDelete = playerHistoryRepository.findAll().size();

        // Delete the playerHistory
        restPlayerHistoryMockMvc.perform(delete("/api/player-histories/{id}", playerHistory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PlayerHistory> playerHistoryList = playerHistoryRepository.findAll();
        assertThat(playerHistoryList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the PlayerHistory in Elasticsearch
        verify(mockPlayerHistorySearchRepository, times(1)).deleteById(playerHistory.getId());
    }

    @Test
    @Transactional
    public void searchPlayerHistory() throws Exception {
        // Initialize the database
        playerHistoryRepository.saveAndFlush(playerHistory);
        when(mockPlayerHistorySearchRepository.search(queryStringQuery("id:" + playerHistory.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(playerHistory), PageRequest.of(0, 1), 1));
        // Search the playerHistory
        restPlayerHistoryMockMvc.perform(get("/api/_search/player-histories?query=id:" + playerHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(playerHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].appearance").value(hasItem(DEFAULT_APPEARANCE.intValue())))
            .andExpect(jsonPath("$.[*].minutePlayed").value(hasItem(DEFAULT_MINUTE_PLAYED.intValue())))
            .andExpect(jsonPath("$.[*].goals").value(hasItem(DEFAULT_GOALS.intValue())))
            .andExpect(jsonPath("$.[*].assists").value(hasItem(DEFAULT_ASSISTS.intValue())))
            .andExpect(jsonPath("$.[*].averageRating").value(hasItem(DEFAULT_AVERAGE_RATING.intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlayerHistory.class);
        PlayerHistory playerHistory1 = new PlayerHistory();
        playerHistory1.setId(1L);
        PlayerHistory playerHistory2 = new PlayerHistory();
        playerHistory2.setId(playerHistory1.getId());
        assertThat(playerHistory1).isEqualTo(playerHistory2);
        playerHistory2.setId(2L);
        assertThat(playerHistory1).isNotEqualTo(playerHistory2);
        playerHistory1.setId(null);
        assertThat(playerHistory1).isNotEqualTo(playerHistory2);
    }
}
