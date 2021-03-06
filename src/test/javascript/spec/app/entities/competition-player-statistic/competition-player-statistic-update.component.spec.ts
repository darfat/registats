/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import CompetitionPlayerStatisticUpdateComponent from '@/entities/competition-player-statistic/competition-player-statistic-update.vue';
import CompetitionPlayerStatisticClass from '@/entities/competition-player-statistic/competition-player-statistic-update.component';
import CompetitionPlayerStatisticService from '@/entities/competition-player-statistic/competition-player-statistic.service';

import CompetitionService from '@/entities/competition/competition.service';

import CompetitionStatisticItemService from '@/entities/competition-statistic-item/competition-statistic-item.service';

import PlayerService from '@/entities/player/player.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('CompetitionPlayerStatistic Management Update Component', () => {
    let wrapper: Wrapper<CompetitionPlayerStatisticClass>;
    let comp: CompetitionPlayerStatisticClass;
    let competitionPlayerStatisticServiceStub: SinonStubbedInstance<CompetitionPlayerStatisticService>;

    beforeEach(() => {
      competitionPlayerStatisticServiceStub = sinon.createStubInstance<CompetitionPlayerStatisticService>(
        CompetitionPlayerStatisticService
      );

      wrapper = shallowMount<CompetitionPlayerStatisticClass>(CompetitionPlayerStatisticUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          competitionPlayerStatisticService: () => competitionPlayerStatisticServiceStub,

          competitionService: () => new CompetitionService(),

          competitionStatisticItemService: () => new CompetitionStatisticItemService(),

          playerService: () => new PlayerService()
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.competitionPlayerStatistic = entity;
        competitionPlayerStatisticServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(competitionPlayerStatisticServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.competitionPlayerStatistic = entity;
        competitionPlayerStatisticServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(competitionPlayerStatisticServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
