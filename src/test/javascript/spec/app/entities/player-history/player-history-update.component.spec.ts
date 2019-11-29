/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import format from 'date-fns/format';
import parseISO from 'date-fns/parseISO';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import PlayerHistoryUpdateComponent from '@/entities/player-history/player-history-update.vue';
import PlayerHistoryClass from '@/entities/player-history/player-history-update.component';
import PlayerHistoryService from '@/entities/player-history/player-history.service';

import PlayerService from '@/entities/player/player.service';

import PlayerHistoryStatisticService from '@/entities/player-history-statistic/player-history-statistic.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('PlayerHistory Management Update Component', () => {
    let wrapper: Wrapper<PlayerHistoryClass>;
    let comp: PlayerHistoryClass;
    let playerHistoryServiceStub: SinonStubbedInstance<PlayerHistoryService>;

    beforeEach(() => {
      playerHistoryServiceStub = sinon.createStubInstance<PlayerHistoryService>(PlayerHistoryService);

      wrapper = shallowMount<PlayerHistoryClass>(PlayerHistoryUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          playerHistoryService: () => playerHistoryServiceStub,

          playerService: () => new PlayerService(),

          playerHistoryStatisticService: () => new PlayerHistoryStatisticService()
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
        comp.playerHistory = entity;
        playerHistoryServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(playerHistoryServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.playerHistory = entity;
        playerHistoryServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(playerHistoryServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
