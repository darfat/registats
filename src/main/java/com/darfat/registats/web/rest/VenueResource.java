package com.darfat.registats.web.rest;

import com.darfat.registats.domain.Venue;
import com.darfat.registats.repository.VenueRepository;
import com.darfat.registats.repository.search.VenueSearchRepository;
import com.darfat.registats.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link com.darfat.registats.domain.Venue}.
 */
@RestController
@RequestMapping("/api")
public class VenueResource {

    private final Logger log = LoggerFactory.getLogger(VenueResource.class);

    private static final String ENTITY_NAME = "venue";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VenueRepository venueRepository;

    private final VenueSearchRepository venueSearchRepository;

    public VenueResource(VenueRepository venueRepository, VenueSearchRepository venueSearchRepository) {
        this.venueRepository = venueRepository;
        this.venueSearchRepository = venueSearchRepository;
    }

    /**
     * {@code POST  /venues} : Create a new venue.
     *
     * @param venue the venue to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new venue, or with status {@code 400 (Bad Request)} if the venue has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/venues")
    public ResponseEntity<Venue> createVenue(@Valid @RequestBody Venue venue) throws URISyntaxException {
        log.debug("REST request to save Venue : {}", venue);
        if (venue.getId() != null) {
            throw new BadRequestAlertException("A new venue cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Venue result = venueRepository.save(venue);
        venueSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/venues/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /venues} : Updates an existing venue.
     *
     * @param venue the venue to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated venue,
     * or with status {@code 400 (Bad Request)} if the venue is not valid,
     * or with status {@code 500 (Internal Server Error)} if the venue couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/venues")
    public ResponseEntity<Venue> updateVenue(@Valid @RequestBody Venue venue) throws URISyntaxException {
        log.debug("REST request to update Venue : {}", venue);
        if (venue.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Venue result = venueRepository.save(venue);
        venueSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, venue.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /venues} : get all the venues.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of venues in body.
     */
    @GetMapping("/venues")
    public ResponseEntity<List<Venue>> getAllVenues(Pageable pageable) {
        log.debug("REST request to get a page of Venues");
        Page<Venue> page = venueRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /venues/:id} : get the "id" venue.
     *
     * @param id the id of the venue to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the venue, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/venues/{id}")
    public ResponseEntity<Venue> getVenue(@PathVariable Long id) {
        log.debug("REST request to get Venue : {}", id);
        Optional<Venue> venue = venueRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(venue);
    }

    /**
     * {@code DELETE  /venues/:id} : delete the "id" venue.
     *
     * @param id the id of the venue to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/venues/{id}")
    public ResponseEntity<Void> deleteVenue(@PathVariable Long id) {
        log.debug("REST request to delete Venue : {}", id);
        venueRepository.deleteById(id);
        venueSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/venues?query=:query} : search for the venue corresponding
     * to the query.
     *
     * @param query the query of the venue search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/venues")
    public ResponseEntity<List<Venue>> searchVenues(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Venues for query {}", query);
        Page<Venue> page = venueSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
