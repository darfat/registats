package com.darfat.registats.web.rest;

import com.darfat.registats.RegistatsApp;
import com.darfat.registats.domain.MatchCommentary;
import com.darfat.registats.repository.MatchCommentaryRepository;
import com.darfat.registats.repository.search.MatchCommentarySearchRepository;
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

import com.darfat.registats.domain.enumeration.CommentaryType;
/**
 * Integration tests for the {@link MatchCommentaryResource} REST controller.
 */
@SpringBootTest(classes = RegistatsApp.class)
public class MatchCommentaryResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final CommentaryType DEFAULT_COMMENTARY_TYPE = CommentaryType.HIGHLIGHT;
    private static final CommentaryType UPDATED_COMMENTARY_TYPE = CommentaryType.FULL;

    private static final Integer DEFAULT_MINUTE = 1;
    private static final Integer UPDATED_MINUTE = 2;

    private static final Instant DEFAULT_LOG_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LOG_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private MatchCommentaryRepository matchCommentaryRepository;

    /**
     * This repository is mocked in the com.darfat.registats.repository.search test package.
     *
     * @see com.darfat.registats.repository.search.MatchCommentarySearchRepositoryMockConfiguration
     */
    @Autowired
    private MatchCommentarySearchRepository mockMatchCommentarySearchRepository;

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

    private MockMvc restMatchCommentaryMockMvc;

    private MatchCommentary matchCommentary;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MatchCommentaryResource matchCommentaryResource = new MatchCommentaryResource(matchCommentaryRepository, mockMatchCommentarySearchRepository);
        this.restMatchCommentaryMockMvc = MockMvcBuilders.standaloneSetup(matchCommentaryResource)
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
    public static MatchCommentary createEntity(EntityManager em) {
        MatchCommentary matchCommentary = new MatchCommentary()
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION)
            .commentaryType(DEFAULT_COMMENTARY_TYPE)
            .minute(DEFAULT_MINUTE)
            .logDate(DEFAULT_LOG_DATE);
        return matchCommentary;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MatchCommentary createUpdatedEntity(EntityManager em) {
        MatchCommentary matchCommentary = new MatchCommentary()
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .commentaryType(UPDATED_COMMENTARY_TYPE)
            .minute(UPDATED_MINUTE)
            .logDate(UPDATED_LOG_DATE);
        return matchCommentary;
    }

    @BeforeEach
    public void initTest() {
        matchCommentary = createEntity(em);
    }

    @Test
    @Transactional
    public void createMatchCommentary() throws Exception {
        int databaseSizeBeforeCreate = matchCommentaryRepository.findAll().size();

        // Create the MatchCommentary
        restMatchCommentaryMockMvc.perform(post("/api/match-commentaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(matchCommentary)))
            .andExpect(status().isCreated());

        // Validate the MatchCommentary in the database
        List<MatchCommentary> matchCommentaryList = matchCommentaryRepository.findAll();
        assertThat(matchCommentaryList).hasSize(databaseSizeBeforeCreate + 1);
        MatchCommentary testMatchCommentary = matchCommentaryList.get(matchCommentaryList.size() - 1);
        assertThat(testMatchCommentary.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testMatchCommentary.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testMatchCommentary.getCommentaryType()).isEqualTo(DEFAULT_COMMENTARY_TYPE);
        assertThat(testMatchCommentary.getMinute()).isEqualTo(DEFAULT_MINUTE);
        assertThat(testMatchCommentary.getLogDate()).isEqualTo(DEFAULT_LOG_DATE);

        // Validate the MatchCommentary in Elasticsearch
        verify(mockMatchCommentarySearchRepository, times(1)).save(testMatchCommentary);
    }

    @Test
    @Transactional
    public void createMatchCommentaryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = matchCommentaryRepository.findAll().size();

        // Create the MatchCommentary with an existing ID
        matchCommentary.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMatchCommentaryMockMvc.perform(post("/api/match-commentaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(matchCommentary)))
            .andExpect(status().isBadRequest());

        // Validate the MatchCommentary in the database
        List<MatchCommentary> matchCommentaryList = matchCommentaryRepository.findAll();
        assertThat(matchCommentaryList).hasSize(databaseSizeBeforeCreate);

        // Validate the MatchCommentary in Elasticsearch
        verify(mockMatchCommentarySearchRepository, times(0)).save(matchCommentary);
    }


    @Test
    @Transactional
    public void getAllMatchCommentaries() throws Exception {
        // Initialize the database
        matchCommentaryRepository.saveAndFlush(matchCommentary);

        // Get all the matchCommentaryList
        restMatchCommentaryMockMvc.perform(get("/api/match-commentaries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(matchCommentary.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].commentaryType").value(hasItem(DEFAULT_COMMENTARY_TYPE.toString())))
            .andExpect(jsonPath("$.[*].minute").value(hasItem(DEFAULT_MINUTE)))
            .andExpect(jsonPath("$.[*].logDate").value(hasItem(DEFAULT_LOG_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getMatchCommentary() throws Exception {
        // Initialize the database
        matchCommentaryRepository.saveAndFlush(matchCommentary);

        // Get the matchCommentary
        restMatchCommentaryMockMvc.perform(get("/api/match-commentaries/{id}", matchCommentary.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(matchCommentary.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.commentaryType").value(DEFAULT_COMMENTARY_TYPE.toString()))
            .andExpect(jsonPath("$.minute").value(DEFAULT_MINUTE))
            .andExpect(jsonPath("$.logDate").value(DEFAULT_LOG_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMatchCommentary() throws Exception {
        // Get the matchCommentary
        restMatchCommentaryMockMvc.perform(get("/api/match-commentaries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMatchCommentary() throws Exception {
        // Initialize the database
        matchCommentaryRepository.saveAndFlush(matchCommentary);

        int databaseSizeBeforeUpdate = matchCommentaryRepository.findAll().size();

        // Update the matchCommentary
        MatchCommentary updatedMatchCommentary = matchCommentaryRepository.findById(matchCommentary.getId()).get();
        // Disconnect from session so that the updates on updatedMatchCommentary are not directly saved in db
        em.detach(updatedMatchCommentary);
        updatedMatchCommentary
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .commentaryType(UPDATED_COMMENTARY_TYPE)
            .minute(UPDATED_MINUTE)
            .logDate(UPDATED_LOG_DATE);

        restMatchCommentaryMockMvc.perform(put("/api/match-commentaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMatchCommentary)))
            .andExpect(status().isOk());

        // Validate the MatchCommentary in the database
        List<MatchCommentary> matchCommentaryList = matchCommentaryRepository.findAll();
        assertThat(matchCommentaryList).hasSize(databaseSizeBeforeUpdate);
        MatchCommentary testMatchCommentary = matchCommentaryList.get(matchCommentaryList.size() - 1);
        assertThat(testMatchCommentary.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testMatchCommentary.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMatchCommentary.getCommentaryType()).isEqualTo(UPDATED_COMMENTARY_TYPE);
        assertThat(testMatchCommentary.getMinute()).isEqualTo(UPDATED_MINUTE);
        assertThat(testMatchCommentary.getLogDate()).isEqualTo(UPDATED_LOG_DATE);

        // Validate the MatchCommentary in Elasticsearch
        verify(mockMatchCommentarySearchRepository, times(1)).save(testMatchCommentary);
    }

    @Test
    @Transactional
    public void updateNonExistingMatchCommentary() throws Exception {
        int databaseSizeBeforeUpdate = matchCommentaryRepository.findAll().size();

        // Create the MatchCommentary

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMatchCommentaryMockMvc.perform(put("/api/match-commentaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(matchCommentary)))
            .andExpect(status().isBadRequest());

        // Validate the MatchCommentary in the database
        List<MatchCommentary> matchCommentaryList = matchCommentaryRepository.findAll();
        assertThat(matchCommentaryList).hasSize(databaseSizeBeforeUpdate);

        // Validate the MatchCommentary in Elasticsearch
        verify(mockMatchCommentarySearchRepository, times(0)).save(matchCommentary);
    }

    @Test
    @Transactional
    public void deleteMatchCommentary() throws Exception {
        // Initialize the database
        matchCommentaryRepository.saveAndFlush(matchCommentary);

        int databaseSizeBeforeDelete = matchCommentaryRepository.findAll().size();

        // Delete the matchCommentary
        restMatchCommentaryMockMvc.perform(delete("/api/match-commentaries/{id}", matchCommentary.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MatchCommentary> matchCommentaryList = matchCommentaryRepository.findAll();
        assertThat(matchCommentaryList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the MatchCommentary in Elasticsearch
        verify(mockMatchCommentarySearchRepository, times(1)).deleteById(matchCommentary.getId());
    }

    @Test
    @Transactional
    public void searchMatchCommentary() throws Exception {
        // Initialize the database
        matchCommentaryRepository.saveAndFlush(matchCommentary);
        when(mockMatchCommentarySearchRepository.search(queryStringQuery("id:" + matchCommentary.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(matchCommentary), PageRequest.of(0, 1), 1));
        // Search the matchCommentary
        restMatchCommentaryMockMvc.perform(get("/api/_search/match-commentaries?query=id:" + matchCommentary.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(matchCommentary.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].commentaryType").value(hasItem(DEFAULT_COMMENTARY_TYPE.toString())))
            .andExpect(jsonPath("$.[*].minute").value(hasItem(DEFAULT_MINUTE)))
            .andExpect(jsonPath("$.[*].logDate").value(hasItem(DEFAULT_LOG_DATE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MatchCommentary.class);
        MatchCommentary matchCommentary1 = new MatchCommentary();
        matchCommentary1.setId(1L);
        MatchCommentary matchCommentary2 = new MatchCommentary();
        matchCommentary2.setId(matchCommentary1.getId());
        assertThat(matchCommentary1).isEqualTo(matchCommentary2);
        matchCommentary2.setId(2L);
        assertThat(matchCommentary1).isNotEqualTo(matchCommentary2);
        matchCommentary1.setId(null);
        assertThat(matchCommentary1).isNotEqualTo(matchCommentary2);
    }
}
