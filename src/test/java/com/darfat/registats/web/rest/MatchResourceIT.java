package com.darfat.registats.web.rest;

import com.darfat.registats.RegistatsApp;
import com.darfat.registats.domain.Match;
import com.darfat.registats.repository.MatchRepository;
import com.darfat.registats.repository.search.MatchSearchRepository;
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
 * Integration tests for the {@link MatchResource} REST controller.
 */
@SpringBootTest(classes = RegistatsApp.class)
public class MatchResourceIT {

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_REFREE = "AAAAAAAAAA";
    private static final String UPDATED_REFREE = "BBBBBBBBBB";

    private static final String DEFAULT_LIVE_STREAM_URL = "AAAAAAAAAA";
    private static final String UPDATED_LIVE_STREAM_URL = "BBBBBBBBBB";

    private static final String DEFAULT_WHEATER = "AAAAAAAAAA";
    private static final String UPDATED_WHEATER = "BBBBBBBBBB";

    private static final String DEFAULT_WIND = "AAAAAAAAAA";
    private static final String UPDATED_WIND = "BBBBBBBBBB";

    private static final String DEFAULT_ANALYSIS = "AAAAAAAAAA";
    private static final String UPDATED_ANALYSIS = "BBBBBBBBBB";

    private static final String DEFAULT_PRE_MATCH_TALK = "AAAAAAAAAA";
    private static final String UPDATED_PRE_MATCH_TALK = "BBBBBBBBBB";

    private static final String DEFAULT_POST_MATCH_TALK = "AAAAAAAAAA";
    private static final String UPDATED_POST_MATCH_TALK = "BBBBBBBBBB";

    @Autowired
    private MatchRepository matchRepository;

    /**
     * This repository is mocked in the com.darfat.registats.repository.search test package.
     *
     * @see com.darfat.registats.repository.search.MatchSearchRepositoryMockConfiguration
     */
    @Autowired
    private MatchSearchRepository mockMatchSearchRepository;

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

    private MockMvc restMatchMockMvc;

