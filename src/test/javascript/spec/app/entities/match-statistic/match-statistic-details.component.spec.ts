/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import MatchStatisticDetailComponent from '@/entities/match-statistic/match-statistic-details.vue';
import MatchStatisticClass from '@/entities/match-statistic/match-statistic-details.component';
import MatchStatisticService from '@/entities/match-statistic/match-statistic.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('MatchStatistic Management Detail Component', () => {
    let wrapper: Wrapper<MatchStatisticClass>;
    let comp: MatchStatisticClass;
    let matchStatisticServiceStub: SinonStubbedInstance<MatchStatisticService>;

    beforeEach(() => {
      matchStatisticServiceStub = sinon.createStubInstance<MatchStatisticService>(MatchStatisticService);

      wrapper = shallowMount<MatchStatisticClass>(MatchStatisticDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { matchStatisticService: () => matchStatisticServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundMatchStatistic = { id: 123 };
        matchStatisticServiceStub.find.resolves(foundMatchStatistic);

        // WHEN
        comp.retrieveMatchStatistic(123);
        await comp.$nextTick();

        // THEN
        expect(comp.matchStatistic).toBe(foundMatchStatistic);
      });
    });
  });
});
