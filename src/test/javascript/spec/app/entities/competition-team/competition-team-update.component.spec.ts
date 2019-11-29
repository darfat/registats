/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import CompetitionTeamUpdateComponent from '@/entities/competition-team/competition-team-update.vue';
import CompetitionTeamClass from '@/entities/competition-team/competition-team-update.component';
import CompetitionTeamService from '@/entities/competition-team/competition-team.service';

import CompetitionService from '@/entities/competition/competition.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('CompetitionTeam Management Update Component', () => {
    let wrapper: Wrapper<CompetitionTeamClass>;
    let comp: CompetitionTeamClass;
    let competitionTeamServiceStub: SinonStubbedInstance<CompetitionTeamService>;

    beforeEach(() => {
      competitionTeamServiceStub = sinon.createStubInstance<CompetitionTeamService>(CompetitionTeamService);

      wrapper = shallowMount<CompetitionTeamClass>(CompetitionTeamUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          competitionTeamService: () => competitionTeamServiceStub,

          competitionService: () => new CompetitionService()
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.competitionTeam = entity;
        competitionTeamServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(competitionTeamServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.competitionTeam = entity;
        competitionTeamServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(competitionTeamServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
