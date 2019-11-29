package com.darfat.registats.web.rest;

import com.darfat.registats.domain.MatchStatistic;
import com.darfat.registats.repository.MatchStatisticRepository;
import com.darfat.registats.repository.search.MatchStatisticSearchRepository;
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
 * REST controller for managing {@link com.darfat.registats.domain.MatchStatistic}.
 */
@RestController
@RequestMapping("/api")
public class MatchStatisticResource {

    private final Logger log = LoggerFactory.getLogger(MatchStatisticResource.class);

    private static final String ENTITY_NAME = "matchStatistic";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MatchStatisticRepository matchStatisticRepository;

    private final MatchStatisticSearchRepository matchStatisticSearchRepository;

    public MatchStatisticResource(MatchStatisticRepository matchStatisticRepository, MatchStatisticSearchRepository matchStatisticSearchRepository) {
        this.matchStatisticRepository = matchStatisticRepository;
        this.matchStatisticSearchRepository = matchStatisticSearchRepository;
    }

    /**
     * {@code POST  /match-statistics} : Create a new matchStatistic.
     *
     * @param matchStatistic the matchStatistic to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new matchStatistic, or with status {@code 400 (Bad Request)} if the matchStatistic has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/match-statistics")
    public ResponseEntity<MatchStatistic> createMatchStatistic(@RequestBody MatchStatistic matchStatistic) throws URISyntaxException {
        log.debug("REST request to save MatchStatistic : {}", matchStatistic);
        if (matchStatistic.getId() != null) {
            throw new BadRequestAlertException("A new matchStatistic cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MatchStatistic result = matchStatisticRepository.save(matchStatistic);
        matchStatisticSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/match-statistics/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /match-statistics} : Updates an existing matchStatistic.
     *
     * @param matchStatistic the matchStatistic to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated matchStatistic,
     * or with status {@code 400 (Bad Request)} if the matchStatistic is not valid,
     * or with status {@code 500 (Internal Server Error)} if the matchStatistic couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/match-statistics")
    public ResponseEntity<MatchStatistic> updateMatchStatistic(@RequestBody MatchStatistic matchStatistic) throws URISyntaxException {
        log.debug("REST request to update MatchStatistic : {}", matchStatistic);
        if (matchStatistic.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MatchStatistic result = matchStatisticRepository.save(matchStatistic);
        matchStatisticSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, matchStatistic.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /match-statistics} : get all the matchStatistics.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of matchStatistics in body.
     */
    @GetMapping("/match-statistics")
    public ResponseEntity<List<MatchStatistic>> getAllMatchStatistics(Pageable pageable) {
        log.debug("REST request to get a page of MatchStatistics");
        Page<MatchStatistic> page = matchStatisticRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /match-statistics/:id} : get the "id" matchStatistic.
     *
     * @param id the id of the matchStatistic to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the matchStatistic, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/match-statistics/{id}")
    public ResponseEntity<MatchStatistic> getMatchStatistic(@PathVariable Long id) {
        log.debug("REST request to get MatchStatistic : {}", id);
        Optional<MatchStatistic> matchStatistic = matchStatisticRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(matchStatistic);
    }

    /**
     * {@code DELETE  /match-statistics/:id} : delete the "id" matchStatistic.
     *
     * @param id the id of the matchStatistic to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/match-statistics/{id}")
    public ResponseEntity<Void> deleteMatchStatistic(@PathVariable Long id) {
        log.debug("REST request to delete MatchStatistic : {}", id);
        matchStatisticRepository.deleteById(id);
        matchStatisticSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/match-statistics?query=:query} : search for the matchStatistic corresponding
     * to the query.
     *
     * @param query the query of the matchStatistic search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/match-statistics")
    public ResponseEntity<List<MatchStatistic>> searchMatchStatistics(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of MatchStatistics for query {}", query);
        Page<MatchStatistic> page = matchStatisticSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
