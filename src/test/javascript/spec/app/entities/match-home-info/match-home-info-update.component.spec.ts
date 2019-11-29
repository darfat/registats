/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import MatchHomeInfoUpdateComponent from '@/entities/match-home-info/match-home-info-update.vue';
import MatchHomeInfoClass from '@/entities/match-home-info/match-home-info-update.component';
import MatchHomeInfoService from '@/entities/match-home-info/match-home-info.service';

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
  describe('MatchHomeInfo Management Update Component', () => {
    let wrapper: Wrapper<MatchHomeInfoClass>;
    let comp: MatchHomeInfoClass;
    let matchHomeInfoServiceStub: SinonStubbedInstance<MatchHomeInfoService>;

    beforeEach(() => {
      matchHomeInfoServiceStub = sinon.createStubInstance<MatchHomeInfoService>(MatchHomeInfoService);

      wrapper = shallowMount<MatchHomeInfoClass>(MatchHomeInfoUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          matchHomeInfoService: () => matchHomeInfoServiceStub,

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
        comp.matchHomeInfo = entity;
        matchHomeInfoServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(matchHomeInfoServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.matchHomeInfo = entity;
        matchHomeInfoServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(matchHomeInfoServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
