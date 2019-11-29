package com.darfat.registats.web.rest;

import com.darfat.registats.RegistatsApp;
import com.darfat.registats.domain.MatchLineup;
import com.darfat.registats.repository.MatchLineupRepository;
import com.darfat.registats.repository.search.MatchLineupSearchRepository;
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
 * Integration tests for the {@link MatchLineupResource} REST controller.
 */
@SpringBootTest(classes = RegistatsApp.class)
public class MatchLineupResourceIT {

    private static final Integer DEFAULT_NUMBER = 1;
    private static final Integer UPDATED_NUMBER = 2;

    private static final String DEFAULT_ROLE = "AAAAAAAAAA";
    private static final String UPDATED_ROLE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_FIRST_HALF_PLAY = false;
    private static final Boolean UPDATED_FIRST_HALF_PLAY = true;

    private static final Boolean DEFAULT_SECOND_HALF_PLAY = false;
    private static final Boolean UPDATED_SECOND_HALF_PLAY = true;

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Integer DEFAULT_MINUTE_IN = 1;
    private static final Integer UPDATED_MINUTE_IN = 2;

    private static final Integer DEFAULT_MINOTE_OUT = 1;
    private static final Integer UPDATED_MINOTE_OUT = 2;

    @Autowired
    private MatchLineupRepository matchLineupRepository;

    /**
     * This repository is mocked in the com.darfat.registats.repository.search test package.
     *
     * @see com.darfat.registats.repository.search.MatchLineupSearchRepositoryMockConfiguration
     */
    @Autowired
    private MatchLineupSearchRepository mockMatchLineupSearchRepository;

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

    private MockMvc restMatchLineupMockMvc;

