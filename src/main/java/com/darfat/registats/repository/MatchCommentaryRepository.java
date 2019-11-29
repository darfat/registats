package com.darfat.registats.repository;
import com.darfat.registats.domain.MatchCommentary;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MatchCommentary entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MatchCommentaryRepository extends JpaRepository<MatchCommentary, Long> {

}
