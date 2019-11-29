package com.darfat.registats.repository.search;
import com.darfat.registats.domain.MatchCommentary;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link MatchCommentary} entity.
 */
public interface MatchCommentarySearchRepository extends ElasticsearchRepository<MatchCommentary, Long> {
}
