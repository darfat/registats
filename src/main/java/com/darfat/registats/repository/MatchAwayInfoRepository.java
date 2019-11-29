package com.darfat.registats.repository;
import com.darfat.registats.domain.MatchAwayInfo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MatchAwayInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MatchAwayInfoRepository extends JpaRepository<MatchAwayInfo, Long> {

}
