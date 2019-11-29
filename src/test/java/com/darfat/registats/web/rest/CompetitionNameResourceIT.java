package com.darfat.registats.web.rest;

import com.darfat.registats.RegistatsApp;
import com.darfat.registats.domain.CompetitionName;
import com.darfat.registats.repository.CompetitionNameRepository;
import com.darfat.registats.repository.search.CompetitionNameSearchRepository;
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
 * Integration tests for the {@link CompetitionNameResource} REST controller.
 */
@SpringBootTest(classes = RegistatsApp.class)
public class CompetitionNameResourceIT {

    private static final String DEFAULT_SLUG = "AAAAAAAAAA";
    private static final String UPDATED_SLUG = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_REGION = "AAAAAAAAAA";
    private static final String UPDATED_REGION = "BBBBBBBBBB";

    private static final String DEFAULT_NATION = "AAAAAAAAAA";
    private static final String UPDATED_NATION = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private CompetitionNameRepository competitionNameRepository;

    /**
     * This repository is mocked in the com.darfat.registats.repository.search test package.
     *
     * @see com.darfat.registats.repository.search.CompetitionNameSearchRepositoryMockConfiguration
     */
    @Autowired
    private CompetitionNameSearchRepository mockCompetitionNameSearchRepository;

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

    private MockMvc restCompetitionNameMockMvc;

    private CompetitionName competitionName;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CompetitionNameResource competitionNameResource = new CompetitionNameResource(competitionNameRepository, mockCompetitionNameSearchRepository);
        this.restCompetitionNameMockMvc = MockMvcBuilders.standaloneSetup(competitionNameResource)
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
    public static CompetitionName createEntity(EntityManager em) {
        CompetitionName competitionName = new CompetitionName()
            .slug(DEFAULT_SLUG)
            .city(DEFAULT_CITY)
            .region(DEFAULT_REGION)
            .nation(DEFAULT_NATION)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .active(DEFAULT_ACTIVE);
        return competitionName;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompetitionName createUpdatedEntity(EntityManager em) {
        CompetitionName competitionName = new CompetitionName()
            .slug(UPDATED_SLUG)
            .city(UPDATED_CITY)
            .region(UPDATED_REGION)
            .nation(UPDATED_NATION)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .active(UPDATED_ACTIVE);
        return competitionName;
    }

    @BeforeEach
    public void initTest() {
        competitionName = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompetitionName() throws Exception {
        int databaseSizeBeforeCreate = competitionNameRepository.findAll().size();

        // Create the CompetitionName
        restCompetitionNameMockMvc.perform(post("/api/competition-names")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(competitionName)))
            .andExpect(status().isCreated());

        // Validate the CompetitionName in the database
        List<CompetitionName> competitionNameList = competitionNameRepository.findAll();
        assertThat(competitionNameList).hasSize(databaseSizeBeforeCreate + 1);
        CompetitionName testCompetitionName = competitionNameList.get(competitionNameList.size() - 1);
        assertThat(testCompetitionName.getSlug()).isEqualTo(DEFAULT_SLUG);
        assertThat(testCompetitionName.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testCompetitionName.getRegion()).isEqualTo(DEFAULT_REGION);
        assertThat(testCompetitionName.getNation()).isEqualTo(DEFAULT_NATION);
        assertThat(testCompetitionName.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCompetitionName.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCompetitionName.isActive()).isEqualTo(DEFAULT_ACTIVE);

        // Validate the CompetitionName in Elasticsearch
        verify(mockCompetitionNameSearchRepository, times(1)).save(testCompetitionName);
    }

    @Test
    @Transactional
    public void createCompetitionNameWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = competitionNameRepository.findAll().size();

        // Create the CompetitionName with an existing ID
        competitionName.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompetitionNameMockMvc.perform(post("/api/competition-names")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(competitionName)))
            .andExpect(status().isBadRequest());

        // Validate the CompetitionName in the database
        List<CompetitionName> competitionNameList = competitionNameRepository.findAll();
        assertThat(competitionNameList).hasSize(databaseSizeBeforeCreate);

        // Validate the CompetitionName in Elasticsearch
        verify(mockCompetitionNameSearchRepository, times(0)).save(competitionName);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = competitionNameRepository.findAll().size();
        // set the field null
        competitionName.setName(null);

        // Create the CompetitionName, which fails.

