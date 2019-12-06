package com.darfat.registats.web.rest;

import com.darfat.registats.domain.MatchTeamInfo;
import com.darfat.registats.repository.MatchTeamInfoRepository;
import com.darfat.registats.repository.search.MatchTeamInfoSearchRepository;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link com.darfat.registats.domain.MatchTeamInfo}.
 */
@RestController
@RequestMapping("/api")
public class MatchTeamInfoResource {

    private final Logger log = LoggerFactory.getLogger(MatchTeamInfoResource.class);

    private static final String ENTITY_NAME = "matchTeamInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MatchTeamInfoRepository matchTeamInfoRepository;

    private final MatchTeamInfoSearchRepository matchTeamInfoSearchRepository;

    public MatchTeamInfoResource(MatchTeamInfoRepository matchTeamInfoRepository, MatchTeamInfoSearchRepository matchTeamInfoSearchRepository) {
        this.matchTeamInfoRepository = matchTeamInfoRepository;
        this.matchTeamInfoSearchRepository = matchTeamInfoSearchRepository;
    }

    /**
     * {@code POST  /match-team-infos} : Create a new matchTeamInfo.
     *
     * @param matchTeamInfo the matchTeamInfo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new matchTeamInfo, or with status {@code 400 (Bad Request)} if the matchTeamInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/match-team-infos")
    public ResponseEntity<MatchTeamInfo> createMatchTeamInfo(@RequestBody MatchTeamInfo matchTeamInfo) throws URISyntaxException {
        log.debug("REST request to save MatchTeamInfo : {}", matchTeamInfo);
        if (matchTeamInfo.getId() != null) {
            throw new BadRequestAlertException("A new matchTeamInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MatchTeamInfo result = matchTeamInfoRepository.save(matchTeamInfo);
        matchTeamInfoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/match-team-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /match-team-infos} : Updates an existing matchTeamInfo.
     *
     * @param matchTeamInfo the matchTeamInfo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated matchTeamInfo,
     * or with status {@code 400 (Bad Request)} if the matchTeamInfo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the matchTeamInfo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/match-team-infos")
    public ResponseEntity<MatchTeamInfo> updateMatchTeamInfo(@RequestBody MatchTeamInfo matchTeamInfo) throws URISyntaxException {
        log.debug("REST request to update MatchTeamInfo : {}", matchTeamInfo);
        if (matchTeamInfo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MatchTeamInfo result = matchTeamInfoRepository.save(matchTeamInfo);
        matchTeamInfoSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, matchTeamInfo.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /match-team-infos} : get all the matchTeamInfos.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of matchTeamInfos in body.
     */
    @GetMapping("/match-team-infos")
    public ResponseEntity<List<MatchTeamInfo>> getAllMatchTeamInfos(Pageable pageable) {
        log.debug("REST request to get a page of MatchTeamInfos");
        Page<MatchTeamInfo> page = matchTeamInfoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /match-team-infos/:id} : get the "id" matchTeamInfo.
     *
     * @param id the id of the matchTeamInfo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the matchTeamInfo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/match-team-infos/{id}")
    public ResponseEntity<MatchTeamInfo> getMatchTeamInfo(@PathVariable Long id) {
        log.debug("REST request to get MatchTeamInfo : {}", id);
        Optional<MatchTeamInfo> matchTeamInfo = matchTeamInfoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(matchTeamInfo);
    }

    /**
     * {@code DELETE  /match-team-infos/:id} : delete the "id" matchTeamInfo.
     *
     * @param id the id of the matchTeamInfo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/match-team-infos/{id}")
    public ResponseEntity<Void> deleteMatchTeamInfo(@PathVariable Long id) {
        log.debug("REST request to delete MatchTeamInfo : {}", id);
        matchTeamInfoRepository.deleteById(id);
        matchTeamInfoSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/match-team-infos?query=:query} : search for the matchTeamInfo corresponding
     * to the query.
     *
     * @param query the query of the matchTeamInfo search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/match-team-infos")
    public ResponseEntity<List<MatchTeamInfo>> searchMatchTeamInfos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of MatchTeamInfos for query {}", query);
        Page<MatchTeamInfo> page = matchTeamInfoSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
