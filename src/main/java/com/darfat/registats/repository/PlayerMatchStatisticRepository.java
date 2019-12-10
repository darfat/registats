package com.darfat.registats.repository;
import com.darfat.registats.domain.PlayerMatchStatistic;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;


/**
 * Spring Data  repository for the PlayerMatchStatistic entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlayerMatchStatisticRepository extends JpaRepository<PlayerMatchStatistic, Long> {

    @Transactional
    @Modifying
    List<PlayerMatchStatistic> deleteByMatchLineupId(Long matchLineupId);

}
