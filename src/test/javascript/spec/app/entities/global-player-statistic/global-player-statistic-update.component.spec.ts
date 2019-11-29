/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import GlobalPlayerStatisticUpdateComponent from '@/entities/global-player-statistic/global-player-statistic-update.vue';
import GlobalPlayerStatisticClass from '@/entities/global-player-statistic/global-player-statistic-update.component';
import GlobalPlayerStatisticService from '@/entities/global-player-statistic/global-player-statistic.service';

import PlayerService from '@/entities/player/player.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('GlobalPlayerStatistic Management Update Component', () => {
    let wrapper: Wrapper<GlobalPlayerStatisticClass>;
    let comp: GlobalPlayerStatisticClass;
    let globalPlayerStatisticServiceStub: SinonStubbedInstance<GlobalPlayerStatisticService>;

    beforeEach(() => {
      globalPlayerStatisticServiceStub = sinon.createStubInstance<GlobalPlayerStatisticService>(GlobalPlayerStatisticService);

      wrapper = shallowMount<GlobalPlayerStatisticClass>(GlobalPlayerStatisticUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          globalPlayerStatisticService: () => globalPlayerStatisticServiceStub,

          playerService: () => new PlayerService()
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.globalPlayerStatistic = entity;
        globalPlayerStatisticServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(globalPlayerStatisticServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.globalPlayerStatistic = entity;
        globalPlayerStatisticServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(globalPlayerStatisticServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
