package com.darfat.registats.web.rest;

import com.darfat.registats.domain.CompetitionTeam;
import com.darfat.registats.repository.CompetitionTeamRepository;
import com.darfat.registats.repository.search.CompetitionTeamSearchRepository;
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
 * REST controller for managing {@link com.darfat.registats.domain.CompetitionTeam}.
 */
@RestController
@RequestMapping("/api")
public class CompetitionTeamResource {

    private final Logger log = LoggerFactory.getLogger(CompetitionTeamResource.class);

    private static final String ENTITY_NAME = "competitionTeam";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CompetitionTeamRepository competitionTeamRepository;

    private final CompetitionTeamSearchRepository competitionTeamSearchRepository;

    public CompetitionTeamResource(CompetitionTeamRepository competitionTeamRepository, CompetitionTeamSearchRepository competitionTeamSearchRepository) {
        this.competitionTeamRepository = competitionTeamRepository;
        this.competitionTeamSearchRepository = competitionTeamSearchRepository;
    }

    /**
     * {@code POST  /competition-teams} : Create a new competitionTeam.
     *
     * @param competitionTeam the competitionTeam to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new competitionTeam, or with status {@code 400 (Bad Request)} if the competitionTeam has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/competition-teams")
    public ResponseEntity<CompetitionTeam> createCompetitionTeam(@RequestBody CompetitionTeam competitionTeam) throws URISyntaxException {
        log.debug("REST request to save CompetitionTeam : {}", competitionTeam);
        if (competitionTeam.getId() != null) {
            throw new BadRequestAlertException("A new competitionTeam cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompetitionTeam result = competitionTeamRepository.save(competitionTeam);
        competitionTeamSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/competition-teams/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /competition-teams} : Updates an existing competitionTeam.
     *
     * @param competitionTeam the competitionTeam to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated competitionTeam,
     * or with status {@code 400 (Bad Request)} if the competitionTeam is not valid,
     * or with status {@code 500 (Internal Server Error)} if the competitionTeam couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/competition-teams")
    public ResponseEntity<CompetitionTeam> updateCompetitionTeam(@RequestBody CompetitionTeam competitionTeam) throws URISyntaxException {
        log.debug("REST request to update CompetitionTeam : {}", competitionTeam);
        if (competitionTeam.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CompetitionTeam result = competitionTeamRepository.save(competitionTeam);
        competitionTeamSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, competitionTeam.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /competition-teams} : get all the competitionTeams.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of competitionTeams in body.
     */
    @GetMapping("/competition-teams")
    public ResponseEntity<List<CompetitionTeam>> getAllCompetitionTeams(Pageable pageable) {
        log.debug("REST request to get a page of CompetitionTeams");
        Page<CompetitionTeam> page = competitionTeamRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /competition-teams/:id} : get the "id" competitionTeam.
     *
     * @param id the id of the competitionTeam to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the competitionTeam, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/competition-teams/{id}")
    public ResponseEntity<CompetitionTeam> getCompetitionTeam(@PathVariable Long id) {
        log.debug("REST request to get CompetitionTeam : {}", id);
        Optional<CompetitionTeam> competitionTeam = competitionTeamRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(competitionTeam);
    }

    /**
     * {@code DELETE  /competition-teams/:id} : delete the "id" competitionTeam.
     *
     * @param id the id of the competitionTeam to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/competition-teams/{id}")
    public ResponseEntity<Void> deleteCompetitionTeam(@PathVariable Long id) {
        log.debug("REST request to delete CompetitionTeam : {}", id);
        competitionTeamRepository.deleteById(id);
        competitionTeamSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/competition-teams?query=:query} : search for the competitionTeam corresponding
     * to the query.
     *
     * @param query the query of the competitionTeam search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/competition-teams")
    public ResponseEntity<List<CompetitionTeam>> searchCompetitionTeams(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of CompetitionTeams for query {}", query);
        Page<CompetitionTeam> page = competitionTeamSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
