package com.darfat.registats.web.rest;

import com.darfat.registats.domain.PlayerHistoryStatistic;
import com.darfat.registats.repository.PlayerHistoryStatisticRepository;
import com.darfat.registats.repository.search.PlayerHistoryStatisticSearchRepository;
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
 * REST controller for managing {@link com.darfat.registats.domain.PlayerHistoryStatistic}.
 */
@RestController
@RequestMapping("/api")
public class PlayerHistoryStatisticResource {

    private final Logger log = LoggerFactory.getLogger(PlayerHistoryStatisticResource.class);

    private static final String ENTITY_NAME = "playerHistoryStatistic";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PlayerHistoryStatisticRepository playerHistoryStatisticRepository;

    private final PlayerHistoryStatisticSearchRepository playerHistoryStatisticSearchRepository;

    public PlayerHistoryStatisticResource(PlayerHistoryStatisticRepository playerHistoryStatisticRepository, PlayerHistoryStatisticSearchRepository playerHistoryStatisticSearchRepository) {
        this.playerHistoryStatisticRepository = playerHistoryStatisticRepository;
        this.playerHistoryStatisticSearchRepository = playerHistoryStatisticSearchRepository;
    }

    /**
     * {@code POST  /player-history-statistics} : Create a new playerHistoryStatistic.
     *
     * @param playerHistoryStatistic the playerHistoryStatistic to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new playerHistoryStatistic, or with status {@code 400 (Bad Request)} if the playerHistoryStatistic has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/player-history-statistics")
    public ResponseEntity<PlayerHistoryStatistic> createPlayerHistoryStatistic(@RequestBody PlayerHistoryStatistic playerHistoryStatistic) throws URISyntaxException {
        log.debug("REST request to save PlayerHistoryStatistic : {}", playerHistoryStatistic);
        if (playerHistoryStatistic.getId() != null) {
            throw new BadRequestAlertException("A new playerHistoryStatistic cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PlayerHistoryStatistic result = playerHistoryStatisticRepository.save(playerHistoryStatistic);
        playerHistoryStatisticSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/player-history-statistics/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /player-history-statistics} : Updates an existing playerHistoryStatistic.
     *
     * @param playerHistoryStatistic the playerHistoryStatistic to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated playerHistoryStatistic,
     * or with status {@code 400 (Bad Request)} if the playerHistoryStatistic is not valid,
     * or with status {@code 500 (Internal Server Error)} if the playerHistoryStatistic couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/player-history-statistics")
    public ResponseEntity<PlayerHistoryStatistic> updatePlayerHistoryStatistic(@RequestBody PlayerHistoryStatistic playerHistoryStatistic) throws URISyntaxException {
        log.debug("REST request to update PlayerHistoryStatistic : {}", playerHistoryStatistic);
        if (playerHistoryStatistic.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PlayerHistoryStatistic result = playerHistoryStatisticRepository.save(playerHistoryStatistic);
        playerHistoryStatisticSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, playerHistoryStatistic.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /player-history-statistics} : get all the playerHistoryStatistics.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of playerHistoryStatistics in body.
     */
    @GetMapping("/player-history-statistics")
    public ResponseEntity<List<PlayerHistoryStatistic>> getAllPlayerHistoryStatistics(Pageable pageable) {
        log.debug("REST request to get a page of PlayerHistoryStatistics");
        Page<PlayerHistoryStatistic> page = playerHistoryStatisticRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /player-history-statistics/:id} : get the "id" playerHistoryStatistic.
     *
     * @param id the id of the playerHistoryStatistic to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the playerHistoryStatistic, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/player-history-statistics/{id}")
    public ResponseEntity<PlayerHistoryStatistic> getPlayerHistoryStatistic(@PathVariable Long id) {
        log.debug("REST request to get PlayerHistoryStatistic : {}", id);
        Optional<PlayerHistoryStatistic> playerHistoryStatistic = playerHistoryStatisticRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(playerHistoryStatistic);
    }

    /**
     * {@code DELETE  /player-history-statistics/:id} : delete the "id" playerHistoryStatistic.
     *
     * @param id the id of the playerHistoryStatistic to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/player-history-statistics/{id}")
    public ResponseEntity<Void> deletePlayerHistoryStatistic(@PathVariable Long id) {
        log.debug("REST request to delete PlayerHistoryStatistic : {}", id);
        playerHistoryStatisticRepository.deleteById(id);
        playerHistoryStatisticSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/player-history-statistics?query=:query} : search for the playerHistoryStatistic corresponding
     * to the query.
     *
     * @param query the query of the playerHistoryStatistic search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/player-history-statistics")
    public ResponseEntity<List<PlayerHistoryStatistic>> searchPlayerHistoryStatistics(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of PlayerHistoryStatistics for query {}", query);
        Page<PlayerHistoryStatistic> page = playerHistoryStatisticSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