    private MatchLineup matchLineup;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MatchLineupResource matchLineupResource = new MatchLineupResource(matchLineupRepository, mockMatchLineupSearchRepository);
        this.restMatchLineupMockMvc = MockMvcBuilders.standaloneSetup(matchLineupResource)
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
    public static MatchLineup createEntity(EntityManager em) {
        MatchLineup matchLineup = new MatchLineup()
            .number(DEFAULT_NUMBER)
            .role(DEFAULT_ROLE)
            .firstHalfPlay(DEFAULT_FIRST_HALF_PLAY)
            .secondHalfPlay(DEFAULT_SECOND_HALF_PLAY)
            .status(DEFAULT_STATUS)
            .minuteIn(DEFAULT_MINUTE_IN)
            .minoteOut(DEFAULT_MINOTE_OUT);
        return matchLineup;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MatchLineup createUpdatedEntity(EntityManager em) {
        MatchLineup matchLineup = new MatchLineup()
            .number(UPDATED_NUMBER)
            .role(UPDATED_ROLE)
            .firstHalfPlay(UPDATED_FIRST_HALF_PLAY)
            .secondHalfPlay(UPDATED_SECOND_HALF_PLAY)
            .status(UPDATED_STATUS)
            .minuteIn(UPDATED_MINUTE_IN)
            .minoteOut(UPDATED_MINOTE_OUT);
        return matchLineup;
    }

    @BeforeEach
    public void initTest() {
        matchLineup = createEntity(em);
    }

    @Test
    @Transactional
    public void createMatchLineup() throws Exception {
        int databaseSizeBeforeCreate = matchLineupRepository.findAll().size();

        // Create the MatchLineup
        restMatchLineupMockMvc.perform(post("/api/match-lineups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(matchLineup)))
            .andExpect(status().isCreated());

        // Validate the MatchLineup in the database
        List<MatchLineup> matchLineupList = matchLineupRepository.findAll();
        assertThat(matchLineupList).hasSize(databaseSizeBeforeCreate + 1);
        MatchLineup testMatchLineup = matchLineupList.get(matchLineupList.size() - 1);
        assertThat(testMatchLineup.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testMatchLineup.getRole()).isEqualTo(DEFAULT_ROLE);
        assertThat(testMatchLineup.isFirstHalfPlay()).isEqualTo(DEFAULT_FIRST_HALF_PLAY);
        assertThat(testMatchLineup.isSecondHalfPlay()).isEqualTo(DEFAULT_SECOND_HALF_PLAY);
        assertThat(testMatchLineup.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testMatchLineup.getMinuteIn()).isEqualTo(DEFAULT_MINUTE_IN);
        assertThat(testMatchLineup.getMinoteOut()).isEqualTo(DEFAULT_MINOTE_OUT);

        // Validate the MatchLineup in Elasticsearch
        verify(mockMatchLineupSearchRepository, times(1)).save(testMatchLineup);
    }

    @Test
    @Transactional
    public void createMatchLineupWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = matchLineupRepository.findAll().size();

        // Create the MatchLineup with an existing ID
        matchLineup.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMatchLineupMockMvc.perform(post("/api/match-lineups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(matchLineup)))
            .andExpect(status().isBadRequest());

        // Validate the MatchLineup in the database
        List<MatchLineup> matchLineupList = matchLineupRepository.findAll();
        assertThat(matchLineupList).hasSize(databaseSizeBeforeCreate);

        // Validate the MatchLineup in Elasticsearch
        verify(mockMatchLineupSearchRepository, times(0)).save(matchLineup);
    }


    @Test
    @Transactional
    public void getAllMatchLineups() throws Exception {
        // Initialize the database
        matchLineupRepository.saveAndFlush(matchLineup);

        // Get all the matchLineupList
        restMatchLineupMockMvc.perform(get("/api/match-lineups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(matchLineup.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)))
            .andExpect(jsonPath("$.[*].role").value(hasItem(DEFAULT_ROLE)))
            .andExpect(jsonPath("$.[*].firstHalfPlay").value(hasItem(DEFAULT_FIRST_HALF_PLAY.booleanValue())))
            .andExpect(jsonPath("$.[*].secondHalfPlay").value(hasItem(DEFAULT_SECOND_HALF_PLAY.booleanValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].minuteIn").value(hasItem(DEFAULT_MINUTE_IN)))
            .andExpect(jsonPath("$.[*].minoteOut").value(hasItem(DEFAULT_MINOTE_OUT)));
    }
    
    @Test
    @Transactional
    public void getMatchLineup() throws Exception {
        // Initialize the database
        matchLineupRepository.saveAndFlush(matchLineup);

        // Get the matchLineup
        restMatchLineupMockMvc.perform(get("/api/match-lineups/{id}", matchLineup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(matchLineup.getId().intValue()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER))
            .andExpect(jsonPath("$.role").value(DEFAULT_ROLE))
            .andExpect(jsonPath("$.firstHalfPlay").value(DEFAULT_FIRST_HALF_PLAY.booleanValue()))
            .andExpect(jsonPath("$.secondHalfPlay").value(DEFAULT_SECOND_HALF_PLAY.booleanValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.minuteIn").value(DEFAULT_MINUTE_IN))
            .andExpect(jsonPath("$.minoteOut").value(DEFAULT_MINOTE_OUT));
    }

    @Test
    @Transactional
    public void getNonExistingMatchLineup() throws Exception {
        // Get the matchLineup
        restMatchLineupMockMvc.perform(get("/api/match-lineups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMatchLineup() throws Exception {
        // Initialize the database
        matchLineupRepository.saveAndFlush(matchLineup);

        int databaseSizeBeforeUpdate = matchLineupRepository.findAll().size();

        // Update the matchLineup
        MatchLineup updatedMatchLineup = matchLineupRepository.findById(matchLineup.getId()).get();
        // Disconnect from session so that the updates on updatedMatchLineup are not directly saved in db
        em.detach(updatedMatchLineup);
        updatedMatchLineup
            .number(UPDATED_NUMBER)
            .role(UPDATED_ROLE)
            .firstHalfPlay(UPDATED_FIRST_HALF_PLAY)
            .secondHalfPlay(UPDATED_SECOND_HALF_PLAY)
            .status(UPDATED_STATUS)
            .minuteIn(UPDATED_MINUTE_IN)
            .minoteOut(UPDATED_MINOTE_OUT);

        restMatchLineupMockMvc.perform(put("/api/match-lineups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMatchLineup)))
            .andExpect(status().isOk());

        // Validate the MatchLineup in the database
        List<MatchLineup> matchLineupList = matchLineupRepository.findAll();
        assertThat(matchLineupList).hasSize(databaseSizeBeforeUpdate);
        MatchLineup testMatchLineup = matchLineupList.get(matchLineupList.size() - 1);
        assertThat(testMatchLineup.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testMatchLineup.getRole()).isEqualTo(UPDATED_ROLE);
        assertThat(testMatchLineup.isFirstHalfPlay()).isEqualTo(UPDATED_FIRST_HALF_PLAY);
        assertThat(testMatchLineup.isSecondHalfPlay()).isEqualTo(UPDATED_SECOND_HALF_PLAY);
        assertThat(testMatchLineup.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testMatchLineup.getMinuteIn()).isEqualTo(UPDATED_MINUTE_IN);
        assertThat(testMatchLineup.getMinoteOut()).isEqualTo(UPDATED_MINOTE_OUT);

        // Validate the MatchLineup in Elasticsearch
        verify(mockMatchLineupSearchRepository, times(1)).save(testMatchLineup);
    }

    @Test
    @Transactional
    public void updateNonExistingMatchLineup() throws Exception {
        int databaseSizeBeforeUpdate = matchLineupRepository.findAll().size();

        // Create the MatchLineup

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMatchLineupMockMvc.perform(put("/api/match-lineups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(matchLineup)))
            .andExpect(status().isBadRequest());

        // Validate the MatchLineup in the database
        List<MatchLineup> matchLineupList = matchLineupRepository.findAll();
        assertThat(matchLineupList).hasSize(databaseSizeBeforeUpdate);

        // Validate the MatchLineup in Elasticsearch
        verify(mockMatchLineupSearchRepository, times(0)).save(matchLineup);
    }

    @Test
    @Transactional
    public void deleteMatchLineup() throws Exception {
        // Initialize the database
        matchLineupRepository.saveAndFlush(matchLineup);

        int databaseSizeBeforeDelete = matchLineupRepository.findAll().size();

        // Delete the matchLineup
        restMatchLineupMockMvc.perform(delete("/api/match-lineups/{id}", matchLineup.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MatchLineup> matchLineupList = matchLineupRepository.findAll();
        assertThat(matchLineupList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the MatchLineup in Elasticsearch
        verify(mockMatchLineupSearchRepository, times(1)).deleteById(matchLineup.getId());
    }

    @Test
    @Transactional
    public void searchMatchLineup() throws Exception {
        // Initialize the database
        matchLineupRepository.saveAndFlush(matchLineup);
        when(mockMatchLineupSearchRepository.search(queryStringQuery("id:" + matchLineup.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(matchLineup), PageRequest.of(0, 1), 1));
        // Search the matchLineup
        restMatchLineupMockMvc.perform(get("/api/_search/match-lineups?query=id:" + matchLineup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(matchLineup.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)))
            .andExpect(jsonPath("$.[*].role").value(hasItem(DEFAULT_ROLE)))
            .andExpect(jsonPath("$.[*].firstHalfPlay").value(hasItem(DEFAULT_FIRST_HALF_PLAY.booleanValue())))
            .andExpect(jsonPath("$.[*].secondHalfPlay").value(hasItem(DEFAULT_SECOND_HALF_PLAY.booleanValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].minuteIn").value(hasItem(DEFAULT_MINUTE_IN)))
            .andExpect(jsonPath("$.[*].minoteOut").value(hasItem(DEFAULT_MINOTE_OUT)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MatchLineup.class);
        MatchLineup matchLineup1 = new MatchLineup();
        matchLineup1.setId(1L);
        MatchLineup matchLineup2 = new MatchLineup();
        matchLineup2.setId(matchLineup1.getId());
        assertThat(matchLineup1).isEqualTo(matchLineup2);
        matchLineup2.setId(2L);
        assertThat(matchLineup1).isNotEqualTo(matchLineup2);
        matchLineup1.setId(null);
        assertThat(matchLineup1).isNotEqualTo(matchLineup2);
    }
}
