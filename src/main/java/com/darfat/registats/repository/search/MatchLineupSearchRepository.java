package com.darfat.registats.repository.search;
import com.darfat.registats.domain.MatchLineup;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link MatchLineup} entity.
 */
public interface MatchLineupSearchRepository extends ElasticsearchRepository<MatchLineup, Long> {
}
