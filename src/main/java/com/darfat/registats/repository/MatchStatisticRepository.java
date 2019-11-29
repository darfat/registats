package com.darfat.registats.repository;
import com.darfat.registats.domain.MatchStatistic;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MatchStatistic entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MatchStatisticRepository extends JpaRepository<MatchStatistic, Long> {

}
