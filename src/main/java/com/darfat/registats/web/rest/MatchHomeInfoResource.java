package com.darfat.registats.web.rest;

import com.darfat.registats.domain.MatchHomeInfo;
import com.darfat.registats.repository.MatchHomeInfoRepository;
import com.darfat.registats.repository.search.MatchHomeInfoSearchRepository;
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
 * REST controller for managing {@link com.darfat.registats.domain.MatchHomeInfo}.
 */
@RestController
@RequestMapping("/api")
public class MatchHomeInfoResource {

    private final Logger log = LoggerFactory.getLogger(MatchHomeInfoResource.class);

    private static final String ENTITY_NAME = "matchHomeInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MatchHomeInfoRepository matchHomeInfoRepository;

    private final MatchHomeInfoSearchRepository matchHomeInfoSearchRepository;

    public MatchHomeInfoResource(MatchHomeInfoRepository matchHomeInfoRepository, MatchHomeInfoSearchRepository matchHomeInfoSearchRepository) {
        this.matchHomeInfoRepository = matchHomeInfoRepository;
        this.matchHomeInfoSearchRepository = matchHomeInfoSearchRepository;
    }

    /**
     * {@code POST  /match-home-infos} : Create a new matchHomeInfo.
     *
     * @param matchHomeInfo the matchHomeInfo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new matchHomeInfo, or with status {@code 400 (Bad Request)} if the matchHomeInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/match-home-infos")
    public ResponseEntity<MatchHomeInfo> createMatchHomeInfo(@RequestBody MatchHomeInfo matchHomeInfo) throws URISyntaxException {
        log.debug("REST request to save MatchHomeInfo : {}", matchHomeInfo);
        if (matchHomeInfo.getId() != null) {
            throw new BadRequestAlertException("A new matchHomeInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MatchHomeInfo result = matchHomeInfoRepository.save(matchHomeInfo);
        matchHomeInfoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/match-home-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /match-home-infos} : Updates an existing matchHomeInfo.
     *
     * @param matchHomeInfo the matchHomeInfo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated matchHomeInfo,
     * or with status {@code 400 (Bad Request)} if the matchHomeInfo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the matchHomeInfo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/match-home-infos")
    public ResponseEntity<MatchHomeInfo> updateMatchHomeInfo(@RequestBody MatchHomeInfo matchHomeInfo) throws URISyntaxException {
        log.debug("REST request to update MatchHomeInfo : {}", matchHomeInfo);
        if (matchHomeInfo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MatchHomeInfo result = matchHomeInfoRepository.save(matchHomeInfo);
        matchHomeInfoSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, matchHomeInfo.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /match-home-infos} : get all the matchHomeInfos.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of matchHomeInfos in body.
     */
    @GetMapping("/match-home-infos")
    public ResponseEntity<List<MatchHomeInfo>> getAllMatchHomeInfos(Pageable pageable) {
        log.debug("REST request to get a page of MatchHomeInfos");
        Page<MatchHomeInfo> page = matchHomeInfoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /match-home-infos/:id} : get the "id" matchHomeInfo.
     *
     * @param id the id of the matchHomeInfo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the matchHomeInfo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/match-home-infos/{id}")
    public ResponseEntity<MatchHomeInfo> getMatchHomeInfo(@PathVariable Long id) {
        log.debug("REST request to get MatchHomeInfo : {}", id);
        Optional<MatchHomeInfo> matchHomeInfo = matchHomeInfoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(matchHomeInfo);
    }

    /**
     * {@code DELETE  /match-home-infos/:id} : delete the "id" matchHomeInfo.
     *
     * @param id the id of the matchHomeInfo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/match-home-infos/{id}")
    public ResponseEntity<Void> deleteMatchHomeInfo(@PathVariable Long id) {
        log.debug("REST request to delete MatchHomeInfo : {}", id);
        matchHomeInfoRepository.deleteById(id);
        matchHomeInfoSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/match-home-infos?query=:query} : search for the matchHomeInfo corresponding
     * to the query.
     *
     * @param query the query of the matchHomeInfo search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/match-home-infos")
    public ResponseEntity<List<MatchHomeInfo>> searchMatchHomeInfos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of MatchHomeInfos for query {}", query);
        Page<MatchHomeInfo> page = matchHomeInfoSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
