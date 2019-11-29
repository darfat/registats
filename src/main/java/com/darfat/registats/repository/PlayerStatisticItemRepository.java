package com.darfat.registats.repository;
import com.darfat.registats.domain.PlayerStatisticItem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PlayerStatisticItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlayerStatisticItemRepository extends JpaRepository<PlayerStatisticItem, Long> {

}
