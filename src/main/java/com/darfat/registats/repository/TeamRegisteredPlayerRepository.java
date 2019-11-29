package com.darfat.registats.repository;
import com.darfat.registats.domain.TeamRegisteredPlayer;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TeamRegisteredPlayer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TeamRegisteredPlayerRepository extends JpaRepository<TeamRegisteredPlayer, Long> {

}
