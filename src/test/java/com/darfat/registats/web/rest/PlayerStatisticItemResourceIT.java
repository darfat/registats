package com.darfat.registats.web.rest;

import com.darfat.registats.RegistatsApp;
import com.darfat.registats.domain.PlayerStatisticItem;
import com.darfat.registats.repository.PlayerStatisticItemRepository;
import com.darfat.registats.repository.search.PlayerStatisticItemSearchRepository;
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
 * Integration tests for the {@link PlayerStatisticItemResource} REST controller.
 */
@SpringBootTest(classes = RegistatsApp.class)
public class PlayerStatisticItemResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private PlayerStatisticItemRepository playerStatisticItemRepository;

    /**
     * This repository is mocked in the com.darfat.registats.repository.search test package.
     *
     * @see com.darfat.registats.repository.search.PlayerStatisticItemSearchRepositoryMockConfiguration
     */
    @Autowired
    private PlayerStatisticItemSearchRepository mockPlayerStatisticItemSearchRepository;

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

    private MockMvc restPlayerStatisticItemMockMvc;

    private PlayerStatisticItem playerStatisticItem;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PlayerStatisticItemResource playerStatisticItemResource = new PlayerStatisticItemResource(playerStatisticItemRepository, mockPlayerStatisticItemSearchRepository);
        this.restPlayerStatisticItemMockMvc = MockMvcBuilders.standaloneSetup(playerStatisticItemResource)
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
    public static PlayerStatisticItem createEntity(EntityManager em) {
        PlayerStatisticItem playerStatisticItem = new PlayerStatisticItem()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .active(DEFAULT_ACTIVE);
        return playerStatisticItem;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlayerStatisticItem createUpdatedEntity(EntityManager em) {
        PlayerStatisticItem playerStatisticItem = new PlayerStatisticItem()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .active(UPDATED_ACTIVE);
        return playerStatisticItem;
    }

    @BeforeEach
    public void initTest() {
        playerStatisticItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createPlayerStatisticItem() throws Exception {
        int databaseSizeBeforeCreate = playerStatisticItemRepository.findAll().size();

        // Create the PlayerStatisticItem
        restPlayerStatisticItemMockMvc.perform(post("/api/player-statistic-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(playerStatisticItem)))
            .andExpect(status().isCreated());

        // Validate the PlayerStatisticItem in the database
        List<PlayerStatisticItem> playerStatisticItemList = playerStatisticItemRepository.findAll();
        assertThat(playerStatisticItemList).hasSize(databaseSizeBeforeCreate + 1);
        PlayerStatisticItem testPlayerStatisticItem = playerStatisticItemList.get(playerStatisticItemList.size() - 1);
        assertThat(testPlayerStatisticItem.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPlayerStatisticItem.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPlayerStatisticItem.isActive()).isEqualTo(DEFAULT_ACTIVE);

        // Validate the PlayerStatisticItem in Elasticsearch
        verify(mockPlayerStatisticItemSearchRepository, times(1)).save(testPlayerStatisticItem);
    }

    @Test
    @Transactional
    public void createPlayerStatisticItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = playerStatisticItemRepository.findAll().size();

        // Create the PlayerStatisticItem with an existing ID
        playerStatisticItem.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlayerStatisticItemMockMvc.perform(post("/api/player-statistic-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(playerStatisticItem)))
            .andExpect(status().isBadRequest());

        // Validate the PlayerStatisticItem in the database
        List<PlayerStatisticItem> playerStatisticItemList = playerStatisticItemRepository.findAll();
        assertThat(playerStatisticItemList).hasSize(databaseSizeBeforeCreate);

        // Validate the PlayerStatisticItem in Elasticsearch
        verify(mockPlayerStatisticItemSearchRepository, times(0)).save(playerStatisticItem);
    }


    @Test
    @Transactional
    public void getAllPlayerStatisticItems() throws Exception {
        // Initialize the database
        playerStatisticItemRepository.saveAndFlush(playerStatisticItem);

        // Get all the playerStatisticItemList
        restPlayerStatisticItemMockMvc.perform(get("/api/player-statistic-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(playerStatisticItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getPlayerStatisticItem() throws Exception {
        // Initialize the database
        playerStatisticItemRepository.saveAndFlush(playerStatisticItem);

        // Get the playerStatisticItem
        restPlayerStatisticItemMockMvc.perform(get("/api/player-statistic-items/{id}", playerStatisticItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(playerStatisticItem.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPlayerStatisticItem() throws Exception {
        // Get the playerStatisticItem
        restPlayerStatisticItemMockMvc.perform(get("/api/player-statistic-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlayerStatisticItem() throws Exception {
        // Initialize the database
        playerStatisticItemRepository.saveAndFlush(playerStatisticItem);

        int databaseSizeBeforeUpdate = playerStatisticItemRepository.findAll().size();

        // Update the playerStatisticItem
        PlayerStatisticItem updatedPlayerStatisticItem = playerStatisticItemRepository.findById(playerStatisticItem.getId()).get();
        // Disconnect from session so that the updates on updatedPlayerStatisticItem are not directly saved in db
        em.detach(updatedPlayerStatisticItem);
        updatedPlayerStatisticItem
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .active(UPDATED_ACTIVE);

        restPlayerStatisticItemMockMvc.perform(put("/api/player-statistic-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPlayerStatisticItem)))
            .andExpect(status().isOk());

        // Validate the PlayerStatisticItem in the database
        List<PlayerStatisticItem> playerStatisticItemList = playerStatisticItemRepository.findAll();
        assertThat(playerStatisticItemList).hasSize(databaseSizeBeforeUpdate);
        PlayerStatisticItem testPlayerStatisticItem = playerStatisticItemList.get(playerStatisticItemList.size() - 1);
        assertThat(testPlayerStatisticItem.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPlayerStatisticItem.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPlayerStatisticItem.isActive()).isEqualTo(UPDATED_ACTIVE);

        // Validate the PlayerStatisticItem in Elasticsearch
        verify(mockPlayerStatisticItemSearchRepository, times(1)).save(testPlayerStatisticItem);
    }

    @Test
    @Transactional
    public void updateNonExistingPlayerStatisticItem() throws Exception {
        int databaseSizeBeforeUpdate = playerStatisticItemRepository.findAll().size();

        // Create the PlayerStatisticItem

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlayerStatisticItemMockMvc.perform(put("/api/player-statistic-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(playerStatisticItem)))
            .andExpect(status().isBadRequest());

        // Validate the PlayerStatisticItem in the database
        List<PlayerStatisticItem> playerStatisticItemList = playerStatisticItemRepository.findAll();
        assertThat(playerStatisticItemList).hasSize(databaseSizeBeforeUpdate);

        // Validate the PlayerStatisticItem in Elasticsearch
        verify(mockPlayerStatisticItemSearchRepository, times(0)).save(playerStatisticItem);
    }

    @Test
    @Transactional
    public void deletePlayerStatisticItem() throws Exception {
        // Initialize the database
        playerStatisticItemRepository.saveAndFlush(playerStatisticItem);

        int databaseSizeBeforeDelete = playerStatisticItemRepository.findAll().size();

        // Delete the playerStatisticItem
        restPlayerStatisticItemMockMvc.perform(delete("/api/player-statistic-items/{id}", playerStatisticItem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PlayerStatisticItem> playerStatisticItemList = playerStatisticItemRepository.findAll();
        assertThat(playerStatisticItemList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the PlayerStatisticItem in Elasticsearch
        verify(mockPlayerStatisticItemSearchRepository, times(1)).deleteById(playerStatisticItem.getId());
    }

    @Test
    @Transactional
    public void searchPlayerStatisticItem() throws Exception {
        // Initialize the database
        playerStatisticItemRepository.saveAndFlush(playerStatisticItem);
        when(mockPlayerStatisticItemSearchRepository.search(queryStringQuery("id:" + playerStatisticItem.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(playerStatisticItem), PageRequest.of(0, 1), 1));
        // Search the playerStatisticItem
        restPlayerStatisticItemMockMvc.perform(get("/api/_search/player-statistic-items?query=id:" + playerStatisticItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(playerStatisticItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlayerStatisticItem.class);
        PlayerStatisticItem playerStatisticItem1 = new PlayerStatisticItem();
        playerStatisticItem1.setId(1L);
        PlayerStatisticItem playerStatisticItem2 = new PlayerStatisticItem();
        playerStatisticItem2.setId(playerStatisticItem1.getId());
        assertThat(playerStatisticItem1).isEqualTo(playerStatisticItem2);
        playerStatisticItem2.setId(2L);
        assertThat(playerStatisticItem1).isNotEqualTo(playerStatisticItem2);
        playerStatisticItem1.setId(null);
        assertThat(playerStatisticItem1).isNotEqualTo(playerStatisticItem2);
    }
}
