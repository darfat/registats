package com.darfat.registats.repository.search;
import com.darfat.registats.domain.TeamMember;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link TeamMember} entity.
 */
public interface TeamMemberSearchRepository extends ElasticsearchRepository<TeamMember, Long> {
}
