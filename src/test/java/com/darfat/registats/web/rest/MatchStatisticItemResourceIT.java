package com.darfat.registats.web.rest;

import com.darfat.registats.RegistatsApp;
import com.darfat.registats.domain.MatchStatisticItem;
import com.darfat.registats.repository.MatchStatisticItemRepository;
import com.darfat.registats.repository.search.MatchStatisticItemSearchRepository;
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
 * Integration tests for the {@link MatchStatisticItemResource} REST controller.
 */
@SpringBootTest(classes = RegistatsApp.class)
public class MatchStatisticItemResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private MatchStatisticItemRepository matchStatisticItemRepository;

    /**
     * This repository is mocked in the com.darfat.registats.repository.search test package.
     *
     * @see com.darfat.registats.repository.search.MatchStatisticItemSearchRepositoryMockConfiguration
     */
    @Autowired
    private MatchStatisticItemSearchRepository mockMatchStatisticItemSearchRepository;

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

    private MockMvc restMatchStatisticItemMockMvc;

    private MatchStatisticItem matchStatisticItem;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MatchStatisticItemResource matchStatisticItemResource = new MatchStatisticItemResource(matchStatisticItemRepository, mockMatchStatisticItemSearchRepository);
        this.restMatchStatisticItemMockMvc = MockMvcBuilders.standaloneSetup(matchStatisticItemResource)
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
    public static MatchStatisticItem createEntity(EntityManager em) {
        MatchStatisticItem matchStatisticItem = new MatchStatisticItem()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .active(DEFAULT_ACTIVE);
        return matchStatisticItem;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MatchStatisticItem createUpdatedEntity(EntityManager em) {
        MatchStatisticItem matchStatisticItem = new MatchStatisticItem()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .active(UPDATED_ACTIVE);
        return matchStatisticItem;
    }

    @BeforeEach
    public void initTest() {
        matchStatisticItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createMatchStatisticItem() throws Exception {
        int databaseSizeBeforeCreate = matchStatisticItemRepository.findAll().size();

        // Create the MatchStatisticItem
        restMatchStatisticItemMockMvc.perform(post("/api/match-statistic-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(matchStatisticItem)))
            .andExpect(status().isCreated());

        // Validate the MatchStatisticItem in the database
        List<MatchStatisticItem> matchStatisticItemList = matchStatisticItemRepository.findAll();
        assertThat(matchStatisticItemList).hasSize(databaseSizeBeforeCreate + 1);
        MatchStatisticItem testMatchStatisticItem = matchStatisticItemList.get(matchStatisticItemList.size() - 1);
        assertThat(testMatchStatisticItem.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMatchStatisticItem.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testMatchStatisticItem.isActive()).isEqualTo(DEFAULT_ACTIVE);

        // Validate the MatchStatisticItem in Elasticsearch
        verify(mockMatchStatisticItemSearchRepository, times(1)).save(testMatchStatisticItem);
    }

    @Test
    @Transactional
    public void createMatchStatisticItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = matchStatisticItemRepository.findAll().size();

        // Create the MatchStatisticItem with an existing ID
        matchStatisticItem.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMatchStatisticItemMockMvc.perform(post("/api/match-statistic-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(matchStatisticItem)))
            .andExpect(status().isBadRequest());

        // Validate the MatchStatisticItem in the database
        List<MatchStatisticItem> matchStatisticItemList = matchStatisticItemRepository.findAll();
        assertThat(matchStatisticItemList).hasSize(databaseSizeBeforeCreate);

        // Validate the MatchStatisticItem in Elasticsearch
        verify(mockMatchStatisticItemSearchRepository, times(0)).save(matchStatisticItem);
    }


    @Test
    @Transactional
    public void getAllMatchStatisticItems() throws Exception {
        // Initialize the database
        matchStatisticItemRepository.saveAndFlush(matchStatisticItem);

        // Get all the matchStatisticItemList
        restMatchStatisticItemMockMvc.perform(get("/api/match-statistic-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(matchStatisticItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMatchStatisticItem() throws Exception {
        // Initialize the database
        matchStatisticItemRepository.saveAndFlush(matchStatisticItem);

        // Get the matchStatisticItem
        restMatchStatisticItemMockMvc.perform(get("/api/match-statistic-items/{id}", matchStatisticItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(matchStatisticItem.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingMatchStatisticItem() throws Exception {
        // Get the matchStatisticItem
        restMatchStatisticItemMockMvc.perform(get("/api/match-statistic-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMatchStatisticItem() throws Exception {
        // Initialize the database
        matchStatisticItemRepository.saveAndFlush(matchStatisticItem);

        int databaseSizeBeforeUpdate = matchStatisticItemRepository.findAll().size();

        // Update the matchStatisticItem
        MatchStatisticItem updatedMatchStatisticItem = matchStatisticItemRepository.findById(matchStatisticItem.getId()).get();
        // Disconnect from session so that the updates on updatedMatchStatisticItem are not directly saved in db
        em.detach(updatedMatchStatisticItem);
        updatedMatchStatisticItem
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .active(UPDATED_ACTIVE);

        restMatchStatisticItemMockMvc.perform(put("/api/match-statistic-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMatchStatisticItem)))
            .andExpect(status().isOk());

        // Validate the MatchStatisticItem in the database
        List<MatchStatisticItem> matchStatisticItemList = matchStatisticItemRepository.findAll();
        assertThat(matchStatisticItemList).hasSize(databaseSizeBeforeUpdate);
        MatchStatisticItem testMatchStatisticItem = matchStatisticItemList.get(matchStatisticItemList.size() - 1);
        assertThat(testMatchStatisticItem.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMatchStatisticItem.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMatchStatisticItem.isActive()).isEqualTo(UPDATED_ACTIVE);

        // Validate the MatchStatisticItem in Elasticsearch
        verify(mockMatchStatisticItemSearchRepository, times(1)).save(testMatchStatisticItem);
    }

    @Test
    @Transactional
    public void updateNonExistingMatchStatisticItem() throws Exception {
        int databaseSizeBeforeUpdate = matchStatisticItemRepository.findAll().size();

        // Create the MatchStatisticItem

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMatchStatisticItemMockMvc.perform(put("/api/match-statistic-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(matchStatisticItem)))
            .andExpect(status().isBadRequest());

        // Validate the MatchStatisticItem in the database
        List<MatchStatisticItem> matchStatisticItemList = matchStatisticItemRepository.findAll();
        assertThat(matchStatisticItemList).hasSize(databaseSizeBeforeUpdate);

        // Validate the MatchStatisticItem in Elasticsearch
        verify(mockMatchStatisticItemSearchRepository, times(0)).save(matchStatisticItem);
    }

    @Test
    @Transactional
    public void deleteMatchStatisticItem() throws Exception {
        // Initialize the database
        matchStatisticItemRepository.saveAndFlush(matchStatisticItem);

        int databaseSizeBeforeDelete = matchStatisticItemRepository.findAll().size();

        // Delete the matchStatisticItem
        restMatchStatisticItemMockMvc.perform(delete("/api/match-statistic-items/{id}", matchStatisticItem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MatchStatisticItem> matchStatisticItemList = matchStatisticItemRepository.findAll();
        assertThat(matchStatisticItemList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the MatchStatisticItem in Elasticsearch
        verify(mockMatchStatisticItemSearchRepository, times(1)).deleteById(matchStatisticItem.getId());
    }

    @Test
    @Transactional
    public void searchMatchStatisticItem() throws Exception {
        // Initialize the database
        matchStatisticItemRepository.saveAndFlush(matchStatisticItem);
        when(mockMatchStatisticItemSearchRepository.search(queryStringQuery("id:" + matchStatisticItem.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(matchStatisticItem), PageRequest.of(0, 1), 1));
        // Search the matchStatisticItem
        restMatchStatisticItemMockMvc.perform(get("/api/_search/match-statistic-items?query=id:" + matchStatisticItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(matchStatisticItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MatchStatisticItem.class);
        MatchStatisticItem matchStatisticItem1 = new MatchStatisticItem();
        matchStatisticItem1.setId(1L);
        MatchStatisticItem matchStatisticItem2 = new MatchStatisticItem();
        matchStatisticItem2.setId(matchStatisticItem1.getId());
        assertThat(matchStatisticItem1).isEqualTo(matchStatisticItem2);
        matchStatisticItem2.setId(2L);
        assertThat(matchStatisticItem1).isNotEqualTo(matchStatisticItem2);
        matchStatisticItem1.setId(null);
        assertThat(matchStatisticItem1).isNotEqualTo(matchStatisticItem2);
    }
}
