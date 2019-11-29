/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import CompetitionTeamStatisticDetailComponent from '@/entities/competition-team-statistic/competition-team-statistic-details.vue';
import CompetitionTeamStatisticClass from '@/entities/competition-team-statistic/competition-team-statistic-details.component';
import CompetitionTeamStatisticService from '@/entities/competition-team-statistic/competition-team-statistic.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('CompetitionTeamStatistic Management Detail Component', () => {
    let wrapper: Wrapper<CompetitionTeamStatisticClass>;
    let comp: CompetitionTeamStatisticClass;
    let competitionTeamStatisticServiceStub: SinonStubbedInstance<CompetitionTeamStatisticService>;

    beforeEach(() => {
      competitionTeamStatisticServiceStub = sinon.createStubInstance<CompetitionTeamStatisticService>(CompetitionTeamStatisticService);

      wrapper = shallowMount<CompetitionTeamStatisticClass>(CompetitionTeamStatisticDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { competitionTeamStatisticService: () => competitionTeamStatisticServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundCompetitionTeamStatistic = { id: 123 };
        competitionTeamStatisticServiceStub.find.resolves(foundCompetitionTeamStatistic);

        // WHEN
        comp.retrieveCompetitionTeamStatistic(123);
        await comp.$nextTick();

        // THEN
        expect(comp.competitionTeamStatistic).toBe(foundCompetitionTeamStatistic);
      });
    });
  });
});