        restCompetitionNameMockMvc.perform(post("/api/competition-names")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(competitionName)))
            .andExpect(status().isBadRequest());

        List<CompetitionName> competitionNameList = competitionNameRepository.findAll();
        assertThat(competitionNameList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCompetitionNames() throws Exception {
        // Initialize the database
        competitionNameRepository.saveAndFlush(competitionName);

        // Get all the competitionNameList
        restCompetitionNameMockMvc.perform(get("/api/competition-names?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(competitionName.getId().intValue())))
            .andExpect(jsonPath("$.[*].slug").value(hasItem(DEFAULT_SLUG)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].region").value(hasItem(DEFAULT_REGION)))
            .andExpect(jsonPath("$.[*].nation").value(hasItem(DEFAULT_NATION)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCompetitionName() throws Exception {
        // Initialize the database
        competitionNameRepository.saveAndFlush(competitionName);

        // Get the competitionName
        restCompetitionNameMockMvc.perform(get("/api/competition-names/{id}", competitionName.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(competitionName.getId().intValue()))
            .andExpect(jsonPath("$.slug").value(DEFAULT_SLUG))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY))
            .andExpect(jsonPath("$.region").value(DEFAULT_REGION))
            .andExpect(jsonPath("$.nation").value(DEFAULT_NATION))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCompetitionName() throws Exception {
        // Get the competitionName
        restCompetitionNameMockMvc.perform(get("/api/competition-names/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompetitionName() throws Exception {
        // Initialize the database
        competitionNameRepository.saveAndFlush(competitionName);

        int databaseSizeBeforeUpdate = competitionNameRepository.findAll().size();

        // Update the competitionName
        CompetitionName updatedCompetitionName = competitionNameRepository.findById(competitionName.getId()).get();
        // Disconnect from session so that the updates on updatedCompetitionName are not directly saved in db
        em.detach(updatedCompetitionName);
        updatedCompetitionName
            .slug(UPDATED_SLUG)
            .city(UPDATED_CITY)
            .region(UPDATED_REGION)
            .nation(UPDATED_NATION)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .active(UPDATED_ACTIVE);

        restCompetitionNameMockMvc.perform(put("/api/competition-names")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCompetitionName)))
            .andExpect(status().isOk());

        // Validate the CompetitionName in the database
        List<CompetitionName> competitionNameList = competitionNameRepository.findAll();
        assertThat(competitionNameList).hasSize(databaseSizeBeforeUpdate);
        CompetitionName testCompetitionName = competitionNameList.get(competitionNameList.size() - 1);
        assertThat(testCompetitionName.getSlug()).isEqualTo(UPDATED_SLUG);
        assertThat(testCompetitionName.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testCompetitionName.getRegion()).isEqualTo(UPDATED_REGION);
        assertThat(testCompetitionName.getNation()).isEqualTo(UPDATED_NATION);
        assertThat(testCompetitionName.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCompetitionName.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCompetitionName.isActive()).isEqualTo(UPDATED_ACTIVE);

        // Validate the CompetitionName in Elasticsearch
        verify(mockCompetitionNameSearchRepository, times(1)).save(testCompetitionName);
    }

    @Test
    @Transactional
    public void updateNonExistingCompetitionName() throws Exception {
        int databaseSizeBeforeUpdate = competitionNameRepository.findAll().size();

        // Create the CompetitionName

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompetitionNameMockMvc.perform(put("/api/competition-names")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(competitionName)))
            .andExpect(status().isBadRequest());

        // Validate the CompetitionName in the database
        List<CompetitionName> competitionNameList = competitionNameRepository.findAll();
        assertThat(competitionNameList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CompetitionName in Elasticsearch
        verify(mockCompetitionNameSearchRepository, times(0)).save(competitionName);
    }

    @Test
    @Transactional
    public void deleteCompetitionName() throws Exception {
        // Initialize the database
        competitionNameRepository.saveAndFlush(competitionName);

        int databaseSizeBeforeDelete = competitionNameRepository.findAll().size();

        // Delete the competitionName
        restCompetitionNameMockMvc.perform(delete("/api/competition-names/{id}", competitionName.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CompetitionName> competitionNameList = competitionNameRepository.findAll();
        assertThat(competitionNameList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CompetitionName in Elasticsearch
        verify(mockCompetitionNameSearchRepository, times(1)).deleteById(competitionName.getId());
    }

    @Test
    @Transactional
    public void searchCompetitionName() throws Exception {
        // Initialize the database
        competitionNameRepository.saveAndFlush(competitionName);
        when(mockCompetitionNameSearchRepository.search(queryStringQuery("id:" + competitionName.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(competitionName), PageRequest.of(0, 1), 1));
        // Search the competitionName
        restCompetitionNameMockMvc.perform(get("/api/_search/competition-names?query=id:" + competitionName.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(competitionName.getId().intValue())))
            .andExpect(jsonPath("$.[*].slug").value(hasItem(DEFAULT_SLUG)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].region").value(hasItem(DEFAULT_REGION)))
            .andExpect(jsonPath("$.[*].nation").value(hasItem(DEFAULT_NATION)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompetitionName.class);
        CompetitionName competitionName1 = new CompetitionName();
        competitionName1.setId(1L);
        CompetitionName competitionName2 = new CompetitionName();
        competitionName2.setId(competitionName1.getId());
        assertThat(competitionName1).isEqualTo(competitionName2);
        competitionName2.setId(2L);
        assertThat(competitionName1).isNotEqualTo(competitionName2);
        competitionName1.setId(null);
        assertThat(competitionName1).isNotEqualTo(competitionName2);
    }
}
