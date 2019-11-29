package com.darfat.registats.repository.search;
import com.darfat.registats.domain.Position;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Position} entity.
 */
public interface PositionSearchRepository extends ElasticsearchRepository<Position, Long> {
}
