package com.darfat.registats.web.rest;

import com.darfat.registats.RegistatsApp;
import com.darfat.registats.domain.CompetitionStanding;
import com.darfat.registats.repository.CompetitionStandingRepository;
import com.darfat.registats.repository.search.CompetitionStandingSearchRepository;
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
 * Integration tests for the {@link CompetitionStandingResource} REST controller.
 */
@SpringBootTest(classes = RegistatsApp.class)
public class CompetitionStandingResourceIT {

    private static final Integer DEFAULT_POSITION = 1;
    private static final Integer UPDATED_POSITION = 2;

    private static final Integer DEFAULT_PLAYED = 1;
    private static final Integer UPDATED_PLAYED = 2;

    private static final Integer DEFAULT_WIN = 1;
    private static final Integer UPDATED_WIN = 2;

    private static final Integer DEFAULT_DRAW = 1;
    private static final Integer UPDATED_DRAW = 2;

    private static final Integer DEFAULT_LOSE = 1;
    private static final Integer UPDATED_LOSE = 2;

    private static final Long DEFAULT_GOAL = 1L;
    private static final Long UPDATED_GOAL = 2L;

    private static final Long DEFAULT_GOAL_AGAINTS = 1L;
    private static final Long UPDATED_GOAL_AGAINTS = 2L;

    private static final Long DEFAULT_POINT = 1L;
    private static final Long UPDATED_POINT = 2L;

    private static final Long DEFAULT_MINUS_POINT = 1L;
    private static final Long UPDATED_MINUS_POINT = 2L;

    @Autowired
    private CompetitionStandingRepository competitionStandingRepository;

    /**
     * This repository is mocked in the com.darfat.registats.repository.search test package.
     *
     * @see com.darfat.registats.repository.search.CompetitionStandingSearchRepositoryMockConfiguration
     */
    @Autowired
    private CompetitionStandingSearchRepository mockCompetitionStandingSearchRepository;

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

    private MockMvc restCompetitionStandingMockMvc;

