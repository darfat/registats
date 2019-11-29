/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import CompetitionPlayerStatisticDetailComponent from '@/entities/competition-player-statistic/competition-player-statistic-details.vue';
import CompetitionPlayerStatisticClass from '@/entities/competition-player-statistic/competition-player-statistic-details.component';
import CompetitionPlayerStatisticService from '@/entities/competition-player-statistic/competition-player-statistic.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('CompetitionPlayerStatistic Management Detail Component', () => {
    let wrapper: Wrapper<CompetitionPlayerStatisticClass>;
    let comp: CompetitionPlayerStatisticClass;
    let competitionPlayerStatisticServiceStub: SinonStubbedInstance<CompetitionPlayerStatisticService>;

    beforeEach(() => {
      competitionPlayerStatisticServiceStub = sinon.createStubInstance<CompetitionPlayerStatisticService>(
        CompetitionPlayerStatisticService
      );

      wrapper = shallowMount<CompetitionPlayerStatisticClass>(CompetitionPlayerStatisticDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { competitionPlayerStatisticService: () => competitionPlayerStatisticServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundCompetitionPlayerStatistic = { id: 123 };
        competitionPlayerStatisticServiceStub.find.resolves(foundCompetitionPlayerStatistic);

        // WHEN
        comp.retrieveCompetitionPlayerStatistic(123);
        await comp.$nextTick();

        // THEN
        expect(comp.competitionPlayerStatistic).toBe(foundCompetitionPlayerStatistic);
      });
    });
  });
});
