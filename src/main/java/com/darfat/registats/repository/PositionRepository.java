package com.darfat.registats.repository;
import com.darfat.registats.domain.Position;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Position entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PositionRepository extends JpaRepository<Position, Long> {

    Position findOneByName(String name);
}
