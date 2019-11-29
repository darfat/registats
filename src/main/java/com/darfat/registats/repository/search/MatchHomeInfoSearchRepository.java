package com.darfat.registats.repository.search;
import com.darfat.registats.domain.MatchHomeInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link MatchHomeInfo} entity.
 */
public interface MatchHomeInfoSearchRepository extends ElasticsearchRepository<MatchHomeInfo, Long> {
}
