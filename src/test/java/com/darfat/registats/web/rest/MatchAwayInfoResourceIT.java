package com.darfat.registats.web.rest;

import com.darfat.registats.RegistatsApp;
import com.darfat.registats.domain.MatchAwayInfo;
import com.darfat.registats.repository.MatchAwayInfoRepository;
import com.darfat.registats.repository.search.MatchAwayInfoSearchRepository;
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

import com.darfat.registats.domain.enumeration.Formation;
/**
 * Integration tests for the {@link MatchAwayInfoResource} REST controller.
 */
@SpringBootTest(classes = RegistatsApp.class)
public class MatchAwayInfoResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Formation DEFAULT_FORMATION = Formation.FOURFOURTWO;
    private static final Formation UPDATED_FORMATION = Formation.FOURONETWOTHREE;

    private static final String DEFAULT_ANALYSIS = "AAAAAAAAAA";
    private static final String UPDATED_ANALYSIS = "BBBBBBBBBB";

    private static final String DEFAULT_PRE_MATCH_TALK = "AAAAAAAAAA";
    private static final String UPDATED_PRE_MATCH_TALK = "BBBBBBBBBB";

    private static final String DEFAULT_POST_MATCH_TALK = "AAAAAAAAAA";
    private static final String UPDATED_POST_MATCH_TALK = "BBBBBBBBBB";

    @Autowired
    private MatchAwayInfoRepository matchAwayInfoRepository;

    /**
     * This repository is mocked in the com.darfat.registats.repository.search test package.
     *
     * @see com.darfat.registats.repository.search.MatchAwayInfoSearchRepositoryMockConfiguration
     */
    @Autowired
    private MatchAwayInfoSearchRepository mockMatchAwayInfoSearchRepository;

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

    private MockMvc restMatchAwayInfoMockMvc;

    private MatchAwayInfo matchAwayInfo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MatchAwayInfoResource matchAwayInfoResource = new MatchAwayInfoResource(matchAwayInfoRepository, mockMatchAwayInfoSearchRepository);
        this.restMatchAwayInfoMockMvc = MockMvcBuilders.standaloneSetup(matchAwayInfoResource)
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
    public static MatchAwayInfo createEntity(EntityManager em) {
        MatchAwayInfo matchAwayInfo = new MatchAwayInfo()
            .description(DEFAULT_DESCRIPTION)
            .formation(DEFAULT_FORMATION)
            .analysis(DEFAULT_ANALYSIS)
            .preMatchTalk(DEFAULT_PRE_MATCH_TALK)
            .postMatchTalk(DEFAULT_POST_MATCH_TALK);
        return matchAwayInfo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MatchAwayInfo createUpdatedEntity(EntityManager em) {
        MatchAwayInfo matchAwayInfo = new MatchAwayInfo()
            .description(UPDATED_DESCRIPTION)
            .formation(UPDATED_FORMATION)
            .analysis(UPDATED_ANALYSIS)
            .preMatchTalk(UPDATED_PRE_MATCH_TALK)
            .postMatchTalk(UPDATED_POST_MATCH_TALK);
        return matchAwayInfo;
    }

    @BeforeEach
    public void initTest() {
        matchAwayInfo = createEntity(em);
    }

    @Test
    @Transactional
    public void createMatchAwayInfo() throws Exception {
        int databaseSizeBeforeCreate = matchAwayInfoRepository.findAll().size();

        // Create the MatchAwayInfo
        restMatchAwayInfoMockMvc.perform(post("/api/match-away-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(matchAwayInfo)))
            .andExpect(status().isCreated());

        // Validate the MatchAwayInfo in the database
        List<MatchAwayInfo> matchAwayInfoList = matchAwayInfoRepository.findAll();
        assertThat(matchAwayInfoList).hasSize(databaseSizeBeforeCreate + 1);
        MatchAwayInfo testMatchAwayInfo = matchAwayInfoList.get(matchAwayInfoList.size() - 1);
        assertThat(testMatchAwayInfo.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testMatchAwayInfo.getFormation()).isEqualTo(DEFAULT_FORMATION);
        assertThat(testMatchAwayInfo.getAnalysis()).isEqualTo(DEFAULT_ANALYSIS);
        assertThat(testMatchAwayInfo.getPreMatchTalk()).isEqualTo(DEFAULT_PRE_MATCH_TALK);
        assertThat(testMatchAwayInfo.getPostMatchTalk()).isEqualTo(DEFAULT_POST_MATCH_TALK);

        // Validate the MatchAwayInfo in Elasticsearch
        verify(mockMatchAwayInfoSearchRepository, times(1)).save(testMatchAwayInfo);
    }

    @Test
    @Transactional
    public void createMatchAwayInfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = matchAwayInfoRepository.findAll().size();

        // Create the MatchAwayInfo with an existing ID
        matchAwayInfo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMatchAwayInfoMockMvc.perform(post("/api/match-away-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(matchAwayInfo)))
            .andExpect(status().isBadRequest());

        // Validate the MatchAwayInfo in the database
        List<MatchAwayInfo> matchAwayInfoList = matchAwayInfoRepository.findAll();
        assertThat(matchAwayInfoList).hasSize(databaseSizeBeforeCreate);

        // Validate the MatchAwayInfo in Elasticsearch
        verify(mockMatchAwayInfoSearchRepository, times(0)).save(matchAwayInfo);
    }


    @Test
    @Transactional
    public void getAllMatchAwayInfos() throws Exception {
        // Initialize the database
        matchAwayInfoRepository.saveAndFlush(matchAwayInfo);

        // Get all the matchAwayInfoList
        restMatchAwayInfoMockMvc.perform(get("/api/match-away-infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(matchAwayInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].formation").value(hasItem(DEFAULT_FORMATION.toString())))
            .andExpect(jsonPath("$.[*].analysis").value(hasItem(DEFAULT_ANALYSIS)))
            .andExpect(jsonPath("$.[*].preMatchTalk").value(hasItem(DEFAULT_PRE_MATCH_TALK)))
            .andExpect(jsonPath("$.[*].postMatchTalk").value(hasItem(DEFAULT_POST_MATCH_TALK)));
    }
    
    @Test
    @Transactional
    public void getMatchAwayInfo() throws Exception {
        // Initialize the database
        matchAwayInfoRepository.saveAndFlush(matchAwayInfo);

        // Get the matchAwayInfo
        restMatchAwayInfoMockMvc.perform(get("/api/match-away-infos/{id}", matchAwayInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(matchAwayInfo.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.formation").value(DEFAULT_FORMATION.toString()))
            .andExpect(jsonPath("$.analysis").value(DEFAULT_ANALYSIS))
            .andExpect(jsonPath("$.preMatchTalk").value(DEFAULT_PRE_MATCH_TALK))
            .andExpect(jsonPath("$.postMatchTalk").value(DEFAULT_POST_MATCH_TALK));
    }

    @Test
    @Transactional
    public void getNonExistingMatchAwayInfo() throws Exception {
        // Get the matchAwayInfo
        restMatchAwayInfoMockMvc.perform(get("/api/match-away-infos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMatchAwayInfo() throws Exception {
        // Initialize the database
        matchAwayInfoRepository.saveAndFlush(matchAwayInfo);

        int databaseSizeBeforeUpdate = matchAwayInfoRepository.findAll().size();

        // Update the matchAwayInfo
        MatchAwayInfo updatedMatchAwayInfo = matchAwayInfoRepository.findById(matchAwayInfo.getId()).get();
        // Disconnect from session so that the updates on updatedMatchAwayInfo are not directly saved in db
        em.detach(updatedMatchAwayInfo);
        updatedMatchAwayInfo
            .description(UPDATED_DESCRIPTION)
            .formation(UPDATED_FORMATION)
            .analysis(UPDATED_ANALYSIS)
            .preMatchTalk(UPDATED_PRE_MATCH_TALK)
            .postMatchTalk(UPDATED_POST_MATCH_TALK);

        restMatchAwayInfoMockMvc.perform(put("/api/match-away-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMatchAwayInfo)))
            .andExpect(status().isOk());

        // Validate the MatchAwayInfo in the database
        List<MatchAwayInfo> matchAwayInfoList = matchAwayInfoRepository.findAll();
        assertThat(matchAwayInfoList).hasSize(databaseSizeBeforeUpdate);
        MatchAwayInfo testMatchAwayInfo = matchAwayInfoList.get(matchAwayInfoList.size() - 1);
        assertThat(testMatchAwayInfo.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMatchAwayInfo.getFormation()).isEqualTo(UPDATED_FORMATION);
        assertThat(testMatchAwayInfo.getAnalysis()).isEqualTo(UPDATED_ANALYSIS);
        assertThat(testMatchAwayInfo.getPreMatchTalk()).isEqualTo(UPDATED_PRE_MATCH_TALK);
        assertThat(testMatchAwayInfo.getPostMatchTalk()).isEqualTo(UPDATED_POST_MATCH_TALK);

        // Validate the MatchAwayInfo in Elasticsearch
        verify(mockMatchAwayInfoSearchRepository, times(1)).save(testMatchAwayInfo);
    }

    @Test
    @Transactional
    public void updateNonExistingMatchAwayInfo() throws Exception {
        int databaseSizeBeforeUpdate = matchAwayInfoRepository.findAll().size();

        // Create the MatchAwayInfo

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMatchAwayInfoMockMvc.perform(put("/api/match-away-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(matchAwayInfo)))
            .andExpect(status().isBadRequest());

        // Validate the MatchAwayInfo in the database
        List<MatchAwayInfo> matchAwayInfoList = matchAwayInfoRepository.findAll();
        assertThat(matchAwayInfoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the MatchAwayInfo in Elasticsearch
        verify(mockMatchAwayInfoSearchRepository, times(0)).save(matchAwayInfo);
    }

    @Test
    @Transactional
    public void deleteMatchAwayInfo() throws Exception {
        // Initialize the database
        matchAwayInfoRepository.saveAndFlush(matchAwayInfo);

        int databaseSizeBeforeDelete = matchAwayInfoRepository.findAll().size();

        // Delete the matchAwayInfo
        restMatchAwayInfoMockMvc.perform(delete("/api/match-away-infos/{id}", matchAwayInfo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MatchAwayInfo> matchAwayInfoList = matchAwayInfoRepository.findAll();
        assertThat(matchAwayInfoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the MatchAwayInfo in Elasticsearch
        verify(mockMatchAwayInfoSearchRepository, times(1)).deleteById(matchAwayInfo.getId());
    }

    @Test
    @Transactional
    public void searchMatchAwayInfo() throws Exception {
        // Initialize the database
        matchAwayInfoRepository.saveAndFlush(matchAwayInfo);
        when(mockMatchAwayInfoSearchRepository.search(queryStringQuery("id:" + matchAwayInfo.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(matchAwayInfo), PageRequest.of(0, 1), 1));
        // Search the matchAwayInfo
        restMatchAwayInfoMockMvc.perform(get("/api/_search/match-away-infos?query=id:" + matchAwayInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(matchAwayInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].formation").value(hasItem(DEFAULT_FORMATION.toString())))
            .andExpect(jsonPath("$.[*].analysis").value(hasItem(DEFAULT_ANALYSIS)))
            .andExpect(jsonPath("$.[*].preMatchTalk").value(hasItem(DEFAULT_PRE_MATCH_TALK)))
            .andExpect(jsonPath("$.[*].postMatchTalk").value(hasItem(DEFAULT_POST_MATCH_TALK)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MatchAwayInfo.class);
        MatchAwayInfo matchAwayInfo1 = new MatchAwayInfo();
        matchAwayInfo1.setId(1L);
        MatchAwayInfo matchAwayInfo2 = new MatchAwayInfo();
        matchAwayInfo2.setId(matchAwayInfo1.getId());
        assertThat(matchAwayInfo1).isEqualTo(matchAwayInfo2);
        matchAwayInfo2.setId(2L);
        assertThat(matchAwayInfo1).isNotEqualTo(matchAwayInfo2);
        matchAwayInfo1.setId(null);
        assertThat(matchAwayInfo1).isNotEqualTo(matchAwayInfo2);
    }
}
