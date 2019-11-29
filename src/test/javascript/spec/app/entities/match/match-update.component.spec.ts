/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import format from 'date-fns/format';
import parseISO from 'date-fns/parseISO';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import MatchUpdateComponent from '@/entities/match/match-update.vue';
import MatchClass from '@/entities/match/match-update.component';
import MatchService from '@/entities/match/match.service';

import MatchCommentaryService from '@/entities/match-commentary/match-commentary.service';

import VenueService from '@/entities/venue/venue.service';

import TeamService from '@/entities/team/team.service';

import CompetitionService from '@/entities/competition/competition.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('Match Management Update Component', () => {
    let wrapper: Wrapper<MatchClass>;
    let comp: MatchClass;
    let matchServiceStub: SinonStubbedInstance<MatchService>;

    beforeEach(() => {
      matchServiceStub = sinon.createStubInstance<MatchService>(MatchService);

      wrapper = shallowMount<MatchClass>(MatchUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          matchService: () => matchServiceStub,

          matchCommentaryService: () => new MatchCommentaryService(),

          venueService: () => new VenueService(),

          teamService: () => new TeamService(),

          competitionService: () => new CompetitionService()
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
        comp.match = entity;
        matchServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(matchServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.match = entity;
        matchServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(matchServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
