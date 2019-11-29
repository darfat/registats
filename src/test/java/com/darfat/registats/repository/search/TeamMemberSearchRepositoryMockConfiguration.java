package com.darfat.registats.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link TeamMemberSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class TeamMemberSearchRepositoryMockConfiguration {

    @MockBean
    private TeamMemberSearchRepository mockTeamMemberSearchRepository;

}
