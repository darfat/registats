/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import CompetitionNameUpdateComponent from '@/entities/competition-name/competition-name-update.vue';
import CompetitionNameClass from '@/entities/competition-name/competition-name-update.component';
import CompetitionNameService from '@/entities/competition-name/competition-name.service';

import CompetitionService from '@/entities/competition/competition.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('CompetitionName Management Update Component', () => {
    let wrapper: Wrapper<CompetitionNameClass>;
    let comp: CompetitionNameClass;
    let competitionNameServiceStub: SinonStubbedInstance<CompetitionNameService>;

    beforeEach(() => {
      competitionNameServiceStub = sinon.createStubInstance<CompetitionNameService>(CompetitionNameService);

      wrapper = shallowMount<CompetitionNameClass>(CompetitionNameUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          competitionNameService: () => competitionNameServiceStub,

          competitionService: () => new CompetitionService()
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.competitionName = entity;
        competitionNameServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(competitionNameServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.competitionName = entity;
        competitionNameServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(competitionNameServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
