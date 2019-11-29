/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import MatchStatisticItemUpdateComponent from '@/entities/match-statistic-item/match-statistic-item-update.vue';
import MatchStatisticItemClass from '@/entities/match-statistic-item/match-statistic-item-update.component';
import MatchStatisticItemService from '@/entities/match-statistic-item/match-statistic-item.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('MatchStatisticItem Management Update Component', () => {
    let wrapper: Wrapper<MatchStatisticItemClass>;
    let comp: MatchStatisticItemClass;
    let matchStatisticItemServiceStub: SinonStubbedInstance<MatchStatisticItemService>;

    beforeEach(() => {
      matchStatisticItemServiceStub = sinon.createStubInstance<MatchStatisticItemService>(MatchStatisticItemService);

      wrapper = shallowMount<MatchStatisticItemClass>(MatchStatisticItemUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          matchStatisticItemService: () => matchStatisticItemServiceStub
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.matchStatisticItem = entity;
        matchStatisticItemServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(matchStatisticItemServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.matchStatisticItem = entity;
        matchStatisticItemServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(matchStatisticItemServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
