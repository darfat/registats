/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import PlayerMatchStatisticDetailComponent from '@/entities/player-match-statistic/player-match-statistic-details.vue';
import PlayerMatchStatisticClass from '@/entities/player-match-statistic/player-match-statistic-details.component';
import PlayerMatchStatisticService from '@/entities/player-match-statistic/player-match-statistic.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('PlayerMatchStatistic Management Detail Component', () => {
    let wrapper: Wrapper<PlayerMatchStatisticClass>;
    let comp: PlayerMatchStatisticClass;
    let playerMatchStatisticServiceStub: SinonStubbedInstance<PlayerMatchStatisticService>;

    beforeEach(() => {
      playerMatchStatisticServiceStub = sinon.createStubInstance<PlayerMatchStatisticService>(PlayerMatchStatisticService);

      wrapper = shallowMount<PlayerMatchStatisticClass>(PlayerMatchStatisticDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { playerMatchStatisticService: () => playerMatchStatisticServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundPlayerMatchStatistic = { id: 123 };
        playerMatchStatisticServiceStub.find.resolves(foundPlayerMatchStatistic);

        // WHEN
        comp.retrievePlayerMatchStatistic(123);
        await comp.$nextTick();

        // THEN
        expect(comp.playerMatchStatistic).toBe(foundPlayerMatchStatistic);
      });
    });
  });
});
