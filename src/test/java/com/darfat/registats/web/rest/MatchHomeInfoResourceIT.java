package com.darfat.registats.web.rest;

import com.darfat.registats.RegistatsApp;
import com.darfat.registats.domain.MatchHomeInfo;
import com.darfat.registats.repository.MatchHomeInfoRepository;
import com.darfat.registats.repository.search.MatchHomeInfoSearchRepository;
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
 * Integration tests for the {@link MatchHomeInfoResource} REST controller.
 */
@SpringBootTest(classes = RegistatsApp.class)
public class MatchHomeInfoResourceIT {

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
    private MatchHomeInfoRepository matchHomeInfoRepository;

    /**
     * This repository is mocked in the com.darfat.registats.repository.search test package.
     *
     * @see com.darfat.registats.repository.search.MatchHomeInfoSearchRepositoryMockConfiguration
     */
    @Autowired
    private MatchHomeInfoSearchRepository mockMatchHomeInfoSearchRepository;

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

    private MockMvc restMatchHomeInfoMockMvc;

    private MatchHomeInfo matchHomeInfo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MatchHomeInfoResource matchHomeInfoResource = new MatchHomeInfoResource(matchHomeInfoRepository, mockMatchHomeInfoSearchRepository);
        this.restMatchHomeInfoMockMvc = MockMvcBuilders.standaloneSetup(matchHomeInfoResource)
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
    public static MatchHomeInfo createEntity(EntityManager em) {
        MatchHomeInfo matchHomeInfo = new MatchHomeInfo()
            .description(DEFAULT_DESCRIPTION)
            .formation(DEFAULT_FORMATION)
            .analysis(DEFAULT_ANALYSIS)
            .preMatchTalk(DEFAULT_PRE_MATCH_TALK)
            .postMatchTalk(DEFAULT_POST_MATCH_TALK);
        return matchHomeInfo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MatchHomeInfo createUpdatedEntity(EntityManager em) {
        MatchHomeInfo matchHomeInfo = new MatchHomeInfo()
            .description(UPDATED_DESCRIPTION)
            .formation(UPDATED_FORMATION)
            .analysis(UPDATED_ANALYSIS)
            .preMatchTalk(UPDATED_PRE_MATCH_TALK)
            .postMatchTalk(UPDATED_POST_MATCH_TALK);
        return matchHomeInfo;
    }

    @BeforeEach
    public void initTest() {
        matchHomeInfo = createEntity(em);
    }

    @Test
    @Transactional
    public void createMatchHomeInfo() throws Exception {
        int databaseSizeBeforeCreate = matchHomeInfoRepository.findAll().size();

        // Create the MatchHomeInfo
        restMatchHomeInfoMockMvc.perform(post("/api/match-home-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(matchHomeInfo)))
            .andExpect(status().isCreated());

        // Validate the MatchHomeInfo in the database
        List<MatchHomeInfo> matchHomeInfoList = matchHomeInfoRepository.findAll();
        assertThat(matchHomeInfoList).hasSize(databaseSizeBeforeCreate + 1);
        MatchHomeInfo testMatchHomeInfo = matchHomeInfoList.get(matchHomeInfoList.size() - 1);
        assertThat(testMatchHomeInfo.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testMatchHomeInfo.getFormation()).isEqualTo(DEFAULT_FORMATION);
        assertThat(testMatchHomeInfo.getAnalysis()).isEqualTo(DEFAULT_ANALYSIS);
        assertThat(testMatchHomeInfo.getPreMatchTalk()).isEqualTo(DEFAULT_PRE_MATCH_TALK);
        assertThat(testMatchHomeInfo.getPostMatchTalk()).isEqualTo(DEFAULT_POST_MATCH_TALK);

        // Validate the MatchHomeInfo in Elasticsearch
        verify(mockMatchHomeInfoSearchRepository, times(1)).save(testMatchHomeInfo);
    }

    @Test
    @Transactional
    public void createMatchHomeInfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = matchHomeInfoRepository.findAll().size();

        // Create the MatchHomeInfo with an existing ID
        matchHomeInfo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMatchHomeInfoMockMvc.perform(post("/api/match-home-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(matchHomeInfo)))
            .andExpect(status().isBadRequest());

        // Validate the MatchHomeInfo in the database
        List<MatchHomeInfo> matchHomeInfoList = matchHomeInfoRepository.findAll();
        assertThat(matchHomeInfoList).hasSize(databaseSizeBeforeCreate);

        // Validate the MatchHomeInfo in Elasticsearch
        verify(mockMatchHomeInfoSearchRepository, times(0)).save(matchHomeInfo);
    }


    @Test
    @Transactional
    public void getAllMatchHomeInfos() throws Exception {
        // Initialize the database
        matchHomeInfoRepository.saveAndFlush(matchHomeInfo);

        // Get all the matchHomeInfoList
        restMatchHomeInfoMockMvc.perform(get("/api/match-home-infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(matchHomeInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].formation").value(hasItem(DEFAULT_FORMATION.toString())))
            .andExpect(jsonPath("$.[*].analysis").value(hasItem(DEFAULT_ANALYSIS)))
            .andExpect(jsonPath("$.[*].preMatchTalk").value(hasItem(DEFAULT_PRE_MATCH_TALK)))
            .andExpect(jsonPath("$.[*].postMatchTalk").value(hasItem(DEFAULT_POST_MATCH_TALK)));
    }
    
    @Test
    @Transactional
    public void getMatchHomeInfo() throws Exception {
        // Initialize the database
        matchHomeInfoRepository.saveAndFlush(matchHomeInfo);

        // Get the matchHomeInfo
        restMatchHomeInfoMockMvc.perform(get("/api/match-home-infos/{id}", matchHomeInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(matchHomeInfo.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.formation").value(DEFAULT_FORMATION.toString()))
            .andExpect(jsonPath("$.analysis").value(DEFAULT_ANALYSIS))
            .andExpect(jsonPath("$.preMatchTalk").value(DEFAULT_PRE_MATCH_TALK))
            .andExpect(jsonPath("$.postMatchTalk").value(DEFAULT_POST_MATCH_TALK));
    }

    @Test
    @Transactional
    public void getNonExistingMatchHomeInfo() throws Exception {
        // Get the matchHomeInfo
        restMatchHomeInfoMockMvc.perform(get("/api/match-home-infos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMatchHomeInfo() throws Exception {
        // Initialize the database
        matchHomeInfoRepository.saveAndFlush(matchHomeInfo);

        int databaseSizeBeforeUpdate = matchHomeInfoRepository.findAll().size();

        // Update the matchHomeInfo
        MatchHomeInfo updatedMatchHomeInfo = matchHomeInfoRepository.findById(matchHomeInfo.getId()).get();
        // Disconnect from session so that the updates on updatedMatchHomeInfo are not directly saved in db
        em.detach(updatedMatchHomeInfo);
        updatedMatchHomeInfo
            .description(UPDATED_DESCRIPTION)
            .formation(UPDATED_FORMATION)
            .analysis(UPDATED_ANALYSIS)
            .preMatchTalk(UPDATED_PRE_MATCH_TALK)
            .postMatchTalk(UPDATED_POST_MATCH_TALK);

        restMatchHomeInfoMockMvc.perform(put("/api/match-home-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMatchHomeInfo)))
            .andExpect(status().isOk());

        // Validate the MatchHomeInfo in the database
        List<MatchHomeInfo> matchHomeInfoList = matchHomeInfoRepository.findAll();
        assertThat(matchHomeInfoList).hasSize(databaseSizeBeforeUpdate);
        MatchHomeInfo testMatchHomeInfo = matchHomeInfoList.get(matchHomeInfoList.size() - 1);
        assertThat(testMatchHomeInfo.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMatchHomeInfo.getFormation()).isEqualTo(UPDATED_FORMATION);
        assertThat(testMatchHomeInfo.getAnalysis()).isEqualTo(UPDATED_ANALYSIS);
        assertThat(testMatchHomeInfo.getPreMatchTalk()).isEqualTo(UPDATED_PRE_MATCH_TALK);
        assertThat(testMatchHomeInfo.getPostMatchTalk()).isEqualTo(UPDATED_POST_MATCH_TALK);

        // Validate the MatchHomeInfo in Elasticsearch
        verify(mockMatchHomeInfoSearchRepository, times(1)).save(testMatchHomeInfo);
    }

    @Test
    @Transactional
    public void updateNonExistingMatchHomeInfo() throws Exception {
        int databaseSizeBeforeUpdate = matchHomeInfoRepository.findAll().size();

        // Create the MatchHomeInfo

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMatchHomeInfoMockMvc.perform(put("/api/match-home-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(matchHomeInfo)))
            .andExpect(status().isBadRequest());

        // Validate the MatchHomeInfo in the database
        List<MatchHomeInfo> matchHomeInfoList = matchHomeInfoRepository.findAll();
        assertThat(matchHomeInfoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the MatchHomeInfo in Elasticsearch
        verify(mockMatchHomeInfoSearchRepository, times(0)).save(matchHomeInfo);
    }

    @Test
    @Transactional
    public void deleteMatchHomeInfo() throws Exception {
        // Initialize the database
        matchHomeInfoRepository.saveAndFlush(matchHomeInfo);

        int databaseSizeBeforeDelete = matchHomeInfoRepository.findAll().size();

        // Delete the matchHomeInfo
        restMatchHomeInfoMockMvc.perform(delete("/api/match-home-infos/{id}", matchHomeInfo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MatchHomeInfo> matchHomeInfoList = matchHomeInfoRepository.findAll();
        assertThat(matchHomeInfoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the MatchHomeInfo in Elasticsearch
        verify(mockMatchHomeInfoSearchRepository, times(1)).deleteById(matchHomeInfo.getId());
    }

    @Test
    @Transactional
    public void searchMatchHomeInfo() throws Exception {
        // Initialize the database
        matchHomeInfoRepository.saveAndFlush(matchHomeInfo);
        when(mockMatchHomeInfoSearchRepository.search(queryStringQuery("id:" + matchHomeInfo.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(matchHomeInfo), PageRequest.of(0, 1), 1));
        // Search the matchHomeInfo
        restMatchHomeInfoMockMvc.perform(get("/api/_search/match-home-infos?query=id:" + matchHomeInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(matchHomeInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].formation").value(hasItem(DEFAULT_FORMATION.toString())))
            .andExpect(jsonPath("$.[*].analysis").value(hasItem(DEFAULT_ANALYSIS)))
            .andExpect(jsonPath("$.[*].preMatchTalk").value(hasItem(DEFAULT_PRE_MATCH_TALK)))
            .andExpect(jsonPath("$.[*].postMatchTalk").value(hasItem(DEFAULT_POST_MATCH_TALK)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MatchHomeInfo.class);
        MatchHomeInfo matchHomeInfo1 = new MatchHomeInfo();
        matchHomeInfo1.setId(1L);
        MatchHomeInfo matchHomeInfo2 = new MatchHomeInfo();
        matchHomeInfo2.setId(matchHomeInfo1.getId());
        assertThat(matchHomeInfo1).isEqualTo(matchHomeInfo2);
        matchHomeInfo2.setId(2L);
        assertThat(matchHomeInfo1).isNotEqualTo(matchHomeInfo2);
        matchHomeInfo1.setId(null);
        assertThat(matchHomeInfo1).isNotEqualTo(matchHomeInfo2);
    }
}
