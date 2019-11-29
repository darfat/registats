package com.darfat.registats.repository;
import com.darfat.registats.domain.CompetitionTeamStatistic;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CompetitionTeamStatistic entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompetitionTeamStatisticRepository extends JpaRepository<CompetitionTeamStatistic, Long> {

}
