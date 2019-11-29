package com.darfat.registats.repository;
import com.darfat.registats.domain.PlayerHistory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PlayerHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlayerHistoryRepository extends JpaRepository<PlayerHistory, Long> {

}
