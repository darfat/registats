package com.darfat.registats.web.rest;

import com.darfat.registats.domain.CompetitionPlayerStatistic;
import com.darfat.registats.repository.CompetitionPlayerStatisticRepository;
import com.darfat.registats.repository.search.CompetitionPlayerStatisticSearchRepository;
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
 * REST controller for managing {@link com.darfat.registats.domain.CompetitionPlayerStatistic}.
 */
@RestController
@RequestMapping("/api")
public class CompetitionPlayerStatisticResource {

    private final Logger log = LoggerFactory.getLogger(CompetitionPlayerStatisticResource.class);

    private static final String ENTITY_NAME = "competitionPlayerStatistic";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CompetitionPlayerStatisticRepository competitionPlayerStatisticRepository;

    private final CompetitionPlayerStatisticSearchRepository competitionPlayerStatisticSearchRepository;

    public CompetitionPlayerStatisticResource(CompetitionPlayerStatisticRepository competitionPlayerStatisticRepository, CompetitionPlayerStatisticSearchRepository competitionPlayerStatisticSearchRepository) {
        this.competitionPlayerStatisticRepository = competitionPlayerStatisticRepository;
        this.competitionPlayerStatisticSearchRepository = competitionPlayerStatisticSearchRepository;
    }

    /**
     * {@code POST  /competition-player-statistics} : Create a new competitionPlayerStatistic.
     *
     * @param competitionPlayerStatistic the competitionPlayerStatistic to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new competitionPlayerStatistic, or with status {@code 400 (Bad Request)} if the competitionPlayerStatistic has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/competition-player-statistics")
    public ResponseEntity<CompetitionPlayerStatistic> createCompetitionPlayerStatistic(@RequestBody CompetitionPlayerStatistic competitionPlayerStatistic) throws URISyntaxException {
        log.debug("REST request to save CompetitionPlayerStatistic : {}", competitionPlayerStatistic);
        if (competitionPlayerStatistic.getId() != null) {
            throw new BadRequestAlertException("A new competitionPlayerStatistic cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompetitionPlayerStatistic result = competitionPlayerStatisticRepository.save(competitionPlayerStatistic);
        competitionPlayerStatisticSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/competition-player-statistics/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /competition-player-statistics} : Updates an existing competitionPlayerStatistic.
     *
     * @param competitionPlayerStatistic the competitionPlayerStatistic to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated competitionPlayerStatistic,
     * or with status {@code 400 (Bad Request)} if the competitionPlayerStatistic is not valid,
     * or with status {@code 500 (Internal Server Error)} if the competitionPlayerStatistic couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/competition-player-statistics")
    public ResponseEntity<CompetitionPlayerStatistic> updateCompetitionPlayerStatistic(@RequestBody CompetitionPlayerStatistic competitionPlayerStatistic) throws URISyntaxException {
        log.debug("REST request to update CompetitionPlayerStatistic : {}", competitionPlayerStatistic);
        if (competitionPlayerStatistic.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CompetitionPlayerStatistic result = competitionPlayerStatisticRepository.save(competitionPlayerStatistic);
        competitionPlayerStatisticSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, competitionPlayerStatistic.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /competition-player-statistics} : get all the competitionPlayerStatistics.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of competitionPlayerStatistics in body.
     */
    @GetMapping("/competition-player-statistics")
    public ResponseEntity<List<CompetitionPlayerStatistic>> getAllCompetitionPlayerStatistics(Pageable pageable) {
        log.debug("REST request to get a page of CompetitionPlayerStatistics");
        Page<CompetitionPlayerStatistic> page = competitionPlayerStatisticRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /competition-player-statistics/:id} : get the "id" competitionPlayerStatistic.
     *
     * @param id the id of the competitionPlayerStatistic to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the competitionPlayerStatistic, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/competition-player-statistics/{id}")
    public ResponseEntity<CompetitionPlayerStatistic> getCompetitionPlayerStatistic(@PathVariable Long id) {
        log.debug("REST request to get CompetitionPlayerStatistic : {}", id);
        Optional<CompetitionPlayerStatistic> competitionPlayerStatistic = competitionPlayerStatisticRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(competitionPlayerStatistic);
    }

    /**
     * {@code DELETE  /competition-player-statistics/:id} : delete the "id" competitionPlayerStatistic.
     *
     * @param id the id of the competitionPlayerStatistic to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/competition-player-statistics/{id}")
    public ResponseEntity<Void> deleteCompetitionPlayerStatistic(@PathVariable Long id) {
        log.debug("REST request to delete CompetitionPlayerStatistic : {}", id);
        competitionPlayerStatisticRepository.deleteById(id);
        competitionPlayerStatisticSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/competition-player-statistics?query=:query} : search for the competitionPlayerStatistic corresponding
     * to the query.
     *
     * @param query the query of the competitionPlayerStatistic search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/competition-player-statistics")
    public ResponseEntity<List<CompetitionPlayerStatistic>> searchCompetitionPlayerStatistics(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of CompetitionPlayerStatistics for query {}", query);
        Page<CompetitionPlayerStatistic> page = competitionPlayerStatisticSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
