package com.darfat.registats.repository.search;
import com.darfat.registats.domain.MatchTeamInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link MatchTeamInfo} entity.
 */
public interface MatchTeamInfoSearchRepository extends ElasticsearchRepository<MatchTeamInfo, Long> {
}
