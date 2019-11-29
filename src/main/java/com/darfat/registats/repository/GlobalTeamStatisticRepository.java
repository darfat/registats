package com.darfat.registats.repository;
import com.darfat.registats.domain.GlobalTeamStatistic;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the GlobalTeamStatistic entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GlobalTeamStatisticRepository extends JpaRepository<GlobalTeamStatistic, Long> {

}
