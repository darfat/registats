package com.darfat.registats.repository;
import com.darfat.registats.domain.MatchTeamInfo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MatchTeamInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MatchTeamInfoRepository extends JpaRepository<MatchTeamInfo, Long> {

}
