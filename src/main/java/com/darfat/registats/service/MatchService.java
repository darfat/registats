package com.darfat.registats.service;

import com.darfat.registats.domain.*;
import com.darfat.registats.repository.*;
import org.checkerframework.checker.units.qual.A;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class MatchService {

    private final Logger log = LoggerFactory.getLogger(MatchService.class);


    @Autowired
    PlayerStatisticItemRepository playerStatisticItemRepository;

    @Autowired
    MatchStatisticItemRepository matchStatisticItemRepository;

    @Autowired
    PositionService positionService;

    @Autowired
    MatchCommentaryRepository matchCommentaryRepository;

    @Autowired
    MatchLineupRepository matchLineupRepository;

    @Autowired
    MatchStatisticRepository matchStatisticRepository;

    @Autowired
    PlayerMatchStatisticRepository playerMatchStatisticRepository;

    public Match startMatch(Match match){
        Set<MatchStatistic> matchStatistics = initMatchStats(match.getHomeTeamInfo());
        match.getHomeTeamInfo().setStatistics(matchStatistics);
        Set<MatchLineup> lineups = initPlayerMatchStatistic(match.getHomeTeamInfo().getLineups());
        match.getHomeTeamInfo().setLineups(lineups);
        saveStats(match);
        return match;
    }
    public void endMatch(Match match){
        saveStats(match);
        saveCommentaries(match);
    }
    Set<MatchLineup> initPlayerMatchStatistic(Set<MatchLineup> starting){
        Set<MatchLineup> lineups = new HashSet<>();
        for(MatchLineup lineup:starting){
            if(lineup.getNumber()!=null) {
                Set<PlayerMatchStatistic> statistics = initPlayerMatchStatistic(lineup);
                lineup.setPosition(positionService.findPosition(lineup.getRole()));
                lineup.setStatistics(statistics);
                if(lineup.getMinuteIn()!=null && lineup.getMinuteIn().intValue()>-1){
                    lineup.setStatus("STARTING");
                } else {
                    lineup.setStatus("SUBS");
                }
                lineups.add(lineup);
            }
        }
        return lineups;
    }
    Set<PlayerMatchStatistic> initPlayerMatchStatistic(MatchLineup lineup){
        Set<PlayerMatchStatistic> statistics = new HashSet<>();
        List<PlayerStatisticItem> items = playerStatisticItemRepository.findAllByActive(true);
        for (PlayerStatisticItem item:items){
            statistics.add(new PlayerMatchStatistic(lineup,item));
        }
        return statistics;
    }

    Set<MatchStatistic> initMatchStats(MatchTeamInfo teamInfo){
        Set<MatchStatistic> statistics = new HashSet<>();
        List<MatchStatisticItem> items = matchStatisticItemRepository.findAllByActive(true);
        for (MatchStatisticItem item:items){
            statistics.add(new MatchStatistic(teamInfo,item));
        }
        return statistics;
    }
    private void saveCommentaries(Match match){
        if(match.getCommentaries()!=null && match.getCommentaries().size()>0){
            log.debug("save commentaries");
            for(MatchCommentary m : match.getCommentaries()){
                if(m.getMatch()==null){
                    m.setMatch(match);
                }
                matchCommentaryRepository.save(m);

            }
        }

    }

    private void saveStats(Match match){
        if(match.getHomeTeamInfo()!=null){
            final MatchTeamInfo teamInfo = match.getHomeTeamInfo();
            if(teamInfo.getLineups()!=null && teamInfo.getLineups().size() > 0){
                for(MatchLineup lineup:teamInfo.getLineups()){
                    log.info("save lineup {} ",lineup);
                    if(lineup.getNumber()== null || lineup.getRole() == null){
                        if(lineup.getId()!=null){
                            matchLineupRepository.delete(lineup);
                            ;
                        }
                        break;
                    }
                    if(lineup.getMatchTeamInfo()==null){
                        lineup.setMatchTeamInfo(teamInfo);
                    }
                    
                   // lineup.setPosition(positionService.findPosition(lineup.getRole()));
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

}
