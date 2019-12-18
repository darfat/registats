package com.darfat.registats.repository;
import com.darfat.registats.domain.MatchStatisticItem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the MatchStatisticItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MatchStatisticItemRepository extends JpaRepository<MatchStatisticItem, Long> {

    List<MatchStatisticItem> findAllByActive(Boolean active);
}
