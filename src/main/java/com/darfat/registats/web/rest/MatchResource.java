package com.darfat.registats.web.rest;

import com.darfat.registats.domain.*;
import com.darfat.registats.repository.*;
import com.darfat.registats.repository.search.MatchSearchRepository;
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

import javax.transaction.Transactional;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link com.darfat.registats.domain.Match}.
 */
@RestController
@RequestMapping("/api")
public class MatchResource {

    private final Logger log = LoggerFactory.getLogger(MatchResource.class);

    private static final String ENTITY_NAME = "match";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MatchRepository matchRepository;

    private final MatchSearchRepository matchSearchRepository;

    private final MatchCommentaryRepository matchCommentaryRepository;

    private final MatchLineupRepository matchLineupRepository;

    private final MatchStatisticRepository matchStatisticRepository;

    private final PlayerMatchStatisticRepository playerMatchStatisticRepository;

    public MatchResource(MatchRepository matchRepository, MatchSearchRepository matchSearchRepository,
                         MatchCommentaryRepository matchCommentaryRepository,
                         MatchLineupRepository matchLineupRepository,
                         MatchStatisticRepository matchStatisticRepository,
                         PlayerMatchStatisticRepository playerMatchStatisticRepository) {
        this.matchRepository = matchRepository;
        this.matchSearchRepository = matchSearchRepository;

        this.matchCommentaryRepository = matchCommentaryRepository;
        this.matchLineupRepository = matchLineupRepository;
        this.matchStatisticRepository = matchStatisticRepository;
        this.playerMatchStatisticRepository = playerMatchStatisticRepository;
    }

    /**
     * {@code POST  /matches} : Create a new match.
     *
     * @param match the match to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new match, or with status {@code 400 (Bad Request)} if the match has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/matches")
    public ResponseEntity<Match> createMatch(@RequestBody Match match) throws URISyntaxException {
        log.debug("REST request to save Match : {}", match);
        if (match.getId() != null) {
            throw new BadRequestAlertException("A new match cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Match result = matchRepository.save(match);
        matchSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/matches/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /matches} : Updates an existing match.
     *
     * @param match the match to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated match,
     * or with status {@code 400 (Bad Request)} if the match is not valid,
     * or with status {@code 500 (Internal Server Error)} if the match couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/matches")
    public ResponseEntity<Match> updateMatch(@RequestBody Match match) throws URISyntaxException {
        log.debug("REST request to update Match : {}", match);
        if (match.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Match result = matchRepository.save(match);
        matchSearchRepository.save(result);
        saveRelationship(match);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, match.getId().toString()))
            .body(result);
    }
    private void saveRelationship(Match match){
        if(match.getCommentaries()!=null && match.getCommentaries().size()>0){
            log.debug("save commentaries");
            for(MatchCommentary m : match.getCommentaries()){
                if(m.getMatch()==null){
                    m.setMatch(match);
                }
                matchCommentaryRepository.save(m);

            }
        }
        if(match.getHomeTeamInfo()!=null){
            final MatchTeamInfo teamInfo = match.getHomeTeamInfo();
            if(teamInfo.getLineups()!=null && teamInfo.getLineups().size() > 0){
                for(MatchLineup lineup:teamInfo.getLineups()){
                    if(lineup.getMatchTeamInfo()==null){
                        lineup.setMatchTeamInfo(teamInfo);
                    }
                    matchLineupRepository.save(lineup);
                    if(lineup.getStatistics()!=null && lineup.getStatistics().size()>0){
                        for(PlayerMatchStatistic playerStats:lineup.getStatistics()) {
                            if (playerStats.getMatchLineup() == null) {
                                playerStats.setMatchLineup(lineup);
                            }
                            playerMatchStatisticRepository.save(playerStats);
                        }
                    }
                }
            }

            if(teamInfo.getStatistics()!=null && teamInfo.getStatistics().size() > 0){
                for(MatchStatistic stats:teamInfo.getStatistics()){
                    if(stats.getMatchTeamInfo()==null){
                        stats.setMatchTeamInfo(teamInfo);
                    }
                    matchStatisticRepository.save(stats);
                }
            }

        }
    }

    /**
     * {@code GET  /matches} : get all the matches.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of matches in body.
     */
    @GetMapping("/matches")
    public ResponseEntity<List<Match>> getAllMatches(Pageable pageable) {
        log.debug("REST request to get a page of Matches");
        Page<Match> page = matchRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /matches/:id} : get the "id" match.
     *
     * @param id the id of the match to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the match, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/matches/{id}")
    public ResponseEntity<Match> getMatch(@PathVariable Long id) {
        log.debug("REST request to get Match : {}", id);
        Optional<Match> match = matchRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(match);
    }

    /**
     * {@code DELETE  /matches/:id} : delete the "id" match.
     *
     * @param id the id of the match to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/matches/{id}")
    public ResponseEntity<Void> deleteMatch(@PathVariable Long id) {
        log.debug("REST request to delete Match : {}", id);
        matchRepository.deleteById(id);
        matchSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/matches?query=:query} : search for the match corresponding
     * to the query.
     *
     * @param query the query of the match search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/matches")
    public ResponseEntity<List<Match>> searchMatches(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Matches for query {}", query);
        Page<Match> page = matchSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
