package com.darfat.registats.web.rest;

import com.darfat.registats.domain.PlayerMatchStatistic;
import com.darfat.registats.repository.PlayerMatchStatisticRepository;
import com.darfat.registats.repository.search.PlayerMatchStatisticSearchRepository;
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
 * REST controller for managing {@link com.darfat.registats.domain.PlayerMatchStatistic}.
 */
@RestController
@RequestMapping("/api")
public class PlayerMatchStatisticResource {

    private final Logger log = LoggerFactory.getLogger(PlayerMatchStatisticResource.class);

    private static final String ENTITY_NAME = "playerMatchStatistic";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PlayerMatchStatisticRepository playerMatchStatisticRepository;

    private final PlayerMatchStatisticSearchRepository playerMatchStatisticSearchRepository;

    public PlayerMatchStatisticResource(PlayerMatchStatisticRepository playerMatchStatisticRepository, PlayerMatchStatisticSearchRepository playerMatchStatisticSearchRepository) {
        this.playerMatchStatisticRepository = playerMatchStatisticRepository;
        this.playerMatchStatisticSearchRepository = playerMatchStatisticSearchRepository;
    }

    /**
     * {@code POST  /player-match-statistics} : Create a new playerMatchStatistic.
     *
     * @param playerMatchStatistic the playerMatchStatistic to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new playerMatchStatistic, or with status {@code 400 (Bad Request)} if the playerMatchStatistic has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/player-match-statistics")
    public ResponseEntity<PlayerMatchStatistic> createPlayerMatchStatistic(@RequestBody PlayerMatchStatistic playerMatchStatistic) throws URISyntaxException {
        log.debug("REST request to save PlayerMatchStatistic : {}", playerMatchStatistic);
        if (playerMatchStatistic.getId() != null) {
            throw new BadRequestAlertException("A new playerMatchStatistic cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PlayerMatchStatistic result = playerMatchStatisticRepository.save(playerMatchStatistic);
        playerMatchStatisticSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/player-match-statistics/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /player-match-statistics} : Updates an existing playerMatchStatistic.
     *
     * @param playerMatchStatistic the playerMatchStatistic to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated playerMatchStatistic,
     * or with status {@code 400 (Bad Request)} if the playerMatchStatistic is not valid,
     * or with status {@code 500 (Internal Server Error)} if the playerMatchStatistic couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/player-match-statistics")
    public ResponseEntity<PlayerMatchStatistic> updatePlayerMatchStatistic(@RequestBody PlayerMatchStatistic playerMatchStatistic) throws URISyntaxException {
        log.debug("REST request to update PlayerMatchStatistic : {}", playerMatchStatistic);
        if (playerMatchStatistic.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PlayerMatchStatistic result = playerMatchStatisticRepository.save(playerMatchStatistic);
        playerMatchStatisticSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, playerMatchStatistic.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /player-match-statistics} : get all the playerMatchStatistics.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of playerMatchStatistics in body.
     */
    @GetMapping("/player-match-statistics")
    public ResponseEntity<List<PlayerMatchStatistic>> getAllPlayerMatchStatistics(Pageable pageable) {
        log.debug("REST request to get a page of PlayerMatchStatistics");
        Page<PlayerMatchStatistic> page = playerMatchStatisticRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /player-match-statistics/:id} : get the "id" playerMatchStatistic.
     *
     * @param id the id of the playerMatchStatistic to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the playerMatchStatistic, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/player-match-statistics/{id}")
    public ResponseEntity<PlayerMatchStatistic> getPlayerMatchStatistic(@PathVariable Long id) {
        log.debug("REST request to get PlayerMatchStatistic : {}", id);
        Optional<PlayerMatchStatistic> playerMatchStatistic = playerMatchStatisticRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(playerMatchStatistic);
    }

    /**
     * {@code DELETE  /player-match-statistics/:id} : delete the "id" playerMatchStatistic.
     *
     * @param id the id of the playerMatchStatistic to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/player-match-statistics/{id}")
    public ResponseEntity<Void> deletePlayerMatchStatistic(@PathVariable Long id) {
        log.debug("REST request to delete PlayerMatchStatistic : {}", id);
        playerMatchStatisticRepository.deleteById(id);
        playerMatchStatisticSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/player-match-statistics?query=:query} : search for the playerMatchStatistic corresponding
     * to the query.
     *
     * @param query the query of the playerMatchStatistic search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/player-match-statistics")
    public ResponseEntity<List<PlayerMatchStatistic>> searchPlayerMatchStatistics(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of PlayerMatchStatistics for query {}", query);
        Page<PlayerMatchStatistic> page = playerMatchStatisticSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
