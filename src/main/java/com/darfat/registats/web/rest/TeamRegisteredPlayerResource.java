package com.darfat.registats.web.rest;

import com.darfat.registats.domain.TeamRegisteredPlayer;
import com.darfat.registats.repository.TeamRegisteredPlayerRepository;
import com.darfat.registats.repository.search.TeamRegisteredPlayerSearchRepository;
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
 * REST controller for managing {@link com.darfat.registats.domain.TeamRegisteredPlayer}.
 */
@RestController
@RequestMapping("/api")
public class TeamRegisteredPlayerResource {

    private final Logger log = LoggerFactory.getLogger(TeamRegisteredPlayerResource.class);

    private static final String ENTITY_NAME = "teamRegisteredPlayer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TeamRegisteredPlayerRepository teamRegisteredPlayerRepository;

    private final TeamRegisteredPlayerSearchRepository teamRegisteredPlayerSearchRepository;

    public TeamRegisteredPlayerResource(TeamRegisteredPlayerRepository teamRegisteredPlayerRepository, TeamRegisteredPlayerSearchRepository teamRegisteredPlayerSearchRepository) {
        this.teamRegisteredPlayerRepository = teamRegisteredPlayerRepository;
        this.teamRegisteredPlayerSearchRepository = teamRegisteredPlayerSearchRepository;
    }

    /**
     * {@code POST  /team-registered-players} : Create a new teamRegisteredPlayer.
     *
     * @param teamRegisteredPlayer the teamRegisteredPlayer to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new teamRegisteredPlayer, or with status {@code 400 (Bad Request)} if the teamRegisteredPlayer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/team-registered-players")
    public ResponseEntity<TeamRegisteredPlayer> createTeamRegisteredPlayer(@RequestBody TeamRegisteredPlayer teamRegisteredPlayer) throws URISyntaxException {
        log.debug("REST request to save TeamRegisteredPlayer : {}", teamRegisteredPlayer);
        if (teamRegisteredPlayer.getId() != null) {
            throw new BadRequestAlertException("A new teamRegisteredPlayer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TeamRegisteredPlayer result = teamRegisteredPlayerRepository.save(teamRegisteredPlayer);
        teamRegisteredPlayerSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/team-registered-players/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /team-registered-players} : Updates an existing teamRegisteredPlayer.
     *
     * @param teamRegisteredPlayer the teamRegisteredPlayer to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated teamRegisteredPlayer,
     * or with status {@code 400 (Bad Request)} if the teamRegisteredPlayer is not valid,
     * or with status {@code 500 (Internal Server Error)} if the teamRegisteredPlayer couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/team-registered-players")
    public ResponseEntity<TeamRegisteredPlayer> updateTeamRegisteredPlayer(@RequestBody TeamRegisteredPlayer teamRegisteredPlayer) throws URISyntaxException {
        log.debug("REST request to update TeamRegisteredPlayer : {}", teamRegisteredPlayer);
        if (teamRegisteredPlayer.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TeamRegisteredPlayer result = teamRegisteredPlayerRepository.save(teamRegisteredPlayer);
        teamRegisteredPlayerSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, teamRegisteredPlayer.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /team-registered-players} : get all the teamRegisteredPlayers.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of teamRegisteredPlayers in body.
     */
    @GetMapping("/team-registered-players")
    public ResponseEntity<List<TeamRegisteredPlayer>> getAllTeamRegisteredPlayers(Pageable pageable) {
        log.debug("REST request to get a page of TeamRegisteredPlayers");
        Page<TeamRegisteredPlayer> page = teamRegisteredPlayerRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /team-registered-players/:id} : get the "id" teamRegisteredPlayer.
     *
     * @param id the id of the teamRegisteredPlayer to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the teamRegisteredPlayer, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/team-registered-players/{id}")
    public ResponseEntity<TeamRegisteredPlayer> getTeamRegisteredPlayer(@PathVariable Long id) {
        log.debug("REST request to get TeamRegisteredPlayer : {}", id);
        Optional<TeamRegisteredPlayer> teamRegisteredPlayer = teamRegisteredPlayerRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(teamRegisteredPlayer);
    }

    /**
     * {@code DELETE  /team-registered-players/:id} : delete the "id" teamRegisteredPlayer.
     *
     * @param id the id of the teamRegisteredPlayer to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/team-registered-players/{id}")
    public ResponseEntity<Void> deleteTeamRegisteredPlayer(@PathVariable Long id) {
        log.debug("REST request to delete TeamRegisteredPlayer : {}", id);
        teamRegisteredPlayerRepository.deleteById(id);
        teamRegisteredPlayerSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/team-registered-players?query=:query} : search for the teamRegisteredPlayer corresponding
     * to the query.
     *
     * @param query the query of the teamRegisteredPlayer search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/team-registered-players")
    public ResponseEntity<List<TeamRegisteredPlayer>> searchTeamRegisteredPlayers(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of TeamRegisteredPlayers for query {}", query);
        Page<TeamRegisteredPlayer> page = teamRegisteredPlayerSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
