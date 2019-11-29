/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import PlayerMatchStatisticUpdateComponent from '@/entities/player-match-statistic/player-match-statistic-update.vue';
import PlayerMatchStatisticClass from '@/entities/player-match-statistic/player-match-statistic-update.component';
import PlayerMatchStatisticService from '@/entities/player-match-statistic/player-match-statistic.service';

import MatchLineupService from '@/entities/match-lineup/match-lineup.service';

import PlayerStatisticItemService from '@/entities/player-statistic-item/player-statistic-item.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('PlayerMatchStatistic Management Update Component', () => {
    let wrapper: Wrapper<PlayerMatchStatisticClass>;
    let comp: PlayerMatchStatisticClass;
    let playerMatchStatisticServiceStub: SinonStubbedInstance<PlayerMatchStatisticService>;

    beforeEach(() => {
      playerMatchStatisticServiceStub = sinon.createStubInstance<PlayerMatchStatisticService>(PlayerMatchStatisticService);

      wrapper = shallowMount<PlayerMatchStatisticClass>(PlayerMatchStatisticUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          playerMatchStatisticService: () => playerMatchStatisticServiceStub,

          matchLineupService: () => new MatchLineupService(),

          playerStatisticItemService: () => new PlayerStatisticItemService()
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.playerMatchStatistic = entity;
        playerMatchStatisticServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(playerMatchStatisticServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.playerMatchStatistic = entity;
        playerMatchStatisticServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(playerMatchStatisticServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
