/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import CompetitionStandingUpdateComponent from '@/entities/competition-standing/competition-standing-update.vue';
import CompetitionStandingClass from '@/entities/competition-standing/competition-standing-update.component';
import CompetitionStandingService from '@/entities/competition-standing/competition-standing.service';

import CompetitionService from '@/entities/competition/competition.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('CompetitionStanding Management Update Component', () => {
    let wrapper: Wrapper<CompetitionStandingClass>;
    let comp: CompetitionStandingClass;
    let competitionStandingServiceStub: SinonStubbedInstance<CompetitionStandingService>;

    beforeEach(() => {
      competitionStandingServiceStub = sinon.createStubInstance<CompetitionStandingService>(CompetitionStandingService);

      wrapper = shallowMount<CompetitionStandingClass>(CompetitionStandingUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          competitionStandingService: () => competitionStandingServiceStub,

          competitionService: () => new CompetitionService()
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.competitionStanding = entity;
        competitionStandingServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(competitionStandingServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.competitionStanding = entity;
        competitionStandingServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(competitionStandingServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
