package com.darfat.registats.web.rest;

import com.darfat.registats.domain.PlayerStatisticItem;
import com.darfat.registats.repository.PlayerStatisticItemRepository;
import com.darfat.registats.repository.search.PlayerStatisticItemSearchRepository;
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
 * REST controller for managing {@link com.darfat.registats.domain.PlayerStatisticItem}.
 */
@RestController
@RequestMapping("/api")
public class PlayerStatisticItemResource {

    private final Logger log = LoggerFactory.getLogger(PlayerStatisticItemResource.class);

    private static final String ENTITY_NAME = "playerStatisticItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PlayerStatisticItemRepository playerStatisticItemRepository;

    private final PlayerStatisticItemSearchRepository playerStatisticItemSearchRepository;

    public PlayerStatisticItemResource(PlayerStatisticItemRepository playerStatisticItemRepository, PlayerStatisticItemSearchRepository playerStatisticItemSearchRepository) {
        this.playerStatisticItemRepository = playerStatisticItemRepository;
        this.playerStatisticItemSearchRepository = playerStatisticItemSearchRepository;
    }

    /**
     * {@code POST  /player-statistic-items} : Create a new playerStatisticItem.
     *
     * @param playerStatisticItem the playerStatisticItem to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new playerStatisticItem, or with status {@code 400 (Bad Request)} if the playerStatisticItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/player-statistic-items")
    public ResponseEntity<PlayerStatisticItem> createPlayerStatisticItem(@RequestBody PlayerStatisticItem playerStatisticItem) throws URISyntaxException {
        log.debug("REST request to save PlayerStatisticItem : {}", playerStatisticItem);
        if (playerStatisticItem.getId() != null) {
            throw new BadRequestAlertException("A new playerStatisticItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PlayerStatisticItem result = playerStatisticItemRepository.save(playerStatisticItem);
        playerStatisticItemSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/player-statistic-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /player-statistic-items} : Updates an existing playerStatisticItem.
     *
     * @param playerStatisticItem the playerStatisticItem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated playerStatisticItem,
     * or with status {@code 400 (Bad Request)} if the playerStatisticItem is not valid,
     * or with status {@code 500 (Internal Server Error)} if the playerStatisticItem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/player-statistic-items")
    public ResponseEntity<PlayerStatisticItem> updatePlayerStatisticItem(@RequestBody PlayerStatisticItem playerStatisticItem) throws URISyntaxException {
        log.debug("REST request to update PlayerStatisticItem : {}", playerStatisticItem);
        if (playerStatisticItem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PlayerStatisticItem result = playerStatisticItemRepository.save(playerStatisticItem);
        playerStatisticItemSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, playerStatisticItem.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /player-statistic-items} : get all the playerStatisticItems.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of playerStatisticItems in body.
     */
    @GetMapping("/player-statistic-items")
    public ResponseEntity<List<PlayerStatisticItem>> getAllPlayerStatisticItems(Pageable pageable) {
        log.debug("REST request to get a page of PlayerStatisticItems");
        Page<PlayerStatisticItem> page = playerStatisticItemRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /player-statistic-items/:id} : get the "id" playerStatisticItem.
     *
     * @param id the id of the playerStatisticItem to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the playerStatisticItem, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/player-statistic-items/{id}")
    public ResponseEntity<PlayerStatisticItem> getPlayerStatisticItem(@PathVariable Long id) {
        log.debug("REST request to get PlayerStatisticItem : {}", id);
        Optional<PlayerStatisticItem> playerStatisticItem = playerStatisticItemRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(playerStatisticItem);
    }

    /**
     * {@code DELETE  /player-statistic-items/:id} : delete the "id" playerStatisticItem.
     *
     * @param id the id of the playerStatisticItem to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/player-statistic-items/{id}")
    public ResponseEntity<Void> deletePlayerStatisticItem(@PathVariable Long id) {
        log.debug("REST request to delete PlayerStatisticItem : {}", id);
        playerStatisticItemRepository.deleteById(id);
        playerStatisticItemSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/player-statistic-items?query=:query} : search for the playerStatisticItem corresponding
     * to the query.
     *
     * @param query the query of the playerStatisticItem search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/player-statistic-items")
    public ResponseEntity<List<PlayerStatisticItem>> searchPlayerStatisticItems(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of PlayerStatisticItems for query {}", query);
        Page<PlayerStatisticItem> page = playerStatisticItemSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
