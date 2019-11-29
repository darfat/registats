/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import CompetitionTeamStatisticUpdateComponent from '@/entities/competition-team-statistic/competition-team-statistic-update.vue';
import CompetitionTeamStatisticClass from '@/entities/competition-team-statistic/competition-team-statistic-update.component';
import CompetitionTeamStatisticService from '@/entities/competition-team-statistic/competition-team-statistic.service';

import CompetitionService from '@/entities/competition/competition.service';

import CompetitionStatisticItemService from '@/entities/competition-statistic-item/competition-statistic-item.service';

import TeamService from '@/entities/team/team.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('CompetitionTeamStatistic Management Update Component', () => {
    let wrapper: Wrapper<CompetitionTeamStatisticClass>;
    let comp: CompetitionTeamStatisticClass;
    let competitionTeamStatisticServiceStub: SinonStubbedInstance<CompetitionTeamStatisticService>;

    beforeEach(() => {
      competitionTeamStatisticServiceStub = sinon.createStubInstance<CompetitionTeamStatisticService>(CompetitionTeamStatisticService);

      wrapper = shallowMount<CompetitionTeamStatisticClass>(CompetitionTeamStatisticUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          competitionTeamStatisticService: () => competitionTeamStatisticServiceStub,

          competitionService: () => new CompetitionService(),

          competitionStatisticItemService: () => new CompetitionStatisticItemService(),

          teamService: () => new TeamService()
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.competitionTeamStatistic = entity;
        competitionTeamStatisticServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(competitionTeamStatisticServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.competitionTeamStatistic = entity;
        competitionTeamStatisticServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(competitionTeamStatisticServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
