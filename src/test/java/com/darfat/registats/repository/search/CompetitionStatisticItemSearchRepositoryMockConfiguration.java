package com.darfat.registats.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link CompetitionStatisticItemSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class CompetitionStatisticItemSearchRepositoryMockConfiguration {

    @MockBean
    private CompetitionStatisticItemSearchRepository mockCompetitionStatisticItemSearchRepository;

}
