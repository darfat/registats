package com.darfat.registats.web.rest;

import com.darfat.registats.domain.MatchCommentary;
import com.darfat.registats.repository.MatchCommentaryRepository;
import com.darfat.registats.repository.search.MatchCommentarySearchRepository;
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
 * REST controller for managing {@link com.darfat.registats.domain.MatchCommentary}.
 */
@RestController
@RequestMapping("/api")
public class MatchCommentaryResource {

    private final Logger log = LoggerFactory.getLogger(MatchCommentaryResource.class);

    private static final String ENTITY_NAME = "matchCommentary";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MatchCommentaryRepository matchCommentaryRepository;

    private final MatchCommentarySearchRepository matchCommentarySearchRepository;

    public MatchCommentaryResource(MatchCommentaryRepository matchCommentaryRepository, MatchCommentarySearchRepository matchCommentarySearchRepository) {
        this.matchCommentaryRepository = matchCommentaryRepository;
        this.matchCommentarySearchRepository = matchCommentarySearchRepository;
    }

    /**
     * {@code POST  /match-commentaries} : Create a new matchCommentary.
     *
     * @param matchCommentary the matchCommentary to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new matchCommentary, or with status {@code 400 (Bad Request)} if the matchCommentary has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/match-commentaries")
    public ResponseEntity<MatchCommentary> createMatchCommentary(@RequestBody MatchCommentary matchCommentary) throws URISyntaxException {
        log.debug("REST request to save MatchCommentary : {}", matchCommentary);
        if (matchCommentary.getId() != null) {
            throw new BadRequestAlertException("A new matchCommentary cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MatchCommentary result = matchCommentaryRepository.save(matchCommentary);
        matchCommentarySearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/match-commentaries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /match-commentaries} : Updates an existing matchCommentary.
     *
     * @param matchCommentary the matchCommentary to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated matchCommentary,
     * or with status {@code 400 (Bad Request)} if the matchCommentary is not valid,
     * or with status {@code 500 (Internal Server Error)} if the matchCommentary couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/match-commentaries")
    public ResponseEntity<MatchCommentary> updateMatchCommentary(@RequestBody MatchCommentary matchCommentary) throws URISyntaxException {
        log.debug("REST request to update MatchCommentary : {}", matchCommentary);
        if (matchCommentary.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MatchCommentary result = matchCommentaryRepository.save(matchCommentary);
        matchCommentarySearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, matchCommentary.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /match-commentaries} : get all the matchCommentaries.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of matchCommentaries in body.
     */
    @GetMapping("/match-commentaries")
    public ResponseEntity<List<MatchCommentary>> getAllMatchCommentaries(Pageable pageable) {
        log.debug("REST request to get a page of MatchCommentaries");
        Page<MatchCommentary> page = matchCommentaryRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /match-commentaries/:id} : get the "id" matchCommentary.
     *
     * @param id the id of the matchCommentary to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the matchCommentary, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/match-commentaries/{id}")
    public ResponseEntity<MatchCommentary> getMatchCommentary(@PathVariable Long id) {
        log.debug("REST request to get MatchCommentary : {}", id);
        Optional<MatchCommentary> matchCommentary = matchCommentaryRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(matchCommentary);
    }

    /**
     * {@code DELETE  /match-commentaries/:id} : delete the "id" matchCommentary.
     *
     * @param id the id of the matchCommentary to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/match-commentaries/{id}")
    public ResponseEntity<Void> deleteMatchCommentary(@PathVariable Long id) {
        log.debug("REST request to delete MatchCommentary : {}", id);
        matchCommentaryRepository.deleteById(id);
        matchCommentarySearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/match-commentaries?query=:query} : search for the matchCommentary corresponding
     * to the query.
     *
     * @param query the query of the matchCommentary search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/match-commentaries")
    public ResponseEntity<List<MatchCommentary>> searchMatchCommentaries(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of MatchCommentaries for query {}", query);
        Page<MatchCommentary> page = matchCommentarySearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
