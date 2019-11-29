/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import MatchLineupUpdateComponent from '@/entities/match-lineup/match-lineup-update.vue';
import MatchLineupClass from '@/entities/match-lineup/match-lineup-update.component';
import MatchLineupService from '@/entities/match-lineup/match-lineup.service';

import PlayerMatchStatisticService from '@/entities/player-match-statistic/player-match-statistic.service';

import PositionService from '@/entities/position/position.service';

import PlayerService from '@/entities/player/player.service';

import MatchHomeInfoService from '@/entities/match-home-info/match-home-info.service';

import MatchAwayInfoService from '@/entities/match-away-info/match-away-info.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('MatchLineup Management Update Component', () => {
    let wrapper: Wrapper<MatchLineupClass>;
    let comp: MatchLineupClass;
    let matchLineupServiceStub: SinonStubbedInstance<MatchLineupService>;

    beforeEach(() => {
      matchLineupServiceStub = sinon.createStubInstance<MatchLineupService>(MatchLineupService);

      wrapper = shallowMount<MatchLineupClass>(MatchLineupUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          matchLineupService: () => matchLineupServiceStub,

          playerMatchStatisticService: () => new PlayerMatchStatisticService(),

          positionService: () => new PositionService(),

          playerService: () => new PlayerService(),

          matchHomeInfoService: () => new MatchHomeInfoService(),

          matchAwayInfoService: () => new MatchAwayInfoService()
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.matchLineup = entity;
        matchLineupServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(matchLineupServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.matchLineup = entity;
        matchLineupServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(matchLineupServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
