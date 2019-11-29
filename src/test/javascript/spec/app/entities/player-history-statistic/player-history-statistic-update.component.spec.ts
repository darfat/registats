/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import PlayerHistoryStatisticUpdateComponent from '@/entities/player-history-statistic/player-history-statistic-update.vue';
import PlayerHistoryStatisticClass from '@/entities/player-history-statistic/player-history-statistic-update.component';
import PlayerHistoryStatisticService from '@/entities/player-history-statistic/player-history-statistic.service';

import PlayerHistoryService from '@/entities/player-history/player-history.service';

import PlayerStatisticItemService from '@/entities/player-statistic-item/player-statistic-item.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('PlayerHistoryStatistic Management Update Component', () => {
    let wrapper: Wrapper<PlayerHistoryStatisticClass>;
    let comp: PlayerHistoryStatisticClass;
    let playerHistoryStatisticServiceStub: SinonStubbedInstance<PlayerHistoryStatisticService>;

    beforeEach(() => {
      playerHistoryStatisticServiceStub = sinon.createStubInstance<PlayerHistoryStatisticService>(PlayerHistoryStatisticService);

      wrapper = shallowMount<PlayerHistoryStatisticClass>(PlayerHistoryStatisticUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          playerHistoryStatisticService: () => playerHistoryStatisticServiceStub,

          playerHistoryService: () => new PlayerHistoryService(),

          playerStatisticItemService: () => new PlayerStatisticItemService()
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.playerHistoryStatistic = entity;
        playerHistoryStatisticServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(playerHistoryStatisticServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.playerHistoryStatistic = entity;
        playerHistoryStatisticServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(playerHistoryStatisticServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
