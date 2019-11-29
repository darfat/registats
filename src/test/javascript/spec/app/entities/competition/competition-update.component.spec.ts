/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import format from 'date-fns/format';
import parseISO from 'date-fns/parseISO';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import CompetitionUpdateComponent from '@/entities/competition/competition-update.vue';
import CompetitionClass from '@/entities/competition/competition-update.component';
import CompetitionService from '@/entities/competition/competition.service';

import CompetitionNameService from '@/entities/competition-name/competition-name.service';

import CompetitionTeamService from '@/entities/competition-team/competition-team.service';

import CompetitionStandingService from '@/entities/competition-standing/competition-standing.service';

import CompetitionPlayerStatisticService from '@/entities/competition-player-statistic/competition-player-statistic.service';

import CompetitionTeamStatisticService from '@/entities/competition-team-statistic/competition-team-statistic.service';

import MatchService from '@/entities/match/match.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('Competition Management Update Component', () => {
    let wrapper: Wrapper<CompetitionClass>;
    let comp: CompetitionClass;
    let competitionServiceStub: SinonStubbedInstance<CompetitionService>;

    beforeEach(() => {
      competitionServiceStub = sinon.createStubInstance<CompetitionService>(CompetitionService);

      wrapper = shallowMount<CompetitionClass>(CompetitionUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          competitionService: () => competitionServiceStub,

          competitionNameService: () => new CompetitionNameService(),

          competitionTeamService: () => new CompetitionTeamService(),

          competitionStandingService: () => new CompetitionStandingService(),

          competitionPlayerStatisticService: () => new CompetitionPlayerStatisticService(),

          competitionTeamStatisticService: () => new CompetitionTeamStatisticService(),

          matchService: () => new MatchService()
        }
      });
      comp = wrapper.vm;
    });

    describe('load', () => {
      it('Should convert date from string', () => {
        // GIVEN
        const date = new Date('2019-10-15T11:42:02Z');

        // WHEN
        const convertedDate = comp.convertDateTimeFromServer(date);

        // THEN
        expect(convertedDate).toEqual(format(date, DATE_TIME_LONG_FORMAT));
      });

      it('Should not convert date if date is not present', () => {
        expect(comp.convertDateTimeFromServer(null)).toBeNull();
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.competition = entity;
        competitionServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(competitionServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.competition = entity;
        competitionServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(competitionServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
