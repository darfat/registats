package com.darfat.registats.repository.search;
import com.darfat.registats.domain.TeamRegisteredPlayer;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link TeamRegisteredPlayer} entity.
 */
public interface TeamRegisteredPlayerSearchRepository extends ElasticsearchRepository<TeamRegisteredPlayer, Long> {
}
