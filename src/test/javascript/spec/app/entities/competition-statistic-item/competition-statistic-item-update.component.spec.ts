/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import CompetitionStatisticItemUpdateComponent from '@/entities/competition-statistic-item/competition-statistic-item-update.vue';
import CompetitionStatisticItemClass from '@/entities/competition-statistic-item/competition-statistic-item-update.component';
import CompetitionStatisticItemService from '@/entities/competition-statistic-item/competition-statistic-item.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('CompetitionStatisticItem Management Update Component', () => {
    let wrapper: Wrapper<CompetitionStatisticItemClass>;
    let comp: CompetitionStatisticItemClass;
    let competitionStatisticItemServiceStub: SinonStubbedInstance<CompetitionStatisticItemService>;

    beforeEach(() => {
      competitionStatisticItemServiceStub = sinon.createStubInstance<CompetitionStatisticItemService>(CompetitionStatisticItemService);

      wrapper = shallowMount<CompetitionStatisticItemClass>(CompetitionStatisticItemUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          competitionStatisticItemService: () => competitionStatisticItemServiceStub
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.competitionStatisticItem = entity;
        competitionStatisticItemServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(competitionStatisticItemServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.competitionStatisticItem = entity;
        competitionStatisticItemServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(competitionStatisticItemServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
