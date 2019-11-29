/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import MatchAwayInfoUpdateComponent from '@/entities/match-away-info/match-away-info-update.vue';
import MatchAwayInfoClass from '@/entities/match-away-info/match-away-info-update.component';
import MatchAwayInfoService from '@/entities/match-away-info/match-away-info.service';

import MatchLineupService from '@/entities/match-lineup/match-lineup.service';

import MatchStatisticService from '@/entities/match-statistic/match-statistic.service';

import MatchService from '@/entities/match/match.service';

import TeamService from '@/entities/team/team.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('MatchAwayInfo Management Update Component', () => {
    let wrapper: Wrapper<MatchAwayInfoClass>;
    let comp: MatchAwayInfoClass;
    let matchAwayInfoServiceStub: SinonStubbedInstance<MatchAwayInfoService>;

    beforeEach(() => {
      matchAwayInfoServiceStub = sinon.createStubInstance<MatchAwayInfoService>(MatchAwayInfoService);

      wrapper = shallowMount<MatchAwayInfoClass>(MatchAwayInfoUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          matchAwayInfoService: () => matchAwayInfoServiceStub,

          matchLineupService: () => new MatchLineupService(),

          matchStatisticService: () => new MatchStatisticService(),

          matchService: () => new MatchService(),

          teamService: () => new TeamService()
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.matchAwayInfo = entity;
        matchAwayInfoServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(matchAwayInfoServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.matchAwayInfo = entity;
        matchAwayInfoServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(matchAwayInfoServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