    private Match match;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MatchResource matchResource = new MatchResource(matchRepository, mockMatchSearchRepository);
        this.restMatchMockMvc = MockMvcBuilders.standaloneSetup(matchResource)
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
    public static Match createEntity(EntityManager em) {
        Match match = new Match()
            .date(DEFAULT_DATE)
            .time(DEFAULT_TIME)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .refree(DEFAULT_REFREE)
            .liveStreamUrl(DEFAULT_LIVE_STREAM_URL)
            .wheater(DEFAULT_WHEATER)
            .wind(DEFAULT_WIND)
            .analysis(DEFAULT_ANALYSIS)
            .preMatchTalk(DEFAULT_PRE_MATCH_TALK)
            .postMatchTalk(DEFAULT_POST_MATCH_TALK);
        return match;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Match createUpdatedEntity(EntityManager em) {
        Match match = new Match()
            .date(UPDATED_DATE)
            .time(UPDATED_TIME)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .refree(UPDATED_REFREE)
            .liveStreamUrl(UPDATED_LIVE_STREAM_URL)
            .wheater(UPDATED_WHEATER)
            .wind(UPDATED_WIND)
            .analysis(UPDATED_ANALYSIS)
            .preMatchTalk(UPDATED_PRE_MATCH_TALK)
            .postMatchTalk(UPDATED_POST_MATCH_TALK);
        return match;
    }

    @BeforeEach
    public void initTest() {
        match = createEntity(em);
    }

    @Test
    @Transactional
    public void createMatch() throws Exception {
        int databaseSizeBeforeCreate = matchRepository.findAll().size();

        // Create the Match
        restMatchMockMvc.perform(post("/api/matches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(match)))
            .andExpect(status().isCreated());

        // Validate the Match in the database
        List<Match> matchList = matchRepository.findAll();
        assertThat(matchList).hasSize(databaseSizeBeforeCreate + 1);
        Match testMatch = matchList.get(matchList.size() - 1);
        assertThat(testMatch.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testMatch.getTime()).isEqualTo(DEFAULT_TIME);
        assertThat(testMatch.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMatch.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testMatch.getRefree()).isEqualTo(DEFAULT_REFREE);
        assertThat(testMatch.getLiveStreamUrl()).isEqualTo(DEFAULT_LIVE_STREAM_URL);
        assertThat(testMatch.getWheater()).isEqualTo(DEFAULT_WHEATER);
        assertThat(testMatch.getWind()).isEqualTo(DEFAULT_WIND);
        assertThat(testMatch.getAnalysis()).isEqualTo(DEFAULT_ANALYSIS);
        assertThat(testMatch.getPreMatchTalk()).isEqualTo(DEFAULT_PRE_MATCH_TALK);
        assertThat(testMatch.getPostMatchTalk()).isEqualTo(DEFAULT_POST_MATCH_TALK);

        // Validate the Match in Elasticsearch
        verify(mockMatchSearchRepository, times(1)).save(testMatch);
    }

    @Test
    @Transactional
    public void createMatchWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = matchRepository.findAll().size();

        // Create the Match with an existing ID
        match.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMatchMockMvc.perform(post("/api/matches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(match)))
            .andExpect(status().isBadRequest());

        // Validate the Match in the database
        List<Match> matchList = matchRepository.findAll();
        assertThat(matchList).hasSize(databaseSizeBeforeCreate);

        // Validate the Match in Elasticsearch
        verify(mockMatchSearchRepository, times(0)).save(match);
    }


    @Test
    @Transactional
    public void getAllMatches() throws Exception {
        // Initialize the database
        matchRepository.saveAndFlush(match);

        // Get all the matchList
        restMatchMockMvc.perform(get("/api/matches?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(match.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].time").value(hasItem(DEFAULT_TIME.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].refree").value(hasItem(DEFAULT_REFREE)))
            .andExpect(jsonPath("$.[*].liveStreamUrl").value(hasItem(DEFAULT_LIVE_STREAM_URL)))
            .andExpect(jsonPath("$.[*].wheater").value(hasItem(DEFAULT_WHEATER)))
            .andExpect(jsonPath("$.[*].wind").value(hasItem(DEFAULT_WIND)))
            .andExpect(jsonPath("$.[*].analysis").value(hasItem(DEFAULT_ANALYSIS)))
            .andExpect(jsonPath("$.[*].preMatchTalk").value(hasItem(DEFAULT_PRE_MATCH_TALK)))
            .andExpect(jsonPath("$.[*].postMatchTalk").value(hasItem(DEFAULT_POST_MATCH_TALK)));
    }
    
    @Test
    @Transactional
    public void getMatch() throws Exception {
        // Initialize the database
        matchRepository.saveAndFlush(match);

        // Get the match
        restMatchMockMvc.perform(get("/api/matches/{id}", match.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(match.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.time").value(DEFAULT_TIME.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.refree").value(DEFAULT_REFREE))
            .andExpect(jsonPath("$.liveStreamUrl").value(DEFAULT_LIVE_STREAM_URL))
            .andExpect(jsonPath("$.wheater").value(DEFAULT_WHEATER))
            .andExpect(jsonPath("$.wind").value(DEFAULT_WIND))
            .andExpect(jsonPath("$.analysis").value(DEFAULT_ANALYSIS))
            .andExpect(jsonPath("$.preMatchTalk").value(DEFAULT_PRE_MATCH_TALK))
            .andExpect(jsonPath("$.postMatchTalk").value(DEFAULT_POST_MATCH_TALK));
    }

    @Test
    @Transactional
    public void getNonExistingMatch() throws Exception {
        // Get the match
        restMatchMockMvc.perform(get("/api/matches/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMatch() throws Exception {
        // Initialize the database
        matchRepository.saveAndFlush(match);

        int databaseSizeBeforeUpdate = matchRepository.findAll().size();

        // Update the match
        Match updatedMatch = matchRepository.findById(match.getId()).get();
        // Disconnect from session so that the updates on updatedMatch are not directly saved in db
        em.detach(updatedMatch);
        updatedMatch
            .date(UPDATED_DATE)
            .time(UPDATED_TIME)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .refree(UPDATED_REFREE)
            .liveStreamUrl(UPDATED_LIVE_STREAM_URL)
            .wheater(UPDATED_WHEATER)
            .wind(UPDATED_WIND)
            .analysis(UPDATED_ANALYSIS)
            .preMatchTalk(UPDATED_PRE_MATCH_TALK)
            .postMatchTalk(UPDATED_POST_MATCH_TALK);

        restMatchMockMvc.perform(put("/api/matches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMatch)))
            .andExpect(status().isOk());

        // Validate the Match in the database
        List<Match> matchList = matchRepository.findAll();
        assertThat(matchList).hasSize(databaseSizeBeforeUpdate);
        Match testMatch = matchList.get(matchList.size() - 1);
        assertThat(testMatch.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testMatch.getTime()).isEqualTo(UPDATED_TIME);
        assertThat(testMatch.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMatch.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMatch.getRefree()).isEqualTo(UPDATED_REFREE);
        assertThat(testMatch.getLiveStreamUrl()).isEqualTo(UPDATED_LIVE_STREAM_URL);
        assertThat(testMatch.getWheater()).isEqualTo(UPDATED_WHEATER);
        assertThat(testMatch.getWind()).isEqualTo(UPDATED_WIND);
        assertThat(testMatch.getAnalysis()).isEqualTo(UPDATED_ANALYSIS);
        assertThat(testMatch.getPreMatchTalk()).isEqualTo(UPDATED_PRE_MATCH_TALK);
        assertThat(testMatch.getPostMatchTalk()).isEqualTo(UPDATED_POST_MATCH_TALK);

        // Validate the Match in Elasticsearch
        verify(mockMatchSearchRepository, times(1)).save(testMatch);
    }

    @Test
    @Transactional
    public void updateNonExistingMatch() throws Exception {
        int databaseSizeBeforeUpdate = matchRepository.findAll().size();

        // Create the Match

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMatchMockMvc.perform(put("/api/matches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(match)))
            .andExpect(status().isBadRequest());

        // Validate the Match in the database
        List<Match> matchList = matchRepository.findAll();
        assertThat(matchList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Match in Elasticsearch
        verify(mockMatchSearchRepository, times(0)).save(match);
    }

    @Test
    @Transactional
    public void deleteMatch() throws Exception {
        // Initialize the database
        matchRepository.saveAndFlush(match);

        int databaseSizeBeforeDelete = matchRepository.findAll().size();

        // Delete the match
        restMatchMockMvc.perform(delete("/api/matches/{id}", match.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Match> matchList = matchRepository.findAll();
        assertThat(matchList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Match in Elasticsearch
        verify(mockMatchSearchRepository, times(1)).deleteById(match.getId());
    }

    @Test
    @Transactional
    public void searchMatch() throws Exception {
        // Initialize the database
        matchRepository.saveAndFlush(match);
        when(mockMatchSearchRepository.search(queryStringQuery("id:" + match.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(match), PageRequest.of(0, 1), 1));
        // Search the match
        restMatchMockMvc.perform(get("/api/_search/matches?query=id:" + match.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(match.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].time").value(hasItem(DEFAULT_TIME.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].refree").value(hasItem(DEFAULT_REFREE)))
            .andExpect(jsonPath("$.[*].liveStreamUrl").value(hasItem(DEFAULT_LIVE_STREAM_URL)))
            .andExpect(jsonPath("$.[*].wheater").value(hasItem(DEFAULT_WHEATER)))
            .andExpect(jsonPath("$.[*].wind").value(hasItem(DEFAULT_WIND)))
            .andExpect(jsonPath("$.[*].analysis").value(hasItem(DEFAULT_ANALYSIS)))
            .andExpect(jsonPath("$.[*].preMatchTalk").value(hasItem(DEFAULT_PRE_MATCH_TALK)))
            .andExpect(jsonPath("$.[*].postMatchTalk").value(hasItem(DEFAULT_POST_MATCH_TALK)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Match.class);
        Match match1 = new Match();
        match1.setId(1L);
        Match match2 = new Match();
        match2.setId(match1.getId());
        assertThat(match1).isEqualTo(match2);
        match2.setId(2L);
        assertThat(match1).isNotEqualTo(match2);
        match1.setId(null);
        assertThat(match1).isNotEqualTo(match2);
    }
}
