package com.darfat.registats.web.rest;

import com.darfat.registats.domain.MatchStatisticItem;
import com.darfat.registats.repository.MatchStatisticItemRepository;
import com.darfat.registats.repository.search.MatchStatisticItemSearchRepository;
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
 * REST controller for managing {@link com.darfat.registats.domain.MatchStatisticItem}.
 */
@RestController
@RequestMapping("/api")
public class MatchStatisticItemResource {

    private final Logger log = LoggerFactory.getLogger(MatchStatisticItemResource.class);

    private static final String ENTITY_NAME = "matchStatisticItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MatchStatisticItemRepository matchStatisticItemRepository;

    private final MatchStatisticItemSearchRepository matchStatisticItemSearchRepository;

    public MatchStatisticItemResource(MatchStatisticItemRepository matchStatisticItemRepository, MatchStatisticItemSearchRepository matchStatisticItemSearchRepository) {
        this.matchStatisticItemRepository = matchStatisticItemRepository;
        this.matchStatisticItemSearchRepository = matchStatisticItemSearchRepository;
    }

    /**
     * {@code POST  /match-statistic-items} : Create a new matchStatisticItem.
     *
     * @param matchStatisticItem the matchStatisticItem to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new matchStatisticItem, or with status {@code 400 (Bad Request)} if the matchStatisticItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/match-statistic-items")
    public ResponseEntity<MatchStatisticItem> createMatchStatisticItem(@RequestBody MatchStatisticItem matchStatisticItem) throws URISyntaxException {
        log.debug("REST request to save MatchStatisticItem : {}", matchStatisticItem);
        if (matchStatisticItem.getId() != null) {
            throw new BadRequestAlertException("A new matchStatisticItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MatchStatisticItem result = matchStatisticItemRepository.save(matchStatisticItem);
        matchStatisticItemSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/match-statistic-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /match-statistic-items} : Updates an existing matchStatisticItem.
     *
     * @param matchStatisticItem the matchStatisticItem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated matchStatisticItem,
     * or with status {@code 400 (Bad Request)} if the matchStatisticItem is not valid,
     * or with status {@code 500 (Internal Server Error)} if the matchStatisticItem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/match-statistic-items")
    public ResponseEntity<MatchStatisticItem> updateMatchStatisticItem(@RequestBody MatchStatisticItem matchStatisticItem) throws URISyntaxException {
        log.debug("REST request to update MatchStatisticItem : {}", matchStatisticItem);
        if (matchStatisticItem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MatchStatisticItem result = matchStatisticItemRepository.save(matchStatisticItem);
        matchStatisticItemSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, matchStatisticItem.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /match-statistic-items} : get all the matchStatisticItems.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of matchStatisticItems in body.
     */
    @GetMapping("/match-statistic-items")
    public ResponseEntity<List<MatchStatisticItem>> getAllMatchStatisticItems(Pageable pageable) {
        log.debug("REST request to get a page of MatchStatisticItems");
        Page<MatchStatisticItem> page = matchStatisticItemRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /match-statistic-items/:id} : get the "id" matchStatisticItem.
     *
     * @param id the id of the matchStatisticItem to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the matchStatisticItem, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/match-statistic-items/{id}")
    public ResponseEntity<MatchStatisticItem> getMatchStatisticItem(@PathVariable Long id) {
        log.debug("REST request to get MatchStatisticItem : {}", id);
        Optional<MatchStatisticItem> matchStatisticItem = matchStatisticItemRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(matchStatisticItem);
    }

    /**
     * {@code DELETE  /match-statistic-items/:id} : delete the "id" matchStatisticItem.
     *
     * @param id the id of the matchStatisticItem to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/match-statistic-items/{id}")
    public ResponseEntity<Void> deleteMatchStatisticItem(@PathVariable Long id) {
        log.debug("REST request to delete MatchStatisticItem : {}", id);
        matchStatisticItemRepository.deleteById(id);
        matchStatisticItemSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/match-statistic-items?query=:query} : search for the matchStatisticItem corresponding
     * to the query.
     *
     * @param query the query of the matchStatisticItem search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/match-statistic-items")
    public ResponseEntity<List<MatchStatisticItem>> searchMatchStatisticItems(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of MatchStatisticItems for query {}", query);
        Page<MatchStatisticItem> page = matchStatisticItemSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
