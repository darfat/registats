package com.darfat.registats.repository;
import com.darfat.registats.domain.MatchLineup;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MatchLineup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MatchLineupRepository extends JpaRepository<MatchLineup, Long> {

}
