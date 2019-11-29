package com.darfat.registats.web.rest;

import com.darfat.registats.RegistatsApp;
import com.darfat.registats.domain.TeamRegisteredPlayer;
import com.darfat.registats.repository.TeamRegisteredPlayerRepository;
import com.darfat.registats.repository.search.TeamRegisteredPlayerSearchRepository;
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
 * Integration tests for the {@link TeamRegisteredPlayerResource} REST controller.
 */
@SpringBootTest(classes = RegistatsApp.class)
public class TeamRegisteredPlayerResourceIT {

    private static final Long DEFAULT_NUMBER = 1L;
    private static final Long UPDATED_NUMBER = 2L;

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private TeamRegisteredPlayerRepository teamRegisteredPlayerRepository;

    /**
     * This repository is mocked in the com.darfat.registats.repository.search test package.
     *
     * @see com.darfat.registats.repository.search.TeamRegisteredPlayerSearchRepositoryMockConfiguration
     */
    @Autowired
    private TeamRegisteredPlayerSearchRepository mockTeamRegisteredPlayerSearchRepository;

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

    private MockMvc restTeamRegisteredPlayerMockMvc;

    private TeamRegisteredPlayer teamRegisteredPlayer;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TeamRegisteredPlayerResource teamRegisteredPlayerResource = new TeamRegisteredPlayerResource(teamRegisteredPlayerRepository, mockTeamRegisteredPlayerSearchRepository);
        this.restTeamRegisteredPlayerMockMvc = MockMvcBuilders.standaloneSetup(teamRegisteredPlayerResource)
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
    public static TeamRegisteredPlayer createEntity(EntityManager em) {
        TeamRegisteredPlayer teamRegisteredPlayer = new TeamRegisteredPlayer()
            .number(DEFAULT_NUMBER)
            .status(DEFAULT_STATUS)
            .active(DEFAULT_ACTIVE);
        return teamRegisteredPlayer;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TeamRegisteredPlayer createUpdatedEntity(EntityManager em) {
        TeamRegisteredPlayer teamRegisteredPlayer = new TeamRegisteredPlayer()
            .number(UPDATED_NUMBER)
            .status(UPDATED_STATUS)
            .active(UPDATED_ACTIVE);
        return teamRegisteredPlayer;
    }

    @BeforeEach
    public void initTest() {
        teamRegisteredPlayer = createEntity(em);
    }

    @Test
    @Transactional
    public void createTeamRegisteredPlayer() throws Exception {
        int databaseSizeBeforeCreate = teamRegisteredPlayerRepository.findAll().size();

        // Create the TeamRegisteredPlayer
        restTeamRegisteredPlayerMockMvc.perform(post("/api/team-registered-players")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(teamRegisteredPlayer)))
            .andExpect(status().isCreated());

        // Validate the TeamRegisteredPlayer in the database
        List<TeamRegisteredPlayer> teamRegisteredPlayerList = teamRegisteredPlayerRepository.findAll();
        assertThat(teamRegisteredPlayerList).hasSize(databaseSizeBeforeCreate + 1);
        TeamRegisteredPlayer testTeamRegisteredPlayer = teamRegisteredPlayerList.get(teamRegisteredPlayerList.size() - 1);
        assertThat(testTeamRegisteredPlayer.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testTeamRegisteredPlayer.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testTeamRegisteredPlayer.isActive()).isEqualTo(DEFAULT_ACTIVE);

        // Validate the TeamRegisteredPlayer in Elasticsearch
        verify(mockTeamRegisteredPlayerSearchRepository, times(1)).save(testTeamRegisteredPlayer);
    }

    @Test
    @Transactional
    public void createTeamRegisteredPlayerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = teamRegisteredPlayerRepository.findAll().size();

