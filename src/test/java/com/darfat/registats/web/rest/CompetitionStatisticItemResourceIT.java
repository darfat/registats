package com.darfat.registats.web.rest;

import com.darfat.registats.RegistatsApp;
import com.darfat.registats.domain.CompetitionStatisticItem;
import com.darfat.registats.repository.CompetitionStatisticItemRepository;
import com.darfat.registats.repository.search.CompetitionStatisticItemSearchRepository;
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

import com.darfat.registats.domain.enumeration.CompetitionStatisticItemCategory;
/**
 * Integration tests for the {@link CompetitionStatisticItemResource} REST controller.
 */
@SpringBootTest(classes = RegistatsApp.class)
public class CompetitionStatisticItemResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final CompetitionStatisticItemCategory DEFAULT_CATEGORY = CompetitionStatisticItemCategory.TEAM;
    private static final CompetitionStatisticItemCategory UPDATED_CATEGORY = CompetitionStatisticItemCategory.PLAYER;

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private CompetitionStatisticItemRepository competitionStatisticItemRepository;

    /**
     * This repository is mocked in the com.darfat.registats.repository.search test package.
     *
     * @see com.darfat.registats.repository.search.CompetitionStatisticItemSearchRepositoryMockConfiguration
     */
    @Autowired
    private CompetitionStatisticItemSearchRepository mockCompetitionStatisticItemSearchRepository;

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

    private MockMvc restCompetitionStatisticItemMockMvc;

    private CompetitionStatisticItem competitionStatisticItem;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CompetitionStatisticItemResource competitionStatisticItemResource = new CompetitionStatisticItemResource(competitionStatisticItemRepository, mockCompetitionStatisticItemSearchRepository);
        this.restCompetitionStatisticItemMockMvc = MockMvcBuilders.standaloneSetup(competitionStatisticItemResource)
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
    public static CompetitionStatisticItem createEntity(EntityManager em) {
        CompetitionStatisticItem competitionStatisticItem = new CompetitionStatisticItem()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .category(DEFAULT_CATEGORY)
            .active(DEFAULT_ACTIVE);
        return competitionStatisticItem;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompetitionStatisticItem createUpdatedEntity(EntityManager em) {
        CompetitionStatisticItem competitionStatisticItem = new CompetitionStatisticItem()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .category(UPDATED_CATEGORY)
            .active(UPDATED_ACTIVE);
        return competitionStatisticItem;
    }

    @BeforeEach
    public void initTest() {
        competitionStatisticItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompetitionStatisticItem() throws Exception {
        int databaseSizeBeforeCreate = competitionStatisticItemRepository.findAll().size();

        // Create the CompetitionStatisticItem
        restCompetitionStatisticItemMockMvc.perform(post("/api/competition-statistic-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(competitionStatisticItem)))
            .andExpect(status().isCreated());

        // Validate the CompetitionStatisticItem in the database
        List<CompetitionStatisticItem> competitionStatisticItemList = competitionStatisticItemRepository.findAll();
        assertThat(competitionStatisticItemList).hasSize(databaseSizeBeforeCreate + 1);
        CompetitionStatisticItem testCompetitionStatisticItem = competitionStatisticItemList.get(competitionStatisticItemList.size() - 1);
        assertThat(testCompetitionStatisticItem.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCompetitionStatisticItem.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCompetitionStatisticItem.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testCompetitionStatisticItem.isActive()).isEqualTo(DEFAULT_ACTIVE);

        // Validate the CompetitionStatisticItem in Elasticsearch
        verify(mockCompetitionStatisticItemSearchRepository, times(1)).save(testCompetitionStatisticItem);
    }

    @Test
    @Transactional
    public void createCompetitionStatisticItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = competitionStatisticItemRepository.findAll().size();

        // Create the CompetitionStatisticItem with an existing ID
        competitionStatisticItem.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompetitionStatisticItemMockMvc.perform(post("/api/competition-statistic-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(competitionStatisticItem)))
            .andExpect(status().isBadRequest());

        // Validate the CompetitionStatisticItem in the database
        List<CompetitionStatisticItem> competitionStatisticItemList = competitionStatisticItemRepository.findAll();
        assertThat(competitionStatisticItemList).hasSize(databaseSizeBeforeCreate);

        // Validate the CompetitionStatisticItem in Elasticsearch
        verify(mockCompetitionStatisticItemSearchRepository, times(0)).save(competitionStatisticItem);
    }


    @Test
    @Transactional
    public void getAllCompetitionStatisticItems() throws Exception {
        // Initialize the database
        competitionStatisticItemRepository.saveAndFlush(competitionStatisticItem);

        // Get all the competitionStatisticItemList
        restCompetitionStatisticItemMockMvc.perform(get("/api/competition-statistic-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(competitionStatisticItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCompetitionStatisticItem() throws Exception {
        // Initialize the database
        competitionStatisticItemRepository.saveAndFlush(competitionStatisticItem);

        // Get the competitionStatisticItem
        restCompetitionStatisticItemMockMvc.perform(get("/api/competition-statistic-items/{id}", competitionStatisticItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(competitionStatisticItem.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCompetitionStatisticItem() throws Exception {
        // Get the competitionStatisticItem
        restCompetitionStatisticItemMockMvc.perform(get("/api/competition-statistic-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompetitionStatisticItem() throws Exception {
        // Initialize the database
        competitionStatisticItemRepository.saveAndFlush(competitionStatisticItem);

        int databaseSizeBeforeUpdate = competitionStatisticItemRepository.findAll().size();

        // Update the competitionStatisticItem
        CompetitionStatisticItem updatedCompetitionStatisticItem = competitionStatisticItemRepository.findById(competitionStatisticItem.getId()).get();
        // Disconnect from session so that the updates on updatedCompetitionStatisticItem are not directly saved in db
        em.detach(updatedCompetitionStatisticItem);
        updatedCompetitionStatisticItem
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .category(UPDATED_CATEGORY)
            .active(UPDATED_ACTIVE);

        restCompetitionStatisticItemMockMvc.perform(put("/api/competition-statistic-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCompetitionStatisticItem)))
            .andExpect(status().isOk());

        // Validate the CompetitionStatisticItem in the database
        List<CompetitionStatisticItem> competitionStatisticItemList = competitionStatisticItemRepository.findAll();
        assertThat(competitionStatisticItemList).hasSize(databaseSizeBeforeUpdate);
        CompetitionStatisticItem testCompetitionStatisticItem = competitionStatisticItemList.get(competitionStatisticItemList.size() - 1);
        assertThat(testCompetitionStatisticItem.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCompetitionStatisticItem.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCompetitionStatisticItem.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testCompetitionStatisticItem.isActive()).isEqualTo(UPDATED_ACTIVE);

        // Validate the CompetitionStatisticItem in Elasticsearch
        verify(mockCompetitionStatisticItemSearchRepository, times(1)).save(testCompetitionStatisticItem);
    }

    @Test
    @Transactional
    public void updateNonExistingCompetitionStatisticItem() throws Exception {
        int databaseSizeBeforeUpdate = competitionStatisticItemRepository.findAll().size();

        // Create the CompetitionStatisticItem

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompetitionStatisticItemMockMvc.perform(put("/api/competition-statistic-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(competitionStatisticItem)))
            .andExpect(status().isBadRequest());

        // Validate the CompetitionStatisticItem in the database
        List<CompetitionStatisticItem> competitionStatisticItemList = competitionStatisticItemRepository.findAll();
        assertThat(competitionStatisticItemList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CompetitionStatisticItem in Elasticsearch
        verify(mockCompetitionStatisticItemSearchRepository, times(0)).save(competitionStatisticItem);
    }

    @Test
    @Transactional
    public void deleteCompetitionStatisticItem() throws Exception {
        // Initialize the database
        competitionStatisticItemRepository.saveAndFlush(competitionStatisticItem);

        int databaseSizeBeforeDelete = competitionStatisticItemRepository.findAll().size();

        // Delete the competitionStatisticItem
        restCompetitionStatisticItemMockMvc.perform(delete("/api/competition-statistic-items/{id}", competitionStatisticItem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CompetitionStatisticItem> competitionStatisticItemList = competitionStatisticItemRepository.findAll();
        assertThat(competitionStatisticItemList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CompetitionStatisticItem in Elasticsearch
        verify(mockCompetitionStatisticItemSearchRepository, times(1)).deleteById(competitionStatisticItem.getId());
    }

    @Test
    @Transactional
    public void searchCompetitionStatisticItem() throws Exception {
        // Initialize the database
        competitionStatisticItemRepository.saveAndFlush(competitionStatisticItem);
        when(mockCompetitionStatisticItemSearchRepository.search(queryStringQuery("id:" + competitionStatisticItem.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(competitionStatisticItem), PageRequest.of(0, 1), 1));
        // Search the competitionStatisticItem
        restCompetitionStatisticItemMockMvc.perform(get("/api/_search/competition-statistic-items?query=id:" + competitionStatisticItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(competitionStatisticItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompetitionStatisticItem.class);
        CompetitionStatisticItem competitionStatisticItem1 = new CompetitionStatisticItem();
        competitionStatisticItem1.setId(1L);
        CompetitionStatisticItem competitionStatisticItem2 = new CompetitionStatisticItem();
        competitionStatisticItem2.setId(competitionStatisticItem1.getId());
        assertThat(competitionStatisticItem1).isEqualTo(competitionStatisticItem2);
        competitionStatisticItem2.setId(2L);
        assertThat(competitionStatisticItem1).isNotEqualTo(competitionStatisticItem2);
        competitionStatisticItem1.setId(null);
        assertThat(competitionStatisticItem1).isNotEqualTo(competitionStatisticItem2);
    }
}
