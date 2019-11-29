package com.darfat.registats.repository;
import com.darfat.registats.domain.CompetitionStanding;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CompetitionStanding entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompetitionStandingRepository extends JpaRepository<CompetitionStanding, Long> {

}
