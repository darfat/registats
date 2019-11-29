package com.darfat.registats.repository;
import com.darfat.registats.domain.CompetitionTeam;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CompetitionTeam entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompetitionTeamRepository extends JpaRepository<CompetitionTeam, Long> {

}
