package com.darfat.registats.repository;
import com.darfat.registats.domain.PlayerHistoryStatistic;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PlayerHistoryStatistic entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlayerHistoryStatisticRepository extends JpaRepository<PlayerHistoryStatistic, Long> {

}
