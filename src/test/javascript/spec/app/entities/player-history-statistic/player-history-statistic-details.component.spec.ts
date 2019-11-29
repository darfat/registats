/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import PlayerHistoryStatisticDetailComponent from '@/entities/player-history-statistic/player-history-statistic-details.vue';
import PlayerHistoryStatisticClass from '@/entities/player-history-statistic/player-history-statistic-details.component';
import PlayerHistoryStatisticService from '@/entities/player-history-statistic/player-history-statistic.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('PlayerHistoryStatistic Management Detail Component', () => {
    let wrapper: Wrapper<PlayerHistoryStatisticClass>;
    let comp: PlayerHistoryStatisticClass;
    let playerHistoryStatisticServiceStub: SinonStubbedInstance<PlayerHistoryStatisticService>;

    beforeEach(() => {
      playerHistoryStatisticServiceStub = sinon.createStubInstance<PlayerHistoryStatisticService>(PlayerHistoryStatisticService);

      wrapper = shallowMount<PlayerHistoryStatisticClass>(PlayerHistoryStatisticDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { playerHistoryStatisticService: () => playerHistoryStatisticServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundPlayerHistoryStatistic = { id: 123 };
        playerHistoryStatisticServiceStub.find.resolves(foundPlayerHistoryStatistic);

        // WHEN
        comp.retrievePlayerHistoryStatistic(123);
        await comp.$nextTick();

        // THEN
        expect(comp.playerHistoryStatistic).toBe(foundPlayerHistoryStatistic);
      });
    });
  });
});
