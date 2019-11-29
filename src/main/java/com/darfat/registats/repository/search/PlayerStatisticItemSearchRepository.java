package com.darfat.registats.repository.search;
import com.darfat.registats.domain.PlayerStatisticItem;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link PlayerStatisticItem} entity.
 */
public interface PlayerStatisticItemSearchRepository extends ElasticsearchRepository<PlayerStatisticItem, Long> {
}