        // Create the TeamRegisteredPlayer with an existing ID
        teamRegisteredPlayer.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTeamRegisteredPlayerMockMvc.perform(post("/api/team-registered-players")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(teamRegisteredPlayer)))
            .andExpect(status().isBadRequest());

        // Validate the TeamRegisteredPlayer in the database
        List<TeamRegisteredPlayer> teamRegisteredPlayerList = teamRegisteredPlayerRepository.findAll();
        assertThat(teamRegisteredPlayerList).hasSize(databaseSizeBeforeCreate);

        // Validate the TeamRegisteredPlayer in Elasticsearch
        verify(mockTeamRegisteredPlayerSearchRepository, times(0)).save(teamRegisteredPlayer);
    }


    @Test
    @Transactional
    public void getAllTeamRegisteredPlayers() throws Exception {
        // Initialize the database
        teamRegisteredPlayerRepository.saveAndFlush(teamRegisteredPlayer);

        // Get all the teamRegisteredPlayerList
        restTeamRegisteredPlayerMockMvc.perform(get("/api/team-registered-players?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(teamRegisteredPlayer.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getTeamRegisteredPlayer() throws Exception {
        // Initialize the database
        teamRegisteredPlayerRepository.saveAndFlush(teamRegisteredPlayer);

        // Get the teamRegisteredPlayer
        restTeamRegisteredPlayerMockMvc.perform(get("/api/team-registered-players/{id}", teamRegisteredPlayer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(teamRegisteredPlayer.getId().intValue()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER.intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTeamRegisteredPlayer() throws Exception {
        // Get the teamRegisteredPlayer
        restTeamRegisteredPlayerMockMvc.perform(get("/api/team-registered-players/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTeamRegisteredPlayer() throws Exception {
        // Initialize the database
        teamRegisteredPlayerRepository.saveAndFlush(teamRegisteredPlayer);

        int databaseSizeBeforeUpdate = teamRegisteredPlayerRepository.findAll().size();

        // Update the teamRegisteredPlayer
        TeamRegisteredPlayer updatedTeamRegisteredPlayer = teamRegisteredPlayerRepository.findById(teamRegisteredPlayer.getId()).get();
        // Disconnect from session so that the updates on updatedTeamRegisteredPlayer are not directly saved in db
        em.detach(updatedTeamRegisteredPlayer);
        updatedTeamRegisteredPlayer
            .number(UPDATED_NUMBER)
            .status(UPDATED_STATUS)
            .active(UPDATED_ACTIVE);

        restTeamRegisteredPlayerMockMvc.perform(put("/api/team-registered-players")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTeamRegisteredPlayer)))
            .andExpect(status().isOk());

        // Validate the TeamRegisteredPlayer in the database
        List<TeamRegisteredPlayer> teamRegisteredPlayerList = teamRegisteredPlayerRepository.findAll();
        assertThat(teamRegisteredPlayerList).hasSize(databaseSizeBeforeUpdate);
        TeamRegisteredPlayer testTeamRegisteredPlayer = teamRegisteredPlayerList.get(teamRegisteredPlayerList.size() - 1);
        assertThat(testTeamRegisteredPlayer.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testTeamRegisteredPlayer.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTeamRegisteredPlayer.isActive()).isEqualTo(UPDATED_ACTIVE);

        // Validate the TeamRegisteredPlayer in Elasticsearch
        verify(mockTeamRegisteredPlayerSearchRepository, times(1)).save(testTeamRegisteredPlayer);
    }

    @Test
    @Transactional
    public void updateNonExistingTeamRegisteredPlayer() throws Exception {
        int databaseSizeBeforeUpdate = teamRegisteredPlayerRepository.findAll().size();

        // Create the TeamRegisteredPlayer

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTeamRegisteredPlayerMockMvc.perform(put("/api/team-registered-players")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(teamRegisteredPlayer)))
            .andExpect(status().isBadRequest());

        // Validate the TeamRegisteredPlayer in the database
        List<TeamRegisteredPlayer> teamRegisteredPlayerList = teamRegisteredPlayerRepository.findAll();
        assertThat(teamRegisteredPlayerList).hasSize(databaseSizeBeforeUpdate);

        // Validate the TeamRegisteredPlayer in Elasticsearch
        verify(mockTeamRegisteredPlayerSearchRepository, times(0)).save(teamRegisteredPlayer);
    }

    @Test
    @Transactional
    public void deleteTeamRegisteredPlayer() throws Exception {
        // Initialize the database
        teamRegisteredPlayerRepository.saveAndFlush(teamRegisteredPlayer);

        int databaseSizeBeforeDelete = teamRegisteredPlayerRepository.findAll().size();

        // Delete the teamRegisteredPlayer
        restTeamRegisteredPlayerMockMvc.perform(delete("/api/team-registered-players/{id}", teamRegisteredPlayer.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TeamRegisteredPlayer> teamRegisteredPlayerList = teamRegisteredPlayerRepository.findAll();
        assertThat(teamRegisteredPlayerList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the TeamRegisteredPlayer in Elasticsearch
        verify(mockTeamRegisteredPlayerSearchRepository, times(1)).deleteById(teamRegisteredPlayer.getId());
    }

    @Test
    @Transactional
    public void searchTeamRegisteredPlayer() throws Exception {
        // Initialize the database
        teamRegisteredPlayerRepository.saveAndFlush(teamRegisteredPlayer);
        when(mockTeamRegisteredPlayerSearchRepository.search(queryStringQuery("id:" + teamRegisteredPlayer.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(teamRegisteredPlayer), PageRequest.of(0, 1), 1));
        // Search the teamRegisteredPlayer
        restTeamRegisteredPlayerMockMvc.perform(get("/api/_search/team-registered-players?query=id:" + teamRegisteredPlayer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(teamRegisteredPlayer.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TeamRegisteredPlayer.class);
        TeamRegisteredPlayer teamRegisteredPlayer1 = new TeamRegisteredPlayer();
        teamRegisteredPlayer1.setId(1L);
        TeamRegisteredPlayer teamRegisteredPlayer2 = new TeamRegisteredPlayer();
        teamRegisteredPlayer2.setId(teamRegisteredPlayer1.getId());
        assertThat(teamRegisteredPlayer1).isEqualTo(teamRegisteredPlayer2);
        teamRegisteredPlayer2.setId(2L);
        assertThat(teamRegisteredPlayer1).isNotEqualTo(teamRegisteredPlayer2);
        teamRegisteredPlayer1.setId(null);
        assertThat(teamRegisteredPlayer1).isNotEqualTo(teamRegisteredPlayer2);
    }
}
