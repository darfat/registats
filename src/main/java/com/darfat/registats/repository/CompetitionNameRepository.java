package com.darfat.registats.repository;
import com.darfat.registats.domain.CompetitionName;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CompetitionName entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompetitionNameRepository extends JpaRepository<CompetitionName, Long> {

}
