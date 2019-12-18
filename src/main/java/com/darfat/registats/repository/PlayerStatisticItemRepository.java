package com.darfat.registats.repository;
import com.darfat.registats.domain.PlayerStatisticItem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the PlayerStatisticItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlayerStatisticItemRepository extends JpaRepository<PlayerStatisticItem, Long> {

    public List<PlayerStatisticItem> findAllByActive(Boolean active);

}
