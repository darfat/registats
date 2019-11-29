package com.darfat.registats.web.rest;

import com.darfat.registats.domain.GlobalTeamStatistic;
import com.darfat.registats.repository.GlobalTeamStatisticRepository;
import com.darfat.registats.repository.search.GlobalTeamStatisticSearchRepository;
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
 * REST controller for managing {@link com.darfat.registats.domain.GlobalTeamStatistic}.
 */
@RestController
@RequestMapping("/api")
public class GlobalTeamStatisticResource {

    private final Logger log = LoggerFactory.getLogger(GlobalTeamStatisticResource.class);

    private static final String ENTITY_NAME = "globalTeamStatistic";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GlobalTeamStatisticRepository globalTeamStatisticRepository;

    private final GlobalTeamStatisticSearchRepository globalTeamStatisticSearchRepository;

    public GlobalTeamStatisticResource(GlobalTeamStatisticRepository globalTeamStatisticRepository, GlobalTeamStatisticSearchRepository globalTeamStatisticSearchRepository) {
        this.globalTeamStatisticRepository = globalTeamStatisticRepository;
        this.globalTeamStatisticSearchRepository = globalTeamStatisticSearchRepository;
    }

    /**
     * {@code POST  /global-team-statistics} : Create a new globalTeamStatistic.
     *
     * @param globalTeamStatistic the globalTeamStatistic to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new globalTeamStatistic, or with status {@code 400 (Bad Request)} if the globalTeamStatistic has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/global-team-statistics")
    public ResponseEntity<GlobalTeamStatistic> createGlobalTeamStatistic(@RequestBody GlobalTeamStatistic globalTeamStatistic) throws URISyntaxException {
        log.debug("REST request to save GlobalTeamStatistic : {}", globalTeamStatistic);
        if (globalTeamStatistic.getId() != null) {
            throw new BadRequestAlertException("A new globalTeamStatistic cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GlobalTeamStatistic result = globalTeamStatisticRepository.save(globalTeamStatistic);
        globalTeamStatisticSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/global-team-statistics/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /global-team-statistics} : Updates an existing globalTeamStatistic.
     *
     * @param globalTeamStatistic the globalTeamStatistic to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated globalTeamStatistic,
     * or with status {@code 400 (Bad Request)} if the globalTeamStatistic is not valid,
     * or with status {@code 500 (Internal Server Error)} if the globalTeamStatistic couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/global-team-statistics")
    public ResponseEntity<GlobalTeamStatistic> updateGlobalTeamStatistic(@RequestBody GlobalTeamStatistic globalTeamStatistic) throws URISyntaxException {
        log.debug("REST request to update GlobalTeamStatistic : {}", globalTeamStatistic);
        if (globalTeamStatistic.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GlobalTeamStatistic result = globalTeamStatisticRepository.save(globalTeamStatistic);
        globalTeamStatisticSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, globalTeamStatistic.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /global-team-statistics} : get all the globalTeamStatistics.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of globalTeamStatistics in body.
     */
    @GetMapping("/global-team-statistics")
    public ResponseEntity<List<GlobalTeamStatistic>> getAllGlobalTeamStatistics(Pageable pageable) {
        log.debug("REST request to get a page of GlobalTeamStatistics");
        Page<GlobalTeamStatistic> page = globalTeamStatisticRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /global-team-statistics/:id} : get the "id" globalTeamStatistic.
     *
     * @param id the id of the globalTeamStatistic to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the globalTeamStatistic, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/global-team-statistics/{id}")
    public ResponseEntity<GlobalTeamStatistic> getGlobalTeamStatistic(@PathVariable Long id) {
        log.debug("REST request to get GlobalTeamStatistic : {}", id);
        Optional<GlobalTeamStatistic> globalTeamStatistic = globalTeamStatisticRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(globalTeamStatistic);
    }

    /**
     * {@code DELETE  /global-team-statistics/:id} : delete the "id" globalTeamStatistic.
     *
     * @param id the id of the globalTeamStatistic to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/global-team-statistics/{id}")
    public ResponseEntity<Void> deleteGlobalTeamStatistic(@PathVariable Long id) {
        log.debug("REST request to delete GlobalTeamStatistic : {}", id);
        globalTeamStatisticRepository.deleteById(id);
        globalTeamStatisticSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/global-team-statistics?query=:query} : search for the globalTeamStatistic corresponding
     * to the query.
     *
     * @param query the query of the globalTeamStatistic search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/global-team-statistics")
    public ResponseEntity<List<GlobalTeamStatistic>> searchGlobalTeamStatistics(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of GlobalTeamStatistics for query {}", query);
        Page<GlobalTeamStatistic> page = globalTeamStatisticSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
