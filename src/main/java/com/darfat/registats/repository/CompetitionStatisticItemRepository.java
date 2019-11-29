package com.darfat.registats.repository;
import com.darfat.registats.domain.CompetitionStatisticItem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CompetitionStatisticItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompetitionStatisticItemRepository extends JpaRepository<CompetitionStatisticItem, Long> {

}
