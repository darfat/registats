package com.darfat.registats.repository;
import com.darfat.registats.domain.GlobalPlayerStatistic;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the GlobalPlayerStatistic entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GlobalPlayerStatisticRepository extends JpaRepository<GlobalPlayerStatistic, Long> {

}
