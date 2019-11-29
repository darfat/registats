package com.darfat.registats.repository.search;
import com.darfat.registats.domain.Player;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Player} entity.
 */
public interface PlayerSearchRepository extends ElasticsearchRepository<Player, Long> {
}
