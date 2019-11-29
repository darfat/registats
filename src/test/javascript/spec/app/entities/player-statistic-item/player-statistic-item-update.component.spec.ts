/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import PlayerStatisticItemUpdateComponent from '@/entities/player-statistic-item/player-statistic-item-update.vue';
import PlayerStatisticItemClass from '@/entities/player-statistic-item/player-statistic-item-update.component';
import PlayerStatisticItemService from '@/entities/player-statistic-item/player-statistic-item.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('PlayerStatisticItem Management Update Component', () => {
    let wrapper: Wrapper<PlayerStatisticItemClass>;
    let comp: PlayerStatisticItemClass;
    let playerStatisticItemServiceStub: SinonStubbedInstance<PlayerStatisticItemService>;

    beforeEach(() => {
      playerStatisticItemServiceStub = sinon.createStubInstance<PlayerStatisticItemService>(PlayerStatisticItemService);

      wrapper = shallowMount<PlayerStatisticItemClass>(PlayerStatisticItemUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          playerStatisticItemService: () => playerStatisticItemServiceStub
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.playerStatisticItem = entity;
        playerStatisticItemServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(playerStatisticItemServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.playerStatisticItem = entity;
        playerStatisticItemServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(playerStatisticItemServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
