package com.darfat.registats.repository;
import com.darfat.registats.domain.CompetitionPlayerStatistic;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CompetitionPlayerStatistic entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompetitionPlayerStatisticRepository extends JpaRepository<CompetitionPlayerStatistic, Long> {

}
