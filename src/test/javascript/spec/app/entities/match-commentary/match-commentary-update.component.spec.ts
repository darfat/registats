/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import format from 'date-fns/format';
import parseISO from 'date-fns/parseISO';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import MatchCommentaryUpdateComponent from '@/entities/match-commentary/match-commentary-update.vue';
import MatchCommentaryClass from '@/entities/match-commentary/match-commentary-update.component';
import MatchCommentaryService from '@/entities/match-commentary/match-commentary.service';

import PlayerStatisticItemService from '@/entities/player-statistic-item/player-statistic-item.service';

import MatchStatisticItemService from '@/entities/match-statistic-item/match-statistic-item.service';

import TeamService from '@/entities/team/team.service';

import PlayerService from '@/entities/player/player.service';

import MatchService from '@/entities/match/match.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('MatchCommentary Management Update Component', () => {
    let wrapper: Wrapper<MatchCommentaryClass>;
    let comp: MatchCommentaryClass;
    let matchCommentaryServiceStub: SinonStubbedInstance<MatchCommentaryService>;

    beforeEach(() => {
      matchCommentaryServiceStub = sinon.createStubInstance<MatchCommentaryService>(MatchCommentaryService);

      wrapper = shallowMount<MatchCommentaryClass>(MatchCommentaryUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          matchCommentaryService: () => matchCommentaryServiceStub,

          playerStatisticItemService: () => new PlayerStatisticItemService(),

          matchStatisticItemService: () => new MatchStatisticItemService(),

          teamService: () => new TeamService(),

          playerService: () => new PlayerService(),

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
        comp.matchCommentary = entity;
        matchCommentaryServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(matchCommentaryServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.matchCommentary = entity;
        matchCommentaryServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(matchCommentaryServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
