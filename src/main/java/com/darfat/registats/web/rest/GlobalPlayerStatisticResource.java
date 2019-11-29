package com.darfat.registats.web.rest;

import com.darfat.registats.domain.GlobalPlayerStatistic;
import com.darfat.registats.repository.GlobalPlayerStatisticRepository;
import com.darfat.registats.repository.search.GlobalPlayerStatisticSearchRepository;
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
 * REST controller for managing {@link com.darfat.registats.domain.GlobalPlayerStatistic}.
 */
@RestController
@RequestMapping("/api")
public class GlobalPlayerStatisticResource {

    private final Logger log = LoggerFactory.getLogger(GlobalPlayerStatisticResource.class);

    private static final String ENTITY_NAME = "globalPlayerStatistic";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GlobalPlayerStatisticRepository globalPlayerStatisticRepository;

    private final GlobalPlayerStatisticSearchRepository globalPlayerStatisticSearchRepository;

    public GlobalPlayerStatisticResource(GlobalPlayerStatisticRepository globalPlayerStatisticRepository, GlobalPlayerStatisticSearchRepository globalPlayerStatisticSearchRepository) {
        this.globalPlayerStatisticRepository = globalPlayerStatisticRepository;
        this.globalPlayerStatisticSearchRepository = globalPlayerStatisticSearchRepository;
    }

    /**
     * {@code POST  /global-player-statistics} : Create a new globalPlayerStatistic.
     *
     * @param globalPlayerStatistic the globalPlayerStatistic to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new globalPlayerStatistic, or with status {@code 400 (Bad Request)} if the globalPlayerStatistic has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/global-player-statistics")
    public ResponseEntity<GlobalPlayerStatistic> createGlobalPlayerStatistic(@RequestBody GlobalPlayerStatistic globalPlayerStatistic) throws URISyntaxException {
        log.debug("REST request to save GlobalPlayerStatistic : {}", globalPlayerStatistic);
        if (globalPlayerStatistic.getId() != null) {
            throw new BadRequestAlertException("A new globalPlayerStatistic cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GlobalPlayerStatistic result = globalPlayerStatisticRepository.save(globalPlayerStatistic);
        globalPlayerStatisticSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/global-player-statistics/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /global-player-statistics} : Updates an existing globalPlayerStatistic.
     *
     * @param globalPlayerStatistic the globalPlayerStatistic to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated globalPlayerStatistic,
     * or with status {@code 400 (Bad Request)} if the globalPlayerStatistic is not valid,
     * or with status {@code 500 (Internal Server Error)} if the globalPlayerStatistic couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/global-player-statistics")
    public ResponseEntity<GlobalPlayerStatistic> updateGlobalPlayerStatistic(@RequestBody GlobalPlayerStatistic globalPlayerStatistic) throws URISyntaxException {
        log.debug("REST request to update GlobalPlayerStatistic : {}", globalPlayerStatistic);
        if (globalPlayerStatistic.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GlobalPlayerStatistic result = globalPlayerStatisticRepository.save(globalPlayerStatistic);
        globalPlayerStatisticSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, globalPlayerStatistic.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /global-player-statistics} : get all the globalPlayerStatistics.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of globalPlayerStatistics in body.
     */
    @GetMapping("/global-player-statistics")
    public ResponseEntity<List<GlobalPlayerStatistic>> getAllGlobalPlayerStatistics(Pageable pageable) {
        log.debug("REST request to get a page of GlobalPlayerStatistics");
        Page<GlobalPlayerStatistic> page = globalPlayerStatisticRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /global-player-statistics/:id} : get the "id" globalPlayerStatistic.
     *
     * @param id the id of the globalPlayerStatistic to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the globalPlayerStatistic, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/global-player-statistics/{id}")
    public ResponseEntity<GlobalPlayerStatistic> getGlobalPlayerStatistic(@PathVariable Long id) {
        log.debug("REST request to get GlobalPlayerStatistic : {}", id);
        Optional<GlobalPlayerStatistic> globalPlayerStatistic = globalPlayerStatisticRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(globalPlayerStatistic);
    }

    /**
     * {@code DELETE  /global-player-statistics/:id} : delete the "id" globalPlayerStatistic.
     *
     * @param id the id of the globalPlayerStatistic to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/global-player-statistics/{id}")
    public ResponseEntity<Void> deleteGlobalPlayerStatistic(@PathVariable Long id) {
        log.debug("REST request to delete GlobalPlayerStatistic : {}", id);
        globalPlayerStatisticRepository.deleteById(id);
        globalPlayerStatisticSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/global-player-statistics?query=:query} : search for the globalPlayerStatistic corresponding
     * to the query.
     *
     * @param query the query of the globalPlayerStatistic search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/global-player-statistics")
    public ResponseEntity<List<GlobalPlayerStatistic>> searchGlobalPlayerStatistics(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of GlobalPlayerStatistics for query {}", query);
        Page<GlobalPlayerStatistic> page = globalPlayerStatisticSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
