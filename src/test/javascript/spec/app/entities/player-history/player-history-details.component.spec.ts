/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import PlayerHistoryDetailComponent from '@/entities/player-history/player-history-details.vue';
import PlayerHistoryClass from '@/entities/player-history/player-history-details.component';
import PlayerHistoryService from '@/entities/player-history/player-history.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('PlayerHistory Management Detail Component', () => {
    let wrapper: Wrapper<PlayerHistoryClass>;
    let comp: PlayerHistoryClass;
    let playerHistoryServiceStub: SinonStubbedInstance<PlayerHistoryService>;

    beforeEach(() => {
      playerHistoryServiceStub = sinon.createStubInstance<PlayerHistoryService>(PlayerHistoryService);

      wrapper = shallowMount<PlayerHistoryClass>(PlayerHistoryDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { playerHistoryService: () => playerHistoryServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundPlayerHistory = { id: 123 };
        playerHistoryServiceStub.find.resolves(foundPlayerHistory);

        // WHEN
        comp.retrievePlayerHistory(123);
        await comp.$nextTick();

        // THEN
        expect(comp.playerHistory).toBe(foundPlayerHistory);
      });
    });
  });
});
