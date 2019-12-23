package com.darfat.registats.web.rest;

import com.darfat.registats.domain.PlayerMatchStatisticLocation;
import com.darfat.registats.repository.PlayerMatchStatisticLocationRepository;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;


/**
 * REST controller for managing {@link PlayerMatchStatisticLocation}.
 */
@RestController
@RequestMapping("/api")
public class PlayerMatchStatisticLocationResource {

    private final Logger log = LoggerFactory.getLogger(PlayerMatchStatisticLocationResource.class);

    private static final String ENTITY_NAME = "playerMatchStatisticLocation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PlayerMatchStatisticLocationRepository playerMatchStatisticLocationRepository;


    public PlayerMatchStatisticLocationResource(PlayerMatchStatisticLocationRepository playerMatchStatisticLocationRepository) {
        this.playerMatchStatisticLocationRepository = playerMatchStatisticLocationRepository;
    }

    /**
     * {@code POST  /player-match-statistic-locations} : Create a new playerMatchStatistic.
     *
     * @param playerMatchStatistic the playerMatchStatistic to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new playerMatchStatistic, or with status {@code 400 (Bad Request)} if the playerMatchStatistic has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/player-match-statistic-locations")
    public ResponseEntity<PlayerMatchStatisticLocation> createPlayerMatchStatisticLocation(@RequestBody PlayerMatchStatisticLocation playerMatchStatistic) throws URISyntaxException {
        log.debug("REST request to save PlayerMatchStatisticLocation : {}", playerMatchStatistic);
        if (playerMatchStatistic.getId() != null) {
            throw new BadRequestAlertException("A new playerMatchStatistic cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PlayerMatchStatisticLocation result = playerMatchStatisticLocationRepository.save(playerMatchStatistic);
        return ResponseEntity.created(new URI("/api/player-match-statistic-locations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /player-match-statistic-locations} : Updates an existing playerMatchStatistic.
     *
     * @param playerMatchStatistic the playerMatchStatistic to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated playerMatchStatistic,
     * or with status {@code 400 (Bad Request)} if the playerMatchStatistic is not valid,
     * or with status {@code 500 (Internal Server Error)} if the playerMatchStatistic couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/player-match-statistic-locations")
    public ResponseEntity<PlayerMatchStatisticLocation> updatePlayerMatchStatisticLocation(@RequestBody PlayerMatchStatisticLocation playerMatchStatistic) throws URISyntaxException {
        log.debug("REST request to update PlayerMatchStatisticLocation : {}", playerMatchStatistic);
        if (playerMatchStatistic.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PlayerMatchStatisticLocation result = playerMatchStatisticLocationRepository.save(playerMatchStatistic);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, playerMatchStatistic.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /player-match-statistic-locations} : get all the playerMatchStatistics.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of playerMatchStatistics in body.
     */
    @GetMapping("/player-match-statistic-locations")
    public ResponseEntity<List<PlayerMatchStatisticLocation>> getAllPlayerMatchStatisticLocations(Pageable pageable) {
        log.debug("REST request to get a page of PlayerMatchStatisticLocations");
        Page<PlayerMatchStatisticLocation> page = playerMatchStatisticLocationRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /player-match-statistic-locations/:id} : get the "id" playerMatchStatistic.
     *
     * @param id the id of the playerMatchStatistic to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the playerMatchStatistic, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/player-match-statistic-locations/{id}")
    public ResponseEntity<PlayerMatchStatisticLocation> getPlayerMatchStatisticLocation(@PathVariable Long id) {
        log.debug("REST request to get PlayerMatchStatisticLocation : {}", id);
        Optional<PlayerMatchStatisticLocation> playerMatchStatistic = playerMatchStatisticLocationRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(playerMatchStatistic);
    }

    /**
     * {@code DELETE  /player-match-statistic-locations/:id} : delete the "id" playerMatchStatistic.
     *
     * @param id the id of the playerMatchStatistic to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/player-match-statistic-locations/{id}")
    public ResponseEntity<Void> deletePlayerMatchStatisticLocation(@PathVariable Long id) {
        log.debug("REST request to delete PlayerMatchStatisticLocation : {}", id);
        playerMatchStatisticLocationRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

}