    private CompetitionStanding competitionStanding;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CompetitionStandingResource competitionStandingResource = new CompetitionStandingResource(competitionStandingRepository, mockCompetitionStandingSearchRepository);
        this.restCompetitionStandingMockMvc = MockMvcBuilders.standaloneSetup(competitionStandingResource)
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
    public static CompetitionStanding createEntity(EntityManager em) {
        CompetitionStanding competitionStanding = new CompetitionStanding()
            .position(DEFAULT_POSITION)
            .played(DEFAULT_PLAYED)
            .win(DEFAULT_WIN)
            .draw(DEFAULT_DRAW)
            .lose(DEFAULT_LOSE)
            .goal(DEFAULT_GOAL)
            .goalAgaints(DEFAULT_GOAL_AGAINTS)
            .point(DEFAULT_POINT)
            .minusPoint(DEFAULT_MINUS_POINT);
        return competitionStanding;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompetitionStanding createUpdatedEntity(EntityManager em) {
        CompetitionStanding competitionStanding = new CompetitionStanding()
            .position(UPDATED_POSITION)
            .played(UPDATED_PLAYED)
            .win(UPDATED_WIN)
            .draw(UPDATED_DRAW)
            .lose(UPDATED_LOSE)
            .goal(UPDATED_GOAL)
            .goalAgaints(UPDATED_GOAL_AGAINTS)
            .point(UPDATED_POINT)
            .minusPoint(UPDATED_MINUS_POINT);
        return competitionStanding;
    }

    @BeforeEach
    public void initTest() {
        competitionStanding = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompetitionStanding() throws Exception {
        int databaseSizeBeforeCreate = competitionStandingRepository.findAll().size();

        // Create the CompetitionStanding
        restCompetitionStandingMockMvc.perform(post("/api/competition-standings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(competitionStanding)))
            .andExpect(status().isCreated());

        // Validate the CompetitionStanding in the database
        List<CompetitionStanding> competitionStandingList = competitionStandingRepository.findAll();
        assertThat(competitionStandingList).hasSize(databaseSizeBeforeCreate + 1);
        CompetitionStanding testCompetitionStanding = competitionStandingList.get(competitionStandingList.size() - 1);
        assertThat(testCompetitionStanding.getPosition()).isEqualTo(DEFAULT_POSITION);
        assertThat(testCompetitionStanding.getPlayed()).isEqualTo(DEFAULT_PLAYED);
        assertThat(testCompetitionStanding.getWin()).isEqualTo(DEFAULT_WIN);
        assertThat(testCompetitionStanding.getDraw()).isEqualTo(DEFAULT_DRAW);
        assertThat(testCompetitionStanding.getLose()).isEqualTo(DEFAULT_LOSE);
        assertThat(testCompetitionStanding.getGoal()).isEqualTo(DEFAULT_GOAL);
        assertThat(testCompetitionStanding.getGoalAgaints()).isEqualTo(DEFAULT_GOAL_AGAINTS);
        assertThat(testCompetitionStanding.getPoint()).isEqualTo(DEFAULT_POINT);
        assertThat(testCompetitionStanding.getMinusPoint()).isEqualTo(DEFAULT_MINUS_POINT);

        // Validate the CompetitionStanding in Elasticsearch
        verify(mockCompetitionStandingSearchRepository, times(1)).save(testCompetitionStanding);
    }

    @Test
    @Transactional
    public void createCompetitionStandingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = competitionStandingRepository.findAll().size();

        // Create the CompetitionStanding with an existing ID
        competitionStanding.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompetitionStandingMockMvc.perform(post("/api/competition-standings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(competitionStanding)))
            .andExpect(status().isBadRequest());

        // Validate the CompetitionStanding in the database
        List<CompetitionStanding> competitionStandingList = competitionStandingRepository.findAll();
        assertThat(competitionStandingList).hasSize(databaseSizeBeforeCreate);

        // Validate the CompetitionStanding in Elasticsearch
        verify(mockCompetitionStandingSearchRepository, times(0)).save(competitionStanding);
    }


    @Test
    @Transactional
    public void getAllCompetitionStandings() throws Exception {
        // Initialize the database
        competitionStandingRepository.saveAndFlush(competitionStanding);

        // Get all the competitionStandingList
        restCompetitionStandingMockMvc.perform(get("/api/competition-standings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(competitionStanding.getId().intValue())))
            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION)))
            .andExpect(jsonPath("$.[*].played").value(hasItem(DEFAULT_PLAYED)))
            .andExpect(jsonPath("$.[*].win").value(hasItem(DEFAULT_WIN)))
            .andExpect(jsonPath("$.[*].draw").value(hasItem(DEFAULT_DRAW)))
            .andExpect(jsonPath("$.[*].lose").value(hasItem(DEFAULT_LOSE)))
            .andExpect(jsonPath("$.[*].goal").value(hasItem(DEFAULT_GOAL.intValue())))
            .andExpect(jsonPath("$.[*].goalAgaints").value(hasItem(DEFAULT_GOAL_AGAINTS.intValue())))
            .andExpect(jsonPath("$.[*].point").value(hasItem(DEFAULT_POINT.intValue())))
            .andExpect(jsonPath("$.[*].minusPoint").value(hasItem(DEFAULT_MINUS_POINT.intValue())));
    }
    
    @Test
    @Transactional
    public void getCompetitionStanding() throws Exception {
        // Initialize the database
        competitionStandingRepository.saveAndFlush(competitionStanding);

        // Get the competitionStanding
        restCompetitionStandingMockMvc.perform(get("/api/competition-standings/{id}", competitionStanding.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(competitionStanding.getId().intValue()))
            .andExpect(jsonPath("$.position").value(DEFAULT_POSITION))
            .andExpect(jsonPath("$.played").value(DEFAULT_PLAYED))
            .andExpect(jsonPath("$.win").value(DEFAULT_WIN))
            .andExpect(jsonPath("$.draw").value(DEFAULT_DRAW))
            .andExpect(jsonPath("$.lose").value(DEFAULT_LOSE))
            .andExpect(jsonPath("$.goal").value(DEFAULT_GOAL.intValue()))
            .andExpect(jsonPath("$.goalAgaints").value(DEFAULT_GOAL_AGAINTS.intValue()))
            .andExpect(jsonPath("$.point").value(DEFAULT_POINT.intValue()))
            .andExpect(jsonPath("$.minusPoint").value(DEFAULT_MINUS_POINT.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCompetitionStanding() throws Exception {
        // Get the competitionStanding
        restCompetitionStandingMockMvc.perform(get("/api/competition-standings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompetitionStanding() throws Exception {
        // Initialize the database
        competitionStandingRepository.saveAndFlush(competitionStanding);

        int databaseSizeBeforeUpdate = competitionStandingRepository.findAll().size();

        // Update the competitionStanding
        CompetitionStanding updatedCompetitionStanding = competitionStandingRepository.findById(competitionStanding.getId()).get();
        // Disconnect from session so that the updates on updatedCompetitionStanding are not directly saved in db
        em.detach(updatedCompetitionStanding);
        updatedCompetitionStanding
            .position(UPDATED_POSITION)
            .played(UPDATED_PLAYED)
            .win(UPDATED_WIN)
            .draw(UPDATED_DRAW)
            .lose(UPDATED_LOSE)
            .goal(UPDATED_GOAL)
            .goalAgaints(UPDATED_GOAL_AGAINTS)
            .point(UPDATED_POINT)
            .minusPoint(UPDATED_MINUS_POINT);

        restCompetitionStandingMockMvc.perform(put("/api/competition-standings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCompetitionStanding)))
            .andExpect(status().isOk());

        // Validate the CompetitionStanding in the database
        List<CompetitionStanding> competitionStandingList = competitionStandingRepository.findAll();
        assertThat(competitionStandingList).hasSize(databaseSizeBeforeUpdate);
        CompetitionStanding testCompetitionStanding = competitionStandingList.get(competitionStandingList.size() - 1);
        assertThat(testCompetitionStanding.getPosition()).isEqualTo(UPDATED_POSITION);
        assertThat(testCompetitionStanding.getPlayed()).isEqualTo(UPDATED_PLAYED);
        assertThat(testCompetitionStanding.getWin()).isEqualTo(UPDATED_WIN);
        assertThat(testCompetitionStanding.getDraw()).isEqualTo(UPDATED_DRAW);
        assertThat(testCompetitionStanding.getLose()).isEqualTo(UPDATED_LOSE);
        assertThat(testCompetitionStanding.getGoal()).isEqualTo(UPDATED_GOAL);
        assertThat(testCompetitionStanding.getGoalAgaints()).isEqualTo(UPDATED_GOAL_AGAINTS);
        assertThat(testCompetitionStanding.getPoint()).isEqualTo(UPDATED_POINT);
        assertThat(testCompetitionStanding.getMinusPoint()).isEqualTo(UPDATED_MINUS_POINT);

        // Validate the CompetitionStanding in Elasticsearch
        verify(mockCompetitionStandingSearchRepository, times(1)).save(testCompetitionStanding);
    }

    @Test
    @Transactional
    public void updateNonExistingCompetitionStanding() throws Exception {
        int databaseSizeBeforeUpdate = competitionStandingRepository.findAll().size();

        // Create the CompetitionStanding

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompetitionStandingMockMvc.perform(put("/api/competition-standings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(competitionStanding)))
            .andExpect(status().isBadRequest());

        // Validate the CompetitionStanding in the database
        List<CompetitionStanding> competitionStandingList = competitionStandingRepository.findAll();
        assertThat(competitionStandingList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CompetitionStanding in Elasticsearch
        verify(mockCompetitionStandingSearchRepository, times(0)).save(competitionStanding);
    }

    @Test
    @Transactional
    public void deleteCompetitionStanding() throws Exception {
        // Initialize the database
        competitionStandingRepository.saveAndFlush(competitionStanding);

        int databaseSizeBeforeDelete = competitionStandingRepository.findAll().size();

        // Delete the competitionStanding
        restCompetitionStandingMockMvc.perform(delete("/api/competition-standings/{id}", competitionStanding.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CompetitionStanding> competitionStandingList = competitionStandingRepository.findAll();
        assertThat(competitionStandingList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CompetitionStanding in Elasticsearch
        verify(mockCompetitionStandingSearchRepository, times(1)).deleteById(competitionStanding.getId());
    }

    @Test
    @Transactional
    public void searchCompetitionStanding() throws Exception {
        // Initialize the database
        competitionStandingRepository.saveAndFlush(competitionStanding);
        when(mockCompetitionStandingSearchRepository.search(queryStringQuery("id:" + competitionStanding.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(competitionStanding), PageRequest.of(0, 1), 1));
        // Search the competitionStanding
        restCompetitionStandingMockMvc.perform(get("/api/_search/competition-standings?query=id:" + competitionStanding.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(competitionStanding.getId().intValue())))
            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION)))
            .andExpect(jsonPath("$.[*].played").value(hasItem(DEFAULT_PLAYED)))
            .andExpect(jsonPath("$.[*].win").value(hasItem(DEFAULT_WIN)))
            .andExpect(jsonPath("$.[*].draw").value(hasItem(DEFAULT_DRAW)))
            .andExpect(jsonPath("$.[*].lose").value(hasItem(DEFAULT_LOSE)))
            .andExpect(jsonPath("$.[*].goal").value(hasItem(DEFAULT_GOAL.intValue())))
            .andExpect(jsonPath("$.[*].goalAgaints").value(hasItem(DEFAULT_GOAL_AGAINTS.intValue())))
            .andExpect(jsonPath("$.[*].point").value(hasItem(DEFAULT_POINT.intValue())))
            .andExpect(jsonPath("$.[*].minusPoint").value(hasItem(DEFAULT_MINUS_POINT.intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompetitionStanding.class);
        CompetitionStanding competitionStanding1 = new CompetitionStanding();
        competitionStanding1.setId(1L);
        CompetitionStanding competitionStanding2 = new CompetitionStanding();
        competitionStanding2.setId(competitionStanding1.getId());
        assertThat(competitionStanding1).isEqualTo(competitionStanding2);
        competitionStanding2.setId(2L);
        assertThat(competitionStanding1).isNotEqualTo(competitionStanding2);
        competitionStanding1.setId(null);
        assertThat(competitionStanding1).isNotEqualTo(competitionStanding2);
    }
}
