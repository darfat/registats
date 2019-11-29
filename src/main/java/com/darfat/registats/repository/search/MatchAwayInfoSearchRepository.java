package com.darfat.registats.repository.search;
import com.darfat.registats.domain.MatchAwayInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link MatchAwayInfo} entity.
 */
public interface MatchAwayInfoSearchRepository extends ElasticsearchRepository<MatchAwayInfo, Long> {
}
