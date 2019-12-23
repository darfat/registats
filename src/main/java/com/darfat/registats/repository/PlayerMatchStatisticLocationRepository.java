package com.darfat.registats.repository;

import com.darfat.registats.domain.PlayerMatchStatisticLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface PlayerMatchStatisticLocationRepository extends JpaRepository<PlayerMatchStatisticLocation, Long> {
}
