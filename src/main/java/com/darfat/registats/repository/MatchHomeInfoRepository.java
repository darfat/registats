package com.darfat.registats.repository;
import com.darfat.registats.domain.MatchHomeInfo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MatchHomeInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MatchHomeInfoRepository extends JpaRepository<MatchHomeInfo, Long> {

}
