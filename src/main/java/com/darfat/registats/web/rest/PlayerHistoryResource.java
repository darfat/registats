package com.darfat.registats.web.rest;

import com.darfat.registats.domain.PlayerHistory;
import com.darfat.registats.repository.PlayerHistoryRepository;
import com.darfat.registats.repository.search.PlayerHistorySearchRepository;
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
 * REST controller for managing {@link com.darfat.registats.domain.PlayerHistory}.
 */
@RestController
@RequestMapping("/api")
public class PlayerHistoryResource {

    private final Logger log = LoggerFactory.getLogger(PlayerHistoryResource.class);

    private static final String ENTITY_NAME = "playerHistory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PlayerHistoryRepository playerHistoryRepository;

    private final PlayerHistorySearchRepository playerHistorySearchRepository;

    public PlayerHistoryResource(PlayerHistoryRepository playerHistoryRepository, PlayerHistorySearchRepository playerHistorySearchRepository) {
        this.playerHistoryRepository = playerHistoryRepository;
        this.playerHistorySearchRepository = playerHistorySearchRepository;
    }

    /**
     * {@code POST  /player-histories} : Create a new playerHistory.
     *
     * @param playerHistory the playerHistory to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new playerHistory, or with status {@code 400 (Bad Request)} if the playerHistory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/player-histories")
    public ResponseEntity<PlayerHistory> createPlayerHistory(@RequestBody PlayerHistory playerHistory) throws URISyntaxException {
        log.debug("REST request to save PlayerHistory : {}", playerHistory);
        if (playerHistory.getId() != null) {
            throw new BadRequestAlertException("A new playerHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PlayerHistory result = playerHistoryRepository.save(playerHistory);
        playerHistorySearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/player-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /player-histories} : Updates an existing playerHistory.
     *
     * @param playerHistory the playerHistory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated playerHistory,
     * or with status {@code 400 (Bad Request)} if the playerHistory is not valid,
     * or with status {@code 500 (Internal Server Error)} if the playerHistory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/player-histories")
    public ResponseEntity<PlayerHistory> updatePlayerHistory(@RequestBody PlayerHistory playerHistory) throws URISyntaxException {
        log.debug("REST request to update PlayerHistory : {}", playerHistory);
        if (playerHistory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PlayerHistory result = playerHistoryRepository.save(playerHistory);
        playerHistorySearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, playerHistory.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /player-histories} : get all the playerHistories.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of playerHistories in body.
     */
    @GetMapping("/player-histories")
    public ResponseEntity<List<PlayerHistory>> getAllPlayerHistories(Pageable pageable) {
        log.debug("REST request to get a page of PlayerHistories");
        Page<PlayerHistory> page = playerHistoryRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /player-histories/:id} : get the "id" playerHistory.
     *
     * @param id the id of the playerHistory to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the playerHistory, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/player-histories/{id}")
    public ResponseEntity<PlayerHistory> getPlayerHistory(@PathVariable Long id) {
        log.debug("REST request to get PlayerHistory : {}", id);
        Optional<PlayerHistory> playerHistory = playerHistoryRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(playerHistory);
    }

    /**
     * {@code DELETE  /player-histories/:id} : delete the "id" playerHistory.
     *
     * @param id the id of the playerHistory to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/player-histories/{id}")
    public ResponseEntity<Void> deletePlayerHistory(@PathVariable Long id) {
        log.debug("REST request to delete PlayerHistory : {}", id);
        playerHistoryRepository.deleteById(id);
        playerHistorySearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/player-histories?query=:query} : search for the playerHistory corresponding
     * to the query.
     *
     * @param query the query of the playerHistory search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/player-histories")
    public ResponseEntity<List<PlayerHistory>> searchPlayerHistories(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of PlayerHistories for query {}", query);
        Page<PlayerHistory> page = playerHistorySearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
