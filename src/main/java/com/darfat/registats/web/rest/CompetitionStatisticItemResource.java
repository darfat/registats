package com.darfat.registats.web.rest;

import com.darfat.registats.domain.CompetitionStatisticItem;
import com.darfat.registats.repository.CompetitionStatisticItemRepository;
import com.darfat.registats.repository.search.CompetitionStatisticItemSearchRepository;
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
 * REST controller for managing {@link com.darfat.registats.domain.CompetitionStatisticItem}.
 */
@RestController
@RequestMapping("/api")
public class CompetitionStatisticItemResource {

    private final Logger log = LoggerFactory.getLogger(CompetitionStatisticItemResource.class);

    private static final String ENTITY_NAME = "competitionStatisticItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CompetitionStatisticItemRepository competitionStatisticItemRepository;

    private final CompetitionStatisticItemSearchRepository competitionStatisticItemSearchRepository;

    public CompetitionStatisticItemResource(CompetitionStatisticItemRepository competitionStatisticItemRepository, CompetitionStatisticItemSearchRepository competitionStatisticItemSearchRepository) {
        this.competitionStatisticItemRepository = competitionStatisticItemRepository;
        this.competitionStatisticItemSearchRepository = competitionStatisticItemSearchRepository;
    }

    /**
     * {@code POST  /competition-statistic-items} : Create a new competitionStatisticItem.
     *
     * @param competitionStatisticItem the competitionStatisticItem to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new competitionStatisticItem, or with status {@code 400 (Bad Request)} if the competitionStatisticItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/competition-statistic-items")
    public ResponseEntity<CompetitionStatisticItem> createCompetitionStatisticItem(@RequestBody CompetitionStatisticItem competitionStatisticItem) throws URISyntaxException {
        log.debug("REST request to save CompetitionStatisticItem : {}", competitionStatisticItem);
        if (competitionStatisticItem.getId() != null) {
            throw new BadRequestAlertException("A new competitionStatisticItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompetitionStatisticItem result = competitionStatisticItemRepository.save(competitionStatisticItem);
        competitionStatisticItemSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/competition-statistic-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /competition-statistic-items} : Updates an existing competitionStatisticItem.
     *
     * @param competitionStatisticItem the competitionStatisticItem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated competitionStatisticItem,
     * or with status {@code 400 (Bad Request)} if the competitionStatisticItem is not valid,
     * or with status {@code 500 (Internal Server Error)} if the competitionStatisticItem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/competition-statistic-items")
    public ResponseEntity<CompetitionStatisticItem> updateCompetitionStatisticItem(@RequestBody CompetitionStatisticItem competitionStatisticItem) throws URISyntaxException {
        log.debug("REST request to update CompetitionStatisticItem : {}", competitionStatisticItem);
        if (competitionStatisticItem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CompetitionStatisticItem result = competitionStatisticItemRepository.save(competitionStatisticItem);
        competitionStatisticItemSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, competitionStatisticItem.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /competition-statistic-items} : get all the competitionStatisticItems.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of competitionStatisticItems in body.
     */
    @GetMapping("/competition-statistic-items")
    public ResponseEntity<List<CompetitionStatisticItem>> getAllCompetitionStatisticItems(Pageable pageable) {
        log.debug("REST request to get a page of CompetitionStatisticItems");
        Page<CompetitionStatisticItem> page = competitionStatisticItemRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /competition-statistic-items/:id} : get the "id" competitionStatisticItem.
     *
     * @param id the id of the competitionStatisticItem to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the competitionStatisticItem, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/competition-statistic-items/{id}")
    public ResponseEntity<CompetitionStatisticItem> getCompetitionStatisticItem(@PathVariable Long id) {
        log.debug("REST request to get CompetitionStatisticItem : {}", id);
        Optional<CompetitionStatisticItem> competitionStatisticItem = competitionStatisticItemRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(competitionStatisticItem);
    }

    /**
     * {@code DELETE  /competition-statistic-items/:id} : delete the "id" competitionStatisticItem.
     *
     * @param id the id of the competitionStatisticItem to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/competition-statistic-items/{id}")
    public ResponseEntity<Void> deleteCompetitionStatisticItem(@PathVariable Long id) {
        log.debug("REST request to delete CompetitionStatisticItem : {}", id);
        competitionStatisticItemRepository.deleteById(id);
        competitionStatisticItemSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/competition-statistic-items?query=:query} : search for the competitionStatisticItem corresponding
     * to the query.
     *
     * @param query the query of the competitionStatisticItem search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/competition-statistic-items")
    public ResponseEntity<List<CompetitionStatisticItem>> searchCompetitionStatisticItems(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of CompetitionStatisticItems for query {}", query);
        Page<CompetitionStatisticItem> page = competitionStatisticItemSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
