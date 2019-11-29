/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import MatchStatisticUpdateComponent from '@/entities/match-statistic/match-statistic-update.vue';
import MatchStatisticClass from '@/entities/match-statistic/match-statistic-update.component';
import MatchStatisticService from '@/entities/match-statistic/match-statistic.service';

import TeamService from '@/entities/team/team.service';

import MatchStatisticItemService from '@/entities/match-statistic-item/match-statistic-item.service';

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
  describe('MatchStatistic Management Update Component', () => {
    let wrapper: Wrapper<MatchStatisticClass>;
    let comp: MatchStatisticClass;
    let matchStatisticServiceStub: SinonStubbedInstance<MatchStatisticService>;

    beforeEach(() => {
      matchStatisticServiceStub = sinon.createStubInstance<MatchStatisticService>(MatchStatisticService);

      wrapper = shallowMount<MatchStatisticClass>(MatchStatisticUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          matchStatisticService: () => matchStatisticServiceStub,

          teamService: () => new TeamService(),

          matchStatisticItemService: () => new MatchStatisticItemService(),

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
        comp.matchStatistic = entity;
        matchStatisticServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(matchStatisticServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.matchStatistic = entity;
        matchStatisticServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(matchStatisticServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
