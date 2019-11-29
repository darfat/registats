package com.darfat.registats.web.rest;

import com.darfat.registats.domain.CompetitionTeamStatistic;
import com.darfat.registats.repository.CompetitionTeamStatisticRepository;
import com.darfat.registats.repository.search.CompetitionTeamStatisticSearchRepository;
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
 * REST controller for managing {@link com.darfat.registats.domain.CompetitionTeamStatistic}.
 */
@RestController
@RequestMapping("/api")
public class CompetitionTeamStatisticResource {

    private final Logger log = LoggerFactory.getLogger(CompetitionTeamStatisticResource.class);

    private static final String ENTITY_NAME = "competitionTeamStatistic";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CompetitionTeamStatisticRepository competitionTeamStatisticRepository;

    private final CompetitionTeamStatisticSearchRepository competitionTeamStatisticSearchRepository;

    public CompetitionTeamStatisticResource(CompetitionTeamStatisticRepository competitionTeamStatisticRepository, CompetitionTeamStatisticSearchRepository competitionTeamStatisticSearchRepository) {
        this.competitionTeamStatisticRepository = competitionTeamStatisticRepository;
        this.competitionTeamStatisticSearchRepository = competitionTeamStatisticSearchRepository;
    }

    /**
     * {@code POST  /competition-team-statistics} : Create a new competitionTeamStatistic.
     *
     * @param competitionTeamStatistic the competitionTeamStatistic to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new competitionTeamStatistic, or with status {@code 400 (Bad Request)} if the competitionTeamStatistic has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/competition-team-statistics")
    public ResponseEntity<CompetitionTeamStatistic> createCompetitionTeamStatistic(@RequestBody CompetitionTeamStatistic competitionTeamStatistic) throws URISyntaxException {
        log.debug("REST request to save CompetitionTeamStatistic : {}", competitionTeamStatistic);
        if (competitionTeamStatistic.getId() != null) {
            throw new BadRequestAlertException("A new competitionTeamStatistic cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompetitionTeamStatistic result = competitionTeamStatisticRepository.save(competitionTeamStatistic);
        competitionTeamStatisticSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/competition-team-statistics/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /competition-team-statistics} : Updates an existing competitionTeamStatistic.
     *
     * @param competitionTeamStatistic the competitionTeamStatistic to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated competitionTeamStatistic,
     * or with status {@code 400 (Bad Request)} if the competitionTeamStatistic is not valid,
     * or with status {@code 500 (Internal Server Error)} if the competitionTeamStatistic couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/competition-team-statistics")
    public ResponseEntity<CompetitionTeamStatistic> updateCompetitionTeamStatistic(@RequestBody CompetitionTeamStatistic competitionTeamStatistic) throws URISyntaxException {
        log.debug("REST request to update CompetitionTeamStatistic : {}", competitionTeamStatistic);
        if (competitionTeamStatistic.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CompetitionTeamStatistic result = competitionTeamStatisticRepository.save(competitionTeamStatistic);
        competitionTeamStatisticSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, competitionTeamStatistic.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /competition-team-statistics} : get all the competitionTeamStatistics.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of competitionTeamStatistics in body.
     */
    @GetMapping("/competition-team-statistics")
    public ResponseEntity<List<CompetitionTeamStatistic>> getAllCompetitionTeamStatistics(Pageable pageable) {
        log.debug("REST request to get a page of CompetitionTeamStatistics");
        Page<CompetitionTeamStatistic> page = competitionTeamStatisticRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /competition-team-statistics/:id} : get the "id" competitionTeamStatistic.
     *
     * @param id the id of the competitionTeamStatistic to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the competitionTeamStatistic, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/competition-team-statistics/{id}")
    public ResponseEntity<CompetitionTeamStatistic> getCompetitionTeamStatistic(@PathVariable Long id) {
        log.debug("REST request to get CompetitionTeamStatistic : {}", id);
        Optional<CompetitionTeamStatistic> competitionTeamStatistic = competitionTeamStatisticRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(competitionTeamStatistic);
    }

    /**
     * {@code DELETE  /competition-team-statistics/:id} : delete the "id" competitionTeamStatistic.
     *
     * @param id the id of the competitionTeamStatistic to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/competition-team-statistics/{id}")
    public ResponseEntity<Void> deleteCompetitionTeamStatistic(@PathVariable Long id) {
        log.debug("REST request to delete CompetitionTeamStatistic : {}", id);
        competitionTeamStatisticRepository.deleteById(id);
        competitionTeamStatisticSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/competition-team-statistics?query=:query} : search for the competitionTeamStatistic corresponding
     * to the query.
     *
     * @param query the query of the competitionTeamStatistic search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/competition-team-statistics")
    public ResponseEntity<List<CompetitionTeamStatistic>> searchCompetitionTeamStatistics(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of CompetitionTeamStatistics for query {}", query);
        Page<CompetitionTeamStatistic> page = competitionTeamStatisticSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
