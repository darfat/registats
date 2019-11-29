/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import GlobalPlayerStatisticDetailComponent from '@/entities/global-player-statistic/global-player-statistic-details.vue';
import GlobalPlayerStatisticClass from '@/entities/global-player-statistic/global-player-statistic-details.component';
import GlobalPlayerStatisticService from '@/entities/global-player-statistic/global-player-statistic.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('GlobalPlayerStatistic Management Detail Component', () => {
    let wrapper: Wrapper<GlobalPlayerStatisticClass>;
    let comp: GlobalPlayerStatisticClass;
    let globalPlayerStatisticServiceStub: SinonStubbedInstance<GlobalPlayerStatisticService>;

    beforeEach(() => {
      globalPlayerStatisticServiceStub = sinon.createStubInstance<GlobalPlayerStatisticService>(GlobalPlayerStatisticService);

      wrapper = shallowMount<GlobalPlayerStatisticClass>(GlobalPlayerStatisticDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { globalPlayerStatisticService: () => globalPlayerStatisticServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundGlobalPlayerStatistic = { id: 123 };
        globalPlayerStatisticServiceStub.find.resolves(foundGlobalPlayerStatistic);

        // WHEN
        comp.retrieveGlobalPlayerStatistic(123);
        await comp.$nextTick();

        // THEN
        expect(comp.globalPlayerStatistic).toBe(foundGlobalPlayerStatistic);
      });
    });
  });
});
