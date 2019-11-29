/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import GlobalTeamStatisticDetailComponent from '@/entities/global-team-statistic/global-team-statistic-details.vue';
import GlobalTeamStatisticClass from '@/entities/global-team-statistic/global-team-statistic-details.component';
import GlobalTeamStatisticService from '@/entities/global-team-statistic/global-team-statistic.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('GlobalTeamStatistic Management Detail Component', () => {
    let wrapper: Wrapper<GlobalTeamStatisticClass>;
    let comp: GlobalTeamStatisticClass;
    let globalTeamStatisticServiceStub: SinonStubbedInstance<GlobalTeamStatisticService>;

    beforeEach(() => {
      globalTeamStatisticServiceStub = sinon.createStubInstance<GlobalTeamStatisticService>(GlobalTeamStatisticService);

      wrapper = shallowMount<GlobalTeamStatisticClass>(GlobalTeamStatisticDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { globalTeamStatisticService: () => globalTeamStatisticServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundGlobalTeamStatistic = { id: 123 };
        globalTeamStatisticServiceStub.find.resolves(foundGlobalTeamStatistic);

        // WHEN
        comp.retrieveGlobalTeamStatistic(123);
        await comp.$nextTick();

        // THEN
        expect(comp.globalTeamStatistic).toBe(foundGlobalTeamStatistic);
      });
    });
  });
});
