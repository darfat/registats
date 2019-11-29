package com.darfat.registats.web.rest;

import com.darfat.registats.domain.CompetitionStanding;
import com.darfat.registats.repository.CompetitionStandingRepository;
import com.darfat.registats.repository.search.CompetitionStandingSearchRepository;
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

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link com.darfat.registats.domain.CompetitionStanding}.
 */
@RestController
@RequestMapping("/api")
public class CompetitionStandingResource {

    private final Logger log = LoggerFactory.getLogger(CompetitionStandingResource.class);

    private static final String ENTITY_NAME = "competitionStanding";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CompetitionStandingRepository competitionStandingRepository;

    private final CompetitionStandingSearchRepository competitionStandingSearchRepository;

    public CompetitionStandingResource(CompetitionStandingRepository competitionStandingRepository, CompetitionStandingSearchRepository competitionStandingSearchRepository) {
        this.competitionStandingRepository = competitionStandingRepository;
        this.competitionStandingSearchRepository = competitionStandingSearchRepository;
    }

    /**
     * {@code POST  /competition-standings} : Create a new competitionStanding.
     *
     * @param competitionStanding the competitionStanding to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new competitionStanding, or with status {@code 400 (Bad Request)} if the competitionStanding has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/competition-standings")
    public ResponseEntity<CompetitionStanding> createCompetitionStanding(@RequestBody CompetitionStanding competitionStanding) throws URISyntaxException {
        log.debug("REST request to save CompetitionStanding : {}", competitionStanding);
        if (competitionStanding.getId() != null) {
            throw new BadRequestAlertException("A new competitionStanding cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompetitionStanding result = competitionStandingRepository.save(competitionStanding);
        competitionStandingSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/competition-standings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /competition-standings} : Updates an existing competitionStanding.
     *
     * @param competitionStanding the competitionStanding to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated competitionStanding,
     * or with status {@code 400 (Bad Request)} if the competitionStanding is not valid,
     * or with status {@code 500 (Internal Server Error)} if the competitionStanding couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/competition-standings")
    public ResponseEntity<CompetitionStanding> updateCompetitionStanding(@RequestBody CompetitionStanding competitionStanding) throws URISyntaxException {
        log.debug("REST request to update CompetitionStanding : {}", competitionStanding);
        if (competitionStanding.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CompetitionStanding result = competitionStandingRepository.save(competitionStanding);
        competitionStandingSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, competitionStanding.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /competition-standings} : get all the competitionStandings.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of competitionStandings in body.
     */
    @GetMapping("/competition-standings")
    public ResponseEntity<List<CompetitionStanding>> getAllCompetitionStandings(Pageable pageable) {
        log.debug("REST request to get a page of CompetitionStandings");
        Page<CompetitionStanding> page = competitionStandingRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /competition-standings/:id} : get the "id" competitionStanding.
     *
     * @param id the id of the competitionStanding to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the competitionStanding, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/competition-standings/{id}")
    public ResponseEntity<CompetitionStanding> getCompetitionStanding(@PathVariable Long id) {
        log.debug("REST request to get CompetitionStanding : {}", id);
        Optional<CompetitionStanding> competitionStanding = competitionStandingRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(competitionStanding);
    }

    /**
     * {@code DELETE  /competition-standings/:id} : delete the "id" competitionStanding.
     *
     * @param id the id of the competitionStanding to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/competition-standings/{id}")
    public ResponseEntity<Void> deleteCompetitionStanding(@PathVariable Long id) {
        log.debug("REST request to delete CompetitionStanding : {}", id);
        competitionStandingRepository.deleteById(id);
        competitionStandingSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/competition-standings?query=:query} : search for the competitionStanding corresponding
     * to the query.
     *
     * @param query the query of the competitionStanding search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/competition-standings")
    public ResponseEntity<List<CompetitionStanding>> searchCompetitionStandings(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of CompetitionStandings for query {}", query);
        Page<CompetitionStanding> page = competitionStandingSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
