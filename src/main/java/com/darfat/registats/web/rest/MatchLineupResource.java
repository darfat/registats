package com.darfat.registats.web.rest;

import com.darfat.registats.domain.MatchLineup;
import com.darfat.registats.repository.MatchLineupRepository;
import com.darfat.registats.repository.search.MatchLineupSearchRepository;
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
 * REST controller for managing {@link com.darfat.registats.domain.MatchLineup}.
 */
@RestController
@RequestMapping("/api")
public class MatchLineupResource {

    private final Logger log = LoggerFactory.getLogger(MatchLineupResource.class);

    private static final String ENTITY_NAME = "matchLineup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MatchLineupRepository matchLineupRepository;

    private final MatchLineupSearchRepository matchLineupSearchRepository;

    public MatchLineupResource(MatchLineupRepository matchLineupRepository, MatchLineupSearchRepository matchLineupSearchRepository) {
        this.matchLineupRepository = matchLineupRepository;
        this.matchLineupSearchRepository = matchLineupSearchRepository;
    }

    /**
     * {@code POST  /match-lineups} : Create a new matchLineup.
     *
     * @param matchLineup the matchLineup to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new matchLineup, or with status {@code 400 (Bad Request)} if the matchLineup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/match-lineups")
    public ResponseEntity<MatchLineup> createMatchLineup(@RequestBody MatchLineup matchLineup) throws URISyntaxException {
        log.debug("REST request to save MatchLineup : {}", matchLineup);
        if (matchLineup.getId() != null) {
            throw new BadRequestAlertException("A new matchLineup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MatchLineup result = matchLineupRepository.save(matchLineup);
        matchLineupSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/match-lineups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /match-lineups} : Updates an existing matchLineup.
     *
     * @param matchLineup the matchLineup to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated matchLineup,
     * or with status {@code 400 (Bad Request)} if the matchLineup is not valid,
     * or with status {@code 500 (Internal Server Error)} if the matchLineup couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/match-lineups")
    public ResponseEntity<MatchLineup> updateMatchLineup(@RequestBody MatchLineup matchLineup) throws URISyntaxException {
        log.debug("REST request to update MatchLineup : {}", matchLineup);
        if (matchLineup.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MatchLineup result = matchLineupRepository.save(matchLineup);
        matchLineupSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, matchLineup.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /match-lineups} : get all the matchLineups.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of matchLineups in body.
     */
    @GetMapping("/match-lineups")
    public ResponseEntity<List<MatchLineup>> getAllMatchLineups(Pageable pageable) {
        log.debug("REST request to get a page of MatchLineups");
        Page<MatchLineup> page = matchLineupRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /match-lineups/:id} : get the "id" matchLineup.
     *
     * @param id the id of the matchLineup to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the matchLineup, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/match-lineups/{id}")
    public ResponseEntity<MatchLineup> getMatchLineup(@PathVariable Long id) {
        log.debug("REST request to get MatchLineup : {}", id);
        Optional<MatchLineup> matchLineup = matchLineupRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(matchLineup);
    }

    /**
     * {@code DELETE  /match-lineups/:id} : delete the "id" matchLineup.
     *
     * @param id the id of the matchLineup to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/match-lineups/{id}")
    public ResponseEntity<Void> deleteMatchLineup(@PathVariable Long id) {
        log.debug("REST request to delete MatchLineup : {}", id);
        matchLineupRepository.deleteById(id);
        matchLineupSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/match-lineups?query=:query} : search for the matchLineup corresponding
     * to the query.
     *
     * @param query the query of the matchLineup search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/match-lineups")
    public ResponseEntity<List<MatchLineup>> searchMatchLineups(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of MatchLineups for query {}", query);
        Page<MatchLineup> page = matchLineupSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
