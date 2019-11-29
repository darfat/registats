package com.darfat.registats.repository.search;
import com.darfat.registats.domain.CompetitionTeam;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link CompetitionTeam} entity.
 */
public interface CompetitionTeamSearchRepository extends ElasticsearchRepository<CompetitionTeam, Long> {
}
