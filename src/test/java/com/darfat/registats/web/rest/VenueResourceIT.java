package com.darfat.registats.web.rest;

import com.darfat.registats.RegistatsApp;
import com.darfat.registats.domain.Venue;
import com.darfat.registats.repository.VenueRepository;
import com.darfat.registats.repository.search.VenueSearchRepository;
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
 * Integration tests for the {@link VenueResource} REST controller.
 */
@SpringBootTest(classes = RegistatsApp.class)
public class VenueResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION = "BBBBBBBBBB";

    @Autowired
    private VenueRepository venueRepository;

    /**
     * This repository is mocked in the com.darfat.registats.repository.search test package.
     *
     * @see com.darfat.registats.repository.search.VenueSearchRepositoryMockConfiguration
     */
    @Autowired
    private VenueSearchRepository mockVenueSearchRepository;

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

    private MockMvc restVenueMockMvc;

    private Venue venue;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VenueResource venueResource = new VenueResource(venueRepository, mockVenueSearchRepository);
        this.restVenueMockMvc = MockMvcBuilders.standaloneSetup(venueResource)
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
    public static Venue createEntity(EntityManager em) {
        Venue venue = new Venue()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .address(DEFAULT_ADDRESS)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .location(DEFAULT_LOCATION);
        return venue;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Venue createUpdatedEntity(EntityManager em) {
        Venue venue = new Venue()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .address(UPDATED_ADDRESS)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .location(UPDATED_LOCATION);
        return venue;
    }

    @BeforeEach
    public void initTest() {
        venue = createEntity(em);
    }

    @Test
    @Transactional
    public void createVenue() throws Exception {
        int databaseSizeBeforeCreate = venueRepository.findAll().size();

        // Create the Venue
        restVenueMockMvc.perform(post("/api/venues")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(venue)))
            .andExpect(status().isCreated());

        // Validate the Venue in the database
        List<Venue> venueList = venueRepository.findAll();
        assertThat(venueList).hasSize(databaseSizeBeforeCreate + 1);
        Venue testVenue = venueList.get(venueList.size() - 1);
        assertThat(testVenue.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testVenue.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testVenue.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testVenue.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testVenue.getLocation()).isEqualTo(DEFAULT_LOCATION);

        // Validate the Venue in Elasticsearch
        verify(mockVenueSearchRepository, times(1)).save(testVenue);
    }

    @Test
    @Transactional
    public void createVenueWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = venueRepository.findAll().size();

        // Create the Venue with an existing ID
        venue.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVenueMockMvc.perform(post("/api/venues")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(venue)))
            .andExpect(status().isBadRequest());

        // Validate the Venue in the database
        List<Venue> venueList = venueRepository.findAll();
        assertThat(venueList).hasSize(databaseSizeBeforeCreate);

        // Validate the Venue in Elasticsearch
        verify(mockVenueSearchRepository, times(0)).save(venue);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = venueRepository.findAll().size();
        // set the field null
        venue.setName(null);

        // Create the Venue, which fails.

        restVenueMockMvc.perform(post("/api/venues")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(venue)))
            .andExpect(status().isBadRequest());

        List<Venue> venueList = venueRepository.findAll();
        assertThat(venueList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVenues() throws Exception {
        // Initialize the database
        venueRepository.saveAndFlush(venue);

        // Get all the venueList
        restVenueMockMvc.perform(get("/api/venues?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(venue.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION)));
    }
    
    @Test
    @Transactional
    public void getVenue() throws Exception {
        // Initialize the database
        venueRepository.saveAndFlush(venue);

        // Get the venue
        restVenueMockMvc.perform(get("/api/venues/{id}", venue.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(venue.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION));
    }

    @Test
    @Transactional
    public void getNonExistingVenue() throws Exception {
        // Get the venue
        restVenueMockMvc.perform(get("/api/venues/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVenue() throws Exception {
        // Initialize the database
        venueRepository.saveAndFlush(venue);

        int databaseSizeBeforeUpdate = venueRepository.findAll().size();

        // Update the venue
        Venue updatedVenue = venueRepository.findById(venue.getId()).get();
        // Disconnect from session so that the updates on updatedVenue are not directly saved in db
        em.detach(updatedVenue);
        updatedVenue
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .address(UPDATED_ADDRESS)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .location(UPDATED_LOCATION);

        restVenueMockMvc.perform(put("/api/venues")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedVenue)))
            .andExpect(status().isOk());

        // Validate the Venue in the database
        List<Venue> venueList = venueRepository.findAll();
        assertThat(venueList).hasSize(databaseSizeBeforeUpdate);
        Venue testVenue = venueList.get(venueList.size() - 1);
        assertThat(testVenue.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testVenue.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testVenue.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testVenue.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testVenue.getLocation()).isEqualTo(UPDATED_LOCATION);

        // Validate the Venue in Elasticsearch
        verify(mockVenueSearchRepository, times(1)).save(testVenue);
    }

    @Test
    @Transactional
    public void updateNonExistingVenue() throws Exception {
        int databaseSizeBeforeUpdate = venueRepository.findAll().size();

        // Create the Venue

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVenueMockMvc.perform(put("/api/venues")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(venue)))
            .andExpect(status().isBadRequest());

        // Validate the Venue in the database
        List<Venue> venueList = venueRepository.findAll();
        assertThat(venueList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Venue in Elasticsearch
        verify(mockVenueSearchRepository, times(0)).save(venue);
    }

    @Test
    @Transactional
    public void deleteVenue() throws Exception {
        // Initialize the database
        venueRepository.saveAndFlush(venue);

        int databaseSizeBeforeDelete = venueRepository.findAll().size();

        // Delete the venue
        restVenueMockMvc.perform(delete("/api/venues/{id}", venue.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Venue> venueList = venueRepository.findAll();
        assertThat(venueList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Venue in Elasticsearch
        verify(mockVenueSearchRepository, times(1)).deleteById(venue.getId());
    }

    @Test
    @Transactional
    public void searchVenue() throws Exception {
        // Initialize the database
        venueRepository.saveAndFlush(venue);
        when(mockVenueSearchRepository.search(queryStringQuery("id:" + venue.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(venue), PageRequest.of(0, 1), 1));
        // Search the venue
        restVenueMockMvc.perform(get("/api/_search/venues?query=id:" + venue.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(venue.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Venue.class);
        Venue venue1 = new Venue();
        venue1.setId(1L);
        Venue venue2 = new Venue();
        venue2.setId(venue1.getId());
        assertThat(venue1).isEqualTo(venue2);
        venue2.setId(2L);
        assertThat(venue1).isNotEqualTo(venue2);
        venue1.setId(null);
        assertThat(venue1).isNotEqualTo(venue2);
    }
}
